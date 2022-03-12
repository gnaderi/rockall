package com.rockall.trade;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rockall.trade.model.Loan;
import com.rockall.trade.model.LoanAlert;
import com.rockall.trade.model.NormalizedTrade;
import com.rockall.trade.model.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class LoanAlertServiceImpl implements LoanAlertService {
    private static final Logger logger = LoggerFactory.getLogger(LoanAlertServiceImpl.class);
    private MarketDataFileManager marketDataFileManager;
    private ObjectMapper objectMapper;

    @Autowired
    public LoanAlertServiceImpl(MarketDataFileManager marketDataFileManager, ObjectMapper objectMapper) {
        this.marketDataFileManager = marketDataFileManager;
        this.objectMapper = objectMapper;
    }

    @Override
    public Map<String, NormalizedTrade> loadTrades(String fileName) {
        Map<String, NormalizedTrade> tradeMap = new HashMap<>();
        try {
            List<Trade> trades = objectMapper.readValue(Paths.get(fileName).toFile(), objectMapper.getTypeFactory().constructCollectionType(List.class, Trade.class));
            List<NormalizedTrade> normalizedTrades = marketDataFileManager.normalize(trades);
            tradeMap = normalizedTrades.parallelStream().collect(Collectors.toMap(NormalizedTrade::getIsin, Function.identity()));
        } catch (Exception ex) {
            logger.error("Unable to load list of normalized trades!!!. Exception details:{}", ex.getMessage());
            System.exit(1);
        }
        return tradeMap;
    }

    @Override
    public List<Loan> loadLoans(String fileName) {
        try {
            return objectMapper.readValue(Paths.get(fileName).toFile(), objectMapper.getTypeFactory().constructCollectionType(List.class, Loan.class));
        } catch (Exception ex) {
            logger.error("Unable to get Loans  info from requested json file{}. Exception details:{}", fileName, ex.getMessage());
            ex.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<LoanAlert> fidAlerts(List<Loan> loans, Map<String, NormalizedTrade> tradeMap) {
        //Filter out not secured loans
        return loans.parallelStream().map(l -> calEligibleCollateralValue(l, tradeMap)).filter(loanAlert -> loanAlert.getAmount() > loanAlert.getEligibleCollateral()).collect(Collectors.toList());
    }

    private LoanAlert calEligibleCollateralValue(Loan loan, Map<String, NormalizedTrade> tradeMap) {
        Double eligibleCollateral = loan.getPositions().parallelStream().map(p -> tradeMap.get(p.getId()).getPrice() * p.getQuantity()).mapToDouble(Double::doubleValue).sum();
        return new LoanAlert(loan.getId(), loan.getCreditPolicy(), loan.getAmount(), eligibleCollateral);
    }

    @Override
    public void exportAlerts(List<LoanAlert> alerts) {
        try {
            String directoryName = "loanAlerts";//will create a directory beside jar file or at the path that jar file get called
            File directory = new File(directoryName);
            if (!directory.exists()) {
                directory.mkdir();
            }
            File tempFile = File.createTempFile("Loans-alerts-", ".json", directory);
            objectMapper.writeValue(tempFile, alerts);
            System.exit(0);
        } catch (Exception exception) {
            logger.error("Unable to export list of normalized trades!!!. Exception details:{}", exception.getMessage());
            System.exit(1);
        }

    }
}

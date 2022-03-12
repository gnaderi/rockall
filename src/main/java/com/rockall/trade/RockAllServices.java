package com.rockall.trade;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rockall.trade.model.Loan;
import com.rockall.trade.model.LoanAlert;
import com.rockall.trade.model.NormalizedTrade;
import com.rockall.trade.model.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class RockAllServices implements CommandLineRunner {

    public static final String MARKET_DATA = "MarketData";
    public static final String LOAN_ALERT = "LoanAlert";
    private static final Logger logger = LoggerFactory.getLogger(RockAllServices.class);
    private MarketDataFileManager marketDataFileManager;
    private LoanAlertService loanAlertService;
    private ObjectMapper objectMapper;

    @Autowired
    public RockAllServices(MarketDataFileManager marketDataFileManager, LoanAlertService loanAlertService, ObjectMapper objectMapper) {
        this.marketDataFileManager = marketDataFileManager;
        this.loanAlertService = loanAlertService;
        this.objectMapper = objectMapper;
    }

    public static void main(String[] args) {
        SpringApplication.run(RockAllServices.class, args);
    }

    @Override
    public void run(String... args) throws IOException {
        if (args.length < 2) {
            logger.error("Invalid number of arguments!!!");
            System.exit(1);
        }
        logger.info("Running RockAllService[{}] ....", args[0]);
        if (args[0].equalsIgnoreCase(MARKET_DATA)) {
            String tradSource = args[1];
            List<Trade> trades = tradSource.contains("http") ? marketDataFileManager.fetchMarketDataByURL(tradSource) : marketDataFileManager.fetchMarketDataLocally(tradSource);
            List<NormalizedTrade> normalizedTrades = marketDataFileManager.normalize(trades);
            marketDataFileManager.export(normalizedTrades);
        } else if (args[0].equalsIgnoreCase(LOAN_ALERT)) {
            String marketDataFileName = args[1];
            String loansFileName = args[2];
            Map<String, NormalizedTrade> tradeMap = loanAlertService.loadTrades(marketDataFileName);
            List<Loan> loans = loanAlertService.loadLoans(loansFileName);
            List<LoanAlert> loanAlerts = loanAlertService.fidAlerts(loans, tradeMap);
            loanAlertService.exportAlerts(loanAlerts);
            logger.warn("List of risky loans:\n{}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(loanAlerts));

        } else {
            logger.error("Invalid service call! please pass one of the service options{MarketData,LoanAlert} as first argument in your command line.");
        }
    }
}
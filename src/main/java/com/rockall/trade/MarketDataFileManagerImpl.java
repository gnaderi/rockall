package com.rockall.trade;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rockall.trade.helper.RestApiHelper;
import com.rockall.trade.model.NormalizedTrade;
import com.rockall.trade.model.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarketDataFileManagerImpl implements MarketDataFileManager {
    private static final Logger logger = LoggerFactory.getLogger(MarketDataFileManagerImpl.class);
    private RestApiHelper helper;
    private ObjectMapper objectMapper;

    @Autowired
    public MarketDataFileManagerImpl(RestApiHelper helper, ObjectMapper objectMapper) {
        this.helper = helper;
        this.objectMapper = objectMapper;
    }

    public List<Trade> fetchMarketDataByURL(String url) {
        try {
            String fileContent = helper.executeServiceEndpoint(url, String.class);
            return objectMapper.readValue(fileContent, objectMapper.getTypeFactory().constructCollectionType(List.class, Trade.class));
        } catch (Exception ex) {
            logger.error("Unable to get Market Data info from requested url{}. Exception details:{}", url, ex.getMessage());
            ex.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Trade> fetchMarketDataLocally(String fileName) {
        try {
            return objectMapper.readValue(Paths.get(fileName).toFile(), objectMapper.getTypeFactory().constructCollectionType(List.class, Trade.class));
        } catch (Exception ex) {
            logger.error("Unable to get Market Data info from requested file{}. Exception details:{}", fileName, ex.getMessage());
            ex.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<NormalizedTrade> normalize(List<Trade> trades) {
        //TODO Currency conversion
        //TODO Convert all other currency to USD
        return trades.parallelStream().map(t -> new NormalizedTrade(t.getTicker(), t.getPrice(), t.getId())).collect(Collectors.toList());
    }

    @Override
    public void export(List<NormalizedTrade> normalizedTrades) {
        try {
            String directoryName = "normalizedTrades";//will create a directory beside jar file or at the path that jar file get called
            File directory = new File(directoryName);
            if (!directory.exists()) {
                directory.mkdir();
            }
            File tempFile = File.createTempFile("Normalized-Trades-", ".json", directory);
            objectMapper.writeValue(tempFile, normalizedTrades);
            System.exit(0);
        } catch (Exception exception) {
            logger.error("Unable to export list of normalized trades!!!. Exception details:{}", exception.getMessage());
            System.exit(1);
        }

    }
}

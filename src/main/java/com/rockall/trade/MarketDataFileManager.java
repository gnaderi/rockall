package com.rockall.trade;

import com.rockall.trade.model.NormalizedTrade;
import com.rockall.trade.model.Trade;

import java.io.IOException;
import java.util.List;

public interface MarketDataFileManager {
    /**
     * Fetch Market data information from a json file, full path((absolute path) of the file required
     * @param url http location of market data file
     * @return list of trades
     */
    List<Trade> fetchMarketDataByURL(String url);

    /**
     * Fetch Market data information from a json file, full path((absolute path) of the file required
     * @param fileName
     * @return list of trades
     */
    List<Trade> fetchMarketDataLocally(String fileName);

    /**
     * Normalize the trades and remove trailing zeros, remove unwanted fields and make them ready for load alert.
     * @param trades
     * @return list of normalized trades
     */
    List<NormalizedTrade> normalize(List<Trade> trades);

    /**
     * Export Normalized Trades n a json file under the folder 'normalizedTrades' beside the fatjar or from the path that the program executed.
     * @param normalizedTrades
     * @throws IOException
     */
    void export(List<NormalizedTrade> normalizedTrades) throws IOException;

}

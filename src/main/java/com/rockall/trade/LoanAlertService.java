package com.rockall.trade;

import com.rockall.trade.model.Loan;
import com.rockall.trade.model.LoanAlert;
import com.rockall.trade.model.NormalizedTrade;

import java.util.List;
import java.util.Map;

public interface LoanAlertService {
    Map<String, NormalizedTrade> loadTrades(String fileName);

    /**
     * Load loans information from a json file, full path((absolute path) of the file required
     * @param fileName
     * @return
     */
    List<Loan> loadLoans(String fileName);

    /**
     * Filter out list of not fully secured loans
     * @param loans
     * @param tradeMap
     * @return list of alerted loan that need to be investigated
     */
    List<LoanAlert> fidAlerts(List<Loan> loans, Map<String, NormalizedTrade> tradeMap);

    /**
     * Export alerted loans in a json file under the folder 'loanAlerts' beside the fatjar or from the path that the program executed.
     * @param alerts
     */
    void exportAlerts(List<LoanAlert> alerts);
}

package com.rockall.trade;

import java.util.Currency;

public interface CurrencyConvertor {
    /**
     * Convert any currency to USD
     *
     * @return result of source currency conversion value to USD
     */
    Double convert(Currency currency, Double amount);
}

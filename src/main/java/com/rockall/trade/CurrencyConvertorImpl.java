package com.rockall.trade;

import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Currency;

/**
 * It is nice to have option to have work with different currency.
 * It will convert any other currency to USD.
 */
@Service
public class CurrencyConvertorImpl implements CurrencyConvertor {
    @Override
    public Double convert(Currency currency, Double amount) {
        //TODO to be implemented!
        throw new NotImplementedException();
    }
}

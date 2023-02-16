package com.project.coindispenser.dao;

import java.util.Map;

import com.project.coindispenser.model.Coin;

public interface CoinDispenser {

    public Map<Coin, Integer> dispense(int value) throws CoinsNotAvailableException;

    public Map<Coin, Integer> getCoinAvailableCount();
    
}

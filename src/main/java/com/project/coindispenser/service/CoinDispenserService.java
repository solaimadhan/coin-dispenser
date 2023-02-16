package com.project.coindispenser.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.coindispenser.dao.CoinDispenser;
import com.project.coindispenser.dao.CoinsNotAvailableException;
import com.project.coindispenser.model.Coin;

@Component
public class CoinDispenserService {

    @Autowired
    CoinDispenser coinDispenser;
    
    public Map<String, Integer> dispenseCoins(int value) throws CoinsNotAvailableException {

        Map<Coin, Integer> map = coinDispenser.dispense(value);
        Map<String, Integer> resultMap = new LinkedHashMap<>();

        for (Coin coin : map.keySet()) {
            resultMap.put(coin.stringValue(), map.get(coin));
        }
        return resultMap;
    }

    public Map<String, Integer> getCoinAvailableCount() {
        Map<Coin, Integer> map = coinDispenser.getCoinAvailableCount();
        Map<String, Integer> resultMap = new HashMap<>();

        for (Coin coin : map.keySet()) {
            resultMap.put(coin.stringValue(), map.get(coin));
        };
        return resultMap;
    }

}
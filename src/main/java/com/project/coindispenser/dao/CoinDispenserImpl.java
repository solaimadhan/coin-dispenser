package com.project.coindispenser.dao;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.project.coindispenser.model.Coin;

import jakarta.annotation.PostConstruct;

@Component
public class CoinDispenserImpl implements CoinDispenser {

    private static Logger LOGGER = LoggerFactory.getLogger(CoinDispenserImpl.class);
    
    @Value("${max_coin_quantity:20}")
    private int MAX_QUANTITY;

    private Map<Coin, Integer> availableQtyMap = new Hashtable<>();

    @PostConstruct
    public void init() {
        for (Coin coin : Coin.values()) {
            availableQtyMap.put(coin, MAX_QUANTITY);
        }
    }

    public Map<Coin, Integer> getCoinAvailableCount() {
        return availableQtyMap;
    }

    public Map<Coin, Integer> dispense(int value) throws CoinsNotAvailableException {

        printAvailableMapAndValue();

        Map<Coin, Integer> requiredQtyMap = new LinkedHashMap<>();
        int total = value * 100;    // Converting dollar amount to cents
        int sum = 0;

        for (Coin coin : Coin.values()) {
            int available = availableQtyMap.get(coin).intValue();
            if (available == 0) continue;

            int required = (total-sum) / coin.value();
            if (required == 0) continue;

            requiredQtyMap.put(coin, available >= required ? required : available);
            sum += coin.value() * requiredQtyMap.get(coin);

            if (sum == total) {
                break;
            }
        }

        if (sum != total) {
            LOGGER.error("No coin change available for ${}", value);
            throw new CoinsNotAvailableException("No change available");
        }

        // Update the hash map with the new value
        for (Coin coin : requiredQtyMap.keySet()) {
            availableQtyMap.computeIfPresent(coin, 
                                (key, available) -> available - requiredQtyMap.get(coin));
        }
        return requiredQtyMap;
    }

    private void printAvailableMapAndValue() {
        if (LOGGER.isDebugEnabled()) {
            int maxAmount = 0;
            for (Coin coin : availableQtyMap.keySet()) {
                maxAmount += (coin.value() * availableQtyMap.get(coin));
            }
            LOGGER.debug("Available => {} ->  Amount = {}", availableQtyMap, (float) maxAmount/100);
        }
    }
}

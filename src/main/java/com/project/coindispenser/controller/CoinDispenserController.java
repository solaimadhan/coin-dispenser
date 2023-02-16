package com.project.coindispenser.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.coindispenser.dao.CoinsNotAvailableException;
import com.project.coindispenser.model.InputRequest;
import com.project.coindispenser.service.CoinDispenserService;

import jakarta.validation.Valid;

@RestController
@Validated
public class CoinDispenserController {

    private static Logger LOGGER = LoggerFactory.getLogger(CoinDispenserController.class);

    @Autowired
    CoinDispenserService dispenserService;
    
    @PostMapping("/dispense")
    public Map<String, Integer> dispenseCoins(@Valid @RequestBody InputRequest request)
    throws CoinsNotAvailableException {        
        Map<String, Integer> map = null;
        try {
            map = dispenserService.dispenseCoins(Integer.parseInt(request.getBillValue()));
        } catch (NumberFormatException nfe) {
            // This will not happen as the inputs are validated
        }
        LOGGER.debug("Bill : ${}, Result = {}", request.getBillValue(), map);
        return map;
    }

    @GetMapping("/balance")
    public Map<String, Integer> getCoinAvailableCount() {
        return dispenserService.getCoinAvailableCount();
    }
}

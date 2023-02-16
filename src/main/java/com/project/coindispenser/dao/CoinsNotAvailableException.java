package com.project.coindispenser.dao;

public class CoinsNotAvailableException extends Exception {

    public CoinsNotAvailableException() {
        super();
    }

    public CoinsNotAvailableException(String message) {
        super(message);
    }
    
}

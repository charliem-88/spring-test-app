package com.example.demo.exceptions;

public class KrakenFetchException extends RuntimeException{
    public KrakenFetchException(String endpoint) {
        this(endpoint, null);
    }

    public KrakenFetchException(String endpoint, Throwable cause) {
        super("Failed to fetch from endpoint: " + endpoint, cause);
    }
}

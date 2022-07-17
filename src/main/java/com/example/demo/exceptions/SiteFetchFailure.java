package com.example.demo.exceptions;

public class SiteFetchFailure extends RuntimeException {

    public SiteFetchFailure(String siteId, Throwable rootCause) {
        super("Failed to fetch site with id: " + siteId, rootCause);
    }
}

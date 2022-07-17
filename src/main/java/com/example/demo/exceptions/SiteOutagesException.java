package com.example.demo.exceptions;

public class SiteOutagesException extends RuntimeException{

    public SiteOutagesException(String siteId, Throwable rootCause) {
        super("Failed to post outages for site with id: " + siteId, rootCause);
    }
}

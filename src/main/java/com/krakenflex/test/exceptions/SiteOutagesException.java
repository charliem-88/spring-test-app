package com.krakenflex.test.exceptions;

public class SiteOutagesException extends RuntimeException{

    public SiteOutagesException(String siteId, Throwable rootCause) {
        super("Failed to post outages for site with id: " + siteId, rootCause);
    }
}

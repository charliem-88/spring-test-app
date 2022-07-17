package com.example.demo.exceptions;

public class OutageFetchFailure extends RuntimeException {

    public OutageFetchFailure(Throwable rootCause) {
        super("Failed to fetch outages", rootCause);
    }
}

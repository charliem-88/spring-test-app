package com.krakenflex.test.service;

import com.krakenflex.test.model.Outage;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Handles KrakenFlex test service's "outages" endpoint.
 */
@Service
public class OutageService {

    private final KrakenFlexTestService krakenService;

    @Autowired
    public OutageService(KrakenFlexTestService krakenService) {
        this.krakenService = krakenService;
    }

    public List<Outage> getOutages() {
        return krakenService.fetchEndpoint("outages", new TypeReference<List<Outage>>() {});
    }
}

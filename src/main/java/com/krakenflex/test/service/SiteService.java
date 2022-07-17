package com.krakenflex.test.service;

import com.krakenflex.test.model.SiteInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Handles KrakenFlex test service's "site-info" endpoint.
 */
@Service
public class SiteService {

    private final KrakenFlexTestService krakenService;

    @Autowired
    public SiteService(KrakenFlexTestService krakenService) {
        this.krakenService = krakenService;
    }

    public SiteInfo getSiteInfo(String siteId) {
        return krakenService.fetchEndpoint("site-info/" + siteId,  new TypeReference<SiteInfo>() {});
    }
}

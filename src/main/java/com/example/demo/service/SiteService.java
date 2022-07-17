package com.example.demo.service;

import com.example.demo.model.SiteInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

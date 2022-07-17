package com.example.demo.service;

import com.example.demo.exceptions.SiteFetchFailure;
import com.example.demo.model.SiteInfo;
import com.example.demo.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

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

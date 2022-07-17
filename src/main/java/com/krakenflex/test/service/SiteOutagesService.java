package com.krakenflex.test.service;

import com.krakenflex.test.exceptions.SiteOutagesException;
import com.krakenflex.test.model.Device;
import com.krakenflex.test.model.Outage;
import com.krakenflex.test.model.EnhancedOutage;
import com.krakenflex.test.model.SiteInfo;
import com.krakenflex.test.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Handles KrakenFlex test service's "site-outages" endpoint.
 */
@Service
public class SiteOutagesService {
    public static final String SITE_OUTAGES = "site-outages/";
    private final OutageService outageService;
    private final SiteService siteService;

    private final KrakenFlexTestService krakenService;

    @Autowired
    public SiteOutagesService(OutageService outageService, SiteService siteService, KrakenFlexTestService krakenService) {
        this.outageService = outageService;
        this.siteService = siteService;
        this.krakenService = krakenService;
    }

    public void postOutagesForSite(String siteId, ZonedDateTime newerThan) {
        try {
            List<EnhancedOutage> outagesForSite = getOutagesForSite(siteId, newerThan);

            String jsonAsString = JsonUtil.MAPPER.writeValueAsString(outagesForSite);
            String endpoint = SITE_OUTAGES + siteId;
            krakenService.postEndpoint(endpoint, jsonAsString);
        } catch (JsonProcessingException e) {
            throw new SiteOutagesException(siteId, e);
        }
    }

    public List<EnhancedOutage> getOutagesForSite(String siteId, ZonedDateTime newerThan) {
        List<Outage> outages = outageService.getOutages();
        SiteInfo siteInfo = siteService.getSiteInfo(siteId);

        Map<String, Device> siteDevices = siteInfo.devices()
                .stream()
                .collect(Collectors.toMap(Device::id, device -> device));

        return outages.stream()
                .filter(outage -> (
                        outage.begin().isEqual(newerThan) ||
                        outage.begin().isAfter(newerThan)) &&
                        siteDevices.containsKey(outage.id()))
                .map(outage -> new EnhancedOutage(
                        outage.id(),
                        siteDevices.get(outage.id()).name(),
                        outage.begin(),
                        outage.end()))
                .collect(Collectors.toList());
    }
}

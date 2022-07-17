package com.krakenflex.test;

import com.krakenflex.test.model.EnhancedOutage;
import com.krakenflex.test.model.Outage;
import com.krakenflex.test.model.SiteInfo;
import com.krakenflex.test.service.OutageService;
import com.krakenflex.test.service.SiteOutagesService;
import com.krakenflex.test.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Exposes a simple RESTey API giving access to all services.
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    public static final ZonedDateTime OUTAGE_DATETIME_CUTOFF = ZonedDateTime.parse("2022-01-01T00:00:00.000Z");
    private final OutageService outageService;
    private final SiteService siteService;
    private final SiteOutagesService siteOutagesService;

    @Autowired
    public ApiController(OutageService outageService, SiteService siteService, SiteOutagesService siteOutagesService) {
        this.outageService = outageService;
        this.siteService = siteService;
        this.siteOutagesService = siteOutagesService;
    }

    @RequestMapping(value = "/outages", method = RequestMethod.GET)
    public List<Outage> getOutages() {
        return outageService.getOutages();
    }

    @RequestMapping(value = "/site-info/{id}", method = RequestMethod.GET)
    public SiteInfo getSiteInfo(@PathVariable("id") String siteId) {
        return siteService.getSiteInfo(siteId);
    }

    @RequestMapping(value = "/site-outages/{id}", method = RequestMethod.GET)
    public List<EnhancedOutage> getSiteOutages(@PathVariable("id") String siteId) {
        return siteOutagesService.getOutagesForSite(siteId, OUTAGE_DATETIME_CUTOFF);
    }

    @RequestMapping(value = "/upload-outages/{id}", method = RequestMethod.GET)
    public void uploadOutagesForSite(@PathVariable("id") String siteId) {
        siteOutagesService.postOutagesForSite(siteId, OUTAGE_DATETIME_CUTOFF);
    }
}

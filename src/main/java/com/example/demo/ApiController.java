package com.example.demo;

import com.example.demo.model.EnhancedOutage;
import com.example.demo.model.Outage;
import com.example.demo.model.SiteInfo;
import com.example.demo.service.OutageService;
import com.example.demo.service.SiteOutagesService;
import com.example.demo.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    public static final ZonedDateTime DATE = ZonedDateTime.parse("2022-01-01T00:00:00.000Z");
    private OutageService outageService;
    private SiteService siteService;
    private SiteOutagesService siteOutagesService;

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
        return siteOutagesService.getOutagesForSite(siteId, DATE);
    }

    @RequestMapping(value = "/upload-outages/{id}", method = RequestMethod.GET)
    public void uploadOutagesForSite(@PathVariable("id") String siteId) {
        siteOutagesService.postOutagesForSite(siteId, DATE);
    }
}

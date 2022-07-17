package com.krakenflex.test.service;

import com.krakenflex.test.model.EnhancedOutage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;


@ExtendWith(SpringExtension.class)
@SpringBootTest()
class SiteOutagesServiceTest {

    public static final String NORWICH_PEAR_TREE = "norwich-pear-tree";
    public static final ZonedDateTime DATE = ZonedDateTime.parse("2022-01-01T00:00:00.000Z");
    @Autowired
    SiteOutagesService testSubject;

    @Test
    void siteOutagesService_willReturnListOfOutages_afterGivenDate_forSiteId() {
        List<EnhancedOutage> actual = testSubject.getOutagesForSite(NORWICH_PEAR_TREE, DATE);

        assertThat(actual.size()).isGreaterThan(0);
    }

    @Test
    void siteOutagesService_doesNotReturnOutagesOlderThanGivenDate() {

        List<EnhancedOutage> actual = testSubject.getOutagesForSite(NORWICH_PEAR_TREE, DATE);

        assertFalse(actual.stream().anyMatch(v -> v.begin().isBefore(DATE)));

    }

    @Disabled("As endpoint returns 400 failure")
    @Test
    void postOutagesForSite_willNotThrowError_when() {
        testSubject.postOutagesForSite(NORWICH_PEAR_TREE, DATE);
    }
}
package com.example.demo.service;

import com.example.demo.model.EnhancedOutage;
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
//         when
        List<EnhancedOutage> actual = testSubject.getOutagesForSite(NORWICH_PEAR_TREE, DATE);

//        then
        assertThat(actual.size()).isGreaterThan(0);
    }

    @Test
    void siteOutagesService_doesNotReturnOutagesOlderThanGivenDate() {

        List<EnhancedOutage> actual = testSubject.getOutagesForSite(NORWICH_PEAR_TREE, DATE);

        assertFalse(actual.stream().anyMatch(v -> v.begin().isBefore(DATE)));

    }

    @Test
    void siteOutagesService_onlyReturnsOutagesForGivenSite() {
        List<EnhancedOutage> actual = testSubject.getOutagesForSite(NORWICH_PEAR_TREE, DATE);

        assertFalse(actual.stream().anyMatch(v -> !v.id().equals(NORWICH_PEAR_TREE)));
    }

    @Test
    void postOutagesForSite_willNotThrowError_when() {
        testSubject.postOutagesForSite(NORWICH_PEAR_TREE, DATE);
    }
}
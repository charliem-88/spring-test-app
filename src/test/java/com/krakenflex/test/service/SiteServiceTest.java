package com.krakenflex.test.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
class SiteServiceTest {


    @Autowired
    private SiteService testSubject;


    @Test
    void getSiteInfo_willReturn_InfoForSiteById() {
        var actual = testSubject.getSiteInfo("norwich-pear-tree");

        Assertions.assertThat(actual.name()).isEqualTo("Norwich Pear Tree");
        Assertions.assertThat(actual.id()).isEqualTo("norwich-pear-tree");

        Assertions.assertThat(actual.devices().size()).isGreaterThan(0);
    }

}
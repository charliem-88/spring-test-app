package com.example.demo.service;

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
        //when
        var actual = testSubject.getSiteInfo("norwich-pear-tree");

        // then
        assertThat(actual.name()).isEqualTo("Norwich Pear Tree");
        assertThat(actual.id()).isEqualTo("norwich-pear-tree");

        assertThat(actual.devices().size()).isGreaterThan(0);
    }

}
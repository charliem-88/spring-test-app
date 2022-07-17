package com.krakenflex.test.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
class OutageServiceTest {

    @Autowired
    private OutageService testSubject;

    @Test
    void getOutages_willReturn_listOfOutages() {
        var actual = testSubject.getOutages();

        assertThat(actual.size()).isGreaterThan(0);
    }
}
package me.gijung.HAP.service;

import me.gijung.HAP.exception.AppException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class SearchServiceTest {

    @Autowired
    SearchService searchService;

    @Test
    void 지역_검색_성공 () {

        String query = "부천시 맛집";

        assertThat(searchService.search(query)).isNotNull();
    }


}

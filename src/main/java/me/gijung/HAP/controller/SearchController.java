package me.gijung.HAP.controller;

import lombok.RequiredArgsConstructor;
import me.gijung.HAP.dto.SearchDto;
import me.gijung.HAP.service.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/place")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("")
    public SearchDto search(@RequestParam String query) {
        return searchService.search(query);
    }

}

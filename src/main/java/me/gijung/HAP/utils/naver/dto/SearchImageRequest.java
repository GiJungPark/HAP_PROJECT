package me.gijung.HAP.utils.naver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchImageRequest {

    private String query = "";      // 검색을 원하는 문자열, UTF-8로 인코딩

    private int display = 1;        // 한 번에 표시할 검색 결과 개수(기본값: 10, 최댓값: 100)

    private int start = 1;          // 검색 시작 위치(기본값: 1, 최댓값: 1000)

    private String sort = "sim";    // 검색 결과 정렬 방법 - sim: 정확도순으로 내림차순 정렬, data: 날짜순으로 내림차순 정렬

    private String filter = "all";  // 크기별 검색 결과 필터 - all: 모든 이미지(기본값), large, medium, small

    public MultiValueMap<String, String> toMultiValueMap() {
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("query", query);
        map.add("display", String.valueOf(display));
        map.add("start", String.valueOf(start));
        map.add("sort", sort);
        map.add("filter", filter);

        return map;
    }
}

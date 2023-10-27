package me.gijung.HAP.utils.naver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchLocalRequest {

    private String query = "";  // 검색을 원하는 문자열, UTF-8로 인코딩
    private int display = 5;    // 한 번에 표시할 검색 결과 개수(기본값: 1, 최댓값: 5)
    private int start = 1;      // 검색 시작 위치(기본값: 1, 최댓값: 1)
    // random: 정확도순으로 내림차순 정렬(기본값)
    // comment: 업체 및 기관에 대한 카페, 블로그의 리뷰 개수순으로 내림차순 정렬
    private String sort = "random";


    public MultiValueMap<String, String> toMultiValueMap() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();

        map.add("query", query);
        map.add("display", String.valueOf(display));
        map.add("start", String.valueOf(start));
        map.add("sort", sort);

        return map;
    }
}


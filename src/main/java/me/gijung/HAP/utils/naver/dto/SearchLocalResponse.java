package me.gijung.HAP.utils.naver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchLocalResponse {

    // 지역 검색 출력 결과를 변수화
    private String lastBuildDate;   // 검색 결과를 생성한 시간
    private int total;              // 총 검색 결과 개수
    private int start;              // 검색 시작 위치
    private int display;            // 한 번에 표시할 검색 결과 개수
    // JSON 형식의 결괏값에서는 items 속성의 JSON 배열로 개별 검색 결과를 반환
    private List<SearchLocalItem> items;    // 개별 검색 결과

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchLocalItem {
        private String title;       // 업체, 기관의 이름
        private String link;        // 업체, 기관의 상세 정보 URL
        private String category;    // 업체, 기관의 분류 정보
        private String description; // 업체, 기관에 대한 설명
        private String telephone;   // 값을 반환하지 않는 요소. 하위 호환성을 유지하기 위해 있는 요소
        private String address;     // 업체, 기관명의 지번 주소
        private String roadAddress; // 업체, 기관명의 도로명 주소
        private int mapX;           // 업체, 기관이 위치한 장소의 x좌표 (KATECH 좌표계 기준)
        private int mapY;           // 업체, 기관이 위치한 장소의 y좌표 (KATECH 좌표계 기준)
    }
}

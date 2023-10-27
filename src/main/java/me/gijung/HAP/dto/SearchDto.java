package me.gijung.HAP.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto {

    private List<SearchListItem> items;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchListItem {
        private String title;                   // 음식명, 장소명
        private String address;                 // 주소
        private String readAddress;             // 도로명
        private String imageLink;               // 음식, 가게 이미지 주소
    }
}

package me.gijung.HAP.service;

import lombok.RequiredArgsConstructor;
import me.gijung.HAP.utils.naver.dto.SearchImageRequest;
import me.gijung.HAP.utils.naver.dto.SearchImageResponse;
import me.gijung.HAP.utils.naver.dto.SearchLocalRequest;
import me.gijung.HAP.utils.naver.dto.SearchLocalResponse;
import me.gijung.HAP.dto.SearchDto;
import me.gijung.HAP.exception.AppException;
import me.gijung.HAP.exception.ErrorCode;
import me.gijung.HAP.utils.naver.NaverUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final NaverUtil naverUtil;

    public SearchDto search(String query) {
        List<SearchLocalResponse.SearchLocalItem> searchLocalItems = searchLocal(query);

        List<SearchDto.SearchListItem> result = new ArrayList<>();
        for(int i = 0; i < searchLocalItems.size(); i++) {
            SearchDto.SearchListItem item = new SearchDto.SearchListItem();
            var localItem = searchLocalItems.get(i);
            var imageItem = searchImage(localItem);

            item.setTitle(localItem.getTitle());
            item.setAddress(localItem.getAddress());
            item.setReadAddress(localItem.getRoadAddress());
            item.setImageLink(imageItem.getLink());
            result.add(item);
        }
        SearchDto searchDto = new SearchDto(result);

        return searchDto;
    }

    private List<SearchLocalResponse.SearchLocalItem> searchLocal(String query) {

        SearchLocalRequest searchRequestLocal = new SearchLocalRequest();
        searchRequestLocal.setQuery(query);

        List<SearchLocalResponse.SearchLocalItem> items =  naverUtil.searchLocal(searchRequestLocal).getItems();
        if(items.size() < 1) throw new AppException(ErrorCode.NOT_FOUND_INFORMATION);

        return items;
    }

    private SearchImageResponse.SearchImageItem searchImage(SearchLocalResponse.SearchLocalItem item) {
        var imageQuery = item.getTitle().replaceAll("<[^>]*", "")
                + " " + item.getCategory().replaceAll("<[^>]*", "");

        SearchImageRequest searchImageRequest = new SearchImageRequest();
        searchImageRequest.setQuery(imageQuery);

        SearchImageResponse searchImageResponse = naverUtil.imageSearch(searchImageRequest);

        if(searchImageResponse.getTotal() < 1) throw new AppException(ErrorCode.NOT_FOUND_INFORMATION);

        return searchImageResponse.getItems().stream().findFirst().get();
    }
}

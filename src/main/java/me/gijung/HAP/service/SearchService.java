package me.gijung.HAP.service;

import lombok.RequiredArgsConstructor;
import me.gijung.HAP.utils.naver.dto.SearchLocalRequest;
import me.gijung.HAP.utils.naver.dto.SearchLocalResponse;
import me.gijung.HAP.dto.SearchDto;
import me.gijung.HAP.exception.AppException;
import me.gijung.HAP.exception.ErrorCode;
import me.gijung.HAP.utils.naver.NaverUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final NaverUtil naverUtil;

    public SearchDto.SearchListItem search(String query) {

        List<SearchLocalResponse.SearchLocalItem> searchLocalItems = searchLocal(query);



        return new SearchDto.SearchListItem();
    }

    public List<SearchLocalResponse.SearchLocalItem> searchLocal(String query) {

        SearchLocalRequest searchRequestLocal = new SearchLocalRequest();
        searchRequestLocal.setQuery(query);

        List<SearchLocalResponse.SearchLocalItem> items =  naverUtil.searchLocal(searchRequestLocal).getItems();
        if(items.size() < 1) new AppException(ErrorCode.NOT_FOUND_INFORMATION);

        return items;
    }

}

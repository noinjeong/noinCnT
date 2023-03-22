package com.example.march21st.controller;

import com.example.march21st.Service.MiningService;
import com.example.march21st.Service.NewsService;
import com.example.march21st.dto.NewsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController // @Controller와 @ResponseBody가 합쳐진 어노테이션으로 주 용도는 Json 형태로 객체 데이터를 반환하는 것
@RequiredArgsConstructor //Lombok의 기능으로 final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성
public class NewsController {
    private final NewsService newsService;
    private final MiningService miningService;
    List<NewsDto> newsdata;
    @GetMapping("/news-api")
    public List<NewsDto> NewsController() {
        newsdata = newsService.getNewsData();
        return newsdata;
    }
    @GetMapping("/news-api/mining")
    public Map<String, Integer> TextMining() {
        return miningService.getMiningData(newsdata);
    }
}

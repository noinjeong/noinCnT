package com.ssafy.ssafycntnewsservice.controller;

import com.ssafy.ssafycntnewsservice.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

//@Controller
//public class NewsController {
////    @GetMapping("news-api")
////    public GetNews(){
//
////    }
@RestController // @Controller와 @ResponseBody가 합쳐진 어노테이션으로 주 용도는 Json 형태로 객체 데이터를 반환하는 것
@RequiredArgsConstructor //Lombok의 기능으로 final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성
public class NewsController {
    private final NewsService newsService;
    @GetMapping("/news-api")
    public Map<String, Object> NewsController() {
        return newsService.getNewsData();
    }
}

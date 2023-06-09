package com.ssafy.ssafycntnewsservice.service;

import com.ssafy.ssafycntnewsservice.dto.NewsDto;

import java.util.List;

public interface NewsService {
    List<NewsDto> getNewsData(String country, String item, String startDate, String endDate);
}

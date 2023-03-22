package com.ssafy.ssafycntnewsservice.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl implements NewsService{
    @Override
    public Map<String, Object> getNewsData() {
        Map<String, Object> firstData = new HashMap<>();

        firstData.put("label1", "check1");
        firstData.put("label2", "check2");
        firstData.put("label3", "check3");

        return firstData;
    }
//    public static void crowling() {
//        String search_word = "무역";
//        String start_date = "2023.03.16";
//        String end_date = "2022.02.17";
//        String[] start_idx = {"1", "11", "21", "31", "41", "51", "61", "71", "81", "91"};
//        String URL = "https://search.naver.com/search.naver?where=news&sm=tab_pge&query=" + search_word + "&sort=0&photo=0&field=0&pd=3&ds=" + start_date + "&de=" + end_date + "&cluster_rank=33&mynews=0&office_type=0&office_section_code=0&news_office_checked=&nso=so:r,p:from20230101to20230201,a:all&start=";
//        Document doc;
//        for (int j = 0; j < 10; j++) {
//            try {
//                doc = Jsoup.connect(URL + start_idx[j]).get();
//                Elements news_press = doc.select(".info_group .press");  // 신문사
//                Elements news_info = doc.select(".info_group >span");    // 시간
//                Elements news_title = doc.select(".news_tit");           // 제목
//                Elements news_content = doc.select(".dsc_wrap a");        // 내용
//                int n = 0;
//                for (int i = 0; i < news_press.size(); i++) {
//                    System.out.println(news_press.get(i).text());
//
//                    if (news_info.get(i + n).text().length() < 11) {
//                        n++;
//                    }
//                    System.out.println(news_info.get(i + n).text());
//                    System.out.println(news_title.get(i).text());
//                    System.out.println(news_content.get(i).text());
//                    System.out.println(news_title.get(i).attr("abs:href"));
//
//                }
//
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//    }
}

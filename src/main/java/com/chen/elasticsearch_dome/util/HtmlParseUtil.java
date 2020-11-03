package com.chen.elasticsearch_dome.util;

import com.chen.elasticsearch_dome.entity.Info;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author CHEN
 * @date 2020/10/31  19:49
 * 爬取数据的工具类
 */
public class HtmlParseUtil {
    public static void main(String[] args) throws Exception {
        new HtmlParseUtil().parseCSDN("反射").forEach(System.out::println);
    }
    public List<Info> parseCSDN(String keyWord)throws  Exception{

        String url = "https://so.csdn.net/so/search/s.do?t=all&s=&tm=&v=&l=&u=&q="+keyWord;
        Document document = Jsoup.parse(new URL(url),3000*60);
        Elements elements = document.getElementsByClass("search-list J_search");

        List<Info> list=new ArrayList<>();
        for (Element element : elements) {
            String href=element.getElementsByTag("a").attr("href");
            String title=element.getElementsByClass("limit_width").text();
            String detail=element.getElementsByClass("search-detail").text();
            Info info=new Info();
            info.setDetail(detail);
            info.setTitle(title);
            info.setUrl(href);
            list.add(info);
        }
        return list;
    }
}

package com.chen.elasticsearch_dome.service;

import com.alibaba.fastjson.JSON;
import com.chen.elasticsearch_dome.entity.Info;
import com.chen.elasticsearch_dome.util.HtmlParseUtil;
import com.chen.elasticsearch_dome.util.PageResult;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * @author CHEN
 * @date 2020/10/31  20:41
 */
@Service
public class InfoService {
    @Autowired
    RestHighLevelClient restHighLevelClient;

    //放入es库中
    public Boolean parseInfo(String keyWord)throws  Exception{
        List<Info> list=new HtmlParseUtil().parseCSDN(keyWord);
        BulkRequest bulkRequest=new BulkRequest();
        bulkRequest.timeout("2m");
        for(int i=0;i<list.size();i++){
            bulkRequest.add(new IndexRequest("csdn_info")
            .source(JSON.toJSONString(list.get(i)), XContentType.JSON));
        }
        BulkResponse bulkItemResponses=restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !bulkItemResponses.hasFailures();
    }
    //分页查询
    public  PageResult<Info> searchPAge(String key,int page,int size) throws IOException {
        if(page<=1)page=1;
        //条件查询
        SearchRequest searchRequest=new SearchRequest("csdn_info");
        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();
        //分页
        sourceBuilder.from(page);
        sourceBuilder.size(size);
        //高亮
        HighlightBuilder highlightBuilder=new HighlightBuilder();
        highlightBuilder.requireFieldMatch(true);//多个高亮显示
        highlightBuilder.field("title");
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);
        //精准匹配
        MatchQueryBuilder matchQueryBuilder= QueryBuilders.matchQuery("title",key);
        sourceBuilder.query(matchQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        searchRequest.source(sourceBuilder);
        SearchResponse response=restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

        List<Info> list=new LinkedList<>();

        for(SearchHit documentFields:response.getHits().getHits()){
            String title= (String) documentFields.getSourceAsMap().get("title");
            String url= (String) documentFields.getSourceAsMap().get("url");
            String detail= (String) documentFields.getSourceAsMap().get("detail");
            Info info=new Info();
            info.setDetail(detail);
            info.setTitle(title);
            info.setUrl(url);

            //解析高亮字段
            Map<String, HighlightField> highlightField=documentFields.getHighlightFields();
            HighlightField newTitle=highlightField.get("title");
            if(title!=null){
                Text[] fragment=newTitle.fragments();
                String tem="";
                for(Text text:fragment){
                    tem+=text;
                }
                info.setTitleLight(tem);
            }

            list.add(info);
        }
        //分页结果解析
        long total =list.size();
        Long totalPages = total % size == 0 ? total / size : total / size + 1;
        //解析聚合结果
        return new PageResult<>(total, totalPages,list);
    }

}

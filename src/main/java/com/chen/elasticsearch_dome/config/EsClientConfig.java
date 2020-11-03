package com.chen.elasticsearch_dome.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author CHEN
 * @date 2020/10/31  18:37
 * 原生elasticsearch配置类
 */
@Configuration
@EnableElasticsearchRepositories
public class EsClientConfig {

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("118.178.125.139", 9200, "http")));
    }
}

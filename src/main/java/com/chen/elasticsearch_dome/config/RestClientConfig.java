//package com.chen.elasticsearch_dome.config;
//
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.client.ClientConfiguration;
//import org.springframework.data.elasticsearch.client.RestClients;
//import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
//import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
//
///**
// * @author CHEN
// * @date 2020/10/22  18:24
// * 使用spring data
// */
//@Configuration
//@EnableElasticsearchRepositories
//public class RestClientConfig extends AbstractElasticsearchConfiguration {
//
//    @Override
//    @Bean
//    public RestHighLevelClient elasticsearchClient() {
//        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
//                .connectedTo("118.178.125.139:9200")
//                .build();
//        return RestClients.create(clientConfiguration).rest();
//    }
//}
//
//

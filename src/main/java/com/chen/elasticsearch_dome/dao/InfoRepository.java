package com.chen.elasticsearch_dome.dao;


import com.chen.elasticsearch_dome.entity.Info;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author CHEN
        * @date 2020/10/23  21:39
        */
public interface InfoRepository extends ElasticsearchRepository<Info,String> {
    Page<Info> findByTitle(String title, Pageable pageable);
}

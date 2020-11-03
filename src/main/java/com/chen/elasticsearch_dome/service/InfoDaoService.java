//package com.chen.elasticsearch_dome.service;
//
//import com.alibaba.fastjson.JSON;
//import com.chen.elasticsearch_dome.dao.InfoRepository;
//import com.chen.elasticsearch_dome.entity.Info;
//import com.chen.elasticsearch_dome.util.HtmlParseUtil;
//import com.chen.elasticsearch_dome.util.PageResult;
//
//import com.chen.elasticsearch_dome.util.SearchRequest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//
//import javax.annotation.Resource;
//
//
///**
// * @author CHEN
// * @date 2020/10/31  20:41
// * 使用spring data
// */
//@Service
//public class InfoDaoService {
//    @Resource
//    private InfoRepository infoRepository;
//
//
//    public PageResult<Info> search(SearchRequest request) {
//        int page = request.getPage() - 1;
//        int size = request.getSize();
//        //查询
//        Pageable pageable= PageRequest.of(page, size);
//        Page<Info> page1= infoRepository.findByTitle("%"+request.getKey()+"%",pageable);
//        //分页结果解析
//        long total =page1.getTotalElements();
//        Long totalPages = total % size == 0 ? total / size : total / size + 1;
//        //解析聚合结果
//        return new PageResult<>(total, totalPages,page1.getContent());
//    }
//
//}

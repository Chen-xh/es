package com.chen.elasticsearch_dome.controller;

import com.chen.elasticsearch_dome.entity.Info;
import com.chen.elasticsearch_dome.service.InfoService;
import com.chen.elasticsearch_dome.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * @author CHEN
 * @date 2020/10/31  20:59
 */
@RestController
@CrossOrigin(value = "*", maxAge = 3600, allowCredentials = "true")
public class InfoController {
    @Autowired
    InfoService infoService;

    @GetMapping("/parse/{key}")
    public Boolean  parse(@PathVariable String key) throws Exception {
        return infoService.parseInfo(key);
    }
    @GetMapping("/search")
    public ResponseEntity<PageResult<Info>> parse(String key, int page) throws Exception {
        int size=10;
        return ResponseEntity.ok(infoService.searchPAge(key,page,size));
    }
}

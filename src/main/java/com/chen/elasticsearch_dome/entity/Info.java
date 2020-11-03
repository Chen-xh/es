package com.chen.elasticsearch_dome.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author CHEN
 * @date 2020/10/31  20:16
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Info {
    private String url;
    @Id
    private String title;
//    高亮标题
    private String titleLight;
    private String detail;
}

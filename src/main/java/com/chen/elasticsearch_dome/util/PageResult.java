package com.chen.elasticsearch_dome.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author CHEN
 * @date 2020/10/25  14:44
 * 结果实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageResult<T> {
    private Long total;// 总条数
    private Long totalPage;// 总页数
    private List<T> items;// 当前页数据
    public PageResult(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }
}

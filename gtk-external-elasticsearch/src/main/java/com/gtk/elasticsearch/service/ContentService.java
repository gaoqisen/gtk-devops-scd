package com.gtk.elasticsearch.service;

import com.gtk.elasticsearch.entity.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContentService {

    //保存Content实体
    void save (Content content);

    //基于title和content进行搜索，返回分页
    Page<Content> search(String title, String content, Pageable pageable);

    //基于content进行搜索，返回分页
    Page<Content> search(String content,Pageable pageable);

    //返回所有数据集合
    Page<Content> findAll(Pageable pageable);

}

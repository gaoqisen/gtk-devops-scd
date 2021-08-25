package com.gtk.elasticsearch.service.impl;

import com.gtk.elasticsearch.entity.Content;
import com.gtk.elasticsearch.repository.ContentRepository;
import com.gtk.elasticsearch.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

@Service("contentService")
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;

    @Override
    public void save(Content content) {
        elasticsearchTemplate.createIndex(content.getClass());
        contentRepository.save(content);
    }

    @Override
    public Page<Content> search(String title, String content, Pageable pageable) {
        return contentRepository.findByTitleLikeOrContentLike(title,content,pageable);
    }

    @Override
    public Page<Content> search(String content, Pageable pageable) {
        return contentRepository.findByContentLike(content,pageable);
    }

    @Override
    public Page<Content> findAll(Pageable pageable) {
        Page<Content> page =  contentRepository.findAll(pageable);
        return page;
    }
}

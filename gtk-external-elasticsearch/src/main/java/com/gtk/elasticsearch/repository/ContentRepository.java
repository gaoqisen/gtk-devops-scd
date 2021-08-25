package com.gtk.elasticsearch.repository;

import com.gtk.elasticsearch.entity.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author jasongao
 */
public interface ContentRepository extends ElasticsearchRepository<Content,Long> {

        Page<Content> findByTitleLikeOrContentLike(String title, String content, Pageable pageable);
        Page<Content> findByContentLike(String content, Pageable pageable);

}
package com.gtk.elasticsearch.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "centent",type = "centent",shards = 1, replicas = 0)
public class Content {

    @Id
    private long id;
    private String title;
    private String content;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Content(long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
    public Content() {};
}

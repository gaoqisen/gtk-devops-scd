package com.gtk.elasticsearch.controller;

import com.gtk.elasticsearch.entity.Content;
import com.gtk.elasticsearch.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/save")
    public String save(){
        List<Content> poems = new ArrayList<>();
        poems.add(new Content(4,"123","今天是一个好日子"));
        poems.add(new Content(5,"456","春天快要到了"));
        poems.add(new Content(6,"789","希望能快乐的度过"));

        for(int i=0;i<poems.size();i++){
            contentService.save(poems.get(i));
        }
        return "ok";
    }

    @RequestMapping("/findAll")
    public Object findAll(
            @RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
            @RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize) {
        Pageable pageable = new PageRequest(pageIndex,pageSize);
        Page<Content> poems = contentService.findAll(pageable);
        List<Content> poems1 = poems.getContent();
        return poems1;
    }

    @RequestMapping("/findByContent")
    public Object findByContent(@RequestParam(value="content",required=false,defaultValue="") String content,
                         @RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
                         @RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize
                      ) {
        Pageable pageable = new PageRequest(pageIndex,pageSize);
        Page<Content> poems = contentService.search(content,pageable);
        List<Content> list = poems.getContent();
        return list;
    }

    @RequestMapping("/search")
    public String search(String content,String title, @RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
                         @RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize) {
        Pageable pageable = new PageRequest(pageIndex,pageSize);
        Page<Content> poems = contentService.search(title, content,pageable);
        List<Content> list = poems.getContent();
        return "ok";

    }

}

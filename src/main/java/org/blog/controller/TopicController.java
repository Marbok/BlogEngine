package org.blog.controller;

import org.blog.dao.TopicDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TopicController {

    @Autowired
    private TopicDao topicDao;

    @GetMapping("/topics")
    public Map<Long, String> getTopics() {
        Map<Long, String> topicNames = new HashMap<>();
        topicDao.findAll()
                .forEach(article -> topicNames.put(article.getId(), article.getName()));
        return topicNames;
    }
}

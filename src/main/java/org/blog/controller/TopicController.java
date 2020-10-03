package org.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @GetMapping("/getTopics")
    public String getTopics() throws JsonProcessingException {
        Map<Long, String> topicNames = new HashMap<>();
        topicDao.findAll()
                .forEach(article -> topicNames.put(article.getId(), article.getName()));
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(topicNames);
    }
}

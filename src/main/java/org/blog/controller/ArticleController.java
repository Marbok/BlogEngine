package org.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.blog.dao.ArticleDao;
import org.blog.dao.TopicDao;
import org.blog.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private ArticleDao articleDao;
    private TopicDao topicDao;

    @Autowired
    public ArticleController(ArticleDao articleDao, TopicDao topicDao) {
        this.articleDao = articleDao;
        this.topicDao = topicDao;
    }

    @GetMapping("/{topicId}")
    public String getArticles(@PathVariable Long topicId) throws JsonProcessingException {
        Map<Long, String> articleNames = topicDao.findById(topicId)
                .map(topic -> articleDao.findAllByTopic(topic))
                .map(articles -> articles.stream()
                        .collect(Collectors.toMap(Article::getId, Article::getTitle)))
                .orElse(new HashMap<>());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(articleNames);
    }
}

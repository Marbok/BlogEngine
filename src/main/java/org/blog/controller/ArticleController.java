package org.blog.controller;

import org.blog.controller.dto.ArticleResponse;
import org.blog.controller.mapper.ArticleModelToResponseMapper;
import org.blog.dao.ArticleDao;
import org.blog.dao.TopicDao;
import org.blog.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ArticleController {

    private ArticleDao articleDao;
    private TopicDao topicDao;
    private ArticleModelToResponseMapper articleMapper;

    @Autowired
    public ArticleController(ArticleDao articleDao, TopicDao topicDao, ArticleModelToResponseMapper articleMapper) {
        this.articleDao = articleDao;
        this.topicDao = topicDao;
        this.articleMapper = articleMapper;
    }

    @GetMapping("/articles/{topicId}")
    public Map<Long, String> getArticles(@PathVariable Long topicId) {
        return topicDao.findById(topicId)
                .map(topic -> articleDao.findAllByTopic(topic))
                .map(articles -> articles.stream()
                        .collect(Collectors.toMap(Article::getId, Article::getTitle)))
                .orElse(new HashMap<>());
    }

    @GetMapping("/article/{articleId}")
    public ArticleResponse getArticleById(@PathVariable Long articleId) {
        Article article = articleDao.findById(articleId)
                .orElse(new Article()
                        .setTitle("Article not founded!!!")
                        .setDescription("")
                        .setContent(""));
        return articleMapper.modelToResponse(article);
    }
}

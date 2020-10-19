package org.blog.controller;

import org.blog.controller.mapper.ArticleMapper;
import org.blog.controller.dto.article.ArticleResponse;
import org.blog.controller.dto.article.ArticlesResponse;
import org.blog.exceptions.NotFoundException;
import org.blog.model.Article;
import org.blog.services.api.ArticleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
//@CrossOrigin
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleMapper articleMapper;

    public ArticleController(ArticleService articleService, ArticleMapper articleMapper) {
        this.articleService = articleService;
        this.articleMapper = articleMapper;
    }

    @GetMapping("/articles/{topicId}")
    public List<ArticlesResponse> getArticles(@PathVariable Long topicId) {
        Collection<Article> articles = articleService.findArticlesByTopic(topicId);
        if (articles.isEmpty()) {
            throw new NotFoundException();
        }
        return articles.stream()
                .map(articleMapper::modelToArticlesResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/article/{articleId}")
    public ArticleResponse getArticleById(@PathVariable Long articleId) {
        return articleService.findArticleById(articleId)
                .map(articleMapper::modelToArticleResponse)
                .orElseThrow(NotFoundException::new);
    }
}

package org.blog.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.blog.controller.dto.article.ArticlesResponse;
import org.blog.controller.mapper.ArticleMapper;
import org.blog.exceptions.NotFoundException;
import org.blog.model.Article;
import org.blog.services.api.ArticleService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/articles")
public class ArticlesController {

    private final ArticleService articleService;
    private final ArticleMapper articleMapper;

    @GetMapping
    public List<ArticlesResponse> getArticlesWithFilter(@RequestParam(required = false) Long topicId,
                                                        @RequestParam(required = false) String nickname,
                                                        @RequestParam(defaultValue = "10") Integer limit,
                                                        @RequestParam(defaultValue = "0") Integer page) {
        Map<String, Object> params = new HashMap<>();
        params.put("topicId", topicId);
        params.put("nickname", nickname);
        Collection<Article> articles = articleService.findArticles(limit, page, params);
        if (articles.isEmpty()) {
            throw new NotFoundException();
        }
        return articles.stream()
                .map(articleMapper::modelToArticlesResponse)
                .collect(Collectors.toList());
    }
}

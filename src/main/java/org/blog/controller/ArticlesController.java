package org.blog.controller;

import lombok.AllArgsConstructor;
import org.blog.controller.dto.article.ArticlesResponse;
import org.blog.controller.mapper.ArticleMapper;
import org.blog.exceptions.NotFoundException;
import org.blog.model.Article;
import org.blog.services.api.ArticleService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/articles")
public class ArticlesController {

    private final ArticleService articleService;
    private final ArticleMapper articleMapper;

    @GetMapping
    public List<ArticlesResponse> getArticlesFilter(@RequestParam(required = false) Long topicId,
                                                    @RequestParam(required = false) String nickname) {
        Collection<Article> articles = articleService.findArticlesByTopicAndNickname(topicId, nickname);
        if (articles.isEmpty()) {
            throw new NotFoundException();
        }
        return articles.stream()
                .map(articleMapper::modelToArticlesResponse)
                .collect(Collectors.toList());
    }
}

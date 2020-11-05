package org.blog.controller;

import lombok.AllArgsConstructor;
import org.blog.annotation.CurrentAuthor;
import org.blog.controller.dto.article.ArticleResponse;
import org.blog.controller.dto.article.ArticlesResponse;
import org.blog.controller.mapper.ArticleMapper;
import org.blog.exceptions.NotFoundException;
import org.blog.model.Article;
import org.blog.model.Author;
import org.blog.services.api.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@AllArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleMapper articleMapper;

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

    @PostMapping("/article/create")
    public HttpStatus createNewArticle(@RequestBody Article article,
                                       @CurrentAuthor Author author) {
        article.setAuthor(author);
        articleService.saveNewArticle(article);
        return HttpStatus.CREATED;
    }
}

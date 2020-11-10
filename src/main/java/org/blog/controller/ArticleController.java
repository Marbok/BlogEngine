package org.blog.controller;

import lombok.AllArgsConstructor;
import org.blog.annotation.CurrentAuthor;
import org.blog.controller.dto.article.ArticleCreateRequest;
import org.blog.controller.dto.article.ArticleCreateResponse;
import org.blog.controller.dto.article.ArticleResponse;
import org.blog.controller.mapper.ArticleCreateRequestMapper;
import org.blog.controller.mapper.ArticleMapper;
import org.blog.exceptions.ArticleExistsException;
import org.blog.exceptions.ForbiddenException;
import org.blog.exceptions.NotFoundException;
import org.blog.model.Article;
import org.blog.model.Author;
import org.blog.services.api.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleMapper articleMapper;
    private final ArticleCreateRequestMapper articleCreateRequestMapper;

    @GetMapping("/{articleId}")
    public ArticleResponse getArticleById(@PathVariable Long articleId) {
        return articleService.findArticleById(articleId)
                .map(articleMapper::modelToArticleResponse)
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping("/create")
    public ResponseEntity<ArticleCreateResponse> createNewArticle(@RequestBody ArticleCreateRequest articleRequest,
                                                                  @CurrentAuthor Author author)
            throws ArticleExistsException {
        Article article = articleCreateRequestMapper.articleRequestToModel(articleRequest);
        article.setAuthor(author);
        Long articleId = articleService.saveNewArticle(article).getId();
        ArticleCreateResponse body = new ArticleCreateResponse().setArticleId(String.valueOf(articleId));
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @DeleteMapping("/{articleId}")
    public HttpStatus deleteArticle(@PathVariable Long articleId,
                                    @CurrentAuthor Author author)
            throws ForbiddenException {
        articleService.deleteById(articleId, author);
        return HttpStatus.OK;
    }

    @PutMapping("/{articleId}")
    public HttpStatus updateArticle(@PathVariable Long articleId,
                                    @RequestBody ArticleCreateRequest articleRequest,
                                    @CurrentAuthor Author author)
            throws ForbiddenException {
        Article article = articleCreateRequestMapper.articleRequestToModel(articleRequest)
                .setId(articleId);
        articleService.updateArticle(article, author);
        return HttpStatus.OK;
    }
}

package org.blog.controller;

import org.blog.dao.ArticleDao;
import org.blog.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class ArticleController {

    private ArticleDao articleDao;

    @Autowired
    public ArticleController(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    @GetMapping("/getAll")
    public String getArticle(){
        return articleDao.getArticles()
                .stream()
                .map(Article::toString)
                .collect(Collectors.joining(";"));
    }
}

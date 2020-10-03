package org.blog.controller;

import org.blog.dao.ArticleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleController {

    private ArticleDao articleDao;

    @Autowired
    public ArticleController(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    @GetMapping("/getAllArticle")
    public String getArticle() {
        StringBuilder builder = new StringBuilder();
        articleDao.findAll()
                .forEach(article -> builder.append(article.toString()));
        return builder.toString();
    }
}

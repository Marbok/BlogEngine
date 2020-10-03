package org.blog.dao;

import org.blog.model.Article;

import java.util.List;

public interface ArticleDao {

    void addArticle(Article article);
    void addArticles(List<Article> articles);
    List<Article> getArticles();
}

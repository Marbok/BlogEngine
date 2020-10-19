package org.blog.controller.mapper;

import org.blog.controller.dto.article.ArticleResponse;
import org.blog.controller.dto.article.ArticlesResponse;
import org.blog.model.Article;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    ArticleResponse modelToArticleResponse(Article article);
    ArticlesResponse modelToArticlesResponse(Article article);
}

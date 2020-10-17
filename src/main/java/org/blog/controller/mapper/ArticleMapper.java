package org.blog.controller.mapper;

import org.blog.controller.response.ArticleResponse;
import org.blog.controller.response.ArticlesResponse;
import org.blog.model.Article;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    ArticleResponse modelToArticleResponse(Article article);
    ArticlesResponse modelToArticlesResponse(Article article);
}

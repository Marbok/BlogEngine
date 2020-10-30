package org.blog.controller.mapper;

import org.blog.controller.dto.article.ArticleResponse;
import org.blog.controller.dto.article.ArticlesResponse;
import org.blog.model.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    @Mapping(source = "author.nickname", target = "author")
    ArticleResponse modelToArticleResponse(Article article);

    ArticlesResponse modelToArticlesResponse(Article article);
}

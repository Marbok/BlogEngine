package org.blog.controller.mapper;

import org.blog.controller.dto.ArticleResponse;
import org.blog.model.Article;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleModelToResponseMapper {
    ArticleResponse modelToResponse(Article article);
    Article responseToModel (ArticleResponse response);
}

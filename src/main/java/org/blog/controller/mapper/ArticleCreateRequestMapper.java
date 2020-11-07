package org.blog.controller.mapper;

import org.blog.controller.dto.article.ArticleCreateRequest;
import org.blog.model.Article;
import org.blog.services.api.TopicService;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ArticleCreateRequestMapper {

    @Autowired
    private TopicService topicService;

    public abstract Article articleRequestToModel(ArticleCreateRequest articleCreateRequest);

    @BeforeMapping
    protected void mapTopics(ArticleCreateRequest articleRequest, @MappingTarget Article article) {
        topicService.findTopicById(articleRequest.getTopicId())
                .ifPresent(article::setTopic);
    }
}

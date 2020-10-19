package org.blog.controller.mapper;

import org.blog.controller.dto.topic.TopicsResponse;
import org.blog.model.Topic;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TopicMapper {
    TopicsResponse modelToTopicsResponse(Topic topic);
}

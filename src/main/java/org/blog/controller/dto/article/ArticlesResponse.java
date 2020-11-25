package org.blog.controller.dto.article;

import lombok.Data;
import org.blog.controller.dto.topic.TopicsResponse;

@Data
public class ArticlesResponse {
    private Long id;
    private String title;
    private String description;
    private TopicsResponse topic;
}

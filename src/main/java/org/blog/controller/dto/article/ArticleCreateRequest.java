package org.blog.controller.dto.article;

import lombok.Data;

@Data
public class ArticleCreateRequest {
    private String title;
    private String description;
    private String content;
    private Long topicId;
}

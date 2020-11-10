package org.blog.controller.dto.article;

import lombok.Data;
import org.blog.controller.dto.topic.TopicsResponse;

import java.io.Serializable;

@Data
public class ArticleResponse implements Serializable {

    private String author;
    private String title;
    private String description;
    private String content;
    private TopicsResponse topic;
}

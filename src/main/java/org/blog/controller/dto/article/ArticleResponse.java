package org.blog.controller.dto.article;

import lombok.Data;

import java.io.Serializable;

@Data
public class ArticleResponse implements Serializable {

    private String author;
    private String title;
    private String description;
    private String content;
}

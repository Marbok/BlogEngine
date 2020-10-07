package org.blog.controller.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ArticleResponse implements Serializable {

    private String title;

    private String description;

    private String content;
}

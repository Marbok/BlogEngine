package org.blog.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ARTICLE")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @Column(unique = true)
    private String title;
    private String description;
    private String content;

    @ManyToOne
    @JoinColumn(name = "nickname")
    private Author author;
}

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

    private String title;

    private String description;
}

package org.blog.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private long id;

    @Column(name = "Title")
    private String title;

    @Column(name = "Description")
    private String description;
}

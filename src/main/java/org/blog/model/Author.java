package org.blog.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "AUTHOR")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long id;

    private String nickname;
    private String password;
}

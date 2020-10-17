package org.blog.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "AUTHOR")
public class Author {

    @Id
    @Column(unique = true)
    private String nickname;
    private String password;
}

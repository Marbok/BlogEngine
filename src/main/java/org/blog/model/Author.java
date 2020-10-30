package org.blog.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "AUTHOR")
public class Author {

    @Id
    @Column(unique = true)
    private String nickname;
    private String password;

    @OneToMany(mappedBy = "author")
    private List<Article> articles;
}

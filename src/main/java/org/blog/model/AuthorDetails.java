package org.blog.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class AuthorDetails extends User {

    private final Author author;

    public AuthorDetails(Author author, Collection<? extends GrantedAuthority> authorities) {
        super(author.getNickname(), author.getPassword(), authorities);
        this.author = author;
    }
}

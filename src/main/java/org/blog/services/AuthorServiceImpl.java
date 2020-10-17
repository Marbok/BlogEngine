package org.blog.services;

import org.blog.model.Author;
import org.blog.repository.AuthorRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class AuthorServiceImpl implements UserDetailsService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        Author author = authorRepository.findByNickname(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("user"));

        return new User(author.getNickname(), author.getPassword(), authorities);
    }
}

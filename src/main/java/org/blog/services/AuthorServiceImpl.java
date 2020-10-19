package org.blog.services;

import org.blog.model.Author;
import org.blog.repository.AuthorRepository;
import org.blog.services.api.AuthorService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthorServiceImpl(AuthorRepository authorRepository, PasswordEncoder passwordEncoder) {
        this.authorRepository = authorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        Author author = authorRepository.findByNickname(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("USER"));

        return new User(author.getNickname(), author.getPassword(), authorities);
    }

    @Override
    public Optional<Author> findByNickname(String nickname) {
        return authorRepository.findByNickname(nickname);
    }

    @Override
    public Optional<Author> findByNicknameAndPassword(String nickname, String password) {
        return findByNickname(nickname)
                .filter(author -> passwordEncoder.matches(password, author.getPassword()));
    }
}

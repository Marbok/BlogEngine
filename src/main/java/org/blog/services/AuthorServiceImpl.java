package org.blog.services;

import org.blog.exceptions.IncorrectNicknameException;
import org.blog.model.Author;
import org.blog.model.AuthorDetails;
import org.blog.repository.AuthorRepository;
import org.blog.services.api.AuthorService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.blog.model.Role.USER;
import static org.springframework.util.StringUtils.isEmpty;

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
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(author.getRole().name()));

        return new AuthorDetails(author, authorities);
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

    @Override
    public Author addNewAuthor(Author author) throws IncorrectNicknameException {
        if (isEmpty(author.getNickname()) || isEmpty(author.getPassword())) {
            throw new IncorrectNicknameException();
        }

        Optional<Author> byNickname = authorRepository.findByNickname(author.getNickname());
        if (byNickname.isPresent()) {
            throw new IncorrectNicknameException();
        }
        String encodePassword = passwordEncoder.encode(author.getPassword());
        author.setPassword(encodePassword);
        author.setRole(USER);
        return authorRepository.save(author);
    }
}

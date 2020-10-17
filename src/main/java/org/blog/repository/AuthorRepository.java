package org.blog.repository;

import org.blog.model.Author;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuthorRepository extends CrudRepository<Author, String> {
    Optional<Author> findByNickname(String nickname);
}

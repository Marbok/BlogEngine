package org.blog.repository;

import org.blog.model.Article;
import org.blog.model.Author;
import org.blog.model.Topic;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    List<Article> findByTopicAndAuthor(Topic topic, Author author);

    Optional<Article> findByTitle(String title);

    List<Article> findAllByAuthor(Author author);

    List<Article> findAllByTopic(Topic topic);
}

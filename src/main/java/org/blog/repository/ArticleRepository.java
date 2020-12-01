package org.blog.repository;

import java.util.List;
import java.util.Optional;

import org.blog.model.Article;
import org.blog.model.Author;
import org.blog.model.Topic;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Long>, JpaSpecificationExecutor<Article> {
    List<Article> findByTopicAndAuthor(Topic topic, Author author);

    Optional<Article> findByTitle(String title);

    List<Article> findAllByAuthor(Author author);

    List<Article> findAllByTopic(Topic topic);

    List<Article> findFirst9ByOrderByIdDesc();
}

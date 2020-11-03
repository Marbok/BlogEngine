package org.blog.repository;

import org.blog.model.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    List<Article> findAllByTopic_id(Long topicId);

    Optional<Article> findByTitle(String title);
}

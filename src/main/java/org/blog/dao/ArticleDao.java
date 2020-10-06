package org.blog.dao;

import org.blog.model.Article;
import org.blog.model.Topic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ArticleDao extends CrudRepository<Article, Long> {
    List<Article> findAllByTopic(Topic topic);
}

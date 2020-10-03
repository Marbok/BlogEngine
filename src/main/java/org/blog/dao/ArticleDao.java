package org.blog.dao;

import org.blog.model.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ArticleDao extends CrudRepository<Article, Long> {
}

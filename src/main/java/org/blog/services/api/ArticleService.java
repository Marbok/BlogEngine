package org.blog.services.api;

import org.blog.model.Article;
import org.blog.model.Author;

import java.util.Collection;
import java.util.Optional;

public interface ArticleService {

    /**
     * Find articles name by topic id,
     * if articles not founded, method return empty Collection
     *
     * @param topicId topic id
     * @return List of article
     */
    Collection<Article> findArticlesByTopic(Long topicId);

    /**
     * @param articleId article id
     * @return find article by id, article id equals null - return Optional.empty()
     */
    Optional<Article> findArticleById(Long articleId);

    /**
     * Add new article
     *
     * @param article article
     * @return  saved article
     */
    Article saveNewArticle(Article article);
}

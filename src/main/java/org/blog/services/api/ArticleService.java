package org.blog.services.api;

import org.blog.exceptions.ArticleExistsException;
import org.blog.exceptions.ForbiddenException;
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
     * @return saved article
     * @throws ArticleExistsException when article exists
     */
    Article saveNewArticle(Article article) throws ArticleExistsException;

    /**
     * delete article, if article doesn't exist, do nothing
     * Only author and moderator can delete article
     *
     * @param articleId article id
     * @param author    author, which want delete article
     */
    void deleteById(Long articleId, Author author) throws ForbiddenException;
}

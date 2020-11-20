package org.blog.services.api;

import org.blog.exceptions.ArticleExistsException;
import org.blog.exceptions.ForbiddenException;
import org.blog.model.Article;
import org.blog.model.Author;

import java.util.Collection;
import java.util.Optional;

public interface ArticleService {

    /**
     * Find articles by topic and nickname
     *
     * @param topicId  topic id
     * @param nickname author nickname
     * @return List of articles
     */
    Collection<Article> findArticlesByTopicAndNickname(Long topicId, String nickname);

    /**
     * Find article by nickname
     *
     * @param nickname author nickname
     * @return List of articles
     */
    Collection<Article> findArticleByNickname(String nickname);

    /**
     * Find articles by topic id,
     * if articles not founded, method return empty Collection
     *
     * @param topicId topic id
     * @return List of articles
     */
    Collection<Article> findArticlesByTopicId(Long topicId);

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
     * @param author    author, which wants delete article
     * @throws ForbiddenException if author can't delete article
     */
    void deleteById(Long articleId, Author author) throws ForbiddenException;

    /**
     * update article, if article doesn't exist, do nothing
     * Only author and moderator can delete article
     *
     * @param article article
     * @param author  author, which wants update article
     * @throws ForbiddenException if author can't update article
     */
    void updateArticle(Article article, Author author) throws ForbiddenException;
}

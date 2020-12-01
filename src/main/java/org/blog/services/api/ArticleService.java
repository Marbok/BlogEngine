package org.blog.services.api;

import org.blog.exceptions.ArticleExistsException;
import org.blog.exceptions.ForbiddenException;
import org.blog.model.Article;
import org.blog.model.Author;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface ArticleService {

    /**
     * Find article by input params
     *
     * @param limit  counts of articles in page
     * @param page   num of page
     * @param params params for search
     * @return list of article
     */
    Collection<Article> findArticles(int limit, int page, Map<String, Object> params);

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

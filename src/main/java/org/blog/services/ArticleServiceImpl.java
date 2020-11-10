package org.blog.services;

import lombok.AllArgsConstructor;
import org.blog.exceptions.ArticleExistsException;
import org.blog.exceptions.ForbiddenException;
import org.blog.model.Article;
import org.blog.model.Author;
import org.blog.repository.ArticleRepository;
import org.blog.services.api.ArticleService;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.blog.model.Role.MODERATOR;

@Component
@AllArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Override
    public Collection<Article> findArticlesByTopic(Long topicId) {
        if (topicId == null) {
            return Collections.emptyList();
        }
        return articleRepository.findAllByTopic_id(topicId);
    }

    @Override
    public Optional<Article> findArticleById(Long articleId) {
        if (articleId == null) {
            return Optional.empty();
        }
        return articleRepository.findById(articleId);
    }

    @Override
    public Article saveNewArticle(Article article) throws ArticleExistsException {
        Optional<Article> byTitle = articleRepository.findByTitle(article.getTitle());
        if (byTitle.isPresent()) {
            throw new ArticleExistsException();
        }
        return articleRepository.save(article);
    }

    @Override
    public void deleteById(Long articleId, Author author) throws ForbiddenException {
        Optional<Article> articleOpt = articleRepository.findById(articleId);
        if (articleOpt.isPresent()) {
            Article article = articleOpt.get();
            if (article.getAuthor().equals(author) || author.getRole().equals(MODERATOR)) {
                articleRepository.deleteById(articleId);
            } else {
                throw new ForbiddenException();
            }
        }
    }

    @Override
    public void updateArticle(Article article, Author author) throws ForbiddenException {
        Optional<Article> articleOpt = articleRepository.findById(article.getId());
        if (articleOpt.isPresent()) {
            Article articleBD = articleOpt.get();
            if (articleBD.getAuthor().equals(author)) {
                article.setAuthor(author);
                articleRepository.save(article);
            } else if (author.getRole().equals(MODERATOR)) {
                article.setAuthor(articleBD.getAuthor());
                articleRepository.save(article);
            } else {
                throw new ForbiddenException();
            }
        }
    }

}

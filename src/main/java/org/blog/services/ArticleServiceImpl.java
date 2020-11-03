package org.blog.services;

import lombok.AllArgsConstructor;
import org.blog.exceptions.ArticleExistsException;
import org.blog.model.Article;
import org.blog.repository.ArticleRepository;
import org.blog.services.api.ArticleService;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

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
    public void saveNewArticle(Article article) {
        Optional<Article> byTitle = articleRepository.findByTitle(article.getTitle());
        if (byTitle.isPresent()) {
            throw new ArticleExistsException();
        }
        articleRepository.save(article);
    }


}

package org.blog.services;

import lombok.AllArgsConstructor;
import org.blog.exceptions.ArticleExistsException;
import org.blog.exceptions.ForbiddenException;
import org.blog.model.Article;
import org.blog.model.Author;
import org.blog.model.Topic;
import org.blog.repository.ArticleRepository;
import org.blog.repository.AuthorRepository;
import org.blog.repository.TopicRepository;
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
    private final TopicRepository topicRepository;
    private final AuthorRepository authorRepository;

    @Override
    public Collection<Article> findArticlesByTopicAndNickname(Long topicId, String nickname) {
        if (topicId == null && nickname == null) throw new IllegalArgumentException();
        if (topicId == null) return findArticleByNickname(nickname);
        if (nickname == null) return findArticlesByTopicId(topicId);

        Optional<Topic> topic = topicRepository.findById(topicId);
        Optional<Author> author = authorRepository.findByNickname(nickname);
        if (topic.isPresent() && author.isPresent()) {
            return articleRepository.findByTopicAndAuthor(topic.get(), author.get());
        }
        return Collections.emptyList();
    }

    @Override
    public Collection<Article> findArticleByNickname(String nickname) {
        if (nickname == null) return Collections.emptyList();
        return authorRepository.findByNickname(nickname)
                .map(articleRepository::findAllByAuthor)
                .orElse(Collections.emptyList());
    }

    @Override
    public Collection<Article> findArticlesByTopicId(Long topicId) {
        if (topicId == null) return Collections.emptyList();
        return topicRepository.findById(topicId)
                .map(articleRepository::findAllByTopic)
                .orElse(Collections.emptyList());
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

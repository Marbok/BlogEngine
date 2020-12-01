package org.blog.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.blog.exceptions.ArticleExistsException;
import org.blog.exceptions.ForbiddenException;
import org.blog.model.Article;
import org.blog.model.Author;
import org.blog.repository.ArticleRepository;
import org.blog.services.api.ArticleService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static org.blog.model.Role.MODERATOR;
import static org.blog.repository.specifications.ArticleSpecifications.articlesHasNickname;
import static org.blog.repository.specifications.ArticleSpecifications.articlesHasTopicId;

@Component
@AllArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Override
    public Collection<Article> findArticles(int limit, int page, Map<String, Object> params) {
        PageRequest pageable = PageRequest.of(page, limit, Sort.by("id").descending());
        Long topicId = (Long) params.get("topicId");
        String nickname = (String) params.get("nickname");
        List<Specification<Article>> specs = new ArrayList<>();
        if (topicId != null) {
            specs.add(articlesHasTopicId(topicId));
        }
        if (!StringUtils.isEmpty(nickname)) {
            specs.add(articlesHasNickname(nickname));
        }

        if (specs.isEmpty()) {
            return toList(articleRepository.findAll(null, pageable));
        }

        return specs.stream()
                .reduce(Specification::and)
                .map(spec -> articleRepository.findAll(spec, pageable).getContent())
                .orElse(Collections.emptyList());
    }

    private List<Article> toList(Iterable<Article> iter) {
        List<Article> list = new ArrayList<>();
        iter.forEach(list::add);
        return list;
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

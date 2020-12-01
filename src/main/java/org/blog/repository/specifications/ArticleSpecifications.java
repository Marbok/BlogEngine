package org.blog.repository.specifications;

import org.blog.model.Article;
import org.springframework.data.jpa.domain.Specification;

public abstract class ArticleSpecifications {
    public static Specification<Article> articlesHasTopicId(Long topicId) {
        return (Specification<Article>) (article, cq, cb) -> cb.equal(article.get("topic").get("id"), topicId);
    }

    public static Specification<Article> articlesHasNickname(String nickname) {
        return (Specification<Article>) (article, cq, cb) -> cb.equal(article.get("author").get("nickname"), nickname);
    }
}

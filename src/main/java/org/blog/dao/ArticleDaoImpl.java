package org.blog.dao;

import org.blog.model.Article;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleDaoImpl implements ArticleDao {

    private SessionFactory sessionFactory;

    @Autowired
    public ArticleDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addArticle(Article article) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        session.persist(article);
        session.getTransaction().commit();
    }

    @Override
    public void addArticles(List<Article> articles) {
        Session session = sessionFactory.openSession();

    }

    public List<Article> getArticles() {
        return sessionFactory.openSession()
                .createQuery("FROM Article")
                .list();
    }
}

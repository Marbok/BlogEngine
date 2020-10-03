package org.blog.listeners;

import org.blog.dao.ArticleDao;
import org.blog.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartEventContextListener implements ApplicationListener<ContextRefreshedEvent> {

    private ArticleDao articleDao;
    private boolean isBDNotInitialization = true;

    @Autowired
    public StartEventContextListener(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    @Override
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (isBDNotInitialization) {
            Article article = new Article()
                    .setTitle("java")
                    .setDescription("java article");

            articleDao.addArticle(article);
            isBDNotInitialization = false;
        }
    }
}

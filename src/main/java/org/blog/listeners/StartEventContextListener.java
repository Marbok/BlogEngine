package org.blog.listeners;

import org.blog.dao.ArticleDao;
import org.blog.dao.TopicDao;
import org.blog.model.Article;
import org.blog.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class StartEventContextListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private TopicDao topicDao;

    private boolean isBDNotInitialization = true;


    @Override
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (isBDNotInitialization) {
            Topic topic = new Topic()
                    .setName("java");

            Article article = new Article()
                    .setTopic(topic)
                    .setTitle("java")
                    .setDescription("java article")
                    .setContent("This article about Java language. I can write a lot of about it. And etc.");

            topicDao.saveAll(Arrays.asList(
                    topic,
                    new Topic().setName("Python"),
                    new Topic().setName("C++")
            ));
            articleDao.save(article);
            isBDNotInitialization = false;
        }
    }
}

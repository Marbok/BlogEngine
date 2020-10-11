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
            Topic topicJava = new Topic().setName("java");
            Topic topicPy = new Topic().setName("Python");
            Topic topicC = new Topic().setName("C++");

            Article articleJava = new Article()
                    .setTopic(topicJava)
                    .setTitle("java")
                    .setDescription("java article")
                    .setContent("This article about Java language. I can write a lot of about it. And etc.");

            Article articlePython = new Article()
                    .setTopic(topicPy)
                    .setTitle("Python")
                    .setDescription("Python article")
                    .setContent("This article about Python language. I can write a lot of about it. And etc.");

            Article articleC = new Article()
                    .setTopic(topicC)
                    .setTitle("C++")
                    .setDescription("C++ article")
                    .setContent("This article about C++ language. I can write a lot of about it. And etc.");

            topicDao.saveAll(Arrays.asList(
                    topicJava,
                    topicPy,
                    topicC
            ));
            articleDao.saveAll(Arrays.asList(
                    articleJava,
                    articleC,
                    articlePython
            ));
            isBDNotInitialization = false;
        }
    }
}

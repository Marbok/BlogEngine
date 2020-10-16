package org.blog;

import org.blog.dao.ArticleDao;
import org.blog.dao.TopicDao;
import org.blog.model.Article;
import org.blog.model.Topic;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner initDataBase(ArticleDao articleDao, TopicDao topicDao) {
        return (args) -> {
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
        };
    }
}

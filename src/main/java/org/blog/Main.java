package org.blog;

import org.blog.model.Author;
import org.blog.repository.ArticleRepository;
import org.blog.repository.AuthorRepository;
import org.blog.repository.TopicRepository;
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
    public CommandLineRunner initDataBase(ArticleRepository articleRepository,
                                          TopicRepository topicRepository,
                                          AuthorRepository authorRepository) {
        return (args) -> {
            Author author = new Author()
                    .setNickname("test")
                    .setPassword("$2a$10$Dx.D7a240ySvk9.K9i0wJOKUboNdZzh9aK6NOKkvXRxYS3AjELZpe");//test

            authorRepository.save(author);

            Topic topicJava = new Topic().setName("java");
            Topic topicPy = new Topic().setName("Python");
            Topic topicC = new Topic().setName("C++");

            Article articleJava = new Article()
                    .setTopic(topicJava)
                    .setAuthor(author)
                    .setTitle("java")
                    .setDescription("java article")
                    .setContent("This article about Java language. I can write a lot of about it. And etc.");

            Article articlePython = new Article()
                    .setTopic(topicPy)
                    .setAuthor(author)
                    .setTitle("Python")
                    .setDescription("Python article")
                    .setContent("This article about Python language. I can write a lot of about it. And etc.");

            Article articleC = new Article()
                    .setTopic(topicC)
                    .setAuthor(author)
                    .setTitle("C++")
                    .setDescription("C++ article")
                    .setContent("This article about C++ language. I can write a lot of about it. And etc.");

            topicRepository.saveAll(Arrays.asList(
                    topicJava,
                    topicPy,
                    topicC
            ));
            articleRepository.saveAll(Arrays.asList(
                    articleJava,
                    articleC,
                    articlePython
            ));
        };
    }
}

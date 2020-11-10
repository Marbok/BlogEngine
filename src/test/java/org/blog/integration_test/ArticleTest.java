package org.blog.integration_test;

import org.blog.config.jwt.JwtFilter;
import org.blog.controller.dto.article.ArticleCreateRequest;
import org.blog.controller.dto.article.ArticleResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.blog.util.AuthUtils.getToken;
import static org.blog.util.SerializationUtil.deserialize;
import static org.blog.util.SerializationUtil.serialize;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {"/add-articles.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-articles.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ArticleTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getArticle() throws Exception {
        mockMvc.perform(get("/article/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"author\":\"marbok\",\"title\":\"java\",\"description\":\"java art\",\"content\":\"content article java\"}")));
    }

    @Test
    public void getArticle_fail() throws Exception {
        mockMvc.perform(get("/article/10"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void createArticle_nonAuth() throws Exception {
        mockMvc.perform(post("/article/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"title\":\"JVM\",\"description\":\"about JVM\",\"content\":\"JVM is very interesting\",\"topic\":\"1\"}"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void createArticle() throws Exception {
        String token = getToken(mockMvc, "marbok", "test");

        mockMvc.perform(post("/article/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"title\":\"JVM\",\"description\":\"about JVM\",\"content\":\"JVM is very interesting\",\"topicId\":1}")
                .header(JwtFilter.AUTHORIZATION, "Bearer " + token))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("{\"articleId\":\"4\"}")));
    }

    @Test
    public void deleteArticle() throws Exception {
        String token = getToken(mockMvc, "marbok", "test");

        mockMvc.perform(get("/article/1"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(delete("/article/1")
                .header(JwtFilter.AUTHORIZATION, "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/article/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteArticle_anotherUser() throws Exception {
        String token = getToken(mockMvc, "sun micro", "test");

        mockMvc.perform(get("/article/1"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(delete("/article/1")
                .header(JwtFilter.AUTHORIZATION, "Bearer " + token))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void updateArticle() throws Exception {
        String token = getToken(mockMvc, "marbok", "test");

        String articleResponseJson = mockMvc.perform(get("/article/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"title\":\"java\"")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ArticleResponse articleResponse = deserialize(articleResponseJson, ArticleResponse.class);

        ArticleCreateRequest articleRequest = new ArticleCreateRequest()
                .setTitle("new java")
                .setContent(articleResponse.getContent())
                .setDescription(articleResponse.getDescription())
                .setTopicId(articleResponse.getTopic().getId());

        mockMvc.perform(put("/article/1")
                .header(JwtFilter.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(serialize(articleRequest)))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/article/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"title\":\"new java\"")));

    }

}

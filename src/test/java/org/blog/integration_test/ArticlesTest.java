package org.blog.integration_test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {"/add-articles.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-articles.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ArticlesTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getArticles_fail() throws Exception {
        mockMvc.perform(get("/articles")
                .param("topicId", "10"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getArticles_filterByTopic() throws Exception {
        mockMvc.perform(get("/articles")
                .param("topicId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[{\"id\":1,\"title\":\"java\",\"description\":\"java art\",\"topic\":{\"id\":1,\"name\":\"java\"}},{\"id\":2,\"title\":\"java sun\",\"description\":\"java sun art\",\"topic\":{\"id\":1,\"name\":\"java\"}}]")));
    }

    @Test
    public void getArticles_filterByNickname() throws Exception {
        mockMvc.perform(get("/articles")
                .param("nickname", "marbok"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[{\"id\":1,\"title\":\"java\",\"description\":\"java art\",\"topic\":{\"id\":1,\"name\":\"java\"}},{\"id\":4,\"title\":\"python is cool\",\"description\":\"python marbok\",\"topic\":{\"id\":2,\"name\":\"python\"}}]")));
    }

    @Test
    public void getArticles_filterByTopicIdAndNickname() throws Exception {
        mockMvc.perform(get("/articles")
                .param("topicId", "2")
                .param("nickname", "marbok"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[{\"id\":4,\"title\":\"python is cool\",\"description\":\"python marbok\",\"topic\":{\"id\":2,\"name\":\"python\"}}]")));
    }

    @Test
    public void getArticles_withoutFilters() throws Exception {
        mockMvc.perform(get("/articles"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[{\"id\":4,\"title\":\"python is cool\",\"description\":\"python marbok\",\"topic\":{\"id\":2,\"name\":\"python\"}},{\"id\":3,\"title\":\"python\",\"description\":\"python art\",\"topic\":{\"id\":2,\"name\":\"python\"}},{\"id\":2,\"title\":\"java sun\",\"description\":\"java sun art\",\"topic\":{\"id\":1,\"name\":\"java\"}},{\"id\":1,\"title\":\"java\",\"description\":\"java art\",\"topic\":{\"id\":1,\"name\":\"java\"}}]")));
    }
}

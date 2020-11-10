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
        mockMvc.perform(get("/articles/10"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getArticles() throws Exception {
        mockMvc.perform(get("/articles/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[{\"id\":1,\"title\":\"java\"},{\"id\":2,\"title\":\"java sun\"}]")));
    }
}

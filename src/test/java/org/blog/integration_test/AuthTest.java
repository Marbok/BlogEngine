package org.blog.integration_test;

import org.blog.controller.dto.auth.TokenResponse;
import org.blog.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.blog.config.jwt.JwtFilter.AUTHORIZATION;
import static org.blog.util.SerializationUtil.deserialize;
import static org.blog.util.SerializationUtil.serialize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
// todo refactoring
public class AuthTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql(value = {"/delete-user.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void registration_success() throws Exception {
        String tokenResponse = mockMvc.perform(post("/auth/registration")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(serialize(new Author()
                        .setNickname("Marbok")
                        .setPassword("12345"))))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        TokenResponse token = deserialize(tokenResponse, TokenResponse.class);

        mockMvc.perform(get("/auth/check")
                .header(AUTHORIZATION, "Bearer " + token.getToken()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Sql(value = {"/add-new-author.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-user.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void registration_fail() throws Exception {
        mockMvc.perform(post("/auth/registration")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(serialize(new Author()
                        .setNickname("test")
                        .setPassword("test"))))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Sql(value = {"/add-new-author.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-user.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void token_fail() throws Exception {
        mockMvc.perform(post("/auth/token")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(serialize(new Author()
                        .setNickname("Marbok")
                        .setPassword("12345"))))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Sql(value = {"/add-new-author.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-user.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void token_success() throws Exception {
        String tokenResponse = mockMvc.perform(post("/auth/token")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(serialize(new Author()
                        .setNickname("test")
                        .setPassword("test"))))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        TokenResponse token = deserialize(tokenResponse, TokenResponse.class);

        mockMvc.perform(get("/auth/check")
                .header(AUTHORIZATION, "Bearer " + token.getToken()))
                .andDo(print())
                .andExpect(status().isOk());
    }

}

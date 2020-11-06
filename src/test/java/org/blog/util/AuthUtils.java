package org.blog.util;

import lombok.SneakyThrows;
import org.blog.controller.dto.auth.TokenResponse;
import org.blog.model.Author;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.blog.util.SerializationUtil.deserialize;
import static org.blog.util.SerializationUtil.serialize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Utils for authorisation
 */
public class AuthUtils {

    @SneakyThrows
    public static String getToken(MockMvc mockMvc, String login, String password) {
        String tokenResponse = mockMvc.perform(post("/auth/token")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(serialize(new Author()
                        .setNickname(login)
                        .setPassword(password))))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return deserialize(tokenResponse, TokenResponse.class).getToken();
    }


}

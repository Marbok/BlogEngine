package org.blog.controller.dto.auth;

import lombok.Data;

@Data
public class AuthorRequest {
    private String nickname;
    private String password;
}

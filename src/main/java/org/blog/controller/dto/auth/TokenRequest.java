package org.blog.controller.dto.auth;

import lombok.Data;

@Data
public class TokenRequest {
    private String nickname;
    private String password;
}

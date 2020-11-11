package org.blog.controller.dto.auth;

import lombok.Data;
import org.blog.model.Role;

@Data
public class TokenResponse {
    private String token;
    private Role role;
}

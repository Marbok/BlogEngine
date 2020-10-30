package org.blog.controller;

import org.blog.config.jwt.JwtProvider;
import org.blog.controller.dto.auth.TokenRequest;
import org.blog.controller.dto.auth.TokenResponse;
import org.blog.services.api.AuthorService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    private final AuthorService authorService;
    private final JwtProvider jwtProvider;

    public AuthController(AuthorService authorService, JwtProvider jwtProvider) {
        this.authorService = authorService;
        this.jwtProvider = jwtProvider;
    }

    @GetMapping("/check") // todo delete - only for local test
    public String check() {
        return "OK";
    }

    @PostMapping("/token")
    public TokenResponse token(@RequestBody TokenRequest tokenRequest) {
        return authorService.findByNicknameAndPassword(tokenRequest.getNickname(), tokenRequest.getPassword())
                .map(author -> jwtProvider.generateToken(author.getNickname()))
                .map(token -> new TokenResponse()
                        .setToken(token))
                .orElseThrow(() -> new UsernameNotFoundException("user " + tokenRequest.getNickname() + " not founded"));
    }


}

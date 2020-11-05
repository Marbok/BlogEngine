package org.blog.controller;

import lombok.AllArgsConstructor;
import org.blog.config.jwt.JwtProvider;
import org.blog.controller.dto.auth.TokenResponse;
import org.blog.model.Author;
import org.blog.services.api.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthorService authorService;
    private final JwtProvider jwtProvider;

    /**
     * @return status 200 - if token correct
     */
    @GetMapping("/check")
    public HttpStatus check() {
        return HttpStatus.OK;
    }

    @PostMapping("/token")
    public TokenResponse token(@RequestBody Author authorData) {
        return authorService.findByNicknameAndPassword(authorData.getNickname(), authorData.getPassword())
                .map(author -> jwtProvider.generateToken(author.getNickname()))
                .map(token -> new TokenResponse()
                        .setToken(token))
                .orElseThrow(() -> new UsernameNotFoundException("user " + authorData.getNickname() + " not founded"));
    }

    @PostMapping("/registration")
    public TokenResponse registration(@RequestBody Author author) {
        Author saveAuthor = authorService.addNewAuthor(author);
        String token = jwtProvider.generateToken(saveAuthor.getNickname());
        return new TokenResponse()
                .setToken(token);

    }


}

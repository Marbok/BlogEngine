package org.blog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<HttpStatus> handleNotFoundException() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UsernameNotFoundException.class, ForbiddenException.class})
    public ResponseEntity<HttpStatus> handleUserNameNotFoundException() {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({IncorrectNicknameException.class, ArticleExistsException.class})
    public ResponseEntity<HttpStatus> handleIncorrectNicknameException() {
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}

package org.blog.services.api;

import org.blog.exceptions.IncorrectNicknameException;
import org.blog.model.Author;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface AuthorService extends UserDetailsService {

    /**
     * @param nickname author's nickname
     * @return information about author
     */
    Optional<Author> findByNickname(String nickname);

    /**
     * @param nickname author's nickname
     * @param password author's password
     * @return information about author
     */
    Optional<Author> findByNicknameAndPassword(String nickname, String password);

    /**
     * @param author
     * @return consumer author
     * @throws IncorrectNicknameException if author exists
     */
    Author addNewAuthor(Author author) throws IncorrectNicknameException;
}

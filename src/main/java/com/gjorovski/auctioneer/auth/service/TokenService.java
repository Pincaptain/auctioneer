package com.gjorovski.auctioneer.auth.service;

import com.gjorovski.auctioneer.auth.model.Token;
import com.gjorovski.auctioneer.auth.repository.TokenRepository;
import com.gjorovski.auctioneer.shared.exceptions.BadRequestException;
import com.gjorovski.auctioneer.shared.exceptions.PermissionDeniedException;
import com.gjorovski.auctioneer.user.model.User;
import com.gjorovski.auctioneer.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public TokenService(TokenRepository tokenRepository, UserService userService, PasswordEncoder passwordEncoder) {
        this.tokenRepository = tokenRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public Token getTokenByValue(String value) {
        return this.tokenRepository.findFirstByValue(value);
    }

    public User getUserByTokenValue(String value) {
        Token token = getTokenByValue(value);

        if (token == null) {
            return null;
        }

        return token.getUser();
    }

    public Token createToken(User user) {
        String uuid = UUID.randomUUID().toString();
        Token token = new Token(uuid, user);

        return tokenRepository.save(token);
    }

    public Token getTokenByUsernameAndPassword(String username, String password) {
        User user = userService.getUserByUsername(username);

        if (user == null) {
            throw new BadRequestException("User with that username does not exist.");
        }

        boolean isPasswordValid = passwordEncoder.matches(password, user.getPassword());

        if (!isPasswordValid) {
            throw new BadRequestException("Password provided is invalid.");
        }

        if (!user.isActive()) {
            throw new PermissionDeniedException("User is not active.");
        }

        if (user.getToken() == null) {
            return createToken(user);
        }

        return user.getToken();
    }

    public void deleteToken(Token token) {
        tokenRepository.delete(token);
    }
}

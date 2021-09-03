package com.gjorovski.auctioneer.auth.controller;

import com.gjorovski.auctioneer.auth.domain.Authentication;
import com.gjorovski.auctioneer.auth.service.TokenService;
import com.gjorovski.auctioneer.auth.model.Token;
import com.gjorovski.auctioneer.auth.request.LoginRequest;
import com.gjorovski.auctioneer.auth.response.LogoutResponse;
import com.gjorovski.auctioneer.auth.response.TokenResponse;
import com.gjorovski.auctioneer.shared.exceptions.PermissionDeniedException;
import com.gjorovski.auctioneer.user.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
public class TokenController {
    private final TokenService tokenService;
    private final ModelMapper modelMapper;

    public TokenController(TokenService tokenService, ModelMapper modelMapper) {
        this.tokenService = tokenService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        Token token = tokenService.getTokenByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        TokenResponse tokenResponse = modelMapper.map(token, TokenResponse.class);

        return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
    }

    @GetMapping("logout")
    public ResponseEntity<LogoutResponse> logout(@RequestAttribute Authentication authentication) {
        if (!authentication.isAuthenticated()) {
            throw new PermissionDeniedException(Authentication.NOT_AUTHENTICATED_MESSAGE);
        }

        User user = authentication.getUser();
        Token token = user.getToken();
        String message = String.format("You have been successfully logged out of %s.", user.getUsername());
        LogoutResponse logoutResponse = new LogoutResponse(message);

        tokenService.deleteToken(token);

        return new ResponseEntity<>(logoutResponse, HttpStatus.OK);
    }
}

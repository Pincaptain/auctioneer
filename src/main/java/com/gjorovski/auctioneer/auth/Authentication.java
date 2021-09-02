package com.gjorovski.auctioneer.auth;

import com.gjorovski.auctioneer.user.User;
import lombok.Data;

@Data
public class Authentication {
    public static final String NOT_AUTHENTICATED_MESSAGE = "You do not have permission to perform this action.";

    private final User user;

    public boolean isAuthenticated() {
        return user != null;
    }
}

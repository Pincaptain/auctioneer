package com.gjorovski.auctioneer.auth.domain;

import com.gjorovski.auctioneer.user.model.User;
import lombok.Data;

@Data
public class Authentication {
    public static final String NOT_AUTHENTICATED_MESSAGE = "You do not have permission to perform this action.";

    private final User user;

    public boolean isAuthenticated() {
        return user != null;
    }

    public boolean inGroup(String groupName) {
        if (user == null) {
            return false;
        }

        return user.getGroups()
                .stream()
                .anyMatch(group -> group.getName().equals(groupName));
    }
}

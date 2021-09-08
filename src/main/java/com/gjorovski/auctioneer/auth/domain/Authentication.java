package com.gjorovski.auctioneer.auth.domain;

import com.gjorovski.auctioneer.user.model.User;
import lombok.Data;

@Data
public class Authentication {
    public static final String NOT_AUTHENTICATED_MESSAGE = "You do not have permission to perform this action.";

    private final User user;

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isAuthenticated() {
        return user != null;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean inGroup(String groupName) {
        if (user == null) {
            return false;
        }

        return user.getGroups()
                .stream()
                .anyMatch(group -> group.getName().equals(groupName));
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isUser(User other) {
        if (user == null || other == null) {
            return false;
        }

        return user.getId().equals(other.getId());
    }
}

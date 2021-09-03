package com.gjorovski.auctioneer.user.validation;

import com.gjorovski.auctioneer.auth.data.Authentication;
import com.gjorovski.auctioneer.user.model.User;
import com.gjorovski.auctioneer.user.service.UserService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UpdatableUsernameValidation implements ConstraintValidator<UpdatableUsername, String> {
    private final UserService userService;
    private final HttpServletRequest httpServletRequest;

    public UpdatableUsernameValidation(UserService userService, HttpServletRequest httpServletRequest) {
        this.userService = userService;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public void initialize(UpdatableUsername constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        Authentication authentication = (Authentication) httpServletRequest.getAttribute("authentication");

        if (!authentication.isAuthenticated()) {
            return false;
        }

        if (authentication.getUser().getUsername().equals(username)) {
            return true;
        }

        User user = userService.getUserByUsername(username);

        return user == null;
    }
}

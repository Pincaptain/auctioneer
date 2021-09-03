package com.gjorovski.auctioneer.user.validation;

import com.gjorovski.auctioneer.auth.data.Authentication;
import com.gjorovski.auctioneer.user.model.User;
import com.gjorovski.auctioneer.user.service.UserService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UpdatableEmailValidation implements ConstraintValidator<UpdatableEmail, String> {
    private final UserService userService;
    private final HttpServletRequest httpServletRequest;

    public UpdatableEmailValidation(UserService userService, HttpServletRequest httpServletRequest) {
        this.userService = userService;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public void initialize(UpdatableEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        Authentication authentication = (Authentication) httpServletRequest.getAttribute("authentication");

        if (!authentication.isAuthenticated()) {
            return false;
        }

        if (authentication.getUser().getEmail().equals(email)) {
            return true;
        }

        User user = userService.getUserByEmail(email);

        return user == null;
    }
}

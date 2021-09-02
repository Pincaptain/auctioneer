package com.gjorovski.auctioneer.user.validation;

import com.gjorovski.auctioneer.user.User;
import com.gjorovski.auctioneer.user.UserService;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueEmailValidation implements ConstraintValidator<UniqueEmail, String> {
    private final UserService userService;

    public UniqueEmailValidation(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        User user = userService.getUserByEmail(email);

        return user == null;
    }
}

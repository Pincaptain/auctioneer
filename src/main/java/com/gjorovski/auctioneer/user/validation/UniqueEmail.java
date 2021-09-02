package com.gjorovski.auctioneer.user.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailValidation.class)
public @interface UniqueEmail {
    String message() default "User with that email already exists.";

    Class<?>[] groups() default {};

    public abstract Class<? extends Payload>[] payload() default {};
}

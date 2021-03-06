package com.gjorovski.auctioneer.user.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UpdatableUsernameValidation.class)
public @interface UpdatableUsername {
    String message() default "User with that username already exists.";

    @SuppressWarnings("unused") Class<?>[] groups() default {};

    @SuppressWarnings({"UnnecessaryInterfaceModifier", "unused"}) public abstract Class<? extends Payload>[] payload() default {};
}

package com.gjorovski.auctioneer.auth.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UpdatableGroupNameValidation.class)
public @interface UpdatableGroupName {
    String message() default "Group with that name already exists.";

    @SuppressWarnings("unused") Class<?>[] groups() default {};

    @SuppressWarnings({"unused", "UnnecessaryInterfaceModifier"}) public abstract Class<? extends Payload>[] payload() default {};
}

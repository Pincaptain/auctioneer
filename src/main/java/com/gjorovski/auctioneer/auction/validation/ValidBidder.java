package com.gjorovski.auctioneer.auction.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidBidderValidation.class)
public @interface ValidBidder {
    String message() default "You cannot outbid yourself!";

    @SuppressWarnings("unused") Class<?>[] groups() default {};

    @SuppressWarnings({"unused", "UnnecessaryInterfaceModifier"}) public abstract Class<? extends Payload>[] payload() default {};
}

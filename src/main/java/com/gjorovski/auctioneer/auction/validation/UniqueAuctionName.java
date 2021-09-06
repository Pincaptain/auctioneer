package com.gjorovski.auctioneer.auction.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueAuctionNameValidation.class)
public @interface UniqueAuctionName {
    String message() default "Auction with that email already exists.";

    @SuppressWarnings("unused") Class<?>[] groups() default {};

    @SuppressWarnings({"unused", "UnnecessaryInterfaceModifier"}) public abstract Class<? extends Payload>[] payload() default {};
}

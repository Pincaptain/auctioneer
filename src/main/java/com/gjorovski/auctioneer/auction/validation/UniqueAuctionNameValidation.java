package com.gjorovski.auctioneer.auction.validation;

import com.gjorovski.auctioneer.auction.model.Auction;
import com.gjorovski.auctioneer.auction.service.AuctionService;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueAuctionNameValidation implements ConstraintValidator<UniqueAuctionName, String> {
    private final AuctionService auctionService;

    public UniqueAuctionNameValidation(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @Override
    public void initialize(UniqueAuctionName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        Auction auction = auctionService.getAuctionByName(name);

        return auction == null;
    }
}

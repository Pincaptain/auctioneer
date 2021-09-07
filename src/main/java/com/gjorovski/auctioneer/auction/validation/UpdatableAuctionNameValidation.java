package com.gjorovski.auctioneer.auction.validation;

import com.gjorovski.auctioneer.auction.model.Auction;
import com.gjorovski.auctioneer.auction.service.AuctionService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

@Component
public class UpdatableAuctionNameValidation implements ConstraintValidator<UpdatableAuctionName, String> {
    private final AuctionService auctionService;
    private final HttpServletRequest httpServletRequest;

    public UpdatableAuctionNameValidation(AuctionService auctionService, HttpServletRequest httpServletRequest) {
        this.auctionService = auctionService;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public void initialize(UpdatableAuctionName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        @SuppressWarnings("unchecked")
        Map<String, String> parameters = (Map<String, String>) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long id = Long.parseLong(parameters.get("id"));
        Auction auction = auctionService.getAuctionById(id);

        if (auction.getName().equals(name)) {
            return true;
        }

        Auction other = auctionService.getAuctionByName(name);

        return other == null;
    }
}

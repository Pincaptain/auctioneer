package com.gjorovski.auctioneer.auction.validation;

import com.gjorovski.auctioneer.auction.model.Bid;
import com.gjorovski.auctioneer.auction.model.Lot;
import com.gjorovski.auctioneer.auction.request.BidRequest;
import com.gjorovski.auctioneer.auction.service.LotService;
import com.gjorovski.auctioneer.auth.domain.Authentication;
import com.gjorovski.auctioneer.user.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

@Component
public class ValidBidderValidation implements ConstraintValidator<ValidBidder, BidRequest> {
    private final HttpServletRequest httpServletRequest;
    private final LotService lotService;

    public ValidBidderValidation(HttpServletRequest httpServletRequest, LotService lotService) {
        this.httpServletRequest = httpServletRequest;
        this.lotService = lotService;
    }

    @Override
    public void initialize(ValidBidder constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(BidRequest bidRequest, ConstraintValidatorContext context) {
        Authentication authentication = (Authentication) httpServletRequest.getAttribute("authentication");

        if (!authentication.isAuthenticated()) {
            return false;
        }

        long lotId = getLotId();
        Lot lot = lotService.getLotById(lotId);
        User user = authentication.getUser();
        Bid bid = lotService.getLatestBid(lot);

        if (bid == null) {
            return true;
        }

        return !bid.getBidder().getId().equals(user.getId());
    }

    private long getLotId() {
        @SuppressWarnings("unchecked")
        Map<String, String> parameters = (Map<String, String>) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        return Long.parseLong(parameters.get("lotId"));
    }
}

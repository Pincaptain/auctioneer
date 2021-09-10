package com.gjorovski.auctioneer.auction.validation;

import com.gjorovski.auctioneer.auction.model.Bid;
import com.gjorovski.auctioneer.auction.model.Lot;
import com.gjorovski.auctioneer.auction.service.LotService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

@Component
public class ValidBidValidation implements ConstraintValidator<ValidBid, Double> {
    private final HttpServletRequest httpServletRequest;
    private final LotService lotService;

    public ValidBidValidation(HttpServletRequest httpServletRequest, LotService lotService) {
        this.httpServletRequest = httpServletRequest;
        this.lotService = lotService;
    }

    @Override
    public void initialize(ValidBid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Double bid, ConstraintValidatorContext context) {
        @SuppressWarnings("unchecked")
        Map<String, String> parameters = (Map<String, String>) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long lotId = Long.parseLong(parameters.get("lotId"));
        Lot lot = lotService.getLotById(lotId);
        Bid currentBid = lotService.getLatestBid(lot);

        if (currentBid == null) {
            return true;
        }

        return !(bid <= currentBid.getBid());
    }
}

package com.gjorovski.auctioneer.auction.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class BidRequest {
    @NotNull(message = "Bid cannot be null.")
    @Min(value = 1, message = "Your bid must be at cannot be lower than 1.")
    @Max(value = Long.MAX_VALUE, message = "You won!")
    private double bid;
}

package com.gjorovski.auctioneer.auction.request;

import com.gjorovski.auctioneer.auction.validation.ValidBid;
import com.gjorovski.auctioneer.auction.validation.ValidBidder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@ValidBidder
public class BidRequest {
    @NotNull(message = "Bid cannot be null.")
    @Min(value = 1, message = "Your bid must be at cannot be lower than 1.")
    @Max(value = Long.MAX_VALUE, message = "You won!")
    @ValidBid
    private double bid;
}

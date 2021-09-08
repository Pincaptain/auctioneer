package com.gjorovski.auctioneer.auction.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateLotRequest {
    @NotNull(message = "Count cannot be null.")
    private int count;

    @NotNull(message = "Item Id cannot be null.")
    private long itemId;

    @NotNull(message = "Starting price cannot be null.")
    private double startingPrice;
}

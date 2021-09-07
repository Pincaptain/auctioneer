package com.gjorovski.auctioneer.auction.request;

import com.gjorovski.auctioneer.auction.validation.UpdatableAuctionName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateAuctionRequest {
    @NotNull(message = "Name cannot be null.")
    @NotBlank(message = "Name cannot be blank.")
    @UpdatableAuctionName
    private String name;

    private String details;
}

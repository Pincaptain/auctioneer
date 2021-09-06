package com.gjorovski.auctioneer.auction.request;

import com.gjorovski.auctioneer.auction.validation.UniqueAuctionName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateAuctionRequest {
    @NotNull(message = "Name cannot be null.")
    @NotBlank(message = "Name cannot be blank.")
    @UniqueAuctionName
    private String name;

    private String details;
}

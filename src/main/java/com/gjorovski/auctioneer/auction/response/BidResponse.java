package com.gjorovski.auctioneer.auction.response;

import com.gjorovski.auctioneer.user.response.UserResponse;
import lombok.Data;

@Data
public class BidResponse {
    private Long id;
    private double bid;
    private UserResponse bidder;
}

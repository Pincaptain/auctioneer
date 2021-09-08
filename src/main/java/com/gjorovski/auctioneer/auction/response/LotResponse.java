package com.gjorovski.auctioneer.auction.response;

import com.gjorovski.auctioneer.user.response.UserResponse;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LotResponse {
    private Long id;
    private ItemResponse item;
    private int count;
    private double startingPrice;
    private double currentBid;
    private UserResponse seller;
    private UserResponse highestBidder;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
}

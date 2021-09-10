package com.gjorovski.auctioneer.auction.response;

import com.gjorovski.auctioneer.user.response.UserResponse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class LotResponse {
    private Long id;
    private ItemResponse item;
    private int count;
    private double startingPrice;
    private UserResponse seller;
    private List<BidResponse> bids;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
}

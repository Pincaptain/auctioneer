package com.gjorovski.auctioneer.auction.response;

import lombok.Data;

@Data
public class ItemResponse {
    private Long id;
    private String name;
    private String details;
    private long value;
}

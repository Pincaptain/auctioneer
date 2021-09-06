package com.gjorovski.auctioneer.auction.model;

import com.gjorovski.auctioneer.user.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lot", schema = "public")
@NoArgsConstructor
@Getter
@Setter
public class Lot {
    private static final int DEFAULT_LISTING_DURATION = 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(name = "count", nullable = false)
    private int count;

    @Column(name = "starting_price", nullable = false)
    private double startingPrice;

    @Column(name = "current_bid")
    private double currentBid;

    @ManyToOne
    @JoinColumn(name = "bidder_id")
    private User highestBidder;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "expires_at", nullable = false, updatable = false)
    private LocalDateTime expiresAt = createdAt.plusHours(DEFAULT_LISTING_DURATION);
}

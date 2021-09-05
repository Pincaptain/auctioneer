package com.gjorovski.auctioneer.auction.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

    @Column(name = "current_bid", nullable = false)
    private double currentBid = startingPrice;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "expires_at", nullable = false, updatable = false)
    private LocalDateTime expiresAt = createdAt.plusHours(DEFAULT_LISTING_DURATION);
}

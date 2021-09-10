package com.gjorovski.auctioneer.auction.model;

import com.gjorovski.auctioneer.user.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "bid", schema = "public")
@NoArgsConstructor
@Getter
@Setter
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bid")
    private double bid;

    @ManyToOne
    @JoinColumn(name = "bidder_id")
    private User bidder;

    public Bid(double bid, User bidder) {
        this.bid = bid;
        this.bidder = bidder;
    }
}

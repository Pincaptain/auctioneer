package com.gjorovski.auctioneer.auction.repository;

import com.gjorovski.auctioneer.auction.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    Auction findFirstByName(String name);
}

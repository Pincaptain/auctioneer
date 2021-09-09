package com.gjorovski.auctioneer.auction.repository;

import com.gjorovski.auctioneer.auction.model.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LotRepository extends JpaRepository<Lot, Long> {
    Lot findTopByOrderByIdDesc();
}

package com.gjorovski.auctioneer.auction.repository;

import com.gjorovski.auctioneer.auction.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}

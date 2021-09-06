package com.gjorovski.auctioneer.auction.service;

import com.gjorovski.auctioneer.auction.model.Auction;
import com.gjorovski.auctioneer.auction.repository.AuctionRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionService {
    private final AuctionRepository auctionRepository;

    public AuctionService(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    public List<Auction> getAuctions() {
        return auctionRepository.findAll();
    }

    public Auction getAuctionById(long id) {
        return auctionRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Auction getAuctionByName(String name) {
        return auctionRepository.findFirstByName(name);
    }

    public Auction createAuction(Auction auction) {
        return auctionRepository.save(auction);
    }
}

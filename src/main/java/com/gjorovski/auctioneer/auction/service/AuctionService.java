package com.gjorovski.auctioneer.auction.service;

import com.gjorovski.auctioneer.auction.model.Auction;
import com.gjorovski.auctioneer.auction.repository.AuctionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionService {
    private final AuctionRepository auctionRepository;
    private final ModelMapper modelMapper;

    public AuctionService(AuctionRepository auctionRepository, ModelMapper modelMapper) {
        this.auctionRepository = auctionRepository;
        this.modelMapper = modelMapper;
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

    public Auction updateAuction(long id, Auction auction) {
        Auction currentAuction = getAuctionById(id);

        modelMapper.map(auction, currentAuction);

        return auctionRepository.save(currentAuction);
    }

    public Auction deleteAuction(long id) {
        Auction auction = getAuctionById(id);
        auctionRepository.delete(auction);

        return auction;
    }
}

package com.gjorovski.auctioneer.auction.service;

import com.gjorovski.auctioneer.auction.model.Auction;
import com.gjorovski.auctioneer.auction.model.Lot;
import com.gjorovski.auctioneer.auction.repository.AuctionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionService {
    private final AuctionRepository auctionRepository;
    private final ModelMapper modelMapper;
    private final LotService lotService;

    public AuctionService(AuctionRepository auctionRepository, ModelMapper modelMapper, LotService lotService) {
        this.auctionRepository = auctionRepository;
        this.modelMapper = modelMapper;
        this.lotService = lotService;
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

    public List<Lot> getAuctionLots(long id) {
        Auction auction = getAuctionById(id);

        return auction.getLots();
    }

    public Lot getAuctionLot(long auctionId, long lotId) {
        Auction auction = getAuctionById(auctionId);

        return auction.getLots().stream()
                .filter(lot -> lot.getId().equals(lotId))
                .findFirst()
                .orElseThrow(ResourceNotFoundException::new);
    }

    public Lot createAuctionLot(long auctionId, Lot lot) {
        Lot createdLot = lotService.createLot(lot);
        Auction auction = getAuctionById(auctionId);
        auction.addLot(createdLot);

        auctionRepository.save(auction);

        return createdLot;
    }
}

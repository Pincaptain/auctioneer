package com.gjorovski.auctioneer.auction.service;

import com.gjorovski.auctioneer.auction.model.Auction;
import com.gjorovski.auctioneer.auction.model.Bid;
import com.gjorovski.auctioneer.auction.model.Lot;
import com.gjorovski.auctioneer.auction.repository.AuctionRepository;
import com.gjorovski.auctioneer.shared.exceptions.BadRequestException;
import com.gjorovski.auctioneer.shared.exceptions.PermissionDeniedException;
import com.gjorovski.auctioneer.user.model.User;
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
        Auction auction = getAuctionById(auctionId);
        auction.addLot(lot);

        auctionRepository.save(auction);

        Lot latestLot = lotService.getLatestLot();
        lotService.scheduleLotTask(latestLot);

        return latestLot;
    }

    public Lot deleteAuctionLot(long auctionId, long lotId) {
        Auction auction = getAuctionById(auctionId);
        Lot lot = getAuctionLot(auctionId, lotId);

        auction.removeLot(lot);
        auctionRepository.save(auction);

        return lot;
    }

    public Lot bid(long auctionId, long lotId, double bidAmount, User bidder) {
        Auction auction = getAuctionById(auctionId);
        Lot lot = getAuctionLot(auctionId, lotId);
        Bid bid = new Bid(bidAmount, bidder);

        if (lot.getSeller().getId().equals(bidder.getId())) {
            throw new BadRequestException("You cannot bid on your own lot.");
        }

        lot.addBid(bid);
        auctionRepository.save(auction);

        return lot;
    }

    public Lot cancelBid(long auctionId, long lotId, User user) {
        Auction auction = getAuctionById(auctionId);
        Lot lot = getAuctionLot(auctionId, lotId);
        Bid bid = lotService.getLatestBid(lot);

        if (bid == null) {
            throw new BadRequestException("There are no bids to cancel.");
        }

        if (!bid.getBidder().getId().equals(user.getId())) {
            throw new PermissionDeniedException("The current bid is not yours to cancel.");
        }

        lot.removeBid(bid);
        auctionRepository.save(auction);

        return lot;
    }
}

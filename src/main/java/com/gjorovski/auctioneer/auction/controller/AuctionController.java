package com.gjorovski.auctioneer.auction.controller;

import com.gjorovski.auctioneer.auction.model.Auction;
import com.gjorovski.auctioneer.auction.model.Lot;
import com.gjorovski.auctioneer.auction.request.BidRequest;
import com.gjorovski.auctioneer.auction.request.CreateAuctionRequest;
import com.gjorovski.auctioneer.auction.request.CreateLotRequest;
import com.gjorovski.auctioneer.auction.request.UpdateAuctionRequest;
import com.gjorovski.auctioneer.auction.response.AuctionResponse;
import com.gjorovski.auctioneer.auction.response.LotResponse;
import com.gjorovski.auctioneer.auction.service.AuctionService;
import com.gjorovski.auctioneer.auth.domain.Authentication;
import com.gjorovski.auctioneer.shared.exceptions.PermissionDeniedException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/auctions")
public class AuctionController {
    private final AuctionService auctionService;
    private final ModelMapper modelMapper;

    public AuctionController(AuctionService auctionService, ModelMapper modelMapper) {
        this.auctionService = auctionService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<AuctionResponse>> getAuctions() {
        List<Auction> auctions = auctionService.getAuctions();
        List<AuctionResponse> auctionResponses = auctions.stream()
                .map(auction -> modelMapper.map(auction, AuctionResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(auctionResponses, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<AuctionResponse> getAuction(@PathVariable long id) {
        Auction auction = auctionService.getAuctionById(id);
        AuctionResponse auctionResponse = modelMapper.map(auction, AuctionResponse.class);

        return new ResponseEntity<>(auctionResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AuctionResponse> createAuction(@RequestBody @Valid CreateAuctionRequest createAuctionRequest, @RequestAttribute Authentication authentication) {
        if (!authentication.isAuthenticated()) {
            throw new PermissionDeniedException(Authentication.NOT_AUTHENTICATED_MESSAGE);
        }

        Auction auction = modelMapper.map(createAuctionRequest, Auction.class);
        Auction createdAuction = auctionService.createAuction(auction);
        AuctionResponse auctionResponse = modelMapper.map(createdAuction, AuctionResponse.class);

        return new ResponseEntity<>(auctionResponse, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<AuctionResponse> updateAuction(@PathVariable long id, @RequestBody @Valid UpdateAuctionRequest updateAuctionRequest, @RequestAttribute Authentication authentication) {
        if (!authentication.isAuthenticated()) {
            throw new PermissionDeniedException(Authentication.NOT_AUTHENTICATED_MESSAGE);
        }

        Auction auction = auctionService.getAuctionById(id);

        if (!authentication.isUser(auction.getOwner())) {
            throw new PermissionDeniedException(Authentication.NOT_AUTHENTICATED_MESSAGE);
        }

        Auction auctionChanges = modelMapper.map(updateAuctionRequest, Auction.class);
        Auction updatedAuction = auctionService.updateAuction(id, auctionChanges);
        AuctionResponse auctionResponse = modelMapper.map(updatedAuction, AuctionResponse.class);

        return new ResponseEntity<>(auctionResponse, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<AuctionResponse> deleteAuction(@PathVariable long id, @RequestAttribute Authentication authentication) {
        if (!authentication.isAuthenticated()) {
            throw new PermissionDeniedException(Authentication.NOT_AUTHENTICATED_MESSAGE);
        }

        Auction auction = auctionService.getAuctionById(id);

        if (!authentication.isUser(auction.getOwner())) {
            throw new PermissionDeniedException(Authentication.NOT_AUTHENTICATED_MESSAGE);
        }

        Auction deletedAuction = auctionService.deleteAuction(id);
        AuctionResponse auctionResponse = modelMapper.map(deletedAuction, AuctionResponse.class);

        return new ResponseEntity<>(auctionResponse, HttpStatus.OK);
    }

    @GetMapping("{id}/lots")
    public ResponseEntity<List<LotResponse>> getAuctionLots(@PathVariable long id) {
        List<Lot> lots = auctionService.getAuctionLots(id);
        List<LotResponse> lotResponses = lots.stream()
                .map(lot -> modelMapper.map(lot, LotResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(lotResponses, HttpStatus.OK);
    }

    @GetMapping("{auctionId}/lots/{lotId}")
    public ResponseEntity<LotResponse> getAuctionLot(@PathVariable long auctionId, @PathVariable long lotId) {
        Lot lot = auctionService.getAuctionLot(auctionId, lotId);
        LotResponse lotResponse = modelMapper.map(lot, LotResponse.class);

        return new ResponseEntity<>(lotResponse, HttpStatus.OK);
    }

    @PostMapping("{id}/lots")
    public ResponseEntity<LotResponse> createAuctionLot(@PathVariable long id, @RequestBody @Valid CreateLotRequest createLotRequest, @RequestAttribute Authentication authentication) {
        if (!authentication.isAuthenticated()) {
            throw new PermissionDeniedException(Authentication.NOT_AUTHENTICATED_MESSAGE);
        }

        Lot lot = modelMapper.map(createLotRequest, Lot.class);
        lot.setSeller(authentication.getUser());

        Lot createdLot = auctionService.createAuctionLot(id, lot);
        LotResponse lotResponse = modelMapper.map(createdLot, LotResponse.class);

        return new ResponseEntity<>(lotResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{auctionId}/lots/{lotId}")
    public ResponseEntity<LotResponse> deleteAuctionLot(@PathVariable long auctionId, @PathVariable long lotId, @RequestAttribute Authentication authentication) {
        if (!authentication.isAuthenticated()) {
            throw new PermissionDeniedException(Authentication.NOT_AUTHENTICATED_MESSAGE);
        }

        Lot lot = auctionService.getAuctionLot(auctionId, lotId);

        if (!authentication.isUser(lot.getSeller())) {
            throw new PermissionDeniedException(Authentication.NOT_AUTHENTICATED_MESSAGE);
        }

        Lot deletedLot = auctionService.deleteAuctionLot(auctionId, lotId);
        LotResponse lotResponse = modelMapper.map(deletedLot, LotResponse.class);

        return new ResponseEntity<>(lotResponse, HttpStatus.OK);
    }

    @PostMapping("{auctionId}/lots/{lotId}/bid")
    public ResponseEntity<LotResponse> bid(@PathVariable long auctionId, @PathVariable long lotId, @RequestBody @Valid BidRequest bidRequest, @RequestAttribute Authentication authentication) {
        if (!authentication.isAuthenticated()) {
            throw new PermissionDeniedException(Authentication.NOT_AUTHENTICATED_MESSAGE);
        }

        Lot lot = auctionService.bid(auctionId, lotId, bidRequest.getBid(), authentication.getUser());
        LotResponse lotResponse = modelMapper.map(lot, LotResponse.class);

        return new ResponseEntity<>(lotResponse, HttpStatus.OK);
    }

    @GetMapping("{auctionId}/lots/{lotId}/cancel_bid")
    public ResponseEntity<LotResponse> cancelBid(@PathVariable long auctionId, @PathVariable long lotId, @RequestAttribute Authentication authentication) {
        if (!authentication.isAuthenticated()) {
            throw new PermissionDeniedException(Authentication.NOT_AUTHENTICATED_MESSAGE);
        }

        Lot lot = auctionService.cancelBid(auctionId, lotId, authentication.getUser());
        LotResponse lotResponse = modelMapper.map(lot, LotResponse.class);

        return new ResponseEntity<>(lotResponse, HttpStatus.OK);
    }
}

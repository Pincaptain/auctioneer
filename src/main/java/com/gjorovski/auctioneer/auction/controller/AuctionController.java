package com.gjorovski.auctioneer.auction.controller;

import com.gjorovski.auctioneer.auction.model.Auction;
import com.gjorovski.auctioneer.auction.request.CreateAuctionRequest;
import com.gjorovski.auctioneer.auction.response.AuctionResponse;
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
}

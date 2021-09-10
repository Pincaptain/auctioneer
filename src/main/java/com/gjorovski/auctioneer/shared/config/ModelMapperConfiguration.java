package com.gjorovski.auctioneer.shared.config;

import com.gjorovski.auctioneer.auction.model.Auction;
import com.gjorovski.auctioneer.auction.model.Bid;
import com.gjorovski.auctioneer.auction.model.Item;
import com.gjorovski.auctioneer.auction.model.Lot;
import com.gjorovski.auctioneer.auction.request.CreateLotRequest;
import com.gjorovski.auctioneer.auction.response.BidResponse;
import com.gjorovski.auctioneer.auction.response.ItemResponse;
import com.gjorovski.auctioneer.auction.response.LotResponse;
import com.gjorovski.auctioneer.shared.converter.BidConverter;
import com.gjorovski.auctioneer.shared.converter.ItemConverter;
import com.gjorovski.auctioneer.shared.converter.ModelMapperConverter;
import com.gjorovski.auctioneer.user.model.User;
import com.gjorovski.auctioneer.user.response.UserResponse;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {
    public final ItemConverter itemConverter;

    public ModelMapperConfiguration(ItemConverter itemConverter) {
        this.itemConverter = itemConverter;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.addMappings(auctionPropertyMap());
        modelMapper.addMappings(bidPropertyMap());
        modelMapper.addMappings(lotPropertyMap());
        modelMapper.addMappings(createLotPropertyMap());

        return modelMapper;
    }

    private PropertyMap<Auction, Auction> auctionPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getLots());
            }
        };
    }

    private PropertyMap<Lot, LotResponse> lotPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                using(new ModelMapperConverter<Item, ItemResponse>()).map(source.getItem(), destination.getItem());
                using(new ModelMapperConverter<User, UserResponse>()).map(source.getSeller(), destination.getSeller());
                using(new BidConverter()).map(source.getBids(), destination.getBids());
            }
        };
    }

    private PropertyMap<CreateLotRequest, Lot> createLotPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                using(itemConverter).map(source.getItemId(), destination.getItem());
            }
        };
    }

    private PropertyMap<Bid, BidResponse> bidPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                using(new ModelMapperConverter<User, UserResponse>()).map(source.getBidder(), destination.getBidder());
            }
        };
    }
}

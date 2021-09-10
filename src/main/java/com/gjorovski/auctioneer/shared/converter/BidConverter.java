package com.gjorovski.auctioneer.shared.converter;

import com.gjorovski.auctioneer.auction.model.Bid;
import com.gjorovski.auctioneer.auction.response.BidResponse;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;

import java.util.List;
import java.util.stream.Collectors;

public class BidConverter implements Converter<List<Bid>, List<BidResponse>> {
    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<BidResponse> convert(MappingContext<List<Bid>, List<BidResponse>> context) {
        return context.getSource()
                .stream()
                .map(s -> modelMapper.map(s, BidResponse.class))
                .collect(Collectors.toList());
    }
}

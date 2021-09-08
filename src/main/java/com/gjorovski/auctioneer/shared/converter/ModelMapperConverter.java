package com.gjorovski.auctioneer.shared.converter;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperConverter<S, D> implements Converter<S, D> {
    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public D convert(MappingContext<S, D> context) {
        return modelMapper.map(context.getSource(), context.getDestinationType());
    }
}
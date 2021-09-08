package com.gjorovski.auctioneer.shared.converter;

import com.gjorovski.auctioneer.auction.model.Item;
import com.gjorovski.auctioneer.auction.service.ItemService;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class ItemConverter implements Converter<Long, Item> {
    private final ItemService itemService;

    public ItemConverter(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public Item convert(MappingContext<Long, Item> context) {
        return itemService.getItemById(context.getSource());
    }
}

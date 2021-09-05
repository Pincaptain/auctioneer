package com.gjorovski.auctioneer.auction.service;

import com.gjorovski.auctioneer.auction.model.Item;
import com.gjorovski.auctioneer.auction.repository.ItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    public final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item createItem(Item item) {
        return itemRepository.save(item);
    }
}

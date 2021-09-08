package com.gjorovski.auctioneer.auction.service;

import com.gjorovski.auctioneer.auction.model.Item;
import com.gjorovski.auctioneer.auction.repository.ItemRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    public final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item getItemById(long id) {
        return itemRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Item createItem(Item item) {
        return itemRepository.save(item);
    }
}

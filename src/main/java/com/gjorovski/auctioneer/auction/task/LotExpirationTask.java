package com.gjorovski.auctioneer.auction.task;

import com.gjorovski.auctioneer.auction.model.Lot;
import com.gjorovski.auctioneer.auction.service.LotService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

public class LotExpirationTask implements Runnable {
    private final LotService lotService;

    @Getter
    private final Lot lot;

    private final Logger logger = LoggerFactory.getLogger(LotExpirationTask.class);

    public LotExpirationTask(LotService lotService, Lot lot) {
        this.lotService = lotService;
        this.lot = lot;
    }

    @Override
    public void run() {
        Lot finishedLot;

        try {
            finishedLot = lotService.getLotById(lot.getId());
        } catch (ResourceNotFoundException resourceNotFoundException) {
            logger.error(resourceNotFoundException.getMessage());
            return;
        }

        Lot deletedLot = lotService.deleteLot(finishedLot);

        logger.info(String.format("Lot with id(%d) expired and was promptly deleted.", deletedLot.getId()));
    }
}

package com.gjorovski.auctioneer.auction.task;

import com.gjorovski.auctioneer.auction.model.Lot;
import com.gjorovski.auctioneer.auction.service.LotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LotManagerTask implements Runnable {
    private final LotService lotService;
    private final Lot lot;

    private final Logger logger = LoggerFactory.getLogger(LotManagerTask.class);

    public LotManagerTask(LotService lotService, Lot lot) {
        this.lotService = lotService;
        this.lot = lot;
    }

    @Override
    public void run() {
        Lot deletedLot = lotService.deleteLot(lot);

        logger.info(String.format("Lot with id(%d) expired and was promptly deleted.", deletedLot.getId()));
    }
}

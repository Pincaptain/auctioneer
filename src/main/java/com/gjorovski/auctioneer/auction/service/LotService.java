package com.gjorovski.auctioneer.auction.service;

import com.gjorovski.auctioneer.auction.model.Lot;
import com.gjorovski.auctioneer.auction.repository.LotRepository;
import com.gjorovski.auctioneer.auction.task.LotExpirationTask;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

@Service
public class LotService {
    private final LotRepository lotRepository;
    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;

    public LotService(LotRepository lotRepository, ThreadPoolTaskScheduler threadPoolTaskScheduler) {
        this.lotRepository = lotRepository;
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
    }

    public List<Lot> getLots() {
        return lotRepository.findAll();
    }

    public Lot createLot(Lot lot) {
        Lot createdLot = lotRepository.save(lot);

        scheduleLotTask(createdLot);

        return createdLot;
    }

    public void scheduleLotTask(Lot lot) {
        Instant expirationDate = lot.getExpiresAt().atZone(ZoneId.of("Europe/Paris")).toInstant();
        LotExpirationTask lotExpirationTask = new LotExpirationTask(this, lot);

        threadPoolTaskScheduler.schedule(lotExpirationTask, expirationDate);
    }

    public Lot deleteLot(Lot lot) {
        lotRepository.delete(lot);

        return lot;
    }
}

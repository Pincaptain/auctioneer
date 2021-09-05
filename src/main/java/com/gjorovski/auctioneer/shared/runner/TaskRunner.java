package com.gjorovski.auctioneer.shared.runner;

import com.gjorovski.auctioneer.auction.model.Lot;
import com.gjorovski.auctioneer.auction.service.LotService;
import com.gjorovski.auctioneer.auction.task.LotManagerTask;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

@Component
@Order(1)
public class TaskRunner implements CommandLineRunner {
    private final LotService lotService;
    private final ThreadPoolTaskScheduler scheduler;

    public TaskRunner(LotService lotService, ThreadPoolTaskScheduler scheduler) {
        this.lotService = lotService;
        this.scheduler = scheduler;
    }

    @Override
    public void run(String... args) {
        createLotSchedules();
    }

    private void createLotSchedules() {
        List<Lot> lots = lotService.getLots();

        lots.forEach(lot -> {
            LotManagerTask lotManagerTask = new LotManagerTask(lotService, lot);
            Instant expirationDate = lot.getExpiresAt().toInstant(ZoneOffset.UTC);

            scheduler.schedule(lotManagerTask, expirationDate);
        });
    }
}

package com.gjorovski.auctioneer.shared.runner;

import com.gjorovski.auctioneer.auction.model.Lot;
import com.gjorovski.auctioneer.auction.service.LotService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(1)
public class TaskRunner implements CommandLineRunner {
    private final LotService lotService;

    public TaskRunner(LotService lotService) {
        this.lotService = lotService;
    }

    @Override
    public void run(String... args) {
        createLotSchedules();
    }

    private void createLotSchedules() {
        List<Lot> lots = lotService.getLots();

        lots.forEach(lotService::scheduleLotTask);
    }
}

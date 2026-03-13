package com.nse.option.scheduler;

import com.nse.option.model.callput.MarketSnapshot;
import com.nse.option.service.MarketSnapshotService;
import com.nse.option.service.SnapshotHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SnapshotScheduler
{
    @Autowired
    private MarketSnapshotService marketSnapshotService;

    @Autowired
    private SnapshotHistoryService snapshotHistoryService;

    // Run every 5 minutes (300,000 ms)
    @Scheduled(fixedRate = 300000)
    //@Scheduled(cron = "0 5/5 9-16 * * 1-5")
    public void fetchAndStoreSnapshot() {
        System.out.println("Scheduler: Fetching market snapshot at " + LocalDateTime.now());
        try {
            MarketSnapshot snapshot = marketSnapshotService.getMarketSnapshot();
            if (snapshot != null) {
                marketSnapshotService.enrichSnapshot(snapshot);
                snapshotHistoryService.addSnapshot(snapshot);
                System.out.println("Scheduler: Snapshot stored successfully.");
            } else {
                System.out.println("Scheduler: Fetched snapshot is null.");
            }
        } catch (Exception e) {
            System.err.println("Scheduler: Error fetching snapshot: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

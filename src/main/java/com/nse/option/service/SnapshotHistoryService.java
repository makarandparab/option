package com.nse.option.service;

import com.nse.option.model.callput.MarketSnapshot;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

@Service
public class SnapshotHistoryService
{
    private final ConcurrentLinkedDeque<SnapshotEntry> history = new ConcurrentLinkedDeque<>();
    private static final int MAX_HISTORY_MINUTES = 60; // Keep last 60 minutes

    private static class SnapshotEntry {
        LocalDateTime timestamp;
        MarketSnapshot snapshot;

        SnapshotEntry(LocalDateTime timestamp, MarketSnapshot snapshot) {
            this.timestamp = timestamp;
            this.snapshot = snapshot;
        }
    }

    public void addSnapshot(MarketSnapshot snapshot) {
        if (snapshot == null)
            return;
        history.addFirst(new SnapshotEntry(LocalDateTime.now(), snapshot));

        // Cleanup old snapshots
        LocalDateTime cutoff = LocalDateTime.now().minusMinutes(MAX_HISTORY_MINUTES);
        while (!history.isEmpty() && history.peekLast().timestamp.isBefore(cutoff)) {
            history.pollLast();
        }
    }

    public MarketSnapshot getSnapshotAt(int minutesAgo) {
        LocalDateTime targetTime = LocalDateTime.now().minusMinutes(minutesAgo);
        // Find snapshot closest to target time within a tolerance (e.g. 2 minutes)

        List<SnapshotEntry> candidates = history.stream()
                .filter(e -> Math.abs(ChronoUnit.MINUTES.between(e.timestamp, targetTime)) <= 2)
                .collect(Collectors.toList());

        if (candidates.isEmpty())
            return null;

        // Return the one closest to targetTime
        SnapshotEntry bestMatch = candidates.get(0);
        long minDiff = Math.abs(ChronoUnit.SECONDS.between(bestMatch.timestamp, targetTime));

        for (SnapshotEntry entry : candidates) {
            long diff = Math.abs(ChronoUnit.SECONDS.between(entry.timestamp, targetTime));
            if (diff < minDiff) {
                minDiff = diff;
                bestMatch = entry;
            }
        }

        return bestMatch.snapshot;
    }
}

package com.nse.option.service;

import com.nse.option.model.callput.MarketSnapshot;

public interface MarketSnapshotService
{
    public MarketSnapshot getMarketSnapshot();
    public void enrichSnapshot(MarketSnapshot snapshot); // Added this method
}

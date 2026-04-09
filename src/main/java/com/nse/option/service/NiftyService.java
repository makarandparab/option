package com.nse.option.service;

import com.nse.option.model.iv.ScreenerResponse;
import com.nse.option.model.nifty.OptionChainResponse;

public interface NiftyService
{
    public OptionChainResponse getNiftyOptionData();
    public String getNiftyData();
}

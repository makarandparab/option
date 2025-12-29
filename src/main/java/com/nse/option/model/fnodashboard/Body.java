package com.nse.option.model.fnodashboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Body
{
    @JsonProperty("overViewData")
    private Map<String, IndexOverview> overViewData; // Dynamic keys: NIFTY, BANKNIFTY, etc.

    public Map<String, IndexOverview> getOverViewData() {
        return overViewData;
    }

    public void setOverViewData(Map<String, IndexOverview> overViewData) {
        this.overViewData = overViewData;
    }
}

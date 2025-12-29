  package com.nse.option.model.callput;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InputData 
{
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate tradingDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING) // Example: "2025-12-11T14:44:59+05:30"
    private OffsetDateTime lastUpdatedDateTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime minTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime maxTime;

    private int minStrikePrice;
    private int maxStrikePrice;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private List<LocalDate> expDateList; 


    public LocalDate getTradingDate() {
        return tradingDate;
    }

    public void setTradingDate(LocalDate tradingDate) {
        this.tradingDate = tradingDate;
    }

    public OffsetDateTime getLastUpdatedDateTime() {
        return lastUpdatedDateTime;
    }

    public void setLastUpdatedDateTime(OffsetDateTime lastUpdatedDateTime) {
        this.lastUpdatedDateTime = lastUpdatedDateTime;
    }

    public LocalTime getMinTime() {
        return minTime;
    }

    public void setMinTime(LocalTime minTime) {
        this.minTime = minTime;
    }

    public LocalTime getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(LocalTime maxTime) {
        this.maxTime = maxTime;
    }

    public int getMinStrikePrice() {
        return minStrikePrice;
    }

    public void setMinStrikePrice(int minStrikePrice) {
        this.minStrikePrice = minStrikePrice;
    }

    public int getMaxStrikePrice() {
        return maxStrikePrice;
    }

    public void setMaxStrikePrice(int maxStrikePrice) {
        this.maxStrikePrice = maxStrikePrice;
    }

    public List<LocalDate> getExpDateList() {
        return expDateList;
    }

    public void setExpDateList(List<LocalDate> expDateList) {
        this.expDateList = expDateList;
    }

}

package com.nse.option.model.callput;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

 @JsonIgnoreProperties(ignoreUnknown = true)
public class OverallData 
{
    private long totalVol;
    private long totalCallOi;
    private long totalCallOiChange;
    private double totalCallOiChangeP;

    private long totalPutOi;
    private long totalPutOiChange;
    private double totalPutOiChangeP;

    private double totalPCR;

    private double spotPrice;
    private double spotChange;
    private double spotChangeP;

    private int atm;

    private boolean hideTimeSlider;

    private List<Integer> strikePriceList;

    public long getTotalVol() {
        return totalVol;
    }

    public void setTotalVol(long totalVol) {
        this.totalVol = totalVol;
    }

    public long getTotalCallOi() {
        return totalCallOi;
    }

    public void setTotalCallOi(long totalCallOi) {
        this.totalCallOi = totalCallOi;
    }

    public long getTotalCallOiChange() {
        return totalCallOiChange;
    }

    public void setTotalCallOiChange(long totalCallOiChange) {
        this.totalCallOiChange = totalCallOiChange;
    }

    public double getTotalCallOiChangeP() {
        return totalCallOiChangeP;
    }

    public void setTotalCallOiChangeP(double totalCallOiChangeP) {
        this.totalCallOiChangeP = totalCallOiChangeP;
    }

    public long getTotalPutOi() {
        return totalPutOi;
    }

    public void setTotalPutOi(long totalPutOi) {
        this.totalPutOi = totalPutOi;
    }

    public long getTotalPutOiChange() {
        return totalPutOiChange;
    }

    public void setTotalPutOiChange(long totalPutOiChange) {
        this.totalPutOiChange = totalPutOiChange;
    }

    public double getTotalPutOiChangeP() {
        return totalPutOiChangeP;
    }

    public void setTotalPutOiChangeP(double totalPutOiChangeP) {
        this.totalPutOiChangeP = totalPutOiChangeP;
    }

    public double getTotalPCR() {
        return totalPCR;
    }

    public void setTotalPCR(double totalPCR) {
        this.totalPCR = totalPCR;
    }

    public double getSpotPrice() {
        return spotPrice;
    }

    public void setSpotPrice(double spotPrice) {
        this.spotPrice = spotPrice;
    }

    public double getSpotChange() {
        return spotChange;
    }

    public void setSpotChange(double spotChange) {
        this.spotChange = spotChange;
    }

    public double getSpotChangeP() {
        return spotChangeP;
    }

    public void setSpotChangeP(double spotChangeP) {
        this.spotChangeP = spotChangeP;
    }

    public int getAtm() {
        return atm;
    }

    public void setAtm(int atm) {
        this.atm = atm;
    }

    public boolean isHideTimeSlider() {
        return hideTimeSlider;
    }

    public void setHideTimeSlider(boolean hideTimeSlider) {
        this.hideTimeSlider = hideTimeSlider;
    }

    public List<Integer> getStrikePriceList() {
        return strikePriceList;
    }

    public void setStrikePriceList(List<Integer> strikePriceList) {
        this.strikePriceList = strikePriceList;
    }
     
}

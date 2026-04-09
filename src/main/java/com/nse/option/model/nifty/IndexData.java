package com.nse.option.model.nifty;

public class IndexData
{

    private String indexName;
    private double open;
    private double high;
    private double low;
    private double last;
    private double previousClose;
    private double percChange;
    private double yearHigh;
    private double yearLow;
    private String timeVal;

    private Object constituents;   // null or future complex object
    private double indicativeClose;
    private double icChange;
    private double icPerChange;
    private String isConstituents;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getLast() {
        return last;
    }

    public void setLast(double last) {
        this.last = last;
    }

    public double getPreviousClose() {
        return previousClose;
    }

    public void setPreviousClose(double previousClose) {
        this.previousClose = previousClose;
    }

    public double getPercChange() {
        return percChange;
    }

    public void setPercChange(double percChange) {
        this.percChange = percChange;
    }

    public double getYearHigh() {
        return yearHigh;
    }

    public void setYearHigh(double yearHigh) {
        this.yearHigh = yearHigh;
    }

    public double getYearLow() {
        return yearLow;
    }

    public void setYearLow(double yearLow) {
        this.yearLow = yearLow;
    }

    public String getTimeVal() {
        return timeVal;
    }

    public void setTimeVal(String timeVal) {
        this.timeVal = timeVal;
    }

    public Object getConstituents() {
        return constituents;
    }

    public void setConstituents(Object constituents) {
        this.constituents = constituents;
    }

    public double getIndicativeClose() {
        return indicativeClose;
    }

    public void setIndicativeClose(double indicativeClose) {
        this.indicativeClose = indicativeClose;
    }

    public double getIcChange() {
        return icChange;
    }

    public void setIcChange(double icChange) {
        this.icChange = icChange;
    }

    public double getIcPerChange() {
        return icPerChange;
    }

    public void setIcPerChange(double icPerChange) {
        this.icPerChange = icPerChange;
    }

    public String getIsConstituents() {
        return isConstituents;
    }

    public void setIsConstituents(String isConstituents) {
        this.isConstituents = isConstituents;
    }
}

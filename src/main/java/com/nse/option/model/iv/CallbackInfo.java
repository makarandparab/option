package com.nse.option.model.iv;

public class CallbackInfo
{
    private String bseCode;
    private String nseCode;
    private String isin;
    private String contractType;
    private String expiry;
    private String primaryExchange;
    private String nseExchToken;

    private long contract_id;
    private int lotSize;
    private String optionType;
    private double strikePrice;

    public String getBseCode() {
        return bseCode;
    }

    public void setBseCode(String bseCode) {
        this.bseCode = bseCode;
    }

    public String getNseCode() {
        return nseCode;
    }

    public void setNseCode(String nseCode) {
        this.nseCode = nseCode;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getPrimaryExchange() {
        return primaryExchange;
    }

    public void setPrimaryExchange(String primaryExchange) {
        this.primaryExchange = primaryExchange;
    }

    public String getNseExchToken() {
        return nseExchToken;
    }

    public void setNseExchToken(String nseExchToken) {
        this.nseExchToken = nseExchToken;
    }

    public long getContract_id() {
        return contract_id;
    }

    public void setContract_id(long contract_id) {
        this.contract_id = contract_id;
    }

    public int getLotSize() {
        return lotSize;
    }

    public void setLotSize(int lotSize) {
        this.lotSize = lotSize;
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public double getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(double strikePrice) {
        this.strikePrice = strikePrice;
    }
}

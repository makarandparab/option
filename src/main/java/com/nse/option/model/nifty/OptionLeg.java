package com.nse.option.model.nifty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OptionLeg
{
    private Integer strikePrice;
    private String expiryDate;
    private String underlying;
    private String identifier;

    private Integer openInterest;
    private Integer changeinOpenInterest;
    private BigDecimal pchangeinOpenInterest;

    private Integer totalTradedVolume;

    private BigDecimal impliedVolatility;

    private BigDecimal lastPrice;
    private BigDecimal change;

    private Integer totalBuyQuantity;
    private Integer totalSellQuantity;

    private BigDecimal buyPrice1;
    private Integer buyQuantity1;

    private BigDecimal sellPrice1;
    private Integer sellQuantity1;

    private BigDecimal underlyingValue;

    private String optionType; // JSON shows null; keep as String for future compatibility.

    private BigDecimal pchange;

    public Integer getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(Integer strikePrice) {
        this.strikePrice = strikePrice;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getUnderlying() {
        return underlying;
    }

    public void setUnderlying(String underlying) {
        this.underlying = underlying;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Integer getOpenInterest() {
        return openInterest;
    }

    public void setOpenInterest(Integer openInterest) {
        this.openInterest = openInterest;
    }

    public Integer getChangeinOpenInterest() {
        return changeinOpenInterest;
    }

    public void setChangeinOpenInterest(Integer changeinOpenInterest) {
        this.changeinOpenInterest = changeinOpenInterest;
    }

    public BigDecimal getPchangeinOpenInterest() {
        return pchangeinOpenInterest;
    }

    public void setPchangeinOpenInterest(BigDecimal pchangeinOpenInterest) {
        this.pchangeinOpenInterest = pchangeinOpenInterest;
    }

    public Integer getTotalTradedVolume() {
        return totalTradedVolume;
    }

    public void setTotalTradedVolume(Integer totalTradedVolume) {
        this.totalTradedVolume = totalTradedVolume;
    }

    public BigDecimal getImpliedVolatility() {
        return impliedVolatility;
    }

    public void setImpliedVolatility(BigDecimal impliedVolatility) {
        this.impliedVolatility = impliedVolatility;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }

    public Integer getTotalBuyQuantity() {
        return totalBuyQuantity;
    }

    public void setTotalBuyQuantity(Integer totalBuyQuantity) {
        this.totalBuyQuantity = totalBuyQuantity;
    }

    public Integer getTotalSellQuantity() {
        return totalSellQuantity;
    }

    public void setTotalSellQuantity(Integer totalSellQuantity) {
        this.totalSellQuantity = totalSellQuantity;
    }

    public BigDecimal getBuyPrice1() {
        return buyPrice1;
    }

    public void setBuyPrice1(BigDecimal buyPrice1) {
        this.buyPrice1 = buyPrice1;
    }

    public Integer getBuyQuantity1() {
        return buyQuantity1;
    }

    public void setBuyQuantity1(Integer buyQuantity1) {
        this.buyQuantity1 = buyQuantity1;
    }

    public BigDecimal getSellPrice1() {
        return sellPrice1;
    }

    public void setSellPrice1(BigDecimal sellPrice1) {
        this.sellPrice1 = sellPrice1;
    }

    public Integer getSellQuantity1() {
        return sellQuantity1;
    }

    public void setSellQuantity1(Integer sellQuantity1) {
        this.sellQuantity1 = sellQuantity1;
    }

    public BigDecimal getUnderlyingValue() {
        return underlyingValue;
    }

    public void setUnderlyingValue(BigDecimal underlyingValue) {
        this.underlyingValue = underlyingValue;
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public BigDecimal getPchange() {
        return pchange;
    }

    public void setPchange(BigDecimal pchange) {
        this.pchange = pchange;
    }
}

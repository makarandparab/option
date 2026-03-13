package com.nse.option.model.nifty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OptionDataRow
{
    /**
     * Note: The JSON key is "expiryDates" (plural) but it holds a single date string.
     */
    private String expiryDates;

    @JsonProperty("CE")
    private OptionLeg ce;

    @JsonProperty("PE")
    private OptionLeg pe;

    private Integer strikePrice;

    // Getters & Setters
    public String getExpiryDates() {
        return expiryDates;
    }

    public void setExpiryDates(String expiryDates) {
        this.expiryDates = expiryDates;
    }

    public OptionLeg getCe() {
        return ce;
    }

    public void setCe(OptionLeg ce) {
        this.ce = ce;
    }

    public OptionLeg getPe() {
        return pe;
    }

    public void setPe(OptionLeg pe) {
        this.pe = pe;
    }

    public Integer getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(Integer strikePrice) {
        this.strikePrice = strikePrice;
    }
}

/**
 * Root model for the provided JSON.
 */
package com.nse.option.model.callput;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Root model for the provided JSON.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketSnapshot 
{
    private Head head;
    private Body body;
    private String niftyValue;
    private String niftyData;
    private String spotStrikePrice;

    public String getSpotStrikePrice() {
        return spotStrikePrice;
    }

    public void setSpotStrikePrice(String spotStrikePrice) {
        this.spotStrikePrice = spotStrikePrice;
    }

    public String getNiftyValue() {
        return niftyValue;
    }

    public void setNiftyValue(String niftyValue) {
        this.niftyValue = niftyValue;
    }

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public String getNiftyData() {
        return niftyData;
    }

    public void setNiftyData(String niftyData) {
        this.niftyData = niftyData;
    }
}

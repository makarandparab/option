package com.nse.option.model.iv;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ScreenerResponse
{
    private Head head;
    private Body body;

    @JsonProperty("contractLastUpdated")
    private String contractLastUpdated;

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

    public String getContractLastUpdated() {
        return contractLastUpdated;
    }

    public void setContractLastUpdated(String contractLastUpdated) {
        this.contractLastUpdated = contractLastUpdated;
    }
}

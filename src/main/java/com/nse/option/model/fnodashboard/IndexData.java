package com.nse.option.model.fnodashboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IndexData
{
    @JsonProperty("time")
    private String time;

    @JsonProperty("name")
    private String name;

    @JsonProperty("index_url")
    private String indexUrl;

    @JsonProperty("value")
    private Double value;


    @JsonProperty("near_expiry_date")
    private String near_expiry_date;

    @JsonProperty("insight")
    private String insight;

    @JsonProperty("colour")
    private String insightColor;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndexUrl() {
        return indexUrl;
    }

    public void setIndexUrl(String indexUrl) {
        this.indexUrl = indexUrl;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }



    public String getNear_expiry_date() {
        return near_expiry_date;
    }

    public void setNear_expiry_date(String near_expiry_date) {
        this.near_expiry_date = near_expiry_date;
    }

    public String getInsight() {
        return insight;
    }

    public void setInsight(String insight) {
        this.insight = insight;
    }

    public String getInsightColor() {
        return insightColor;
    }

    public void setInsightColor(String insightColor) {
        this.insightColor = insightColor;
    }
}

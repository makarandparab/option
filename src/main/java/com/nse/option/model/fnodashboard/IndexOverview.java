package com.nse.option.model.fnodashboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IndexOverview
{
    @JsonProperty("index_data")
    private IndexData indexData;

    public IndexData getIndexData() {
        return indexData;
    }

    public void setIndexData(IndexData indexData) {
        this.indexData = indexData;
    }
}

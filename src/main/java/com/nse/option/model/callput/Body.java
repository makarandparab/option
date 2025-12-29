package com.nse.option.model.callput;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Body 
{
     private OverallData overallData;
     private Map<String, OiStrikeData> oiData;
     private InputData inputData;

     public OverallData getOverallData() {
        return overallData;
    }

    public void setOverallData(OverallData overallData) {
        this.overallData = overallData;
    }

    public Map<String, OiStrikeData> getOiData() {
        return oiData;
    }

    public void setOiData(Map<String, OiStrikeData> oiData) {
        this.oiData = oiData;
    }

    public InputData getInputData() {
        return inputData;
    }

    public void setInputData(InputData inputData) {
        this.inputData = inputData;
    }

}

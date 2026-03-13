package com.nse.option.model.nifty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OptionChainResponse
{
    @JsonFormat(pattern = "dd-MMM-yyyy HH:mm:ss")
    private LocalDateTime timestamp;

    private BigDecimal underlyingValue;

    private List<OptionDataRow> data;

    // Getters & Setters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getUnderlyingValue() {
        return underlyingValue;
    }

    public void setUnderlyingValue(BigDecimal underlyingValue) {
        this.underlyingValue = underlyingValue;
    }

    public List<OptionDataRow> getData() {
        return data;
    }

    public void setData(List<OptionDataRow> data) {
        this.data = data;
    }
}

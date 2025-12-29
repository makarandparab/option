package com.nse.option.model.callput;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

 @JsonIgnoreProperties(ignoreUnknown = true)
public class OiStrikeData 
{
    // Put side
    private long putOi;
    private long putPrevOi;
    private long putOiChange;
    private double putOiChangeP;

    // Call side
    private long callOi;
    private long callPrevOi;
    private long callOiChange;
    private double callOiChangeP;

    private PutInfo putInfo;
    private CallInfo callInfo;
    
    public long getPutOi() {
        return putOi;
    }

    public void setPutOi(long putOi) {
        this.putOi = putOi;
    }

    public long getPutPrevOi() {
        return putPrevOi;
    }

    public void setPutPrevOi(long putPrevOi) {
        this.putPrevOi = putPrevOi;
    }

    public long getPutOiChange() {
        return putOiChange;
    }

    public void setPutOiChange(long putOiChange) {
        this.putOiChange = putOiChange;
    }

    public double getPutOiChangeP() {
        return putOiChangeP;
    }

    public void setPutOiChangeP(double putOiChangeP) {
        this.putOiChangeP = putOiChangeP;
    }

    public long getCallOi() {
        return callOi;
    }

    public void setCallOi(long callOi) {
        this.callOi = callOi;
    }

    public long getCallPrevOi() {
        return callPrevOi;
    }

    public void setCallPrevOi(long callPrevOi) {
        this.callPrevOi = callPrevOi;
    }

    public long getCallOiChange() {
        return callOiChange;
    }

    public void setCallOiChange(long callOiChange) {
        this.callOiChange = callOiChange;
    }

    public double getCallOiChangeP() {
        return callOiChangeP;
    }

    public void setCallOiChangeP(double callOiChangeP) {
        this.callOiChangeP = callOiChangeP;
    }

    public PutInfo getPutInfo() {
        return putInfo;
    }

    public void setPutInfo(PutInfo putInfo) {
        this.putInfo = putInfo;
    }

    public CallInfo getCallInfo() {
        return callInfo;
    }

    public void setCallInfo(CallInfo callInfo) {
        this.callInfo = callInfo;
    }
}

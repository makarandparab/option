package com.nse.option.model.iv;

public class StockCell
{
    private long id;
    private String name;
    private String url;
    private CallbackInfo callbackinfo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public CallbackInfo getCallbackinfo() {
        return callbackinfo;
    }

    public void setCallbackinfo(CallbackInfo callbackinfo) {
        this.callbackinfo = callbackinfo;
    }
}

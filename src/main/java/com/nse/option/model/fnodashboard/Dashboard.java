package com.nse.option.model.fnodashboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nse.option.model.callput.Head;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Dashboard
{
    private Head head;
    private Body body;

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
}

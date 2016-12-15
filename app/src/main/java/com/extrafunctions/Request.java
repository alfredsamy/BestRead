package com.extrafunctions;

import org.simpleframework.xml.Element;

/**
 * Created by Mahmoud on 14/12/2016.
 */

public class Request {

    @Element(name="authentication")
    private String authentication;

    @Element(name="method")
    private String method;

    public Request(String authentication, String method) {
        this.authentication = authentication;
        this.method = method;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}

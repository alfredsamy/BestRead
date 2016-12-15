package com.extrafunctions;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Mahmoud on 14/12/2016.
 */
@Root(strict=false)
public class Body {

    @Element(name="html")
    private String html;

    @Element(name ="text")
    private String text;

    public Body() {
//        this.html = html;
//        this.text = text;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

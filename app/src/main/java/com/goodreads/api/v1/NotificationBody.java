package com.goodreads.api.v1;

import android.sax.Element;
import android.sax.EndTextElementListener;

import java.io.Serializable;

/**
 * Created by Mahmoud on 05/12/2016.
 */
public class NotificationBody implements Serializable{

    String html;

    String text;

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

    public NotificationBody copy()
    {
        NotificationBody notificationBodyCopy = new NotificationBody();
        notificationBodyCopy.setText(this.getText());
        notificationBodyCopy.setHtml(this.getHtml());
        return notificationBodyCopy;
    }

    public void clear() {
        this.setHtml("");
        this.setText("");
    }

    public static NotificationBody appendListener(final Element parentElement, int depth) {
        final Element bodyElement = parentElement.getChild("body");
        final NotificationBody notificationBody = new NotificationBody();

        appendCommonListeners(bodyElement, notificationBody, depth);

        return notificationBody;
    }

    private static void appendCommonListeners(final Element bodyElement,final NotificationBody notificationBody, int depth) {
        bodyElement.getChild("html").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                notificationBody.setHtml(body);
            }
        });

        bodyElement.getChild("text").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                notificationBody.setText(body);
            }
        });
    }
}

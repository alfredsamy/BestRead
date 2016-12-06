package com.goodreads.api.v1;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.StartElementListener;

import org.xml.sax.Attributes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahmoud on 05/12/2016.
 */

public class Notification implements Serializable{

    long id;

    List<User> actors = new ArrayList<>();

    boolean flagNew;

    String created_at;

    NotificationBody body;

    String url;

    String resource_type;

    String group_resource_type;

    String group_resource;

    public Notification copy()
    {
        Notification notificationCopy = new Notification();

        notificationCopy.setActors(this.getActors());
        notificationCopy.setBody(this.getBody());
        notificationCopy.setCreated_at(this.getCreated_at());
        notificationCopy.setFlagNew(this.isFlagNew());
        notificationCopy.setGroup_resource(this.getGroup_resource());
        notificationCopy.setGroup_resource_type(this.getGroup_resource_type());
        notificationCopy.setId(this.getId());
        notificationCopy.setResource_type(this.getResource_type());
        notificationCopy.setUrl(this.getUrl());
        return notificationCopy;
    }

    public void clear()
    {
        this.actors.clear();
        this.setUrl("");
        this.setId(0);
        this.setFlagNew(false);
        this.body.clear();
        this.setResource_type("");
        this.setGroup_resource_type("");
        this.setGroup_resource_type("");
    }

    public static Notification appendListener(final Element parentElement, int depth)
    {
        final Element notificationElement = parentElement.getChild("notification");
        final Notification notification = new Notification();

        appendCommonListeners(notificationElement, notification, depth);

        return notification;
    }

    public static List<Notification> appendArrayListener(final Element parentElement, int depth)
    {
        final Element notificationElement = parentElement.getChild("notification");
        final List<Notification> notificationList = new ArrayList<Notification>();
        final Notification notification = new Notification();

        appendCommonListeners(notificationElement, notification, depth);

        notificationElement.setEndElementListener(new EndElementListener()
        {
            @Override
            public void end()
            {
                notificationList.add(notification.copy());
                notification.clear();
            }
        });

        return notificationList;
    }

    private static void appendCommonListeners(final Element notificationElement, final Notification notification, int depth)
    {
        notificationElement.getChild("id").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                notification.setId(Long.valueOf(body));
            }
        });

        notificationElement.getChild("url").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                notification.setUrl(body);
            }
        });

        notificationElement.getChild("created_at").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                notification.setCreated_at(body);
            }
        });

        notificationElement.getChild("resource_type").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                notification.setResource_type(body);
            }
        });

        notificationElement.getChild("group_resource_type").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                notification.setGroup_resource_type(body);
            }
        });

        notificationElement.getChild("group_resource").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                notification.setGroup_resource(body);
            }
        });

        notification.setBody(NotificationBody.appendListener(notificationElement, depth + 1));

        Element actorsElement = notificationElement.getChild("actors");
        notification.setActors(User.appendArrayListener(actorsElement, depth+1));
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGroup_resource() {
        return group_resource;
    }

    public void setGroup_resource(String group_resource) {
        this.group_resource = group_resource;
    }

    public String getGroup_resource_type() {
        return group_resource_type;
    }

    public void setGroup_resource_type(String group_resource_type) {
        this.group_resource_type = group_resource_type;
    }

    public String getResource_type() {
        return resource_type;
    }

    public void setResource_type(String resource_type) {
        this.resource_type = resource_type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public NotificationBody getBody() {
        return body;
    }

    public void setBody(NotificationBody body) {
        this.body = body;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public boolean isFlagNew() {
        return flagNew;
    }

    public void setFlagNew(boolean flagNew) {
        this.flagNew = flagNew;
    }

    public List<User> getActors() {
        return actors;
    }

    public void setActors(List<User> actors) {
        this.actors = actors;
    }

}

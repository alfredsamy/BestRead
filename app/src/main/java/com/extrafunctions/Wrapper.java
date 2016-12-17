package com.extrafunctions;

import android.util.Log;

import org.scribe.model.Response;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mahmoud on 14/12/2016.
 */

public class Wrapper {

    public static List<Notification> getNotifications(Response response) throws Exception {
        Serializer serializer = new Persister();
        GoodreadsResponse goodreadsResponse = serializer.read(GoodreadsResponse.class, response.getBody());
        return goodreadsResponse.getNotifications();
    }

    public static Book getBook(Response response) throws Exception {
        Serializer serializer = new Persister();
        GoodreadsResponse goodreadsResponse = serializer.read(GoodreadsResponse.class,response.getBody());
        return goodreadsResponse.getBook();
    }
}

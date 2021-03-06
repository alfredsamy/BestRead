package com.extrafunctions;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahmoud on 14/12/2016.
 */
@Root(name="GoodreadsResponse", strict = false)
public class GoodreadsResponse {

//    @Element(name="Request")
//    private Request request;

    @ElementList(name="notifications", required = false)
    private ArrayList<Notification> notifications;

    @Element(name="book", required = false)
    private Book book;

    public GoodreadsResponse() {
//        this.request = request;
//        this.notifications = notifications;
    }

//    public Request getRequest() {
//        return request;
//    }

//    public void setRequest(Request request) {
//        this.request = request;
//    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}

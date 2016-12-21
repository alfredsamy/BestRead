package com.bestread.app.bestread;

import android.test.suitebuilder.annotation.LargeTest;

import com.extrafunctions.*;
import com.extrafunctions.User;
import com.goodreads.api.v1.GoodreadsService;
import com.goodreads.api.v1.Update;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

@RunWith(JUnit4.class)
public class NotificationsTest extends TestCase {
    private SessionManager session;
    private int i;

    public NotificationsTest() {
        session = new SessionManager();
        String token = "ZitFYb4QUmwiFylbH469ag";
        String tokenSecret = "o4GH3Kdx3JgbrDGUM9S7HFVclXMLI9IvdvMyVBlZVm8";
        session.g.setAccessToken(token, tokenSecret);
    }

    //Test get Notification actor name
    public void testFeed() throws Exception {
        List<Notification> notifications = session.g.getNotifications();

         User user = notifications.get(0).getUsers().get(0);
        assertEquals("Wrong name", user.getDisplayName(), "Eslam Ramadan");
    }
}

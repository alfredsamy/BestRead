package com.bestread.app.bestread;

import android.test.suitebuilder.annotation.LargeTest;

import com.goodreads.api.v1.GoodreadsService;
import com.goodreads.api.v1.Update;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

@RunWith(JUnit4.class)
public class NewsFeedTest extends TestCase {
    private SessionManager session;
    private int i;

    public NewsFeedTest() {
        session = new SessionManager();
        String token = "JjPloDLok01QB1RnLsEUQ";
        String tokenSecret = "yAJekdOt1q25fcqGJJs4ym16EiRGjBPSFiHeYJek";
        session.g.setAccessToken(token, tokenSecret);
        i = 0;
    }

    //Test get write update actor
    public void testFeed() throws Exception {
        List<Update> updates = session.g.getFriendsUpdates();

        assertEquals("Wrong feed 1", updates.get(i).getActor().getName(), "Somename");
        assertEquals("Wrong feed 11", updates.get(i+10).getActor().getName(), "Dan Brown");
    }

    //Test get write update content
    public void testFeedContent() throws Exception {
        List<Update> updates = session.g.getFriendsUpdates();
        String feed1 = "is now friends with Mahmoud Salah";

        String feed2 = "added a status update:";

        assertEquals("Wrong feed 1", updates.get(i).getActionText(), feed1);
        assertEquals("Wrong feed 2", updates.get(i+7).getActionText(), feed2);
    }
}

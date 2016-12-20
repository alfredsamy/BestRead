package com.bestread.app.bestread;

import android.test.suitebuilder.annotation.LargeTest;

import com.goodreads.api.v1.GoodreadsService;
import com.goodreads.api.v1.Update;
import com.goodreads.api.v1.Work;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

@RunWith(JUnit4.class)
public class SearchTest extends TestCase {
    private SessionManager session;

    public SearchTest() {
        session = new SessionManager();
        String token = "JjPloDLok01QB1RnLsEUQ";
        String tokenSecret = "yAJekdOt1q25fcqGJJs4ym16EiRGjBPSFiHeYJek";
        session.g.setAccessToken(token, tokenSecret);
    }

    //Test get write search result
    public void testFeed() throws Exception {
        String query = "harry potter";
        List<Work> works = session.g.search(query).getResults();

        //assertEquals("Wrong feed 1", works.get(0).getBestBook().getTitle(),
          //      "Harry Potter and the Sorcerer's Stone (Harry Potter, #1)");

        assertEquals("Wrong feed 11", works.get(1).getBestBook().getTitle(),
                "Harry Potter and the Prisoner of Azkaban (Harry Potter, #3)");

        assertEquals("Wrong feed 1", works.get(0).getBestBook().getAuthor().getName(),
                "J.K. Rowling");
    }

    //Test get write update content
    public void testFeedContent() throws Exception {
        String query = "ay 7aga";
        List<Work> works = session.g.search(query).getResults();

        assertEquals(works.size(),0);
    }

}

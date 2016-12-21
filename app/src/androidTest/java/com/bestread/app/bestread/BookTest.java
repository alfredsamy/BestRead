package com.bestread.app.bestread;


import com.extrafunctions.Book;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class BookTest extends TestCase {
    private SessionManager session;

    public BookTest() {
        session = new SessionManager();
        String token = "JjPloDLok01QB1RnLsEUQ";
        String tokenSecret = "yAJekdOt1q25fcqGJJs4ym16EiRGjBPSFiHeYJek";
        session.g.setAccessToken(token, tokenSecret);
    }

    //Book info search
    public void test0BookInfo() throws Exception {
        String query = "151";
        Book book = session.g.getReviewsForBook(query);
        assertEquals("Wrong book title", book.getTitle(), "Anna Karenina");
    }

    //Test Book review
    public void test1BookReview() throws Exception {
        boolean res = session.g.postReview("152","great",4);
        assertTrue(res);
    }
    //Test Add to bookshelf
    public void test2BookShelf() throws Exception {
        boolean res = session.g.addToShelf("read","151");
        assertTrue(res);
    }

    //Test book reviewed
    public void test3Bookreviewed() throws Exception {
        boolean res = session.g.postReview("151","great",4);
        assertFalse(res);
    }

}

package com.bestread.app.bestread;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CommentsTest extends TestCase {
    private SessionManager session;

    public CommentsTest() {
        session = new SessionManager();
        String token = "JjPloDLok01QB1RnLsEUQ";
        String tokenSecret = "yAJekdOt1q25fcqGJJs4ym16EiRGjBPSFiHeYJek";
        session.g.setAccessToken(token, tokenSecret);
    }

    public void testUserInfo() throws Exception {
        boolean res = session.postComment("readstatus", "1043813050", "this is a comment");
        assertEquals("Fail", res, true);

        res = session.postComment("userchallenge", "1043813050", "this is a comment");
        assertEquals("Fail", res, false);

        res = session.postComment("readstatus", "1043813050", "");
        assertEquals("Fail", res, false);
    }
}

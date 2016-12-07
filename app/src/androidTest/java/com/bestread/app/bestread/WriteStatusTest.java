package com.bestread.app.bestread;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class WriteStatusTest extends TestCase {
    private SessionManager session;

    public WriteStatusTest() {
        session = new SessionManager();
        String token = "JjPloDLok01QB1RnLsEUQ";
        String tokenSecret = "yAJekdOt1q25fcqGJJs4ym16EiRGjBPSFiHeYJek";
        session.g.setAccessToken(token, tokenSecret);
    }

    public void testSuccessfullUpdate() throws Exception {
        String status = "some status update";
        boolean res = session.g.postStatusUpdate(status);
        assertEquals("Success", res, true);
    }

    public void testEmptyUpdate() throws Exception {
        String status = "";
        boolean res = session.g.postStatusUpdate(status);
        assertEquals("Success", res, false); // status is empty
    }
}

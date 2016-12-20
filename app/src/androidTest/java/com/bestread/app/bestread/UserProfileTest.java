package com.bestread.app.bestread;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class UserProfileTest extends TestCase {
    private SessionManager session;

    public UserProfileTest() {
        session = new SessionManager();
        String token = "JjPloDLok01QB1RnLsEUQ";
        String tokenSecret = "yAJekdOt1q25fcqGJJs4ym16EiRGjBPSFiHeYJek";
        session.g.setAccessToken(token, tokenSecret);
    }

    public void testUserInfo() throws Exception {
        com.goodreads.api.v1.User user = session.g.getAuthorizedUser().getCorrectUser();

        assertNotNull(user);

        assertEquals("Fail", user.getName(), "Alfred Samy");

        assertEquals("Fail", user.getAbout(), "About ME");

        assertEquals("Fail", user.getFriendsCount(), 2);

        assertNotNull(user.getLastActive());

        assertEquals("Fail", user.getJoined(), "12/2016");

    }


}

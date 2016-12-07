package com.bestread.app.bestread;


import android.test.suitebuilder.annotation.LargeTest;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
@LargeTest
public class LoginTest extends TestCase{
    private SessionManager session;
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    public LoginTest(){
        session = new SessionManager();
    }


    public void testdemo() {
        assertEquals("NOOOOOOO", "a", "a");
    }

    //Throw Exception ==> User Not Authorized
    public void testLoginWithoutAuth() {
        try {
            session.initAccessToken();
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    //Test getting authorization URL
    public void testAuthorization(){
        String authUrl = session.Authorization();
        assertTrue(!authUrl.isEmpty());
    }

    //Success Login using Access Token
    public void testsuccessLogin() {
        String token = "JjPloDLok01QB1RnLsEUQ";
        String tokenSecret = "yAJekdOt1q25fcqGJJs4ym16EiRGjBPSFiHeYJek";
        session.g.setAccessToken(token, tokenSecret);
        com.goodreads.api.v1.User user = null;
        try {
            user = session.g.getAuthorizedUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals("Wrong user", user.getName(), "Alfred Samy");
    }

}

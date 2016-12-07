package com.bestread.app.bestread;

import android.os.StrictMode;
import android.util.Log;

import com.goodreads.api.v1.*;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class LogInTest {
    SessionManager session;
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    public LogInTest(){
        session = new SessionManager();
    }
    @Test
    public void testdemo() {
        assertEquals("NOOOOOOO", "a", "a");
    }

    @Test   //Throw Exception ==> User Not Authorized
    public void testLoginWithoutAuth() throws Exception {
        expectedEx.expect(java.lang.Exception.class);
        expectedEx.expectMessage("User Not Authorized");
        session.initAccessToken();
    }
    @Test   //Test getting authorization URL
    public void testAuthorization(){
        String authUrl = session.Authorization();
        assertTrue(!authUrl.isEmpty());
    }

    @Test   //Success Login
    public void successLogin(){
        String token = "JjPloDLok01QB1RnLsEUQ";
        String tokenSecret =  "yAJekdOt1q25fcqGJJs4ym16EiRGjBPSFiHeYJek";
        session.g.setAccessToken(token,tokenSecret);
    }
}

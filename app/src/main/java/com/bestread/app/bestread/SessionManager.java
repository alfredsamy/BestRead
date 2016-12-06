package com.bestread.app.bestread;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.goodreads.api.v1.GoodreadsService;
import com.goodreads.api.v1.User;

import org.scribe.model.Token;

public class SessionManager {

    static GoodreadsService g;
    private static Token requestToken;
    private static Token accessToken;
    private static User currentUser;

    private String key = "6wT9nOMhwRlK7Ee0D8TUzA";
    private String secret = "2Lu6STmOXq9fb7dse1ZRCinkY4uglIVJ6235cOOQd0";


    public SessionManager() {
        if (g == null) {
            g = new GoodreadsService();
            g.init(key, secret);
        }
    }

    public String Authorization() {

        String authUrl = "";
        try {
            requestToken = g.getRequestToken();
            Log.d("Token", requestToken.getToken());
            authUrl = g.getAuthorizationUrl(requestToken);
            Log.d("URL", authUrl);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("ERROR", "Unable to login");
        }
        return authUrl;
    }

    public void initAccessToken() throws Exception {

        if (g.isAuthenticated())
            return;

        accessToken = g.getAccessToken("verifier you got from the user/callback", requestToken);
        if (accessToken == null)
            throw new Exception("User Not Authorized");

        Log.d("Token", accessToken.getToken());
        try {
            currentUser = g.getAuthorizedUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("User", currentUser.getName());
        Log.d("Access Token", accessToken.getToken());
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void saveAccessToken(SharedPreferences pref) {
        //g.setAccessToken(token, tokenSecrer);
        String token = accessToken.getToken();
        String tokenSecrer = accessToken.getSecret();

        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token", token);
        editor.putString("tokenSecrer", tokenSecrer);
        editor.commit();

    }

    public boolean setAccessToken(SharedPreferences pref) {
        String token = pref.getString("token", null);
        String tokenSecrer = pref.getString("tokenSecrer", null);
        if (token == null) {
            return false;
        }
        g.setAccessToken(token, tokenSecrer);
        return true;
    }

    public boolean postStatus(String comment) {
        try {
            return g.postStatusUpdate(comment);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void logout(SharedPreferences pref) {
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("token");
        editor.remove("tokenSecrer");
        editor.apply();

        currentUser = null;
        g.unAuthenticated();
    }


}

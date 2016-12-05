package com.bestread.app.bestread;

import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.goodreads.api.v1.GoodreadsService;
import com.goodreads.api.v1.User;

import org.scribe.model.Token;

/**
 * Created by alfre on 12/4/2016.
 */
public class SessionManager {

    static GoodreadsService g;
    static Token requestToken;

    public SessionManager(){
        if(g==null) {
            String key = "6wT9nOMhwRlK7Ee0D8TUzA";
            String secret = "2Lu6STmOXq9fb7dse1ZRCinkY4uglIVJ6235cOOQd0";
            g = new GoodreadsService();
            g.init(key, secret);
        }
    }
    public String Authorization(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //Toast.makeText(MainActivity.this, "YOUR MESSAGE", Toast.LENGTH_LONG).show();
        String authUrl = "";
        try {
            requestToken = g.getRequestToken();
            Log.d("Token", requestToken.getToken());
            authUrl = g.getAuthorizationUrl(requestToken);
            Log.d("URL", authUrl);

        }
        catch (Exception e){
            e.printStackTrace();
            Log.d("ERROR", "Unable to login");
        }
        return authUrl;
    }
    public void initAccessToken() throws Exception{
        //Token requestToken = g.getRequestToken();
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);

        Token accessToken = g.getAccessToken("verifier you got from the user/callback", requestToken);

        if(accessToken == null)
            throw new Exception("User Not Authorized");

        Log.d("Token", accessToken.getToken());
        User u = null;
        try {
            u = g.getAuthorizedUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("User",u.getName());
        Log.d("Access Token",accessToken.getToken());
    }

}

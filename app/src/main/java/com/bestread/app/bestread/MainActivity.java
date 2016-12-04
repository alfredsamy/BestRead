package com.bestread.app.bestread;

import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.goodreads.api.v1.*;

import org.scribe.builder.ServiceBuilder;
import org.scribe.oauth.OAuthService;
import org.scribe.model.Token;

public class MainActivity extends AppCompatActivity {
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new SessionManager();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void signIn(View v) throws Exception {
        //Toast.makeText(MainActivity.this, "YOUR MESSAGE", Toast.LENGTH_LONG).show();
//        requestToken = g.getRequestToken();
//        Log.d("Token",requestToken.getToken());
//        String authUrl = g.getAuthorizationUrl(requestToken);
//        Log.d("URL",authUrl);

        String authUrl = session.Authorization();
        Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl));
        startActivity(intent);
    }
    public void login(View v) throws Exception {
        session.initAccessToken();
        //Toast.makeText(MainActivity.this, "User Name", Toast.LENGTH_LONG).show();

    }

}


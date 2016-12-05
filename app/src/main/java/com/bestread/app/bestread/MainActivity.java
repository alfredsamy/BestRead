package com.bestread.app.bestread;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SessionManager session;
    private static String MyPREFERENCES = "BestReadPref";
    private static SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        session = new SessionManager();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if(session.setAccessToken(sharedpreferences)) {//Already Authenticate Go to News Feed
            goToFeed();
            finish();
        }
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
        String authUrl = session.Authorization();
        if(!authUrl.isEmpty()){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl));
            startActivity(intent);
        }else{
            Toast toast = Toast.makeText(getApplicationContext(), "Couldn't Authorize Maybe No Internet ?", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void login(View v) throws Exception {
        try {
            session.initAccessToken();
            session.saveAccessToken(sharedpreferences);
            goToFeed();

        }catch (Exception e){
            e.printStackTrace();
            Toast toast = Toast.makeText(getApplicationContext(), "Please Authorize First", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void goToFeed() {
        Toast toast = Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_LONG);
        toast.show();
        Intent feed = new Intent(this, UserFeed.class);
        startActivity(feed);
    }
}


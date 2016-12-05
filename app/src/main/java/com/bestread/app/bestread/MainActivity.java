package com.bestread.app.bestread;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
            Intent feed = new Intent(this, UserFeed.class);
            startActivity(feed);
        }catch (Exception e){
            Toast toast = Toast.makeText(getApplicationContext(), "Please Authorize First", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void goToFeed(View view) {
        //Toast.makeText(MainActivity.this, "User Name", Toast.LENGTH_LONG).show();
        Intent feed = new Intent(this, UserFeed.class);
        //feed.putExtra("extra","Passed string in extra");
        startActivity(feed);
    }
}


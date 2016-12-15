package com.bestread.app.bestread;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.goodreads.api.v1.GoodreadsService;
import com.goodreads.api.v1.Update;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class UserFeed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userfeed);

        try {
            listInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_userfeeds, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.writePost) {
            Intent writeStatus = new Intent(this, WriteStatus.class);
            startActivity(writeStatus);
            return true;
        }else if(id == R.id.profile_view){
            Intent profile_view = new Intent(this, User.class);
            startActivity(profile_view);
            return true;
        }
        else if(id==R.id.notification){
            Intent notification = new Intent(this, Notifications.class);
            startActivity(notification);
            return true;
        }
        else if(id==R.id.Search){
            Intent search = new Intent(this, Search.class);
            startActivity(search);
            return true;
        }
        else if(id==R.id.logout){
            SessionManager s = new SessionManager();
            s.logout(MainActivity.sharedpreferences);
            //go to main activity
            Intent mainActivity = new Intent(this, MainActivity.class);
            startActivity(mainActivity);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void listInit() throws Exception {
        //populate the Feed list
        GoodreadsService g = new GoodreadsService();

        List<Update> updates = g.getFriendsUpdates();
        Log.d("Feed", updates.size() + "");
        /*
        for (int i=0; i<updates.size();i++) {
            Log.d("Feed",i+"");
            Log.d("Feed",updates.get(i).getUpdateType());
            Log.d("Feed",updates.get(i).getActionText());
        }
        */
        ArrayAdapter<Update> feedArrayAdaptor = new FeedAdaptor(this, 0, updates);

        ListView listView = (ListView) findViewById(R.id.feedList);
        listView.setAdapter(feedArrayAdaptor);
    }

    public void viewNotifications(View v) throws Exception {
        //session.initAccessToken();
        //session.saveAccessToken(sharedpreferences);
        Log.d("Notif:" , v.toString());
        Intent notifications = new Intent(this, Notifications.class);
        startActivity(notifications);
    }

    class FeedAdaptor extends ArrayAdapter<Update> {
        Context context;
        List<Update> objects;
        Bitmap aImg[];
        Bitmap bImg[];

        class ViewHolder {
            public ImageView actorImg;
            public TextView actorName;
            public TextView date;
            public TextView desc;
            public ImageView bookImg;
        }

        public FeedAdaptor(Context context, int resource, List<Update> objects) {
            super(context, resource, objects);
            this.context = context;
            this.objects = objects;
            aImg = new Bitmap[objects.size()];
            bImg = new Bitmap[objects.size()];
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //Log.d("Feed ", position+"");
            Update update = objects.get(position);

            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            View v = inflater.inflate(R.layout.feed_item, null);

            //User Img
            ImageView actorImg = (ImageView) v.findViewById(R.id.actorImage);
            if(aImg[position] == null){
                aImg[position] = loadBitmap(update.getActor().getImageUrl());
            }
            actorImg.setImageBitmap(aImg[position]);

            //User name
            TextView actorName = (TextView) v.findViewById(R.id.actorName);
            actorName.setText(update.getActor().getName());

            //date
            SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy hh:mm:ss Z");
            TextView date = (TextView) v.findViewById(R.id.date);
            date.setText( sdf.format(update.getUpdatedAt()));

            //desc
            TextView desc = (TextView) v.findViewById(R.id.description);
            String d = android.text.Html.fromHtml(update.getActionText()).toString();
            if( update.getBody() != null)
                d += " " + update.getBody();
            desc.setText(d);


            //Book Img
            ImageView bookImg = (ImageView) v.findViewById(R.id.bookImg);
            if(update.getImageUrl().equals(update.getActor().getImageUrl()))
                ((ViewGroup) bookImg.getParent()).removeView(bookImg);
            else {
                if(bImg[position] == null){
                    bImg[position] = loadBitmap(update.getImageUrl());
                }
                bookImg.setImageBitmap(bImg[position]);
            }

            return v;
        }
        public Bitmap loadBitmap(String url) {
            String urldisplay = url;
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

    }
}

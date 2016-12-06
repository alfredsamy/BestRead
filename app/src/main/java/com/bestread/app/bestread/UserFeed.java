package com.bestread.app.bestread;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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

import com.goodreads.api.v1.GoodreadsService;
import com.goodreads.api.v1.Update;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

public class UserFeed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userfeed);
        /*For reference
        String extra = getIntent().getStringExtra("extra");
        ScrollView s = (ScrollView) findViewById(R.id.scrollView);
        Button b = new Button(this);
        b.setText(extra);
        s.addView(b);
        */
        try {
            listInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listInit() throws Exception {
        //populate the Feed list
        GoodreadsService g = new GoodreadsService();

        List<Update> updates = g.getFriendsUpdates();
        Log.d("Feed", updates.size()+"");
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    class FeedAdaptor extends ArrayAdapter<Update> {

        Context context;
        List<Update> objects;

        public FeedAdaptor(Context context, int resource, List<Update> objects) {
            super(context, resource, objects);
            this.context = context;
            this.objects = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //Log.d("Feed ", position+"");
            Update update = objects.get(position);
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            View v = inflater.inflate(R.layout.feed_item, null);

            //Actor Img
            ImageView actorImg = (ImageView) v.findViewById(R.id.actorImage);
            actorImg.setImageBitmap(loadBitmap(update.getActor().getImageUrl()));

            //Actor name
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
            if(!update.getUpdateType().equals("review"))
                ((ViewGroup) bookImg.getParent()).removeView(bookImg);
            else
                bookImg.setImageBitmap(loadBitmap(update.getImageUrl()));

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
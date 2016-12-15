package com.bestread.app.bestread;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.extrafunctions.Notification;
import com.goodreads.api.v1.GoodreadsService;


import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Notifications extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        ArrayList<Notification>  notifications = new ArrayList<>();
        Log.d("wasalnaaaaaaaaaa"," heeeeeeeeeeeeh");
        try
        {
            Log.d("before ", "List init");
            listInit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void listInit() throws IOException, SAXException {
        //populate the Feed list
        GoodreadsService g = new GoodreadsService();
        List<Notification> notifications = g.getNotifications();
        Log.d("Feeda", notifications.size()+"");
        /*
        for (int i=0; i<updates.size();i++) {
            Log.d("Feed",i+"");
            Log.d("Feed",updates.get(i).getUpdateType());
            Log.d("Feed",updates.get(i).getActionText());
        }
        */
        ArrayAdapter<Notification> notificationsArrayAdaptor = new Notifications.NotificationsAdapter(this, 0, notifications);

        ListView listView = (ListView) findViewById(R.id.notificationsList);
        listView.setAdapter(notificationsArrayAdaptor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notifications, menu);
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

    class NotificationsAdapter extends ArrayAdapter<Notification> {

        Context context;
        List<Notification> objects;

        public NotificationsAdapter(Context context, int resource, List<Notification> objects) {
            super(context, resource, objects);
            this.context = context;
            this.objects = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //Log.d("Feed ", position+"");
            Notification notification = objects.get(position);
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            View v = inflater.inflate(R.layout.notification_item, null);

            //User Img
            ImageView actorImg = (ImageView) v.findViewById(R.id.actorImage);
            actorImg.setImageBitmap(loadBitmap(notification.getUsers().get(0).getImageUrl()));

            //User name
            TextView actorName = (TextView) v.findViewById(R.id.actorName);
            actorName.setText(notification.getUsers().get(0).getDisplayName());

            //date
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss-07:00");
            Date startDate = null;
            try {
                startDate = df.parse(notification.getCreatedAt());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy hh:mm:ss");
            TextView date = (TextView) v.findViewById(R.id.date);
            if(startDate!=null){
                date.setText( sdf.format(startDate));
            }
            else date.setText("");
            //desc
            TextView desc = (TextView) v.findViewById(R.id.description);
            desc.setText(notification.getBody().getText());
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


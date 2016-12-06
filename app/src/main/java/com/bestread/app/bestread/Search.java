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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.goodreads.api.v1.GoodreadsService;
import com.goodreads.api.v1.Work;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("arrived here ","yes");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Button mButton = (Button)findViewById(R.id.button4);
        final EditText mEdit   = (EditText)findViewById(R.id.editText2);

        mButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        try {
                            listInit(mEdit.getText().toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (SAXException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void listInit(String query) throws Exception {
        //populate the Feed list

        GoodreadsService g = new GoodreadsService();
        List<Work> works = g.search(query).getResults();
        Log.d("search results: ", works.size()+"");
        /*
        for (int i=0; i<updates.size();i++) {
            Log.d("Feed",i+"");
            Log.d("Feed",updates.get(i).getUpdateType());
            Log.d("Feed",updates.get(i).getActionText());
        }
        */
        ArrayAdapter<Work> resultsArrayAdaptor = new SearchAdapter(this, 0, works);

        ListView listView = (ListView) findViewById(R.id.resultsList);
        listView.setAdapter(resultsArrayAdaptor);
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

    class SearchAdapter extends ArrayAdapter<Work> {

        Context context;
        List<Work> objects;

        public SearchAdapter(Context context, int resource, List<Work> objects) {
            super(context, resource, objects);
            this.context = context;
            this.objects = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //Log.d("Feed ", position+"");
            Work work = objects.get(position);
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            View v = inflater.inflate(R.layout.feed_item, null);

            //Actor Img
            ImageView actorImg = (ImageView) v.findViewById(R.id.actorImage);
            actorImg.setImageBitmap(loadBitmap(work.getBestBook().getImageUrl()));

            //Actor name
            TextView actorName = (TextView) v.findViewById(R.id.actorName);
            actorName.setText(work.getBestBook().getTitle());

            //date
//            SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy hh:mm:ss Z");
              TextView date = (TextView) v.findViewById(R.id.date);
            date.setText(Float.toString(work.getAverageRating()));

            //desc
            TextView desc = (TextView) v.findViewById(R.id.description);
            desc.setText(work.getBestBook().getAuthor().getName());
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

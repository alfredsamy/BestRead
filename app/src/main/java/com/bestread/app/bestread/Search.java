package com.bestread.app.bestread;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.goodreads.api.v1.Author;
import com.goodreads.api.v1.BestBook;
import com.goodreads.api.v1.Book;
import com.goodreads.api.v1.GoodreadsService;
import com.goodreads.api.v1.Work;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Search extends AppCompatActivity {
    private List<Work> works;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("arrived here ", "yes");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Button mButton = (Button) findViewById(R.id.button4);
        final EditText mEdit = (EditText) findViewById(R.id.editText2);

        mButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
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

        works = g.search(query).getResults();
        Log.d("search results: ", works.size() + "");
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

        registerForContextMenu(listView);

        /*
        //Action on Item Click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Work work = works.get(position);
                BestBook book = work.getBestBook();
                Author author = book.getAuthor();

                displayAuthor(author);

            }
        });
        */
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.resultsList) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            //menu.setHeaderTitle(Countries[info.position]);
            menu.setHeaderTitle("Options");
            menu.add(Menu.NONE, 0, 0, "Book Details");
            menu.add(Menu.NONE, 1, 1, "Author Details");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();

        if(menuItemIndex == 0){
            //GOTO Book activity
            Work work = works.get(info.position);
            BestBook book = work.getBestBook();
            displayBook(Integer.toString(book.getId()));
        }
        else if(menuItemIndex == 1){
            //GOTO Author activity
            Work work = works.get(info.position);
            BestBook book = work.getBestBook();
            Author author = book.getAuthor();

            displayAuthor(author);
        }


        return true;
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

    private void displayBook(String bookId) {
        Intent bookIntent = new Intent(this, BooksActivity.class);
        bookIntent.putExtra("ID",bookId);
        startActivity(bookIntent);
    }

    private void displayAuthor(Author author) {
        Intent autorIntent = new Intent(this, AuthorActivity.class);
        autorIntent.putExtra("ID", author.getId());
        startActivity(autorIntent);
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

            Bitmap book = loadBitmap(work.getBestBook().getImageUrl());

            //User Img
            //ImageView actorImg = (ImageView) v.findViewById(R.id.actorImage);
            //actorImg.setImageBitmap(book);

            //Book Title
            TextView actorName = (TextView) v.findViewById(R.id.actorName);
            actorName.setText(work.getBestBook().getTitle());

            //AVG Rating
            TextView date = (TextView) v.findViewById(R.id.date);
            date.setText(Float.toString(work.getAverageRating()));

            //desc
            TextView desc = (TextView) v.findViewById(R.id.description);
            desc.setText("Author: " + work.getBestBook().getAuthor().getName());


            //Book Img
            ImageView bookImg = (ImageView) v.findViewById(R.id.bookImg);
            bookImg.setImageBitmap(book);

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

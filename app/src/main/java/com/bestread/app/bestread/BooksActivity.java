package com.bestread.app.bestread;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.goodreads.api.v1.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.bestread.app.bestread.SessionManager.g;

public class BooksActivity extends AppCompatActivity {

    public String bookMId;
    public GoodreadsService g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        g= new GoodreadsService();
        String bookId = getIntent().getStringExtra("ID");
        this.bookMId = bookId;
        try{
            com.extrafunctions.Book book = g.getReviewsForBook(bookId);
            Log.d("book's image: ",book.getImageUrl());
            Log.d("book's title: ",book.getTitle());
            Log.d("book's description: ",book.getDescription());
            //Book Image
            ImageView bookImg = (ImageView) findViewById(R.id.bookImg);
            bookImg.setImageBitmap(loadBitmap(book.getImageUrl()));
            //Book title
            TextView bookTitle = (TextView) findViewById(R.id.bookTitle);
            bookTitle.setText(book.getTitle());

            TextView bookAuthor = (TextView) findViewById(R.id.bookAuthor);
            bookAuthor.setText(book.getAuthors().get(0).getName());

            TextView rating = (TextView) findViewById(R.id.rating);
            rating.setText(Float.toString(book.getAverageRating()) + "/5.0");

            TextView description = (TextView)findViewById(R.id.description);
            description.setText(Html.fromHtml(book.getDescription()));

        }
        catch(Exception e){
            Toast toast = Toast.makeText(getApplicationContext(), "Failed to get Data, try again later", Toast.LENGTH_SHORT);
            toast.show();
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_author, menu);
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

    public void goToReview(View view){
        Intent reviewIntent = new Intent(this, ReviewActivity.class);
        reviewIntent.putExtra("ID",this.bookMId);
        startActivity(reviewIntent);
    }

    public void addToBookshelf(View view) throws Exception {
        Log.d("0   :","reached here");
        com.goodreads.api.v1.User user = g.getAuthorizedUser();
        String userId = user.getId();
        Log.d("1   :","reached here");
        List<UserShelf> shelves = g.getShelvesForUser(userId);
        ArrayList<String> shelvesNames = new ArrayList<String>();
        for(UserShelf shelf:shelves){
            shelvesNames.add(shelf.getName());
        }
        Log.d("2   :","reached here");
        final String names[] =shelvesNames.toArray(new String[shelvesNames.size()]);
        AlertDialog alertDialog = new AlertDialog.Builder(BooksActivity.this).create();
        LayoutInflater inflater = getLayoutInflater();
        Log.d("3   :","reached here");
        View convertView = (View) inflater.inflate(R.layout.custom, null);
        alertDialog.setView(convertView);
        alertDialog.setTitle("Shelves");
        final ListView lv = (ListView) convertView.findViewById(R.id.listView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
        lv.setAdapter(adapter);
        setClickListener(alertDialog,lv,this.bookMId,names);
        alertDialog.show();
    }

    private void setClickListener(final AlertDialog alertDialog,
                                  final ListView listView,final String bookId,final String[] names){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id)
            {
                boolean response = g.addToShelf(names[position], bookId);
                if(response){
                    Toast toast = Toast.makeText(getApplicationContext(), "Added to bookshelf", Toast.LENGTH_SHORT);
                    toast.show();
                    alertDialog.dismiss();
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Failed to add to bookshelf", Toast.LENGTH_SHORT);
                    toast.show();
                    alertDialog.dismiss();
                }
            }});
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

package com.bestread.app.bestread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goodreads.api.v1.Book;
import com.goodreads.api.v1.GoodreadsService;

import java.io.InputStream;

import static com.bestread.app.bestread.SessionManager.g;

public class BooksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        g= new GoodreadsService();
        String bookId = getIntent().getStringExtra("ID");
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

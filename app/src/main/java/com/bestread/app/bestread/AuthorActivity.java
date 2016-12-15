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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.goodreads.api.v1.Author;
import com.goodreads.api.v1.Book;
import com.goodreads.api.v1.GoodreadsService;
import com.goodreads.api.v1.Update;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

public class AuthorActivity extends AppCompatActivity {
    GoodreadsService g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);
        g = new GoodreadsService();
        int authorId = getIntent().getIntExtra("ID", -1);


        try {
            Author author = g.getAuthorById(authorId + "");

            //Author Image
            ImageView authorImg = (ImageView) findViewById(R.id.authorImg);
            authorImg.setImageBitmap(loadBitmap(author.getImageUrl()));
            //Author Name
            TextView authorName = (TextView) findViewById(R.id.authorName);
            authorName.setText(author.getName());

            //Author Details
            String details = "";
            details += "Hometown: " + author.getHometown() + "\n";
            details += "Gender: " + author.getGender() + "\n";

            if (!author.getBornAt().isEmpty())
                details += "Born at: " + author.getBornAt() + "\n";
            if (!author.getDiedAt().isEmpty())
                details += "Died at: " + author.getDiedAt() + "\n";
            details += "Number of fans: " + author.getFansCount() + "\n";

            TextView authorDetails = (TextView) findViewById(R.id.description);
            authorDetails.setText(details);
            //Author About
            TextView authorAbout = (TextView) findViewById(R.id.about);
            String about = "About:\n" + android.text.Html.fromHtml(author.getAbout()).toString();
            authorAbout.setText(about);

            //Author Book List
            List<Book> books = author.getBooks();
            listInit(books);


        } catch (Exception e) {
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

    private void listInit(List<Book> books) throws Exception {
        //populate the Book List
        ArrayAdapter<Book> booksArrayAdaptor = new BookListAdaptor(this, 0, books);
        ListView listView = (ListView) findViewById(R.id.authorBookList);
        listView.setAdapter(booksArrayAdaptor);
        justifyListViewHeightBasedOnChildren(listView);
    }

    public void justifyListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }


    class BookListAdaptor extends ArrayAdapter<Book> {
        Context context;
        List<Book> objects;
        Bitmap bookImg[];

        public BookListAdaptor(Context context, int resource, List<Book> objects) {
            super(context, resource, objects);
            this.context = context;
            this.objects = objects;
            bookImg = new Bitmap[objects.size()];
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Book book = objects.get(position);

            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            View v = inflater.inflate(R.layout.book_item, null);

            //Book Name
            TextView actorName = (TextView) v.findViewById(R.id.bookItemName);
            actorName.setText(book.getTitle());

            //description
            String desc = "";
            desc += "Avg Rating: " + book.getAverageRating() + "\n";
            desc += "Rating count: " + book.getRatingsCount() + "\n";
            desc += "Published: " + book.getYearPublished() + "\n";


            TextView descView = (TextView) v.findViewById(R.id.bookItemdescription);
            descView.setText(desc);

            //Book Img
            ImageView bookImage = (ImageView) v.findViewById(R.id.bookItemImage);

            if (bookImg[position] == null) {
                bookImg[position] = loadBitmap(book.getImageUrl());
            }
            if (bookImg[position] != null)
                bookImage.setImageBitmap(bookImg[position]);

            return v;
        }
    }
}

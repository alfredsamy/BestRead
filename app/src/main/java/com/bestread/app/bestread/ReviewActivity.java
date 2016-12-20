package com.bestread.app.bestread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.goodreads.api.v1.GoodreadsService;

import static com.bestread.app.bestread.SessionManager.g;

public class ReviewActivity extends AppCompatActivity {

    public String bookMId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        g= new GoodreadsService();
        String bookId = getIntent().getStringExtra("ID");
        this.bookMId = bookId;

        Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"1", "2", "3", "4", "5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }

    public void postReview(View view){
        EditText text = (EditText) findViewById(R.id.editText);
        String review = text.getText().toString();

        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        int rating = 0;
        if(spinner.getSelectedItem()!=null){
            rating = Integer.parseInt(spinner.getSelectedItem().toString());
        }

        String bookId = this.bookMId;

        try{
            boolean response = g.postReview(bookId,review,rating);
            if(response){
                Toast toast = Toast.makeText(getApplicationContext(), "Review posted successfully.", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(), "Book reviewed before.", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        catch (Exception e){
            Toast toast = Toast.makeText(getApplicationContext(), "Error happened, Try again later", Toast.LENGTH_SHORT);
            toast.show();
            e.printStackTrace();

        }
    }
}

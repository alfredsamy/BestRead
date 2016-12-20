package com.bestread.app.bestread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goodreads.api.v1.GoodreadsResponse;
import com.goodreads.api.v1.GoodreadsService;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;

import java.io.InputStream;

public class User extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        DisplayUserProfile();
    }

    private void DisplayUserProfile() {
        GoodreadsService g = new GoodreadsService();
        try {
            com.goodreads.api.v1.User user = g.getAuthorizedUser().getCorrectUser();
            if(user == null){
                Toast toast = Toast.makeText(getApplicationContext(), "Error Loading Profile", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }


            // get user profile picture
            String urldisplay = user.getImageUrl();
            Log.d("DEBUG", "Got image URL: " + urldisplay);
            InputStream in = new java.net.URL(urldisplay).openStream();
            Bitmap pic = BitmapFactory.decodeStream(in);

            // set the imageview to user profile pic
            ImageView profilePic = (ImageView) findViewById(R.id.userpic);
            profilePic.setImageBitmap(pic);

            // get and set username in view
            TextView userNameView = (TextView) findViewById(R.id.username);
            Log.d("DEBUG", "Got Name:" + user.getName());
            userNameView.setText(user.getName());

            // get and set about in view
            TextView userAboutView = (TextView) findViewById(R.id.userabout);
            Log.d("DEBUG", "Got about:" + user.getAbout());
            userAboutView.setText(user.getAbout());


            // get and set friends count in view
            TextView friends_count_textview = (TextView) findViewById(R.id.friends_count);
            friends_count_textview.setText("" + user.getFriendsCount());

            // get and set reviews count in view
            TextView reviews_count_textview = (TextView) findViewById(R.id.reviews_count);
            reviews_count_textview.setText("" + user.getReviewsCount());


            // get and set last active in view
            TextView last_active_textview = (TextView) findViewById(R.id.last_active);
            last_active_textview.setText("" + user.getLastActive());


            // get and set joined in view
            TextView joined_textview = (TextView) findViewById(R.id.joined);
            joined_textview.setText("" + user.getJoined());

        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(), "Error Loading Profile", Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }
    }
}

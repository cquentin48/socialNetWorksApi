package com.example.lpiem.facebookapp;

import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.io.File;

public class TwitterHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_home);

        String username = getIntent().getStringExtra("username");
        TextView usernameTwitterTextView = findViewById(R.id.twitterUsernameTextView);
        usernameTwitterTextView.setText(username);
        Uri imageUri = FileProvider.getUriForFile(TwitterHomeActivity.this,
                BuildConfig.APPLICATION_ID + ".file_provider",
                new File(""));
        TweetComposer.Builder builder = new TweetComposer.Builder(this)
                .text("just setting up my Twitter Kit.")
                .image(imageUri);
        builder.show();
    }
}

package com.example.lpiem.facebookapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private CallbackManager callbackManager;
    private FacebookApiPresenter facebookApiPresenter;
    private AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Twitter.initialize(this);

        callbackManager = CallbackManager.Factory.create();
        facebookApiPresenter = new FacebookApiPresenter(this);

        accessToken = AccessToken.getCurrentAccessToken();

        LoginButton loginButton = findViewById(R.id.login_button_facebook);
        loginButton.setReadPermissions(Arrays.asList("user_status","user_friends"));
        if(facebookApiPresenter.isUserLoggedIn() == true){
            facebookApiPresenter.logIn(loginButton,callbackManager,accessToken);
            Intent facebookIntent = new Intent(MainActivity.this, MainActivityFacebook.class);
            startActivity(facebookIntent);
        }else{
            LoginManager.getInstance().logOut();
        }

        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("CONSUMER_KEY", "CONSUMER_SECRET"))
                .debug(true)
                .build();
        Twitter.initialize(config);
        TwitterLoginButton loginButtonTwitter = findViewById(R.id.login_button_twitter);
        loginButtonTwitter.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;

                login(session);
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(MainActivity.this,
                               "Erreur dans la connexion : vérifiez vos indentifiants",
                                     Toast.LENGTH_SHORT).show();
                exception.printStackTrace();
            }
        });

    }

    private void login(TwitterSession session){
        String username = session.getUserName();
        Intent intent = new Intent(MainActivity.this, TwitterHomeActivity.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void startActivityFacebook(GraphResponse response){
        Intent intent = new Intent(MainActivity.this,MainActivityFacebook.class);
        try{
            JSONArray rawName = response.getJSONObject().getJSONArray("data");
            intent.putExtra("jsondata",rawName.toString());
            Log.d("Retour JSONData",rawName.toString());
            startActivity(intent);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}

package com.example.lpiem.facebookapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.CallbackManager;
import com.facebook.GraphResponse;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    private CallbackManager callbackManager;
    private FacebookApiPresenter facebookApiPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callbackManager = CallbackManager.Factory.create();
        facebookApiPresenter = new FacebookApiPresenter(this);

        LoginButton loginButton = findViewById(R.id.login_button);
        if(facebookApiPresenter.isUserLoggedIn() == true){
            this.startActivityFacebook(null);
        }else{
            facebookApiPresenter.logIn(loginButton,callbackManager);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void startActivityFacebook(GraphResponse response){
        Intent intent = new Intent(MainActivity.this,MainActivityFacebook.class);
        try{
            JSONArray array = response.getJSONObject().getJSONArray("data");
            intent.putExtra("jsondata", array.toString());
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

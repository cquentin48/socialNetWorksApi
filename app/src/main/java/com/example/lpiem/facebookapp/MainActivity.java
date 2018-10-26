package com.example.lpiem.facebookapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Arrays;

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
        loginButton.setReadPermissions(Arrays.asList("user_status","user_friends"));
        if(facebookApiPresenter.isUserLoggedIn() == true){
            Intent facebookIntent = new Intent(MainActivity.this, MainActivityFacebook.class);
            startActivity(facebookIntent);
        }else{
            LoginManager.getInstance().logOut();
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
            JSONArray rawName = response.getJSONObject().getJSONArray("data");
            intent.putExtra("jsondata",rawName.toString());
            Log.d("Retour JSONData",rawName.toString());
            startActivity(intent);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}

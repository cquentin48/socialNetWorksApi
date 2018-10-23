package com.example.lpiem.facebookapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivityFacebook extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_facebook);

        context = this;
        Button logOutButton = findViewById(R.id.decoFacebook);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                Intent goHomeIntent = new Intent(MainActivityFacebook.this, MainActivity.class);
                startActivity(goHomeIntent);
            }
        });
    }

    public void setUserListFacebook(GraphResponse response){
        Intent intent = new Intent(context,MainActivityFacebook.class);
        try{
            JSONArray array = response.getJSONObject().getJSONArray("data");
            intent.putExtra("jsondata", array.toString());
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

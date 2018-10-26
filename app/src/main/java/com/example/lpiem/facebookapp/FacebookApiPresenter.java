package com.example.lpiem.facebookapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Caesar01 on 22/10/2018.
 */

public class FacebookApiPresenter implements FacebookApiInterface {
    private MainActivity mainActivity;

    /**
     * Affichage des notifications
     */
    @Override
    public void notifications() {

    }

    /**
     * Constructeur par défaut
     */
    public FacebookApiPresenter(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Override
    public void logIn(LoginButton loginButton, CallbackManager callbackManager) {

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {Bundle params = new Bundle();
                params.putString("user", "cquentin48");
                params.putString("role", "administrators");
                new GraphRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/8325f4957059689394b689305819752d/roles",
                    null,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            mainActivity.startActivityFacebook(response);
                        }
                    }
            ).executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d("Information","Connexion annulée");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.e("Erreur",exception.toString());
            }
        });
    }

    @Override
    public void logOut() {
        LoginManager.getInstance().logOut();
    }

    @Override
    public void share() {

    }

    @Override
    public boolean isUserLoggedIn() {
        if(AccessToken.getCurrentAccessToken() != null){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Initialisation de l'interface de connexion
     */
    @Override
    public CallbackManager initConnectionStatus(CallbackManager callBackManager) {
        return CallbackManager.Factory.create();
    }
}

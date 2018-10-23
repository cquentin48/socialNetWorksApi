package com.example.lpiem.facebookapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
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
        loginButton.setReadPermissions("email");

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) { AccessToken token = AccessToken.getCurrentAccessToken();
                GraphRequest graphRequest = new GraphRequest(token,"me/friends",null,HttpMethod.GET(
                        new GraphRequest.Callback(){

                            /**
                             * The method that will be called when a request completes.
                             *
                             * @param response the Response of this request, which may include error information if the
                             *                 request was unsuccessful
                             */
                            @Override
                            public void onCompleted(GraphResponse response) {
                                mainActivity.startActivityFacebook(response);

                            }
                        }
                ))
                Bundle param = new Bundle();
                param.putString("fields", "friendlist");
                graphRequest.setParameters(param);
                graphRequest.executeAsync();
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

    private void myNewGraphReq(String friendlistId) {
        final String graphPath = "/"+friendlistId+"/members/";
        AccessToken token = AccessToken.getCurrentAccessToken();
        GraphRequest request = new GraphRequest(token, graphPath, null, HttpMethod.GET, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {
                JSONObject object = graphResponse.getJSONObject();
                try {
                    JSONArray arrayOfUsersInFriendList= object.getJSONArray("data");
                /* Do something with the user list */
                /* ex: get first user in list, "name" */
                    JSONObject user = arrayOfUsersInFriendList.getJSONObject(0);
                    String usersName = user.getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle param = new Bundle();
        param.putString("fields", "name");
        request.setParameters(param);
        request.executeAsync();
    }
}

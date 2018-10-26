package com.example.lpiem.facebookapp;

import android.content.Intent;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

/**
 * Created by Caesar01 on 22/10/2018.
 */

public interface FacebookApiInterface {
    /**
     * Affichage des notifications
     */
    void notifications();

    /**
     * Fonction de connexion
     */
    void logIn(LoginButton loginButton, CallbackManager callbackManager, final AccessToken accessToken);

    /**
     * Fonction de déconnexion
     */
    void logOut();

    /**
     * Fonction de partage
     */
    void share();

    /**
     * Vérifie si l'utilisateur est connecté ou non
     * @return true => Connecté | false => non connecté
     */
    boolean isUserLoggedIn();

    /**
     * Initialisation de l'interface de connexion
     */
    CallbackManager initConnectionStatus(CallbackManager callbackManager);
}

package com.example.lpiem.facebookapp;

import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.twitter.sdk.android.tweetcomposer.TweetUploadService;

/**
 * Created by Caesar01 on 02/11/2018.
 */

public class MyResultReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (TweetUploadService.UPLOAD_SUCCESS.equals(intent.getAction())) {
            Log.d("Opération","Succès de l'envoi d'un tweet");

        } else if (TweetUploadService.UPLOAD_FAILURE.equals(intent.getAction())) {
            Log.d("Opération","Erreur de l'envoi d'un tweet");

        } else if (TweetUploadService.TWEET_COMPOSE_CANCEL.equals(intent.getAction())) {
            // cancel
        }
    }
}

package com.example.ilovezappos;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;

import androidx.core.app.NotificationCompat;

import com.firebase.jobdispatcher.JobService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TMyService extends JobService {
    Double listitem;
    BackgroundTask backgroundTask;

    @Override
    public boolean onStartJob(final com.firebase.jobdispatcher.JobParameters job) {
        listitem = job.getExtras().getDouble("number");
        return false;
    }

    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
        backgroundTask.cancel(true);
        return true;
    }

    private void sendNotification(String messageBody) {

        Intent intent = new Intent(this, ThirdActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("Alert!")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 , notificationBuilder.build());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        backgroundTask = new BackgroundTask();
        backgroundTask.execute();


    }


    public class BackgroundTask extends AsyncTask<Void, Void, Void> {
        String data = "";
        JSONObject jsonObject;
        Double dbaprice;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL("https://www.bitstamp.net/api/v2/ticker_hour/btcusd/");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    data = data + line;
                }

                jsonObject = new JSONObject(data);
                dbaprice = jsonObject.getDouble("bid");

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (listitem > dbaprice) {
                sendNotification("The bitcoin price has fallen below the specified price");
            }
            return null;
        }
    }
}

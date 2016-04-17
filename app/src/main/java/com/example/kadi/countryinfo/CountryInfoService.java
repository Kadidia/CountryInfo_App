package com.example.kadi.countryinfo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kadi on 05/04/2016.
 */
public class CountryInfoService extends Service{
    private TimerTask task ;
    private Timer timer;
    private IBinder myBinder = new MyServiceBinder();
    private JSONObject jObject;
    private String message = "waiting for weather information...";
    final Handler handler = new Handler();


    private String responseConnection;

    public void onCreate(){

    }
    public void onDestroy(){

        timer.cancel();

    }
    @Deprecated
    public void onStart(Intent intent, int startId){

    }
    public int onStartCommand(Intent intent, int flags, int startId){

        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable(){
                    public void run(){
                        for(int i= 0; i< 2; i++) {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }

                    }});
                try {
                    responseConnection = httpRequest("http://pastebin.com/raw/a0Z1q9bM");
                    jObject = new JSONObject(responseConnection);
                    JSONArray jArray = jObject.getJSONArray("weatherObservations");

                    for(int i=0; i<jArray.length(); i++){

                        try {
                            JSONObject element = jArray.getJSONObject(i);
                            String clouds = element.getString("clouds");
                            String stationName = element.getString("stationName");

                            message = clouds + " - " + stationName;

                        }catch (Exception e){

                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        };
        timer.schedule(task, 0, 10000);


        return START_STICKY;


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return myBinder;
    }
    public String httpRequest(String url){
        try {

            URL myUrl = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection) myUrl.openConnection();
            if(httpCon.getResponseCode() != 200) {
                throw new Exception("Failed to connect");
                // or return null;
            }
            else {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(httpCon.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return response.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public class MyServiceBinder extends Binder implements myServiceInterface{
        public String getMyFlickrData(){
            return message;
        }
    }
}

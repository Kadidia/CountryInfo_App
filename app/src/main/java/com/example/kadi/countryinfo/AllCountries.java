package com.example.kadi.countryinfo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AllCountries extends AppCompatActivity implements View.OnClickListener {
    private Button connexion;
    private String message = " ";
    private AlphaAnimation inAnimation;
    private AlphaAnimation outAnimation;
    private FrameLayout progressBarHolder;
    private String responseConnection;
    private JSONObject jObject;
    private Button get_all_countries;
    private UsersBDDHandler usersBDDHandler;
    TextView username;
    private boolean ok = false;



    private ServiceConnection myConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            CountryInfoService.MyServiceBinder myBinder = (CountryInfoService.MyServiceBinder) service;
            message = myBinder.getMyFlickrData();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_countries);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        get_all_countries = (Button)findViewById(R.id.get_all_countries);
        progressBarHolder = (FrameLayout) findViewById(R.id.progressBarHolder);
        usersBDDHandler = new UsersBDDHandler(this);
        username = (TextView)findViewById(R.id.name);
        Bundle dataExtras = getIntent().getExtras();
        username.setText("Hello " + dataExtras.getString("username"));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_all_countries: {

                Intent intent = new Intent(getApplicationContext(), CountryInfoService.class);
                startService(intent);
                bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
                new MyTask(this).execute();
            }
            break;
            case R.id.stop: {

                Intent intent = new Intent(getApplicationContext(), CountryInfoService.class);
                if(ok == false) {
                    unbindService(myConnection);
                    stopService(intent);
                    ok = true;
                }

            }
           break;
            case R.id.deconnexion: {

                        Intent intent = new Intent(getApplicationContext(), Authentification.class);
                        startActivity(intent);

            } break;
        }

    }


    private class MyTask extends AsyncTask<Void, Void, Boolean> {
        private Activity activity;

        public MyTask(Activity activity) {
            this.activity = activity;
        }
        public MyTask() {}

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            get_all_countries.setEnabled(false);
            inAnimation = new AlphaAnimation(0f, 1f);
            inAnimation.setDuration(200);
            progressBarHolder.setAnimation(inAnimation);
            progressBarHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Boolean ok) {
            super.onPostExecute(ok);
            outAnimation = new AlphaAnimation(1f, 0f);
            outAnimation.setDuration(200);
            progressBarHolder.setAnimation(outAnimation);
            progressBarHolder.setVisibility(View.GONE);
            get_all_countries.setEnabled(true);
            if(ok){

                activity.startActivity(new Intent(activity, ListOfCountries.class));
            }
            else Toast.makeText(AllCountries.this, "Connection failed try again!", Toast.LENGTH_LONG).show();


        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                responseConnection = httpRequest("http://pastebin.com/raw/JtLByQB9");
                jObject = new JSONObject(responseConnection);
                JSONArray jArray = jObject.getJSONArray("geonames");

                for(int i=0; i<jArray.length(); i++){

                    try {
                        JSONObject element = jArray.getJSONObject(i);
                        String name = element.getString("name");
                        String countryCode = element.getString("countrycode");
                        String toponymName = element.getString("toponymName");
                        String fclName = element.getString("fclName");
                        String fcodeName = element.getString("fcodeName");
                        String wikipediaLink = element.getString("wikipedia");
                        int population = Integer.parseInt(element.getString("population"));
                        Double lng = Double.parseDouble(element.getString("lng"));
                        Double lat = Double.parseDouble(element.getString("lat"));

                        CountryData data = new CountryData(name,countryCode,toponymName,fclName,fcodeName,
                                wikipediaLink,population,lng,lat);

                        List<CountryData> list = usersBDDHandler.getAllCountries();



                        if(!list.contains(data)) usersBDDHandler.addCountry(data);

                    }catch (Exception e){

                    }
                }
                return true;

                } catch (Exception e) {
                e.printStackTrace();
                return  false;
            }

        }
        public String httpRequest(String url){
            try {

                URL myUrl = new URL(url);
                HttpURLConnection httpCon = (HttpURLConnection) myUrl.openConnection();
                if(httpCon.getResponseCode() != 200) {
                    throw new Exception("Failed to connect");
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


    }
}

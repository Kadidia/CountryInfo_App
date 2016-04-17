package com.example.kadi.countryinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Authentification extends AppCompatActivity implements View.OnClickListener{
    private EditText Username;
    private EditText mdp;
    private User user;
    private UsersBDDHandler usersBDDHandler;
    private String ErrorMessage = "Sorry an error occurs...";
    private AlphaAnimation inAnimation;
    private AlphaAnimation outAnimation;
    private Button connexion;
    private  Intent broadcast;
    private boolean loadingTime = false;

    private FrameLayout progressBarHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        connexion = (Button)findViewById(R.id.connexion);
        progressBarHolder = (FrameLayout) findViewById(R.id.progressBarHolder);
        broadcast = new Intent("andro.jf.broadcast.UserInfo");
        usersBDDHandler = new UsersBDDHandler(this);
        user = new User("test","test");


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.connexion :{

                Username = (EditText)findViewById(R.id.edited_username);
                mdp = (EditText)findViewById(R.id.edited_mdp);

                if(Username.getText().toString().isEmpty() || mdp.getText().toString().isEmpty()){
                    Toast.makeText(Authentification.this, "Vous devez renseigner tous les champs!", Toast.LENGTH_LONG).show();

                }else{
                    user.setUsername(Username.getText().toString());
                    user.setMdp(mdp.getText().toString());
                    new MyTask(this).execute(user);
                }

            }
            break;
        }

    }
    private class MyTask extends AsyncTask<User, Void, Boolean> {
        private Activity activity;

        public MyTask(Activity activity) {
            this.activity = activity;
        }
        public MyTask() {}

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            connexion.setEnabled(false);
            inAnimation = new AlphaAnimation(0f, 1f);
            inAnimation.setDuration(200);
            progressBarHolder.setAnimation(inAnimation);
            progressBarHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Boolean ok) {
          //  loadingMessage.setText("Loading User Space");
            super.onPostExecute(ok);
            outAnimation = new AlphaAnimation(1f, 0f);
            outAnimation.setDuration(200);
            progressBarHolder.setAnimation(outAnimation);
            progressBarHolder.setVisibility(View.GONE);
            connexion.setEnabled(true);
            if(ok){
                Intent intent = new Intent(activity, AllCountries.class);
                intent.putExtra("username", user.getUsername());
                activity.startActivity(intent);
            }
            else Toast.makeText(Authentification.this, "Connection failed try again!", Toast.LENGTH_LONG).show();


        }

        @Override
        protected Boolean doInBackground(User... users) {

                    User user = users[0];
                    boolean exist = usersBDDHandler.isUserExist(user);
                    if(exist) return true;
                    else return false;

        }

    }
}

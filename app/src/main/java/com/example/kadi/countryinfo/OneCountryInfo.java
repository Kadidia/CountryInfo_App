package com.example.kadi.countryinfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OneCountryInfo extends AppCompatActivity {
    private Intent broadcast;
    private boolean ok = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_country_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button retour = (Button)findViewById(R.id.retour);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListOfCountries.class);
                startActivity(intent);
            }
        });

        Button deconnexion = (Button)findViewById(R.id.deconnexion);
        deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Authentification.class);
                startActivity(intent);
            }
        });

        Bundle dataExtras = getIntent().getExtras();
        TextView name = (TextView)findViewById(R.id.name);
        TextView code = (TextView)findViewById(R.id.code);
        TextView fclname = (TextView)findViewById(R.id.fclname);
        TextView topo_name = (TextView)findViewById(R.id.topo_name);
        TextView fcodename = (TextView)findViewById(R.id.fcodename);
        TextView population = (TextView)findViewById(R.id.population);
        TextView lng = (TextView)findViewById(R.id.lng);
        TextView lat = (TextView)findViewById(R.id.lat);
        TextView info = (TextView)findViewById(R.id.info);
        final TextView wiki = (TextView)findViewById(R.id.wiki);
     //   wiki.setMovementMethod(LinkMovementMethod.getInstance());
        broadcast = new Intent("andro.jf.broadcast.UserInfo");


        wiki.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                broadcast.putExtra("website", wiki.getText().toString());
                if(ok == false) {
                    sendBroadcast(broadcast);
                    ok = true;
                }
                return false;
            }
        });

        name.setText("Name: " + dataExtras.getString("name"));
        code.setText("Code: " + dataExtras.getString("code"));
        fclname.setText("Fclname: " + dataExtras.getString("fclname"));
        topo_name.setText("Toponyme_name: " + dataExtras.getString("toponyme"));
        fcodename.setText("Fcodename: " + dataExtras.getString("fcodename"));
        population.setText("Population: " + dataExtras.getString("population"));
        lng.setText("Lng: " + dataExtras.getString("lng"));
        lat.setText("Lat: " + dataExtras.get("lat"));
        wiki.setText(dataExtras.getString("wiki"));
    }

}

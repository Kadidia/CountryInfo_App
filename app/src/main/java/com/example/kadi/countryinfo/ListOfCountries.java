package com.example.kadi.countryinfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListOfCountries extends AppCompatActivity {
    private ListView countries;
    private UsersBDDHandler db;
    private CountryAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_countries);
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

        Button deconnexion = (Button)findViewById(R.id.deconnexion);
        deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Authentification.class);
                startActivity(intent);
            }
        });
        Button retour = (Button)findViewById(R.id.retour);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AllCountries.class);
                intent.putExtra("username", " ");
                startActivity(intent);
            }
        });
        db = new UsersBDDHandler(this);
        countries = (ListView)findViewById(R.id.allCountries);
        List<CountryData> countries_list = db.getAllCountries();

         adapter = new CountryAdapter(ListOfCountries.this, countries_list);
        countries.setAdapter(adapter);

        countries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                CountryData itemnom = (CountryData)parent.getItemAtPosition(position);

                Intent intent = new Intent(getApplicationContext(), OneCountryInfo.class);
                intent.putExtra("name", itemnom.getName());
                intent.putExtra("code", itemnom.getCountryCode());
                intent.putExtra("toponyme", itemnom.getToponymName());
                intent.putExtra("fclname", itemnom.getFclName());
                intent.putExtra("fcodename", itemnom.getFcodeName());
                intent.putExtra("population", String.valueOf(itemnom.getPopulation()));
                intent.putExtra("lng", String.valueOf(itemnom.getLng()));
                intent.putExtra("lat", itemnom.getLat());
                intent.putExtra("wiki", itemnom.getWikipediaLink());
                startActivity(intent);

            }
        });



    }

}

package com.example.weatherforecast1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toolbar;

import java.util.ArrayList;

public class weatherinfoactivity extends AppCompatActivity {

    RecyclerView recyclerView ;
    ArrayList<City>  weatherinfo ;
    Myadapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weatherinfoactivity);
        weatherinfo = new ArrayList<City>();

        String recievedata = getIntent().getStringExtra("7dayinfo");
        String[] daysinfo = recievedata.split("\n");

        for ( int i =0; i < daysinfo.length;i++)
        {
            String[] tempo = daysinfo[i].split("-");
            weatherinfo.add(new City(tempo[0] , tempo[3]  , tempo[2] ,tempo[1]));
        }

        recyclerView = (RecyclerView) findViewById(R.id.recylelist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myadapter = new Myadapter(weatherinfo);
        recyclerView.setAdapter(myadapter);

    }
}

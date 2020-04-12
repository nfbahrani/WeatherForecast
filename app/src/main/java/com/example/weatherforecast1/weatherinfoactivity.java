package com.example.weatherforecast1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class weatherinfoactivity extends AppCompatActivity {

    RecyclerView recyclerView ;
    ArrayList<City>  weatherinfo ;
    Myadapter myadapter;
    TextView showname;
    TextView time_date;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weatherinfoactivity);
        weatherinfo = new ArrayList<City>();

        String recievedata = getIntent().getStringExtra("7dayinfo");
        String name_city = getIntent().getStringExtra("nameofcity");
        String[] daysinfo = recievedata.split("\n");


        for ( int i =0; i < daysinfo.length;i++)
        {
            String[] tempo = daysinfo[i].split("-");
            weatherinfo.add(new City(tempo[0] , tempo[3]  , tempo[2] ,tempo[1]));
        }

        showname = findViewById(R.id.City);

        if (name_city == null)
            showname.setText("Old Datas!");
        else
            showname.setText(String.valueOf(name_city));


        time_date = findViewById(R.id.timeanddate);
        Date date= new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy / HH:mm:ss");
        time_date.setText(formatter.format(date));

        recyclerView = (RecyclerView) findViewById(R.id.recylelist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myadapter = new Myadapter(weatherinfo);
        recyclerView.setAdapter(myadapter);

    }
}

package com.example.weatherforecast1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar ;
    private Button showbtn ;
    private ListView listView;
    String messege;
    private ImageView imageView;


    ArrayAdapter<String> adapter_;
    String[] array = {};
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //// checking network connections

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null == activeNetwork) {
            messege = "No Internet connection";
            Toast.makeText(getApplicationContext(), messege, Toast.LENGTH_LONG).show();
        } else
            {

            listView= findViewById(R.id.listview);
            listView.setVisibility(View.GONE);
            showbtn = (Button) findViewById(R.id.showbutton);
            progressBar = findViewById(R.id.progressbar);
            progressBar.setVisibility(View.GONE);

            showbtn.setOnClickListener(new View.OnClickListener()
            {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this , second_activity.class);
//                startActivity(intent);            progressBar = findViewById(R.id.progressbar);            progressBar = findViewById(R.id.progressbar);

                progressBar.setVisibility(View.VISIBLE);
                //TODO recieve search result and change to strinf

                /// test ////
                array = new String[]{"Tehran" , "Mashhd" , "shiraz" , "tabrix" , "Kerman" , "Sari" };

                adapter_ = new ArrayAdapter<String>(MainActivity.this , android.R.layout.simple_list_item_1, array);
                listView.setAdapter(adapter_);
                progressBar.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);


            }

        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                progressBar.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);

                //TODO

                //TODO : send the x and y is position to another activity
                Intent myintent = new Intent(MainActivity.this, weatherinfoactivity.class);
                ///test
                //myintent.putExtra("Tehran" , position);
                startActivity(myintent);


            }
        });

        }

    }


}

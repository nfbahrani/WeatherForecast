package com.example.weatherforecast1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar ;
    private Button showbtn ;
    private ListView listView;
    String messege;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //// checking network connections

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null == activeNetwork) {
            messege = "No Internet connection";
            Toast.makeText(getApplicationContext(), messege, Toast.LENGTH_LONG).show();
        } else {

            ////progressbar
            init();
            progressBar.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                }
            }, 10000);


            /////button
            showbtn = (Button) findViewById(R.id.showbutton);

//        showbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this , second_activity.class);
//                startActivity(intent);
//            }
//        });

            ///listview
            listView = findViewById(R.id.listview);

        }
    }
    private void init () {
        this.progressBar = findViewById(R.id.progressbar);
    }
}

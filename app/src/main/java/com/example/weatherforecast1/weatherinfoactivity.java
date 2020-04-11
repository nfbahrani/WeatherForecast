package com.example.weatherforecast1;

        import androidx.annotation.RequiresApi;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.os.Build;
        import android.os.Bundle;
        import android.widget.Toolbar;

        import java.util.ArrayList;

public class weatherinfoactivity extends AppCompatActivity {

    RecyclerView recyclerView ;
    ArrayList<City>  weatherinfo ;
    Myadapter myadapter;

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weatherinfoactivity);

//        String recievedata = getIntent().getStringExtra("7dayinfo");
//        String[] daysinfo = recievedata.split("\n");
//
//
//        for ( int i =0; i < daysinfo.length;i++)
//        {
//            String[] tempo = daysinfo[i].split(" ");
//            weatherinfo.add(new City(tempo[0] , tempo[3]  , tempo[2] ,tempo[1]));
//        }


        weatherinfo = new ArrayList<City>();
        //TODO here we should fill with real arraystring
        //////test
        weatherinfo.add(new City("Saturday" , "20" , "3" , "snow"));
        weatherinfo.add(new City("Sunday" , "23" , "-1" , "rain"));
        weatherinfo.add(new City("Monday" , "18" , "2"  , "thunder"));
        weatherinfo.add(new City("Tuesday" , "17" , "2"  , "bizzard"));
        weatherinfo.add(new City("Wednesday" , "20" , "1"  , "cloudy"));
        weatherinfo.add(new City("Thursday" , "24" , "5"  , "sunny"));
        weatherinfo.add(new City("Friday" , "19.5" , "-2.4"  , "windy"));


        /////
        recyclerView = (RecyclerView) findViewById(R.id.recylelist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myadapter = new Myadapter(weatherinfo);
        recyclerView.setAdapter(myadapter);

    }
}

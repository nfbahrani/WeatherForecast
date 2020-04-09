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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weatherinfoactivity);


        weatherinfo = new ArrayList<City>();
        //TODO here we should fill with real arraystring
        //////test
        weatherinfo.add(new City("monday" , "344" , "847" , "snow"));
        weatherinfo.add(new City("tuesday" , "23" , "44" , "rain"));
        weatherinfo.add(new City("thursday" , "1233" , "2324"  , "sunny"));
        /////
        recyclerView = (RecyclerView) findViewById(R.id.recylelist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myadapter = new Myadapter(weatherinfo);
        recyclerView.setAdapter(myadapter);

    }
}

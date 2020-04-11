package com.example.weatherforecast1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private Button showbtn;
    private ListView listView;
    String messege;
    private String places = "";
    private String forecast_of_7_days = "";
    private EditText cityname ;

    ArrayAdapter<String> adapter_;
    String[] array = {};
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        //// checking network connections

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null == activeNetwork) {
            setContentView(R.layout.activity_weatherinfoactivity);
            messege = "No Internet connection";
            Toast.makeText(getApplicationContext(), messege, Toast.LENGTH_LONG).show();

        } else
            {
                setContentView(R.layout.activity_main);

             listView= findViewById(R.id.listview);
            listView.setVisibility(View.GONE);
            showbtn = (Button) findViewById(R.id.showbutton);
            progressBar = findViewById(R.id.progressbar);
            progressBar.setVisibility(View.GONE);
            cityname = findViewById(R.id.Citynametext);



            showbtn.setOnClickListener(new View.OnClickListener()
            {
            @Override
            public void onClick(View v) {


                progressBar.setVisibility(View.VISIBLE);
                Mapbox1 mapbox = new Mapbox1(cityname.getText().toString(), getApplicationContext());
                Log.i("test mapbox", String.valueOf(array));
                mapbox.run();

                array = places.split("\n");
                Log.i("arrray ////// ", array[0]);

                int number_cities = array.length;
                Log.i("number of cities", String.valueOf(number_cities));
                final String[] city_names = new String[number_cities];
                final String[][] coordinates = new String[number_cities][2];

//                for (int i = 0; i < number_cities; i++)
//                {
//
//                    String[] temp = array[i].split(" ");
//                    coordinates[i][0] = temp[temp.length - 2];
//                    coordinates[i][1] = temp[temp.length - 1];
//                    city_names[i] = temp[0];
//                    Log.i("temp string", temp[0]);
//                    for (int j = 0; j < temp.length - 2; j++) {
//                        city_names[i] += temp[j];
//                    }
//
//                }

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        progressBar.setVisibility(View.GONE);
                        adapter_ = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, array);

                        listView.setAdapter(adapter_);

                        listView.setVisibility(View.VISIBLE);
                    }
                }, 1000);

//                adapter_ = new ArrayAdapter<String>(MainActivity.this , android.R.layout.simple_list_item_1, array);
//
//                listView.setAdapter(adapter_);
//                progressBar.setVisibility(View.GONE);
//
//                listView.setVisibility(View.VISIBLE);


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        progressBar.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
//
//                        WeathereAPI weathereAPI = new WeathereAPI(getApplicationContext(), coordinates[position][1] , coordinates[position][1]);
//                        weathereAPI.run();

                        Intent myintent = new Intent(MainActivity.this, weatherinfoactivity.class);
//                        myintent.putExtra("7dayinfo" , forecast_of_7_days);

                        startActivity(myintent);

                        Handler h = new Handler();
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                            }
                        }, 1000);

                    }
                });
            }

        });


        }

    }



    class Mapbox1 extends Thread
    {
        private String cityname;
        private Context context;
        private static final String TAG = "MapboxClass ";
        private String token = "pk.eyJ1IjoibmZiYWhyYW5pIiwiYSI6ImNrOHJreThrbzBmZDQzaGs4OGZrNzZrNW8ifQ.4CQtlZesjnKfddP3K6Or_A";
        private String url = "https://api.mapbox.com/geocoding/v5/mapbox.places/";
        private RequestQueue queue;
        private JsonObjectRequest jsonObjectRequest;

        public String getAns() {
            return ans;
        }

        private String ans = "";

        public Mapbox1(String cityname, final Context context) {
            this.cityname = cityname;
            this.context = context;
        }


        @Override
        public void run()
        {
            queue = Volley.newRequestQueue(context);
            url += cityname + ".json?access_token=" + token;
            final String[] latitude = {null};
            final String[] longitude = {null};
            final String[] resault = {""};
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("features");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject feature = jsonArray.getJSONObject(i);
                            JSONObject geometry = feature.getJSONObject("geometry");
                            String placeName = feature.getString("place_name");
                            JSONArray coordinates = geometry.getJSONArray("coordinates");
                            longitude[0] = coordinates.getString(0);
                            latitude[0] = coordinates.getString(1);
                            ans += placeName + " " + longitude[0] + " " + latitude[0] + "\n";
                        }

                        Handler handler = new Handler();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.i(TAG, "msg : " + ans);

                                places = ans;
                            }
                        });
                    } catch (Exception e)
                    {
                        Log.i(TAG, "err0 : " + e.getMessage());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i(TAG, "err : " + error.getMessage());
                }
            });
            queue.add(jsonObjectRequest);
        }
    }


    class WeathereAPI extends Thread
    {
        private Context context;
        private static final String TAG = "WeathereAPIClass";
        private int num_of_days = 7;
        private String latitude;
        private String longitude;
        private String secret_key = "c0d22705c9144da289a120020200904";
        private String url = "http://api.weatherapi.com/v1/forecast.json?q=";

        private RequestQueue queue;
        private JsonObjectRequest jsonObjectRequest;

        public String getAns() {
            return ans;
        }

        private String ans = "";

        public WeathereAPI(final Context context, String latitude, String longitude) {
            this.context = context;
            this.latitude = latitude;
            this.longitude = longitude;
        }


        @Override
        public void run() {
            url += String.valueOf(latitude) + "," + String.valueOf(longitude) + "&key=" + secret_key + "&days=" + String.valueOf(num_of_days);
            queue = Volley.newRequestQueue(context);
            final String[] date_epoch = {null};
            final String[] mintemp_c = {null};
            final String[] maxtemp_c = {null};
            final String[] status = {null};
            final String[] resault = {""};
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject forecastObj = response.getJSONObject("forecast");
                        JSONArray daysArray = forecastObj.getJSONArray("forecastday");
                        for (int i = 0; i < daysArray.length(); i++) {
                            JSONObject feature = daysArray.getJSONObject(i);
                            JSONObject geometry = feature.getJSONObject("geometry");
                            String placeName = feature.getString("place_name");
                            mintemp_c[0] = String.valueOf(feature.getJSONObject("mintemp_c"));
                            maxtemp_c[0] = String.valueOf(feature.getJSONObject("maxtemp_c"));
                            date_epoch[0] = String.valueOf(feature.getJSONObject("date_epoch"));
                            JSONObject condition = feature.getJSONObject("condition");
                            status[0] = String.valueOf(condition.getJSONObject("text"));
                            ans += String.valueOf(date_epoch) + " " + status + " " + mintemp_c[0] + " " + maxtemp_c[0] + "\n";
                        }
                        Log.i(TAG, "msg : " + ans);
                        Handler handler = new Handler();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                forecast_of_7_days = ans;
                            }
                        });
                    } catch (Exception e) {
                        Log.i(TAG, "err : " + e.getMessage());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i(TAG, "err : " + error.getMessage());
                }
            });
            queue.add(jsonObjectRequest);
        }
    }

}




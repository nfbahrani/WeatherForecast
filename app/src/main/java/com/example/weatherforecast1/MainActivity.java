package com.example.weatherforecast1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private Button showbtn;
    private ListView listView;
    String messege;
    String readdata = "";

    private String forecast_of_7_days = "";
    private EditText cityname;

    ArrayAdapter<String> adapter_;
    String[] array;


    String fileName = "save_data.txt";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //// checking network connections

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null == activeNetwork) {
            setContentView(R.layout.activity_weatherinfoactivity);

            messege = "No Internet connection";

            try {
                readdata = readfile(fileName);
            } catch (IOException e) {
                Log.i("read_error", e.getMessage());
            }
            Intent intent = new Intent(MainActivity.this, weatherinfoactivity.class);
            intent.putExtra("7dayinfo", readdata);
            startActivity(intent);
            finish();
            Toast.makeText(getApplicationContext(), messege, Toast.LENGTH_LONG).show();

        } else {
            setContentView(R.layout.activity_main);
            listView = findViewById(R.id.listview);
            listView.setVisibility(View.GONE);
            showbtn = (Button) findViewById(R.id.showbutton);
            progressBar = findViewById(R.id.progressbar);
            progressBar.setVisibility(View.GONE);
            cityname = findViewById(R.id.Citynametext);


            showbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    Mapbox1 mapbox = new Mapbox1(cityname.getText().toString(), getApplicationContext());
                    mapbox.run();

                }

            });

        }
    }


    public void savefile(String file, String text) throws IOException {
        FileOutputStream fos = openFileOutput(file, Context.MODE_PRIVATE);
        fos.write(text.getBytes());
        fos.close();

    }

    public String readfile(String file) throws IOException {
        String text = "";
        FileInputStream fis = openFileInput(file);
        int ch = fis.read();
        while (ch != -1) {
            ch = fis.read();
            text += (char) ch;
        }
        text = text.substring(0, text.length() - 1);
        return text;

    }


    class Mapbox1 extends Thread {
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
        public void run() {
            queue = Volley.newRequestQueue(context);
            url += cityname + ".json?access_token=" + token;
            final String[] latitude = {null};
            final String[] longitude = {null};
            final String[] lat = {null}, lon = {null};
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
                            if (lat[0] == null) {
                                lat[0] = latitude[0];
                                lon[0] = longitude[0];
                            }

                            JSONArray temp = feature.getJSONArray("place_type");
                            if (String.valueOf(temp.getString(0)).equals("place") ||
                                    String.valueOf(temp.getString(0)).equals("region"))
                                ans += placeName + " " + longitude[0] + " " + latitude[0] + "\n";
                        }
                        if (ans.equals("")){
                            ans += cityname + " " + lon[0] + " " + lat[0] + "\n";
                            Log.i("**************   ", ans);
                        }

                        Handler handler = new Handler();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //places = ans;
                                //Log.i(TAG, "answer =places  : " + places);
                                progressBar.setVisibility(View.GONE);
                                array = ans.split("\n");
                                int number_cities = array.length;
                                Log.i("number of cities", String.valueOf(number_cities));
                                final String[] city_names = new String[number_cities];
                                final String[][] coordinates = new String[number_cities][2];

                                for (int i = 0; i < number_cities; i++) {

                                    String[] temp = array[i].split(" ");
                                    coordinates[i][0] = temp[temp.length - 2];
                                    coordinates[i][1] = temp[temp.length - 1];
                                    city_names[i] = temp[0];
                                    for (int j = 1; j < temp.length - 2; j++) {
                                        city_names[i] += temp[j];
                                    }

                                }

                                adapter_ = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, city_names);
                                listView.setAdapter(adapter_);
                                listView.setVisibility(View.VISIBLE);


                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        progressBar.setVisibility(View.VISIBLE);
                                        listView.setVisibility(View.GONE);

                                        WeathereAPI weathereAPI = new WeathereAPI(getBaseContext(), coordinates[position][1], coordinates[position][0]);
                                        weathereAPI.run();


                                    }
                                });

                            }
                        });
                    } catch (Exception e) {
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

    class WeathereAPI extends Thread {
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
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(final JSONObject response) {
                    try {

                        JSONObject forecast = response.getJSONObject("forecast");
                        JSONArray forecastday = forecast.getJSONArray("forecastday");
                        for (int i = 0; i < forecastday.length(); i++) {
                            JSONObject current_day = forecastday.getJSONObject(i);
                            date_epoch[0] = String.valueOf(current_day.getLong("date_epoch"));
                            JSONObject day = current_day.getJSONObject("day");
                            JSONObject condition = day.getJSONObject("condition");
                            status[0] = String.valueOf(condition.getString("text"));
                            maxtemp_c[0] = String.valueOf(day.getDouble("maxtemp_c"));
                            mintemp_c[0] = String.valueOf(day.getDouble("mintemp_c"));
                            ans += String.valueOf(date_epoch[0]) + "-" + status[0] + "-" + mintemp_c[0] + "-" + maxtemp_c[0] + "\n";
                        }

                        Log.i(TAG, "msg : " + ans);
                        Handler handler = new Handler();
                        handler.post(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void run() {
                                forecast_of_7_days = ans;

                                Intent myintent = new Intent(MainActivity.this, weatherinfoactivity.class);
                                myintent.putExtra("7dayinfo", forecast_of_7_days);
                                myintent.putExtra("nameofcity", cityname.getText().toString());

                                try {
                                    savefile(fileName, forecast_of_7_days);
                                } catch (IOException e) {
                                    Log.i("save_error", e.getMessage());
                                }
                                startActivity(myintent);
                                //finish();
                                Handler h = new Handler();

                                progressBar.setVisibility(View.GONE);

                            }
                        });
                    } catch (Exception e) {
                        Log.i(TAG, "err1 : " + e.getMessage());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i(TAG, "err3 : " + error.getMessage());
                }
            });
            queue.add(jsonObjectRequest);
        }
    }

}




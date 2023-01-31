package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ProgressBar pb;
    private RelativeLayout homeRl;
    private TextView citynameTV, temperatureTV, conditionTV;
    private ImageView searchIV;
    private TextInputEditText cityEDT;
    private RecyclerView weatherRV;
    private List<Weather> weatherList;
    private WeatherAdapter weatherAdapter;
    private LocationManager locationListener;
    private int PERMISSION_CODE = 1;
    private  String cityname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);

        pb = findViewById(R.id.pb);
        homeRl = findViewById(R.id.rootView);
        citynameTV = findViewById(R.id.cityName);
        temperatureTV = findViewById(R.id.temperature);
        conditionTV = findViewById(R.id.weatherType);
        searchIV = findViewById(R.id.searchIc);
        cityEDT = findViewById(R.id.cityInput);
        weatherRV = findViewById(R.id.weatherList);
        weatherList = new ArrayList<>();
        weatherAdapter = new WeatherAdapter(this, weatherList);

        locationListener = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CODE);
        }

        Location location = locationListener.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        cityname = getCityName(location.getLongitude(), location.getLatitude());
        getWeatherInfo(cityname);

        searchIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = cityEDT.getText().toString();
                if(city.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter City Name", Toast.LENGTH_SHORT).show();
                }
                else{
                    citynameTV.setText(cityname);
                    getWeatherInfo(city);
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Please Provide the Permission", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private String getCityName(double longitude, double latitude){
        String cityName = "Nor Found";
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        try{
            List<Address> addresses = gcd.getFromLocation(latitude, longitude, 10);
            for(Address adr : addresses){
                if(adr != null){
                    String city = adr.getLocality();
                    if(city != null && !city.equals(""))
                        cityName = city;
                    else
                        Toast.makeText(this, "User City Not Found", Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return cityName;
    }
    private void getWeatherInfo(String cityName){
        String url = "http://api.weatherapi.com/v1/forecast.json?key=d263b44f1fe74c0bba6124407233001&q"+cityName+"&days=1&aqi=no&alerts=no";
        citynameTV.setText(cityName);
        RequestQueue requestQueue = Volley.newRequestQueue(this);

    }
}
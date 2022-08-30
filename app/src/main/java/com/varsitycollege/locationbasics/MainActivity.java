package com.varsitycollege.locationbasics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements LocationListener {
    LocationManager locationManager;
    private static final int GPS_TIME_INTERVAL = 1000*60*1; // get gps location every 1 min
    private static final int GPS_DISTANCE = 1000; // set the distance value in meters
    private static final int HANDLER_DELAY = 1000*60*1;
    private static final int START_HANDLER_DELAY = 0;

    ListView lstOutput;
    final static String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    final static int PERMISSION_ALL = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT>=23){
            requestPermissions(PERMISSIONS,PERMISSION_ALL);
        }

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run()
            {
                requestLocation();
                handler.postDelayed(this,HANDLER_DELAY);
            }
        },START_HANDLER_DELAY);
    }

    private void requestLocation() {
        if (locationManager == null)
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            if(ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)==
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        GPS_TIME_INTERVAL,GPS_DISTANCE,this);
            }
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }
}
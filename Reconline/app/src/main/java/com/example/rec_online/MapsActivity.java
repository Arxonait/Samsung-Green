package com.example.rec_online;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.rec_online.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.Manifest;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private boolean isCreate_device_metca = true;
    private Marker marker = null;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);




        // Инициализация LocationManager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Обработка обновлений местоположения пользователя
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                LatLng latLng = new LatLng(latitude, longitude);

                if (isCreate_device_metca) {
                    marker = mMap.addMarker(new MarkerOptions().position(latLng).title("Вы здесь"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
                    isCreate_device_metca = false;
                }
                marker.setPosition(latLng);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }


        };

        // Проверка доступности провайдера GPS
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // Запрос разрешений
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
            // Регистрация LocationListener
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
            Toast.makeText(this, "GPS не доступен", Toast.LENGTH_SHORT).show();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map3);
        mapFragment.getMapAsync(this);



    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        for (factory factory : pass_act.factories) {
            Marker new_marker;
            LatLng latLng = new LatLng(factory.x, factory.y);
            new_marker = mMap.addMarker(new MarkerOptions().position(latLng).title(factory.name));
            new_marker.setPosition(latLng);
        }


 












        Button plusZoom = (Button) findViewById(R.id.plus);
        Button minusZoom = (Button) findViewById(R.id.minus);

        Button myGeo = (Button) findViewById(R.id.geo);

        plusZoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float currentZoom = mMap.getCameraPosition().zoom;
                float newZoom = currentZoom + 1.0f; // Увеличение зума на 1

                CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(newZoom);
                mMap.animateCamera(cameraUpdate);

            }
        });

        minusZoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float currentZoom = mMap.getCameraPosition().zoom;
                float newZoom = currentZoom - 1.0f;

                CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(newZoom);
                mMap.animateCamera(cameraUpdate);

            }
        });

        myGeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToMyLocation();
            }
        });




    }

    protected void onDestroy() {
        super.onDestroy();
        // Остановка получения местоположения при закрытии приложения
        locationManager.removeUpdates(locationListener);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }


    private void moveToMyLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);

        if (provider != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Location location = locationManager.getLastKnownLocation(provider);
                if (location != null) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15.0f);
                    mMap.animateCamera(cameraUpdate);
                }
            }
        }
    }
}


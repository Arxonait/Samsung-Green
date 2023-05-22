package com.example.rec_online;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.rec_online.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;

import android.Manifest;

import java.io.IOException;




public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private boolean isCreate_device_metca = true;
    private Marker marker = null;


    static User user;
    public static void getData(User user0) {
        user = user0;
    }


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
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
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


        //


        //
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LatLng startPoint = new LatLng(47.216449, 39.626977); // начальная точка
        LatLng endPoint = new LatLng(47.210331, 39.670987); // конечная точка

        mMap.addMarker(new MarkerOptions().position(startPoint).title("Start Point"));
        mMap.addMarker(new MarkerOptions().position(endPoint).title("End Point"));

        DirectionsResult result = null;
        try {
            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey("AIzaSyCKmbN1VyhhxY_GcIGrSItDdkNEW20B2Ds")
                    .build();
            DirectionsApiRequest request = DirectionsApi.newRequest(context)
                    .origin(String.valueOf(startPoint))
                    .destination(String.valueOf(endPoint));
            result = request.await();
        } catch (ApiException | InterruptedException | IOException e) {
            // обработка исключения
            Log.e(TAG, "Ошибка: " + e.getMessage());
        }


        // добавляем маркеры на карту


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
}


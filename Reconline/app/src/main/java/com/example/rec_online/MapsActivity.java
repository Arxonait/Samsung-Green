package com.example.rec_online;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rec_online.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.Manifest;

import java.util.HashMap;
import java.util.Map;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private static GoogleMap mMap;
    private ActivityMapsBinding binding;

    private static LocationManager locationManager;
    private LocationListener locationListener;

    private boolean isCreate_device_marker = true;
    private Marker marker_geo = null;
    private String marker_geo_id;
    private ConstraintLayout menu_fact;
    private TextView  name_fact;
    private TextView  adres_fact;
    private TextView  mobile_fact;
    private TextView  work_time_fact;
    private TextView  distance_fact;

    private ImageView im_glass;
    private ImageView im_plastic;
    private ImageView im_metal;

    private Map<String, Marker> markerMap;


    public static Factory_obj factory = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getSupportActionBar().hide();



        markerMap = new HashMap<>();
        // Инициализация LocationManager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Обработка обновлений местоположения пользователя
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                LatLng latLng = new LatLng(latitude, longitude);

                if (isCreate_device_marker) {
                    try {
                        marker_geo = mMap.addMarker(new MarkerOptions().position(latLng).title("Вы здесь"));
                        if(factory != null){
                            start_location();
                        }
                        else {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
                        }

                        isCreate_device_marker = false;
                        markerMap.put("my_geo", marker_geo);
                        marker_geo_id = marker_geo.getId();
                        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.geo);
                        marker_geo.setIcon(icon);
                        marker_geo.setPosition(latLng);
                    } catch (Exception e){

                    }

                }

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

        try {
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
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
        catch (Exception e){

        }




    }

    public void onMapReady(GoogleMap googleMap) {
        load_menu();
        mMap = googleMap;
        menu_fact = findViewById(R.id.menu_fact);

        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.green);

        for (Factory_obj factory : Pass_act.factories) {
            Marker new_marker;
            LatLng latLng = new LatLng(factory.x, factory.y);
            new_marker = mMap.addMarker(new MarkerOptions().position(latLng).title(factory.name));
            new_marker.setPosition(latLng);
            new_marker.setIcon(icon);

            markerMap.put(String.valueOf(factory.id), new_marker);

        }
        mMap.setOnMarkerClickListener(this);


        ImageButton plusZoom = findViewById(R.id.plus);
        ImageButton minusZoom = findViewById(R.id.minus);

        ImageButton myGeo = findViewById(R.id.my_geo);


        plusZoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeAnimation = AnimationUtils.loadAnimation(MapsActivity.this, R.anim.fade);
                v.startAnimation(fadeAnimation);
                float currentZoom = mMap.getCameraPosition().zoom;
                float newZoom = currentZoom + 1.0f; // Увеличение зума на 1

                CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(newZoom);
                mMap.animateCamera(cameraUpdate);

            }
        });

        minusZoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeAnimation = AnimationUtils.loadAnimation(MapsActivity.this, R.anim.fade);
                v.startAnimation(fadeAnimation);
                float currentZoom = mMap.getCameraPosition().zoom;
                float newZoom = currentZoom - 1.0f;

                CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(newZoom);
                mMap.animateCamera(cameraUpdate);

            }
        });

        myGeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeAnimation = AnimationUtils.loadAnimation(MapsActivity.this, R.anim.fade);
                v.startAnimation(fadeAnimation);
                moveToMyLocation();
            }
        });







        ImageButton close_menu_fact =  findViewById(R.id.close_menu_fact);
        close_menu_fact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_fact.setVisibility(View.INVISIBLE);
            }
        });
        name_fact = findViewById(R.id.name_fact);
        adres_fact = findViewById(R.id.adres_fact);
        mobile_fact = findViewById(R.id.mobile_fact);
        work_time_fact = findViewById(R.id.time_work_fact);
        distance_fact = findViewById(R.id.distance_fact);

        im_glass = findViewById(R.id.im_map_glass);
        im_plastic = findViewById(R.id.im_map_plastic);
        im_metal = findViewById(R.id.im_map_metal);







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

    public boolean onMarkerClick(Marker marker) {
        // Обработка события нажатия на маркер
        Factory_obj factory_marker = null;
        if(!marker.getId().equals(marker_geo_id)){
            for (Map.Entry<String, Marker> entry : markerMap.entrySet()) {
                Marker marker_cur = entry.getValue();
                if(marker.getId().equals(marker_cur.getId())){
                    int fact_id = Integer.parseInt(entry.getKey());
                    for(Factory_obj fact_cur : Pass_act.factories){
                        if(fact_id == fact_cur.id){
                            factory_marker = fact_cur;
                            break;
                        }
                    }
                    break;
                }
            }

            menu_fact.setVisibility(View.VISIBLE);

            im_glass.setVisibility(View.GONE);
            im_plastic.setVisibility(View.GONE);
            im_metal.setVisibility(View.GONE);
            name_fact.setText(factory_marker.name);
            work_time_fact.setText(factory_marker.work_time);
            mobile_fact.setText(factory_marker.phone);
            adres_fact.setText(factory_marker.address);
            double dist = calcDist(factory_marker.x, factory_marker.y, MapsActivity.this);
            String dist_str;
            if(dist < 1300){
                dist = Math.round(dist*10)/10.0;
                dist_str = String.format(dist + " м");
            }
            else{
                dist = dist/1000;
                dist = Math.round(dist*10)/10.0;
                dist_str = String.format(dist +  " км");
            }

            distance_fact.setText(dist_str);

            if(factory_marker.isGlass){
                im_glass.setVisibility(View.VISIBLE);
            }
            if(factory_marker.isMetal){
                im_metal.setVisibility(View.VISIBLE);
            }
            if(factory_marker.isPlastic){
                im_plastic.setVisibility(View.VISIBLE);
            }

        }
        return false;
    }

    public static double calcDist(double objLat, double objLong, Context context){
        double distance = 0.0;

        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);

        if (provider != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Location location = locationManager.getLastKnownLocation(provider);
                if (location != null) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    float[] results = new float[1];
                    Location.distanceBetween(location.getLatitude(), location.getLongitude(), objLat, objLong, results);

                    distance = results[0];
                }
            }
        }


        return distance;
    }


    private static void start_location(){
        LatLng latLng = new LatLng(factory.x, factory.y);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15.0f);
        mMap.animateCamera(cameraUpdate);

        factory = null;
    }


    private void load_menu() {
        ImageButton bt_rec = findViewById(R.id.bt_map_rec);
        bt_rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeAnimation = AnimationUtils.loadAnimation(MapsActivity.this, R.anim.fade);
                v.startAnimation(fadeAnimation);
                Intent activity_new = new Intent(MapsActivity.this, RecActivity.class);
                startActivity(activity_new);
            }
        });

        ImageButton bt_map = findViewById(R.id.bt_map_map);
        bt_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeAnimation = AnimationUtils.loadAnimation(MapsActivity.this, R.anim.fade);
                v.startAnimation(fadeAnimation);
                //Intent activity_new = new Intent(MapsActivity.this, MapsActivity.class);
                //startActivity(activity_new);
            }
        });

        ImageButton bt_prof = findViewById(R.id.bt_map_prof);
        bt_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeAnimation = AnimationUtils.loadAnimation(MapsActivity.this, R.anim.fade);
                v.startAnimation(fadeAnimation);
                Intent activity_new = new Intent(MapsActivity.this, ProfileActivity.class);
                startActivity(activity_new);

            }
        });
    }

}


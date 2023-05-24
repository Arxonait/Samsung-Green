package com.example.rec_online;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

public class stat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);


        TextView name_stat =  findViewById(R.id.name_stat);
        TextView adres_stat=  findViewById(R.id.adres_stat);
        TextView mobile_stat =  findViewById(R.id.mobile_stat);
        TextView work_time_stat =  findViewById(R.id.time_work_stat);
        TextView distance_stat =  findViewById(R.id.dist_stat);
        ConstraintLayout sec_near_fact = findViewById(R.id.sec_near_fact);

        TextView name_give =  findViewById(R.id.name_stat_give);
        TextView tv_count_gl =  findViewById(R.id.count_gl);
        TextView tv_count_m =  findViewById(R.id.count_m);
        TextView tv_count_p =  findViewById(R.id.count_p);
        ImageButton p_gl = (ImageButton) findViewById(R.id.p_gl);
        ImageButton m_gl = (ImageButton) findViewById(R.id.m_gl);
        ImageButton m_m = (ImageButton) findViewById(R.id.m_m);
        ImageButton p_m = (ImageButton) findViewById(R.id.p_m);
        ImageButton p_p = (ImageButton) findViewById(R.id.p_p);
        ImageButton m_p = (ImageButton) findViewById(R.id.m_p);
        Button bt_give = (Button) findViewById(R.id.give);
        ConstraintLayout sec_give_fact = findViewById(R.id.sec_give_fact);


        Button bt_map = (Button) findViewById(R.id.bt_map);

        factory near_factory = near_fact();
        double near_dist = calkDist(near_factory.x, near_factory.y);
        if(near_dist> 150){
            sec_near_fact.setVisibility(View.VISIBLE);
            sec_give_fact.setVisibility(View.INVISIBLE);

            name_stat.setText(near_factory.name);
            adres_stat.setText(near_factory.adres);
            mobile_stat.setText(near_factory.mobile);
            work_time_stat.setText(near_factory.work_time);
            String dist_str;
            if(near_dist < 1300){
                near_dist = Math.round(near_dist*10)/10.0;
                dist_str = String.format(near_dist + " м");
            }
            else{
                near_dist = near_dist/1000;
                near_dist = Math.round(near_dist*10)/10.0;
                dist_str = String.format(near_dist +  " км");
            }
            distance_stat.setText(dist_str);
        }
        else {
            sec_give_fact.setVisibility(View.VISIBLE);
            sec_near_fact.setVisibility(View.INVISIBLE);
            tv_count_gl.setText("0");
            tv_count_m.setText("0");
            tv_count_p.setText("0");

            name_give.setText(near_factory.name);


        }

        p_gl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeAnimation = AnimationUtils.loadAnimation(stat.this, R.anim.fade);
                v.startAnimation(fadeAnimation);
                int count = Integer.parseInt(tv_count_gl.getText().toString()) + 1;
                tv_count_gl.setText(String.valueOf(count));
            }
        });

        p_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeAnimation = AnimationUtils.loadAnimation(stat.this, R.anim.fade);
                v.startAnimation(fadeAnimation);
                int count = Integer.parseInt(tv_count_m.getText().toString()) + 1;
                tv_count_m.setText(String.valueOf(count));
            }
        });

        p_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeAnimation = AnimationUtils.loadAnimation(stat.this, R.anim.fade);
                v.startAnimation(fadeAnimation);
                int count = Integer.parseInt(tv_count_p.getText().toString()) + 1;
                tv_count_p.setText(String.valueOf(count));
            }
        });

        m_gl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeAnimation = AnimationUtils.loadAnimation(stat.this, R.anim.fade);
                v.startAnimation(fadeAnimation);
                int count = Integer.parseInt(tv_count_gl.getText().toString()) - 1;
                if(count< 0){
                    count = 0;
                }
                tv_count_gl.setText(String.valueOf(count));
            }
        });

        m_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeAnimation = AnimationUtils.loadAnimation(stat.this, R.anim.fade);
                v.startAnimation(fadeAnimation);
                int count = Integer.parseInt(tv_count_m.getText().toString()) - 1;
                if(count< 0){
                    count = 0;
                }
                tv_count_m.setText(String.valueOf(count));
            }
        });

        m_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeAnimation = AnimationUtils.loadAnimation(stat.this, R.anim.fade);
                v.startAnimation(fadeAnimation);
                int count = Integer.parseInt(tv_count_p.getText().toString()) - 1;
                if(count< 0){
                    count = 0;
                }
                tv_count_p.setText(String.valueOf(count));
            }
        });

        bt_give.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bt_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Map = new Intent(stat.this, MapsActivity.class);
                startActivity(Map);
            }
        });


    }

    public factory near_fact(){
        factory best_factory = pass_act.factories.get(0);
        double best_dist = calkDist(best_factory.x, best_factory.y);
        for (factory factory : pass_act.factories) {
            double cur_dist = calkDist(factory.x, factory.y);
            if (best_dist > cur_dist){
                best_dist = cur_dist;
                best_factory = factory;
            }
        }
        return best_factory;

    }

    public double calkDist(double objLat, double objLong){
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        double distance = 0.0;

        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);

        if (provider != null) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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
}
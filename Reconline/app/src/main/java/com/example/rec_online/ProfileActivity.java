package com.example.rec_online;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private Handler handler = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        load_sec_info();

        load_menu();


    }

    private void load_menu() {
        Button bt_stat = findViewById(R.id.bt_stat);
        bt_stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity_new = new Intent(ProfileActivity.this, stat.class);
                startActivity(activity_new);
            }
        });

        Button bt_map = findViewById(R.id.bt_map);
        bt_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity_new = new Intent(ProfileActivity.this, MapsActivity.class);
                startActivity(activity_new);
            }
        });

        Button bt_prof = findViewById(R.id.bt_prof);
        bt_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity_new = new Intent(ProfileActivity.this, ProfileActivity.class);
                startActivity(activity_new);
            }
        });
    }


    private void load_sec_info() {
        TextView tv_login = findViewById(R.id.prof_login);
        TextView tv_name = findViewById(R.id.prof_name);
        TextView tv_fam = findViewById(R.id.prof_fam);

        tv_login.setText(EnterActivity.Data_enter().login);
        tv_name.setText(EnterActivity.Data_enter().name);
        tv_fam.setText(EnterActivity.Data_enter().fam);


        TextView tv_count_balls = findViewById(R.id.prof_count_ball);
        TextView tv_plastic_count = findViewById(R.id.count_p);
        TextView tv_glass_count = findViewById(R.id.count_g);
        TextView tv_metal_count = findViewById(R.id.count_m);


        new Thread(new Runnable() {
            public void run() {
                // выполнение сетевого запроса
                final String answer = Main_server.prof_oper_rec(Integer.parseInt(EnterActivity.Data_enter().id));
                // передача результата в главный поток

                handler.post(new Runnable() {
                    public void run() {
                        // обновление пользовательского интерфейса с использованием результата
                        JSONParser parser = new JSONParser();
                        try {
                            JSONObject json = (JSONObject) parser.parse(answer);
                            if((json.get("status").toString().equals("true"))){
                                tv_count_balls.setText(json.get("ball").toString());
                                tv_glass_count.setText(json.get("glass").toString());
                                tv_metal_count.setText(json.get("metal").toString());
                                tv_plastic_count.setText(json.get("plastic").toString());

                            }
                            else {
                                tv_count_balls.setText("0");
                                tv_glass_count.setText("0");
                                tv_metal_count.setText("0");
                                tv_plastic_count.setText("0");
                            }

                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        }).start();
    }



}

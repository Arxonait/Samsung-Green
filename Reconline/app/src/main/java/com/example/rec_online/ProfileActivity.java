package com.example.rec_online;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ProfileActivity extends AppCompatActivity {

    private Handler handler = new Handler(Looper.getMainLooper());
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sharedPreferences = this.getSharedPreferences("Rec_online_memory", Context.MODE_PRIVATE);

        load_sec_info();

        load_menu();


    }

    private void load_menu() {
        ImageButton bt_stat = findViewById(R.id.bt_prof_rec);
        bt_stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity_new = new Intent(ProfileActivity.this, RecActivity.class);
                startActivity(activity_new);
            }
        });

        ImageButton bt_map = findViewById(R.id.bt_prof_map);
        bt_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity_new = new Intent(ProfileActivity.this, MapsActivity.class);
                startActivity(activity_new);
            }
        });

        ImageButton bt_prof = findViewById(R.id.bt_prof_prof);
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


        Button bt_log_out = findViewById(R.id.log_out);
        bt_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.clear(); // Очистка всех значений

                editor.apply(); // Применение изменений


                Intent activity_new = new Intent(ProfileActivity.this, EnterActivity.class);
                activity_new.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Добавление флагов для очистки задачи и запуска новой активности
                startActivity(activity_new);
            }
        });

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

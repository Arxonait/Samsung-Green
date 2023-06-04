package com.example.rec_online;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements Adapter_prof.ItemClickListener {

    private RecyclerView rview_prof_mes;
    private Adapter_prof adapter;

    private List<Mes_obj> list_mess;


    private final Handler handler = new Handler(Looper.getMainLooper());
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sharedPreferences = this.getSharedPreferences("Rec_online_memory", Context.MODE_PRIVATE);
        load_sec_info();
        load_menu();
        load_sec_history_mes();


    }

    private void load_sec_history_mes() {
        TextView tv_title_mess = findViewById(R.id.tv_historyMess_title);
        rview_prof_mes = findViewById(R.id.rview_prof_mes);
        rview_prof_mes.setLayoutManager(new LinearLayoutManager(this));

        // Создаем и устанавливаем адаптер
        adapter = new Adapter_prof(this);
        rview_prof_mes.setAdapter(adapter);
        rview_prof_mes.setVisibility(View.INVISIBLE);

        // Устанавливаем слушатель нажатий на элементы адаптера
        adapter.setClickListener(this);

        Handler handler = new Handler(Looper.getMainLooper());

        new Thread(new Runnable() {
            public void run() {
                // выполнение сетевого запроса
                final String response;
                try {
                    response = Main_server.historyMess(EnterActivity.get_dataEnter().id);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                handler.post(new Runnable() {
                    public void run() {
                        // обновление пользовательского интерфейса с использованием результата
                        list_mess = new ArrayList<>();
                        try {
                           JSONObject jsonRes = new JSONObject(response);
                           JSONObject json;
                            if (jsonRes.getBoolean("status")) {
                                JSONArray jsonArray = jsonRes.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    json = jsonArray.getJSONObject(i);
                                    Mes_obj new_mess = new Mes_obj();
                                    new_mess.parseJson(json);
                                    list_mess.add(new_mess);
                                }
                                adapter.setData(list_mess);

                                tv_title_mess.setText("Ваши сообщения");
                                rview_prof_mes.setVisibility(View.VISIBLE);
                            }
                            else {
                                rview_prof_mes.setVisibility(View.GONE);
                                tv_title_mess.setText("Здесь будут отображаться Ваши сообщения.\nНо пока, что Вы ничего не получали");
                            }

                        } catch (JSONException | java.text.ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        }).start();


    }

    private void load_menu() {
        ImageButton bt_stat = findViewById(R.id.bt_prof_rec);
        bt_stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeAnimation = AnimationUtils.loadAnimation(ProfileActivity.this, R.anim.fade);
                v.startAnimation(fadeAnimation);
                Intent activity_new = new Intent(ProfileActivity.this, RecActivity.class);
                startActivity(activity_new);
            }
        });

        ImageButton bt_map = findViewById(R.id.bt_prof_map);
        bt_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeAnimation = AnimationUtils.loadAnimation(ProfileActivity.this, R.anim.fade);
                v.startAnimation(fadeAnimation);
                Intent activity_new = new Intent(ProfileActivity.this, MapsActivity.class);
                startActivity(activity_new);
            }
        });

        ImageButton bt_prof = findViewById(R.id.bt_prof_prof);
        bt_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeAnimation = AnimationUtils.loadAnimation(ProfileActivity.this, R.anim.fade);
                v.startAnimation(fadeAnimation);
                load_sec_info();
                load_sec_history_mes();
            }
        });
    }


    private void load_sec_info() {
        TextView tv_login = findViewById(R.id.prof_login);
        TextView tv_name = findViewById(R.id.prof_name);
        TextView tv_fam = findViewById(R.id.prof_fam);

        tv_login.setText(EnterActivity.get_dataEnter().login);
        tv_name.setText(EnterActivity.get_dataEnter().name);
        tv_fam.setText(EnterActivity.get_dataEnter().surname);


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
                startActivity(activity_new);
                finish();
            }
        });

        Button bt_send_mess = findViewById(R.id.bt_prof_sendMess);
        bt_send_mess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity_new = new Intent(ProfileActivity.this, SendMessActivity.class);
                startActivity(activity_new);
            }
        });


        Button bt_admin = findViewById(R.id.bt_admin);
        if (!EnterActivity.get_dataEnter().admin) {
            bt_admin.setVisibility(View.GONE);
        }
        else {
            bt_admin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent activity_new = new Intent(ProfileActivity.this, Admin_panelActivity.class);
                    startActivity(activity_new);
                }
            });
        }


        new Thread(new Runnable() {
            public void run() {
                    // выполнение сетевого запроса
                String response;
                try {
                    response = Main_server.profSummaryStat(EnterActivity.get_dataEnter().id);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                // передача результата в главный поток

                    handler.post(new Runnable() {
                        public void run() {
                            // обновление пользовательского интерфейса с использованием результата
                            try {
                                JSONObject json = new JSONObject(response);
                                if (json.getBoolean("status")) {
                                    tv_count_balls.setText(json.getString("ball"));
                                    tv_glass_count.setText(json.getString("glass"));
                                    tv_metal_count.setText(json.getString("metal"));
                                    tv_plastic_count.setText(json.getString("plastic"));

                                } else {
                                    tv_count_balls.setText("0");
                                    tv_glass_count.setText("0");
                                    tv_metal_count.setText("0");
                                    tv_plastic_count.setText("0");
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }
            }).start();
        }


        @Override
        public void onItemClick (@Nullable View view,int position){
            show_mes_item(position);
        }
        @SuppressLint("DefaultLocale")
        private void show_mes_item (int position){
            Mes_obj mess = list_mess.get(position);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(mess.title);
            String add_text = "";
            if(mess.type.equals("balls")){
                add_text = String.format("Если возникли вопросы, Вы можете воспользоваться обратной связью\n" +
                        "Номер данного сообщения: %d", mess.id);
            }
            else if (mess.id_prev_mes == -1){
                add_text = String.format("\n\nСпасибо за обртаную связь. Ваше сообщение обрабатывается.\n" +
                        "Номер данного сообщения: %d", mess.id);
            }
            builder.setMessage(mess.main_text + add_text)
                    .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (!mess.is_read) {
                                new Thread(new Runnable() {
                                    public void run() {
                                        // выполнение сетевого запроса
                                        final String response;
                                        try {
                                            response = Main_server.update_isRead(mess.id, true);
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                        handler.post(new Runnable() {
                                            public void run() {
                                                // обновление пользовательского интерфейса с использованием результата
                                                JSONObject json;
                                                try {
                                                    json = new JSONObject(response);
                                                    if ( json.getBoolean("status")) {
                                                        mess.is_read = true;
                                                        adapter.setData(list_mess);
                                                    }
                                                } catch (JSONException e) {
                                                    throw new RuntimeException(e);
                                                }

                                            }
                                        });
                                    }
                                }).start();
                            }
                        }
                    }).show();
        }
    protected void onResume() {
        super.onResume();
        load_sec_history_mes();
        load_sec_info();
    }
}


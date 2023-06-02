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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class Profile_Activity extends AppCompatActivity implements Adapter_prof.ItemClickListener {

    private RecyclerView rview_prof_mes;
    private Adapter_prof adapter;

    private List<Mes_obj> mess_view;

    private boolean is_new_user;

    private Handler handler = new Handler(Looper.getMainLooper());
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
        TextView tv_title_mess = findViewById(R.id.title_mess);
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
                String answer_from_server = null;
                try {
                    answer_from_server = Main_server.veiw_mess(EnterActivity.Data_enter().id);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                is_new_user = false;
                String finalAnswer_from_server = answer_from_server;
                handler.post(new Runnable() {
                    public void run() {
                        // обновление пользовательского интерфейса с использованием результата
                        JSONParser parser = new JSONParser();
                        JSONObject json;
                        try {
                            json = (JSONObject) parser.parse(finalAnswer_from_server);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        if (String.valueOf(json.get("status")).equals("false")) {
                            is_new_user = true;
                        }
                        JSONArray jsonArray = (JSONArray) json.get("data");

                        mess_view = new ArrayList<>();

                        for (Object element : jsonArray) {
                            JSONObject jsonObject = (JSONObject) element;

                            Mes_obj new_mess = new Mes_obj();
                            try {
                                new_mess.parseJson(jsonObject);
                            } catch (JSONException ex) {
                                throw new RuntimeException(ex);
                            } catch (java.text.ParseException ex) {
                                throw new RuntimeException(ex);
                            }
                            mess_view.add(new_mess);
                        }


                        if (is_new_user) {
                            rview_prof_mes.setVisibility(View.GONE);
                            tv_title_mess.setVisibility(View.GONE);
                            tv_title_mess.setText("Здесь будут отображаться Ваши сообщения,\nНо пока что Вы ничего не получали");
                        } else {
                            int total_mess = mess_view.size();
                            int count_mess = 0;
                            for (Mes_obj mess : mess_view) {
                                mess.num_cont = total_mess - count_mess;
                                count_mess++;
                            }
                            adapter.setData(mess_view);

                            tv_title_mess.setText("Ваши сообщения");
                            rview_prof_mes.setVisibility(View.VISIBLE);
                            //title_rec_view.setVisibility(View.VISIBLE);
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
                Intent activity_new = new Intent(Profile_Activity.this, RecActivity.class);
                startActivity(activity_new);
            }
        });

        ImageButton bt_map = findViewById(R.id.bt_prof_map);
        bt_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity_new = new Intent(Profile_Activity.this, MapsActivity.class);
                startActivity(activity_new);
            }
        });

        ImageButton bt_prof = findViewById(R.id.bt_prof_prof);
        bt_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load_sec_info();
                load_sec_history_mes();
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


                Intent activity_new = new Intent(Profile_Activity.this, EnterActivity.class);
                activity_new.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Добавление флагов для очистки задачи и запуска новой активности
                startActivity(activity_new);
            }
        });

        Button bt_send_mess = findViewById(R.id.bt_prof_sendMess);
        bt_send_mess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity_new = new Intent(Profile_Activity.this, SendMessActivity.class);
                startActivity(activity_new);
            }
        });


        Button bt_admin = findViewById(R.id.bt_admin);
        if (!EnterActivity.Data_enter().admin) {
            bt_admin.setVisibility(View.GONE);
        } else {
            bt_admin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent activity_new = new Intent(Profile_Activity.this, Admin_panelActivity.class);
                    startActivity(activity_new);
                }
            });
        }


            new Thread(new Runnable() {
                public void run() {
                    // выполнение сетевого запроса
                    final String answer;
                    try {
                        answer = Main_server.prof_oper_rec(EnterActivity.Data_enter().id);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    // передача результата в главный поток

                    handler.post(new Runnable() {
                        public void run() {
                            // обновление пользовательского интерфейса с использованием результата
                            JSONParser parser = new JSONParser();
                            try {
                                JSONObject json = (JSONObject) parser.parse(answer);
                                if ((json.get("status").toString().equals("true"))) {
                                    tv_count_balls.setText(json.get("ball").toString());
                                    tv_glass_count.setText(json.get("glass").toString());
                                    tv_metal_count.setText(json.get("metal").toString());
                                    tv_plastic_count.setText(json.get("plastic").toString());

                                } else {
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


        @Override
        public void onItemClick (@Nullable View view,int position){
            show_mes_item(position);
        }
        @SuppressLint("DefaultLocale")
        private void show_mes_item (int position){
            Mes_obj mess = mess_view.get(position);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(mess.title);
            String add_text = "";
            if(mess.type.equals("balls")){
                add_text = String.format("Если возникли вопросы, Вы можете воспользоваться обратной связью\n" +
                        "Номер данного сообщения: %d", mess.id);
            }
            else if (mess.id_prev_mes == -1){
                add_text = String.format("\nСпасибо за обртаную связь. Ваше сообщение обрабатывается.\n" +
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
                                        String answer_from_server = null;
                                        try {
                                            answer_from_server = Main_server.update_is_read(mess.id, true);
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                        String finalAnswer_from_server = answer_from_server;
                                        handler.post(new Runnable() {
                                            public void run() {
                                                // обновление пользовательского интерфейса с использованием результата
                                                JSONParser parser = new JSONParser();
                                                JSONObject json;
                                                try {
                                                    json = (JSONObject) parser.parse(finalAnswer_from_server);
                                                } catch (ParseException e) {
                                                    throw new RuntimeException(e);
                                                }
                                                if ((boolean) json.get("status")) {
                                                    mess.is_read = true;
                                                    adapter.setData(mess_view);
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


package com.example.rec_online;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class Admin_panelActivity extends AppCompatActivity implements Adapter_admin.ItemClickListener {

    private RecyclerView rview_prof_oper;
    private Adapter_admin adapter;

    private List<Oper_obj> list_oper;

    boolean empty_data = false;


    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);


        load_sec1();

    }



    private void load_sec1() {
        //TextView tv_title_mess = findViewById(R.id.title_mess);
        rview_prof_oper = findViewById(R.id.rview_prof_oper);
        rview_prof_oper.setLayoutManager(new LinearLayoutManager(this));

        // Создаем и устанавливаем адаптер
        adapter = new Adapter_admin(this);
        rview_prof_oper.setAdapter(adapter);
        rview_prof_oper.setVisibility(View.INVISIBLE);

        // Устанавливаем слушатель нажатий на элементы адаптера
        adapter.setClickListener(this);

        Handler handler = new Handler(Looper.getMainLooper());

        new Thread(new Runnable() {
            public void run() {
                // выполнение сетевого запроса
                String answer_from_server = Main_server.admin_select_oper();
                handler.post(new Runnable() {
                    public void run() {
                        // обновление пользовательского интерфейса с использованием результата
                        //JSONParser parser = new JSONParser();
                        org.json.JSONObject json;
                        try {
                            //json = (JSONObject) parser.parse(answer_from_server);
                            json = new JSONObject(answer_from_server);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            if (json.getString("status").equals("false")) {
                                empty_data = true;
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                        JSONArray jsonArray = null;
                        try {
                            jsonArray = json.getJSONArray("data");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        list_oper = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            org.json.JSONObject jsonObject = null;
                            try {
                                jsonObject = jsonArray.getJSONObject(i);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                            Oper_obj new_oper = new Oper_obj();
                            try {
                                new_oper.parseJson(jsonObject);
                            } catch (JSONException ex) {
                                throw new RuntimeException(ex);
                            } catch (java.text.ParseException ex) {
                                throw new RuntimeException(ex);
                            }

                            list_oper.add(new_oper);
                        }
                        if (empty_data) {
                            rview_prof_oper.setVisibility(View.GONE);
                            //tv_title_mess.setVisibility(View.GONE);
                            //tv_title_mess.setText("Здесь будут отображаться Ваши сообщения,\nНо пока что Вы ничего не получали");
                        } else {
                            int total_mess = list_oper.size();
                            int count_mess = 0;
//                            for (Mes_obj mess : list_oper) {
//                                mess.num_cont = total_mess - count_mess;
//                                count_mess++;
//                            }
                            adapter.setData(list_oper);

                            //tv_title_mess.setText("Ваши сообщения");
                            rview_prof_oper.setVisibility(View.VISIBLE);
                            //title_rec_view.setVisibility(View.VISIBLE);
                        }


                    }
                });
            }
        }).start();
    }

    @Override
    public void onItemClick(@Nullable View view, int position) {
        Intent activity_new = new Intent(Admin_panelActivity.this, ItemActivity.class);
        ItemActivity.setData(list_oper.get(position));
        startActivity(activity_new);
    }

    @Override
    protected void onResume() {
        super.onResume();
        load_sec1();
    }
}
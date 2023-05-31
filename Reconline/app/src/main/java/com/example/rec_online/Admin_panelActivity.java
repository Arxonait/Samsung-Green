package com.example.rec_online;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class Admin_panelActivity extends AppCompatActivity implements Adapter_admin.ItemClickListener {

    private RecyclerView rview_prof_oper;
    private Adapter_admin adapter;

    private List<PanelItem_obj> list_oper;

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
                        JSONParser parser = new JSONParser();
                        JSONObject json;
                        try {
                            json = (JSONObject) parser.parse(answer_from_server);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        if (String.valueOf(json.get("status")).equals("false")) {
                            empty_data = true;
                        }
                        JSONArray jsonArray = (JSONArray) json.get("data");

                        list_oper = new ArrayList<>();

                        for (Object element : jsonArray) {
                            JSONObject jsonObject = (JSONObject) element;

                            PanelItem_obj new_mess = new PanelItem_obj();
                            try {
                                new_mess.parseJson(jsonObject);
                            } catch (JSONException ex) {
                                throw new RuntimeException(ex);
                            } catch (java.text.ParseException ex) {
                                throw new RuntimeException(ex);
                            }
                            list_oper.add(new_mess);
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

    }
}
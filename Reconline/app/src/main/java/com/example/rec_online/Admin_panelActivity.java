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
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Admin_panelActivity extends AppCompatActivity implements Adapter_newOper.ItemClickListener, Adapter_newMess.ItemClickListener {

    private RecyclerView rview_prof_oper;
    private RecyclerView rview_mess;
    private Adapter_newOper adapter;
    private Adapter_newMess adapter_mess;

    private List<Oper_obj> list_oper;
    private List<Mes_obj> list_mess;

    boolean empty_data = false;




    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);


        newOper();
        newMess();

    }

    private void newMess() {
        TextView tv_eMess = findViewById(R.id.tv_admPan_empDataMess);
        rview_mess = findViewById(R.id.rview_admPan_mess);
        rview_mess.setLayoutManager(new LinearLayoutManager(this));

        // Создаем и устанавливаем адаптер
        adapter_mess = new Adapter_newMess( this);
        rview_mess.setAdapter(adapter_mess);


        // Устанавливаем слушатель нажатий на элементы адаптера
        adapter_mess.setClickListener(this);



        new Thread(new Runnable() {
            public void run() {
                // выполнение сетевого запроса
                String response = Main_server.admin_select_mess();
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            JSONObject json = new JSONObject(response);
                            if (json.getBoolean("status")) {
                                JSONArray jsonArray = json.getJSONArray("data");
                                list_mess = new ArrayList<>();

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Mes_obj new_mess = new Mes_obj();
                                    new_mess.parseJson(jsonObject);

                                    list_mess.add(new_mess);
                                }
                                adapter_mess.setData(list_mess);
                                rview_mess.setVisibility(View.VISIBLE);
                                tv_eMess.setVisibility(View.GONE);
                            }
                            else{
                                rview_mess.setVisibility(View.GONE);
                                tv_eMess.setVisibility(View.VISIBLE);
                                tv_eMess.setText("Новых сообщений нет");
                            }
                        } catch (JSONException | ParseException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });
            }
        }).start();
    }


    private void newOper() {
        TextView tv_eOper = findViewById(R.id.tv_admPan_empDataOper);
        rview_prof_oper = findViewById(R.id.rview_admPan_oper);
        rview_prof_oper.setLayoutManager(new LinearLayoutManager(this));

        // Создаем и устанавливаем адаптер
        adapter = new Adapter_newOper(this);
        rview_prof_oper.setAdapter(adapter);

        // Устанавливаем слушатель нажатий на элементы адаптера
        adapter.setClickListener(this);


        new Thread(new Runnable() {
            public void run() {
                // выполнение сетевого запроса
                String response = Main_server.admin_select_oper();
                handler.post(new Runnable() {
                    public void run() {
                        // обновление пользовательского интерфейса с использованием результата
                        try {
                            JSONObject json = new JSONObject(response);
                            if (json.getBoolean("status")) {
                                JSONArray jsonArray = json.getJSONArray("data");
                                list_oper = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Oper_obj new_oper = new Oper_obj();

                                    new_oper.parseJson(jsonObject);

                                    list_oper.add(new_oper);
                                }
                                adapter.setData(list_oper);
                                rview_prof_oper.setVisibility(View.VISIBLE);
                                tv_eOper.setVisibility(View.GONE);
                            }
                            else {
                                rview_prof_oper.setVisibility(View.GONE);
                                tv_eOper.setVisibility(View.VISIBLE);
                                tv_eOper.setText("Нет новых операций");
                            }

                    } catch (JSONException | ParseException ex) {
                            throw new RuntimeException(ex);
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
    public void onItemClick_mess(@Nullable View view, int position) {
        Intent activity_new = new Intent(Admin_panelActivity.this, ItemActivity.class);
        ItemActivity.setData(list_mess.get(position));
        startActivity(activity_new);
    }

    @Override
    protected void onResume() {
        super.onResume();
        newOper();
        newMess();
    }


}
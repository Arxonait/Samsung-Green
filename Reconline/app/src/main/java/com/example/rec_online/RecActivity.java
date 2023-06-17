package com.example.rec_online;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecActivity extends AppCompatActivity implements Adapter_rec.ItemClickListener {

    private RecyclerView rec_inf_history;
    private Adapter_rec adapter;
    private List<Oper_obj> list_oper;

    private final Handler handler = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec);

        getSupportActionBar().hide();

        load_sec_infBall();
        load_sec_oper();




        load_menu();


    }

    private void load_sec_oper(){
        Factory_obj near_factory = near_fact();
        double near_dist = MapsActivity.calcDist(near_factory.x, near_factory.y, RecActivity.this);
        if(near_dist > 150){
            load_sec_operFar(near_factory, near_dist);
        }
        else {
            load_sec_newOper(near_factory);
        }
    }

    private void load_sec_operFar(Factory_obj nearFactory, double nearDist){

        ConstraintLayout sec_operFar = findViewById(R.id.sec_operFar);
        sec_operFar.setVisibility(View.VISIBLE);
        ConstraintLayout sec_newOper = findViewById(R.id.sec_newOper);
        sec_newOper.setVisibility(View.GONE);


        TextView tv_nameFact =  findViewById(R.id.name_stat);
        TextView tv_address =  findViewById(R.id.tv_operFar_address);
        TextView tv_phone =  findViewById(R.id.tv_operFar_phone);
        TextView tv_workTime =  findViewById(R.id.tv_operFar_timeWork);
        TextView tv_distance =  findViewById(R.id.tv_operFar_distance);



        ImageView im_glass = findViewById(R.id.im_map_glass);
        ImageView im_metal = findViewById(R.id.im_map_metal);
        ImageView im_plastic = findViewById(R.id.im_map_plastic);

        tv_nameFact.setText(nearFactory.name);
        tv_address.setText(nearFactory.address);
        tv_phone.setText(nearFactory.phone);
        tv_workTime.setText(nearFactory.work_time);

        String dist_str;
        if(nearDist < 1300){
            nearDist = Math.round(nearDist * 10)/10.0;
            dist_str = String.format(nearDist + " м");
        }
        else{
            nearDist = nearDist /1000;
            nearDist = Math.round(nearDist *10)/10.0;
            dist_str = String.format(nearDist +  " км");
        }
        tv_distance.setText(dist_str);
        Pass_act.show_image(nearFactory, im_glass,im_metal, im_plastic);

        ImageButton bt_map_to_fact = findViewById(R.id.bt_map_to_fact);
        bt_map_to_fact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeAnimation = AnimationUtils.loadAnimation(RecActivity.this, R.anim.fade);
                v.startAnimation(fadeAnimation);
                MapsActivity.factory = nearFactory;
                Intent Map = new Intent(RecActivity.this, MapsActivity.class);
                startActivity(Map);
            }
        });
    }


    private void load_sec_newOper(Factory_obj nearFactory){
        ConstraintLayout sec_newOper = findViewById(R.id.sec_newOper);
        sec_newOper.setVisibility(View.VISIBLE);
        ConstraintLayout sec_operFar = findViewById(R.id.sec_operFar);
        sec_operFar.setVisibility(View.GONE);

        TextView nameFact =  findViewById(R.id.tv_newOper_nameFact);
        TextView tv_count_gl =  findViewById(R.id.count_gl);
        TextView tv_count_m =  findViewById(R.id.count_m);
        TextView tv_count_p =  findViewById(R.id.count_p);
        ImageButton p_gl = findViewById(R.id.p_gl);
        ImageButton m_gl = findViewById(R.id.m_gl);
        ImageButton m_m = findViewById(R.id.m_m);
        ImageButton p_m = findViewById(R.id.p_m);
        ImageButton p_p = findViewById(R.id.p_p);
        ImageButton m_p = findViewById(R.id.m_p);
        Button bt_give = findViewById(R.id.bt_newOper_give);



        ImageView plastic_i = findViewById(R.id.plastic_i);
        ImageView metal_i = findViewById(R.id.metal_i);
        ImageView glass_i = findViewById(R.id.glass_i);




        int color = Color.parseColor("#808080");
        if(!nearFactory.isGlass){
            glass_i.setVisibility(View.GONE);
            p_gl.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            m_gl.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
        if(!nearFactory.isMetal){
            metal_i.setVisibility(View.GONE);
            p_m.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            m_m.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
        if(!nearFactory.isPlastic){
            plastic_i.setVisibility(View.GONE);
            p_p.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            m_p.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }


        nameFact.setText(nearFactory.name);


        p_gl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plus_count(v, tv_count_gl, nearFactory.isGlass);
            }
        });

        p_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plus_count(v, tv_count_m, nearFactory.isMetal);
            }
        });

        p_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plus_count(v, tv_count_p, nearFactory.isPlastic);
            }
        });

        m_gl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minus_count(v,tv_count_gl, nearFactory.isGlass);
            }
        });

        m_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minus_count(v,tv_count_m, nearFactory.isMetal);
            }
        });

        m_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minus_count(v,tv_count_p, nearFactory.isPlastic);
            }
        });


        bt_give.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int glass = Integer.parseInt(tv_count_gl.getText().toString());
                int metal = Integer.parseInt(tv_count_m.getText().toString());
                int plastic = Integer.parseInt(tv_count_p.getText().toString());
                if(glass + metal + plastic > 0) {
                    new Thread(new Runnable() {
                        public void run() {
                            // выполнение сетевого запроса
                            Oper_obj newOper = new Oper_obj(EnterActivity.get_dataEnter().id,
                                    nearFactory.id, metal, plastic, glass);
                            String response = Main_server.newOper(newOper);
                            // передача результата в главный поток
                            handler.post(new Runnable() {
                                public void run() {
                                     //обновление пользовательского интерфейса с использованием результата
                                    try {
                                        org.json.JSONObject json = new JSONObject(response);
                                        if(json.getBoolean("status")){
                                            Toast.makeText(getApplicationContext(), "Отправлено на проверку",
                                                    Toast.LENGTH_SHORT).show();
                                            load_sec_infBall();
                                        }
                                        else{

                                        }

                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            });
                        }
                    }).start();
                    tv_count_gl.setText("0");
                    tv_count_p.setText("0");
                    tv_count_m.setText("0");

                }
                else {
                    Toast.makeText(getApplicationContext(), "Вы ничего не указали",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void plus_count(View view, TextView textView, boolean isMaterial){
        if(isMaterial) {
            Animation fadeAnimation = AnimationUtils.loadAnimation(RecActivity.this, R.anim.fade);
            view.startAnimation(fadeAnimation);
            int count = Integer.parseInt(textView.getText().toString()) + 1;
            textView.setText(String.valueOf(count));
        }
        else {
            Toast.makeText(getApplicationContext(), "Для данного центра сдача этого ресурса не возможна", Toast.LENGTH_SHORT).show();
        }
    }
    private void minus_count(View view, TextView textView, boolean isMaterial){
        if(isMaterial) {
            Animation fadeAnimation = AnimationUtils.loadAnimation(RecActivity.this, R.anim.fade);
            view.startAnimation(fadeAnimation);
            int count = Integer.parseInt(textView.getText().toString()) - 1;
            if (count < 0) {
                count = 0;
            }
            textView.setText(String.valueOf(count));
        }
        else {
            Toast.makeText(getApplicationContext(), "Для данного центра сдача этого ресурса не возможна", Toast.LENGTH_SHORT).show();
        }
    }

    private Factory_obj near_fact(){
        Factory_obj best_factory = Pass_act.factories.get(0);
        double best_dist = MapsActivity.calcDist(best_factory.x, best_factory.y, RecActivity.this);
        for (Factory_obj factory : Pass_act.factories) {
            double cur_dist = MapsActivity.calcDist(factory.x, factory.y, RecActivity.this);
            if (best_dist > cur_dist){
                best_dist = cur_dist;
                best_factory = factory;
            }
        }
        return best_factory;

    }

    public void onItemClick(@Nullable View view, int position) {
        show_mes_itemOf_infHistory(position);
    }
    @SuppressLint("DefaultLocale")
    private void show_mes_itemOf_infHistory(int position) {
        Oper_obj oper = list_oper.get(position);
        int status = oper.codeStatus;
        String text_status;
        String text_reason = "";

        if(status == 1){
            text_status = "На рассмотрении";
        }
        else if(status == 10) {
            text_status = "Отклоненно";
            text_reason = String.format("Причина: %s\n", oper.reason);
        }
        else {
            text_status = "Принято";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(String.format("Номер вашей сдачи: %d\n" +
                                "Перерабатывающий центр: %s\n" +
                                "Стекло - %d, Пластик - %d, Металл - %d\n" +
                                "Статус: %s\n%s" +
                                "Баллы: %d\nДата и время - %s", oper.id, oper.name_fact,
                        oper.glass, oper.plastic, oper.metal, text_status, text_reason, oper.ball, oper.time))
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }


    private void load_sec_infBall() {
        rec_inf_history = findViewById(R.id.rview_recInfo_history);
        TextView tv_title = findViewById(R.id.tv_recInfo_title);
        TextView current_ball = findViewById(R.id.tv_recInfo_currBalls);
        LinearLayout sec_titleHistory = findViewById(R.id.sec_recInfo_titleHistory);
        rec_inf_history.setLayoutManager(new LinearLayoutManager(this));

        // Создаем и устанавливаем адаптер
        adapter = new Adapter_rec(this);
        rec_inf_history.setAdapter(adapter);
        rec_inf_history.setVisibility(View.INVISIBLE);

        // Устанавливаем слушатель нажатий на элементы адаптера
        adapter.setClickListener(this);

        new Thread(new Runnable() {
            public void run() {
                // выполнение сетевого запроса
                String response;
                try {
                    response = Main_server.historyOper(EnterActivity.get_dataEnter().id);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                String finalAnswer_response = response;
                handler.post(new Runnable() {
                    public void run() {
                        JSONObject combinedJson;
                        JSONArray jsonArray;
                        list_oper  = new ArrayList<>();
                        try {
                            combinedJson = new JSONObject(finalAnswer_response);
                            if(combinedJson.getBoolean("status")){
                                jsonArray = combinedJson.getJSONArray("data");
                                JSONObject jsonObject;
                                Oper_obj newOper;
                                int balls = 0;
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try {
                                        newOper = new Oper_obj();
                                        jsonObject = jsonArray.getJSONObject(i);
                                        newOper.parseJson(jsonObject);
                                        if(newOper.codeStatus == 11){
                                            balls += newOper.ball;
                                        }
                                        list_oper.add(newOper);
                                    } catch (JSONException | java.text.ParseException e) {
                                        throw new RuntimeException(e);
                                    }

                                }
                                current_ball.setText(String.valueOf(balls));

                                adapter.setData(list_oper);

                                tv_title.setText("Ваши прошлые операции");
                                rec_inf_history.setVisibility(View.VISIBLE);
                                sec_titleHistory.setVisibility(View.VISIBLE);
                            }
                            else {
                                current_ball.setText("0");
                                rec_inf_history.setVisibility(View.GONE);
                                sec_titleHistory.setVisibility(View.GONE);
                                tv_title.setText("Здесь будут отображаться Ваши баллы.\nНо пока, что Вы ничего не сдали");
                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        }).start();



        Button bt_inf_about_balls = findViewById(R.id.bt_recInfo_info);
        bt_inf_about_balls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RecActivity.this);
                builder.setTitle("Информация о баллах");
                builder.setMessage(String.format("За каждую единицу Вы будете получать:\n" +
                                "Стекло - 10 баллов\nМеталл - 2 балла\nПластик - 5 баллов\n\n" +
                                "Начисление баллов происходит не сразу, а через некоторое время, после проверки Администратора.\n" +
                                "Вам могут отказать в начислении баллов в случае подозрительной заявки.\n" +
                                "В будующем баллы Вы сможите обменять баллы на призы."))
                        .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        });
    }


    private void load_menu() {
        ImageButton bt_rec = findViewById(R.id.bt_rec_rec);
        bt_rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeAnimation = AnimationUtils.loadAnimation(RecActivity.this, R.anim.fade);
                v.startAnimation(fadeAnimation);
                load_sec_infBall();
                load_sec_oper();
            }
        });

        ImageButton bt_map = findViewById(R.id.bt_rec_map);
        bt_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeAnimation = AnimationUtils.loadAnimation(RecActivity.this, R.anim.fade);
                v.startAnimation(fadeAnimation);
                Intent activity_new = new Intent(RecActivity.this, MapsActivity.class);
                startActivity(activity_new);
            }
        });

        ImageButton bt_prof = findViewById(R.id.bt_rec_prof);
        bt_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeAnimation = AnimationUtils.loadAnimation(RecActivity.this, R.anim.fade);
                v.startAnimation(fadeAnimation);
                Intent activity_new = new Intent(RecActivity.this, ProfileActivity.class);
                startActivity(activity_new);
            }
        });
    }

}


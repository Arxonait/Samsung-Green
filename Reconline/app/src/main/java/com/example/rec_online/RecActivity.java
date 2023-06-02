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

//import org.json.JSONObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecActivity extends AppCompatActivity implements Adapter_rec.ItemClickListener {

    private RecyclerView rec_inf_history;
    private Adapter_rec adapter;



    private List<Oper_obj> gifts_view;
    boolean is_new_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        load_sec_inf_ball();

        Factory_obj near_factory = near_fact();
        double near_dist = MapsActivity.calcDist(near_factory.x, near_factory.y, RecActivity.this);


        if(near_dist> 150){
            load_sec_oper_far(near_factory, near_dist);
        }
        else {
            load_sec_oper_pass(near_factory);
        }

        load_menu();


    }

    private void load_sec_oper_far(Factory_obj near_factory, double near_dist){

        TextView name_stat =  findViewById(R.id.name_stat);
        TextView adres_stat=  findViewById(R.id.adres_stat);
        TextView mobile_stat =  findViewById(R.id.mobile_stat);
        TextView work_time_stat =  findViewById(R.id.time_work_stat);
        TextView distance_stat =  findViewById(R.id.dist_stat);

        ConstraintLayout sec_oper_far = findViewById(R.id.sec_oper_far);
        sec_oper_far.setVisibility(View.VISIBLE);

        ImageView im_glass = findViewById(R.id.im_map_glass);
        ImageView im_metal = findViewById(R.id.im_map_metal);
        ImageView im_plastic = findViewById(R.id.im_map_plastic);

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
        Pass_act.show_image(near_factory, im_glass,im_metal, im_plastic);

        ImageButton bt_map_to_fact = findViewById(R.id.bt_map_to_fact);
        bt_map_to_fact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapsActivity.factory = near_factory;
                Intent Map = new Intent(RecActivity.this, MapsActivity.class);
                startActivity(Map);
            }
        });


    }


    private void load_sec_oper_pass(Factory_obj near_factory){
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


        ImageView plastic_i = (ImageView) findViewById(R.id.plastic_i);
        ImageView metal_i = (ImageView) findViewById(R.id.metal_i);
        ImageView glass_i = (ImageView) findViewById(R.id.glass_i);




        int color = Color.parseColor("#808080");
        if(!near_factory.glass){
            glass_i.setVisibility(View.GONE);
            p_gl.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            m_gl.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
        if(!near_factory.metal){
            metal_i.setVisibility(View.GONE);
            p_m.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            m_m.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
        if(!near_factory.plastic){
            plastic_i.setVisibility(View.GONE);
            p_p.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            m_p.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }


        sec_give_fact.setVisibility(View.VISIBLE);
        tv_count_gl.setText("0");
        tv_count_m.setText("0");
        tv_count_p.setText("0");


        name_give.setText(near_factory.name);


        p_gl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(near_factory.glass){
                    Animation fadeAnimation = AnimationUtils.loadAnimation(RecActivity.this, R.anim.fade);
                    v.startAnimation(fadeAnimation);
                    int count = Integer.parseInt(tv_count_gl.getText().toString()) + 1;
                    tv_count_gl.setText(String.valueOf(count));
                }
                else {
                    Toast.makeText(getApplicationContext(), "Для данного центра сдача этого ресурса не возможна", Toast.LENGTH_SHORT).show();
                }
            }
        });

        p_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(near_factory.metal) {
                    Animation fadeAnimation = AnimationUtils.loadAnimation(RecActivity.this, R.anim.fade);
                    v.startAnimation(fadeAnimation);
                    int count = Integer.parseInt(tv_count_m.getText().toString()) + 1;
                    tv_count_m.setText(String.valueOf(count));
                }
                else {
                    Toast.makeText(getApplicationContext(), "Для данного центра сдача этого ресурса не возможна", Toast.LENGTH_SHORT).show();
                }
            }
        });

        p_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(near_factory.plastic) {
                    Animation fadeAnimation = AnimationUtils.loadAnimation(RecActivity.this, R.anim.fade);
                    v.startAnimation(fadeAnimation);
                    int count = Integer.parseInt(tv_count_p.getText().toString()) + 1;
                    tv_count_p.setText(String.valueOf(count));
                }
                else {
                    Toast.makeText(getApplicationContext(), "Для данного центра сдача этого ресурса не возможна", Toast.LENGTH_SHORT).show();
                }
            }
        });

        m_gl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(near_factory.glass){
                    Animation fadeAnimation = AnimationUtils.loadAnimation(RecActivity.this, R.anim.fade);
                    v.startAnimation(fadeAnimation);
                    int count = Integer.parseInt(tv_count_gl.getText().toString()) - 1;
                    if(count< 0){
                        count = 0;
                    }
                    tv_count_gl.setText(String.valueOf(count));
                }
                else {
                    Toast.makeText(getApplicationContext(), "Для данного центра сдача этого ресурса не возможна", Toast.LENGTH_SHORT).show();
                }
            }
        });

        m_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(near_factory.metal) {
                    Animation fadeAnimation = AnimationUtils.loadAnimation(RecActivity.this, R.anim.fade);
                    v.startAnimation(fadeAnimation);
                    int count = Integer.parseInt(tv_count_m.getText().toString()) - 1;
                    if (count < 0) {
                        count = 0;
                    }
                    tv_count_m.setText(String.valueOf(count));
                }
                else {
                    Toast.makeText(getApplicationContext(), "Для данного центра сдача этого ресурса не возможна", Toast.LENGTH_SHORT).show();
                }
            }
        });

        m_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(near_factory.plastic) {
                    Animation fadeAnimation = AnimationUtils.loadAnimation(RecActivity.this, R.anim.fade);
                    v.startAnimation(fadeAnimation);
                    int count = Integer.parseInt(tv_count_p.getText().toString()) - 1;
                    if (count < 0) {
                        count = 0;
                    }
                    tv_count_p.setText(String.valueOf(count));
                }
                else {
                    Toast.makeText(getApplicationContext(), "Для данного центра сдача этого ресурса не возможна", Toast.LENGTH_SHORT).show();
                }
            }
        });


        bt_give.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int glass = Integer.parseInt(tv_count_gl.getText().toString());
                int metal = Integer.parseInt(tv_count_m.getText().toString());
                int plastic = Integer.parseInt(tv_count_p.getText().toString());
                if(glass + metal + plastic > 0) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    new Thread(new Runnable() {
                        public void run() {
                            // выполнение сетевого запроса
                            Oper_obj gift_new= new Oper_obj(Integer.parseInt(EnterActivity.Data_enter().id), Integer.parseInt(near_factory.id), metal, plastic, glass);
                            String res = Main_server.gift(gift_new);
                            // передача результата в главный поток
                            handler.post(new Runnable() {
                                public void run() {
                                    load_sec_inf_ball();

                                     //обновление пользовательского интерфейса с использованием результата
                                    try {

                                        JSONParser parser = new JSONParser();
                                        JSONObject answer = (JSONObject) parser.parse(res);
                                        if(answer.get("status").toString().equals("true")){
                                            Toast.makeText(getApplicationContext(), "Отправлено на проверку",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                        else{

                                        }

                                    } catch (ParseException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            });
                        }
                    }).start();

                }
                else {
                    Toast.makeText(getApplicationContext(), "Вы ничего не указали",
                            Toast.LENGTH_SHORT).show();
                }
                tv_count_gl.setText("0");
                tv_count_p.setText("0");
                tv_count_m.setText("0");

            }
        });
    }

    public Factory_obj near_fact(){
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
        // При нажатии на элемент показываем сообщение с кнопкой "Ок"
        show_mes_itemOf_Infhistory(position);
    }
    @SuppressLint("DefaultLocale")
    private void show_mes_itemOf_Infhistory(int position) {
        Oper_obj gift = gifts_view.get(position);
        int status = gift.status;
        String text_status;
        String text_reason = "";

        if(status == 1){
            text_status = "На рассмотрении";
        }
        else if(status == 10) {
            text_status = "Отклоненно";
            text_reason = String.format("Причина: %s\n", gift.reason);
        }
        else {
            text_status = "Принято";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String time = gift.time;
        builder.setMessage(String.format("Номер вашей сдачи: %d\n" +
                                "Перерабатывающий центр: %s\n" +
                                "Стекло - %d, Пластик - %d, Металл - %d\n" +
                                "Статус: %s\n%s" +
                                "Баллы: %d\nДата и время - %s", gift.id, gift.name_fact,
                        gift.glass, gift.plastic, gift.metal, text_status, text_reason, gift.ball, time))
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }


    private void load_sec_inf_ball() {
        rec_inf_history = findViewById(R.id.rec_inf_history);
        TextView title_give = findViewById(R.id.title_give);
        TextView current_ball = findViewById(R.id.current_ball);
        LinearLayout title_rec_view = findViewById(R.id.titleRecView);
        rec_inf_history.setLayoutManager(new LinearLayoutManager(this));

        // Создаем и устанавливаем адаптер
        adapter = new Adapter_rec(this);
        rec_inf_history.setAdapter(adapter);
        rec_inf_history.setVisibility(View.INVISIBLE);

        // Устанавливаем слушатель нажатий на элементы адаптера
        adapter.setClickListener(this);

        Handler handler = new Handler(Looper.getMainLooper());

        new Thread(new Runnable() {
            public void run() {
                // выполнение сетевого запроса
                String answer_from_server = null;
                try {
                    answer_from_server = Main_server.veiw_gift(Integer.parseInt(EnterActivity.Data_enter().id));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                is_new_user = false;
                String finalAnswer_from_server = answer_from_server;
                handler.post(new Runnable() {
                    public void run() {
                        // обновление пользовательского интерфейса с использованием результата
                        //JSONParser parser = new JSONParser();
                        org.json.JSONObject combinedJson = null;
                        try {
                            combinedJson = new org.json.JSONObject(finalAnswer_from_server);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        //combinedJson = (JSONObject) parser.parse(finalAnswer_from_server);

                        try {
                            if(combinedJson.getString("status").equals("false")){
                                is_new_user = true;
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = combinedJson.getJSONArray("data");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        gifts_view = new ArrayList<>();

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

                            gifts_view.add(new_oper);
                        }



                        if(is_new_user){
                                current_ball.setText("0");
                                rec_inf_history.setVisibility(View.GONE);
                                title_rec_view.setVisibility(View.GONE);
                                title_give.setText("Здесь будут отображаться Ваши баллы,\nНо пока что Вы ничего не сдали");
                            }
                        else {
                            int balls = 0;
                            int count_gift = gifts_view.size();
                            int rev_gift = 0;
                            for (Oper_obj gift: gifts_view) {
                                gift.num_cont = count_gift - rev_gift;
                                rev_gift++;
                                if(gift.status == 11){
                                    balls +=gift.ball;
                                }
                            }



                                current_ball.setText(String.valueOf(balls));

                                if (gifts_view.size() > 2 && false) {
                                    adapter.setData(gifts_view.subList(0, 10));
                                } else {
                                    adapter.setData(gifts_view);
                                }
                                title_give.setText("Ваши прошлые gift");
                                rec_inf_history.setVisibility(View.VISIBLE);
                                title_rec_view.setVisibility(View.VISIBLE);
                            }


                    }
                });
            }
        }).start();



        Button bt_inf_about_balls = (Button) findViewById(R.id.bt_inf_about_balls);
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
                //Intent activity_new = new Intent(RecActivity.this, RecActivity.class);
                //startActivity(activity_new);
                load_sec_inf_ball();
            }
        });

        ImageButton bt_map = findViewById(R.id.bt_rec_map);
        bt_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity_new = new Intent(RecActivity.this, MapsActivity.class);
                startActivity(activity_new);
            }
        });

        ImageButton bt_prof = findViewById(R.id.bt_rec_prof);
        bt_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity_new = new Intent(RecActivity.this, Profile_Activity.class);
                startActivity(activity_new);
            }
        });
    }

}


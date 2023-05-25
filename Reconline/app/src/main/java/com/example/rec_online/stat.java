package com.example.rec_online;

import static com.example.rec_online.pass_act.gifts_view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class stat extends AppCompatActivity implements MyAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private Handler handler = new Handler(Looper.getMainLooper());


    private List<Gift_obj> gifts_view;
    boolean is_new_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);





        load_sec_give();









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

        Factory_obj near_factory = near_fact();
        ImageView plastic_i = (ImageView) findViewById(R.id.plastic_i);
        ImageView metal_i = (ImageView) findViewById(R.id.metal_i);
        ImageView glass_i = (ImageView) findViewById(R.id.glass_i);

        //System.out.println(near_factory.glass);
        //System.out.println(near_factory.metal);
        //.out.println(near_factory.plastic);


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

        double near_dist = calkDist(near_factory.x, near_factory.y);
        if(near_dist> 150){
            sec_near_fact.setVisibility(View.VISIBLE);
            sec_give_fact.setVisibility(View.GONE);

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
                if(near_factory.glass){
                    Animation fadeAnimation = AnimationUtils.loadAnimation(stat.this, R.anim.fade);
                    v.startAnimation(fadeAnimation);
                    int count = Integer.parseInt(tv_count_gl.getText().toString()) + 1;
                    tv_count_gl.setText(String.valueOf(count));
                }
            }
        });

        p_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(near_factory.metal) {
                    Animation fadeAnimation = AnimationUtils.loadAnimation(stat.this, R.anim.fade);
                    v.startAnimation(fadeAnimation);
                    int count = Integer.parseInt(tv_count_m.getText().toString()) + 1;
                    tv_count_m.setText(String.valueOf(count));
                }
            }
        });

        p_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(near_factory.plastic) {
                    Animation fadeAnimation = AnimationUtils.loadAnimation(stat.this, R.anim.fade);
                    v.startAnimation(fadeAnimation);
                    int count = Integer.parseInt(tv_count_p.getText().toString()) + 1;
                    tv_count_p.setText(String.valueOf(count));
                }
            }
        });

        m_gl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(near_factory.glass){
                    Animation fadeAnimation = AnimationUtils.loadAnimation(stat.this, R.anim.fade);
                    v.startAnimation(fadeAnimation);
                    int count = Integer.parseInt(tv_count_gl.getText().toString()) - 1;
                    if(count< 0){
                        count = 0;
                    }
                    tv_count_gl.setText(String.valueOf(count));
                }
            }
        });

        m_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(near_factory.metal) {
                    Animation fadeAnimation = AnimationUtils.loadAnimation(stat.this, R.anim.fade);
                    v.startAnimation(fadeAnimation);
                    int count = Integer.parseInt(tv_count_m.getText().toString()) - 1;
                    if (count < 0) {
                        count = 0;
                    }
                    tv_count_m.setText(String.valueOf(count));
                }
            }
        });

        m_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(near_factory.plastic) {
                    Animation fadeAnimation = AnimationUtils.loadAnimation(stat.this, R.anim.fade);
                    v.startAnimation(fadeAnimation);
                    int count = Integer.parseInt(tv_count_p.getText().toString()) - 1;
                    if (count < 0) {
                        count = 0;
                    }
                    tv_count_p.setText(String.valueOf(count));
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
                                    Gift_obj gift_new= new Gift_obj(EnterActivity.Data_enter().id, near_factory.id, metal, plastic, glass);
                                    String res = Main_server.gift(gift_new);

                                    // передача результата в главный поток
                                    handler.post(new Runnable() {
                                        public void run() {
                                            load_sec_give();
                                            // обновление пользовательского интерфейса с использованием результата
                                            //try {
                                                //JSONObject answer = new JSONObject(res);
//                                                if(answer.getBoolean("status")){
//                                                    Toast.makeText(getApplicationContext(), "Вы зарегестрированы",
//                                                            Toast.LENGTH_SHORT).show();
//                                                }
//                                                else{
//                                                    result_element.setText("Пользователь с таким логином уже существует");
//                                                    result_element.setTextColor(Color.RED);
//                                                }

                                            //} catch (JSONException e) {
                                                //throw new RuntimeException(e);
                                            //}
                                        }
                                    });
                                }
                        }).start();
                }

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

    public Factory_obj near_fact(){
        Factory_obj best_factory = pass_act.factories.get(0);
        double best_dist = calkDist(best_factory.x, best_factory.y);
        for (Factory_obj factory : pass_act.factories) {
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





    public void onItemClick(@Nullable View view, int position) {
        // При нажатии на элемент показываем сообщение с кнопкой "Ок"
        showCustomDialog(position);
    }
    private void showCustomDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        Gift_obj gift = gifts_view.get(position);


        builder.setMessage(String.format("Номер вашей сдачи: none\nНазвание перерабатывающего центра: %s\nСтекло - %d, Пластик - %d, Металл - %d\n" +
                                "Статус: %d\nБаллы: %d\nДата и время - none", gift.id_fact,
                        gift.glass, gift.plastic, gift.metal, gift.status, gift.ball))
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }






    private void load_sec_give() {
        recyclerView = findViewById(R.id.recyclerView);
        TextView title_give = findViewById(R.id.title_give);
        TextView current_ball = findViewById(R.id.current_ball);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Создаем и устанавливаем адаптер
        adapter = new MyAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.INVISIBLE);

        // Устанавливаем слушатель нажатий на элементы адаптера
        adapter.setClickListener(this);



        new Thread(new Runnable() {
            public void run() {
                // Выполнение сетевого запроса
                String answer_from_server = Main_server.veiw_gift(Integer.parseInt(EnterActivity.Data_enter().id));

                // Обновление пользовательского интерфейса с использованием результата
                JSONParser parser = new JSONParser();
                is_new_user = false;
                try {
                    JSONObject combinedJson = (JSONObject) parser.parse(answer_from_server);
                    if(String.valueOf(combinedJson.get("status")).equals("false")){
                        is_new_user = true;
                    }
                    JSONArray jsonArray = (JSONArray) combinedJson.get("data");

                    gifts_view = new ArrayList<>();

                    for (Object element : jsonArray) {
                        JSONObject jsonObject = (JSONObject) element;

                        Gift_obj new_gift = new Gift_obj();
                        new_gift.parseJson(jsonObject);

                        gifts_view.add(new_gift);
                    }


                    runOnUiThread(new Runnable() {
                        public void run() {
                            if(is_new_user){
                                current_ball.setText("0");
                                recyclerView.setVisibility(View.GONE);
                                title_give.setText("Здесь будут отображаться Ваши баллы,\nНо пока что Вы ничего не сдали");
                            }
                            else {
                                int balls = 0;
                                for (Gift_obj gift: gifts_view) {
                                    if(gift.status == 11){
                                        balls +=gift.ball;
                                    }
                                }
                                current_ball.setText(String.valueOf(balls));

                                if (gifts_view.size() > 2) {
                                    adapter.setData(gifts_view.subList(0, 3));
                                } else {
                                    adapter.setData(gifts_view);
                                }
                                title_give.setText("Ваши прошлые gift");
                                recyclerView.setVisibility(View.VISIBLE);
                            }

                        }
                    });

                } catch (JSONException e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            // Обработка ошибки
                        }
                    });
                } catch (ParseException e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            // Обработка ошибки
                        }
                    });
                }
            }
        }).start();
    }













}


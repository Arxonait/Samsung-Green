package com.example.rec_online;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class EnterActivity extends AppCompatActivity {

    private static User_obj user_enter;
    private final Handler handler = new Handler(Looper.getMainLooper());

    private SharedPreferences sharedPreferences;

    private boolean is_new_user;

    private EditText et_login;
    private EditText et_password;

    private Button bt_enter;
    private Button bt_reg;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);


        et_login = findViewById(R.id.et_enter_login);
        et_password = findViewById(R.id.et_enter_password);
        bt_enter = findViewById(R.id.enter);
        bt_reg = findViewById(R.id.register);


        process_load(false);

        sharedPreferences = this.getSharedPreferences("Rec_online_memory", Context.MODE_PRIVATE);

        if (sharedPreferences.contains("login") && sharedPreferences.contains("password")) {
            is_new_user = false;
            enter();
        } else {
            is_new_user = true;
            process_load(true);
        }



        bt_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enter();

            }
        });

        bt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Register = new Intent(EnterActivity.this, RegisterActivity.class);
                startActivity(Register);
            }
        });
    }

    public boolean isGPSEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static User_obj get_dataEnter(){
        return user_enter;
    }
    private void process_load(boolean bl){
        bt_enter.setEnabled(bl);
        bt_reg.setEnabled(bl);
    }

    private void enter(){
        process_load(false);

        String login;
        String password;
        if(is_new_user){
            login = String.valueOf(et_login.getText());
            password = String.valueOf(et_password.getText());
        }
        else{
            login = sharedPreferences.getString("login", "");
            password = sharedPreferences.getString("password", "");
        }


        if(login.length() > 0 && password.length() > 0 ){
            boolean gpsEnabled = isGPSEnabled(this);
            if (gpsEnabled){
                new Thread(new Runnable() {
                    public void run() {
                        // выполнение сетевого запроса
                        String response;


                        try {
                            response = Main_server.Enter(login, password);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }



                        if(response.equals("server")){
                            handler.post(new Runnable() {
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Отсутствует соединение с сервером",
                                            Toast.LENGTH_SHORT).show();
                                    is_new_user = true;
                                    process_load(true);
                                }
                            });
                            return;
                        }



                        String finalResponse = response;
                        handler.post(new Runnable() {
                            public void run() {
                                // обновление пользовательского интерфейса с использованием результата
                                try {
                                    JSONObject answer = new JSONObject(finalResponse);
                                    if(answer.getBoolean("status")){
                                        user_enter = new User_obj();
                                        user_enter.parseJson(answer);

                                        if(is_new_user){
                                            // Получение объекта редактора SharedPreferences для внесения изменений
                                            SharedPreferences.Editor editor = sharedPreferences.edit();

                                            // Сохранение логина и пароля
                                            editor.putString("login", login);
                                            editor.putString("password", password);

                                            // Применение изменений
                                            editor.apply();
                                        }

                                        Pass_act.main();

                                        Intent MapsActivity = new Intent(EnterActivity.this, MapsActivity.class);
                                        startActivity(MapsActivity);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Логин или пароль не верен",
                                                Toast.LENGTH_SHORT).show();
                                        process_load(true);
                                    }

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });
                    }
                }).start();
            }
            else {
                Toast.makeText(getApplicationContext(), "Включите GPS",
                        Toast.LENGTH_SHORT).show();
                process_load(true);
            }

        }
        else{
            Toast.makeText(getApplicationContext(), "Заполните все поля",
                    Toast.LENGTH_SHORT).show();
            process_load(true);
        }

    }

}
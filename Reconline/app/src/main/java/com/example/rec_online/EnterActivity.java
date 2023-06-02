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
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class EnterActivity extends AppCompatActivity {

    private static User_obj user_enter;
    private Handler handler = new Handler(Looper.getMainLooper());

    // Получение объекта SharedPreferences
    private SharedPreferences sharedPreferences;

    private boolean is_new_user;

    private EditText ed_login;
    private EditText ed_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_activity);


        ed_login = findViewById(R.id.login);
        ed_password = findViewById(R.id.password);
        Button bt_enter = findViewById(R.id.enter);
        Button bt_reg = findViewById(R.id.register);


        bt_enter.setEnabled(false);
        bt_reg.setEnabled(false);

        sharedPreferences = this.getSharedPreferences("Rec_online_memory", Context.MODE_PRIVATE);

        if (sharedPreferences.contains("login") && sharedPreferences.contains("password")) {
            is_new_user = false;
            enter();
        } else {
            is_new_user = true;
            bt_enter.setEnabled(true);
            bt_reg.setEnabled(true);
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
                Intent Register = new Intent(EnterActivity.this, Register.class);
                startActivity(Register);
            }
        });
    }

    public static User_obj Data_enter(){
        return user_enter;
    }

    private void enter(){
        String login;
        String password;
        if(is_new_user){
            login = String.valueOf(ed_login.getText());
            password = String.valueOf(ed_password.getText());
        }
        else{
            login = sharedPreferences.getString("login", "");
            password = sharedPreferences.getString("password", "");
        }

        if(login.length() > 0 && password.length() > 0 ){
            new Thread(new Runnable() {
                public void run() {
                    // выполнение сетевого запроса
                    String res = null;
                    try {
                        res = Main_server.Enter(login, password);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    // передача результата в главный поток
                    String finalRes = res;
                    handler.post(new Runnable() {
                        public void run() {
                            // обновление пользовательского интерфейса с использованием результата
                            try {
                                JSONObject answer = new JSONObject(finalRes);
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
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Логин или пароль не верен",
                                            Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }
            }).start();
        }
        else{
            Toast.makeText(getApplicationContext(), "Заполните все поля",
                    Toast.LENGTH_SHORT).show();
        }
    }

}
package com.example.rec_online;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class EnterActivity extends AppCompatActivity {

    private static User_obj user_enter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_activity);
        Handler handler = new Handler(Looper.getMainLooper());

        EditText login_element = (EditText) findViewById(R.id.login);
        EditText password_element = (EditText) findViewById(R.id.password);
        Button enter_button = (Button) findViewById(R.id.enter);
        Button reg_button = (Button) findViewById(R.id.register);
        TextView result_element = (TextView) findViewById(R.id.result_enter);

        enter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = String.valueOf(login_element.getText());
                String password = String.valueOf(password_element.getText());
                if(login.length() > 0 && password.length() > 0 ){
                    new Thread(new Runnable() {
                        public void run() {
                            // выполнение сетевого запроса
                            String res = Main_server.Enter(login, password);
                            // передача результата в главный поток
                            handler.post(new Runnable() {
                                public void run() {
                                    // обновление пользовательского интерфейса с использованием результата
                                    try {
                                        JSONObject answer = new JSONObject(res);
                                        if(answer.getBoolean("status")){
                                            user_enter = new User_obj();
                                            user_enter.parseJson(answer);


                                            pass_act.main();

                                            Intent MapsActivity = new Intent(EnterActivity.this, MapsActivity.class);
                                            startActivity(MapsActivity);
                                        }
                                        else{
                                            result_element.setText("Логин или пароль не верен");
                                            result_element.setTextColor(Color.RED);
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
                    result_element.setText("Заполните все поля ");
                    result_element.setTextColor(Color.RED);

                }

            }
        });

        reg_button.setOnClickListener(new View.OnClickListener() {
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

}
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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {
    static int data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Handler handler = new Handler(Looper.getMainLooper());

        EditText login_element = (EditText) findViewById(R.id.login_reg);
        EditText password1_element = (EditText) findViewById(R.id.pass1_reg);
        EditText password2_element = (EditText) findViewById(R.id.pass2_reg);


        EditText name_element = (EditText) findViewById(R.id.userName);
        EditText fam_element = (EditText) findViewById(R.id.userFam);
        EditText number_element = (EditText) findViewById(R.id.Numberr);

        Button button_reg = (Button) findViewById(R.id.button_reg);
        Button button_reg_back = (Button) findViewById(R.id.reg_back);
        TextView result_element = (TextView) findViewById(R.id.Reg_result);



        button_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = String.valueOf(login_element.getText());
                String password1 = String.valueOf(password1_element.getText());
                String password2 = String.valueOf(password1_element.getText());
                String name = String.valueOf(name_element.getText());
                String fam = String.valueOf(fam_element.getText());
                String numberr = String.valueOf(number_element.getText());

                if(login.length() * password1.length() * password2.length() *  name.length() * fam.length() * numberr.length() > 0){
                    if(password1.equals(password2)){
                        if (password1.length() > 0){
                            new Thread(new Runnable() {
                                public void run() {
                                    // выполнение сетевого запроса
                                    User user_new = new User(name, fam, numberr, login, password1, 0, 0, 0,
                                    0 , 0, false);
                                    String res = Main_server.reg(user_new);
                                    // передача результата в главный поток
                                    handler.post(new Runnable() {
                                        public void run() {
                                            // обновление пользовательского интерфейса с использованием результата
                                            try {
                                                JSONObject answer = new JSONObject(res);
                                                if(answer.getBoolean("status")){
                                                    Toast.makeText(getApplicationContext(), "Вы зарегестрированы",
                                                            Toast.LENGTH_SHORT).show();
                                                    Intent enter_act = new Intent(Register.this, EnterActivity.class);
                                                    startActivity(enter_act);


                                                }
                                                else{
                                                    result_element.setText("Пользователь с таким логином уже существует");
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
                            result_element.setText("Пароль должен быть больше n символов");
                        }
                    }
                    else {
                        result_element.setText("Пароли не совпадают");
                    }
                }
                else{
                    result_element.setText("Заполните все поля ");
                }

            }
        });

        button_reg_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent enter_act = new Intent(Register.this, EnterActivity.class);
                startActivity(enter_act);
            }
        });

    }

    public static void getData( Integer data0){
        data = data0;
    }
}
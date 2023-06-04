package com.example.rec_online;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Handler handler = new Handler(Looper.getMainLooper());

        EditText login_element = findViewById(R.id.et_reg_login);
        EditText password1_element = findViewById(R.id.et_reg_pass1);
        EditText password2_element = findViewById(R.id.et_reg_pass2);


        EditText name_element = findViewById(R.id.et_reg_name);
        EditText surname_element = findViewById(R.id.et_reg_surname);
        EditText phone_element = findViewById(R.id.et_reg_phone);

        Button button_reg = findViewById(R.id.button_reg);




        button_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = String.valueOf(login_element.getText());
                String password1 = String.valueOf(password1_element.getText());
                String password2 = String.valueOf(password2_element.getText());
                String name = String.valueOf(name_element.getText());
                String surname = String.valueOf(surname_element.getText());
                String phone = String.valueOf(phone_element.getText());

                if(login.length() * password1.length() * password2.length() *  name.length() *
                        surname.length() * phone.length() > 0){
                    if(password1.equals(password2)){
                        if (password1.length() > 0){
                            new Thread(new Runnable() {
                                public void run() {
                                    // выполнение сетевого запроса
                                    User_obj newUser = new User_obj(name, surname, phone, login, password1, false);
                                    String response = Main_server.reg(newUser);
                                    // передача результата в главный поток
                                    handler.post(new Runnable() {
                                        public void run() {
                                            // обновление пользовательского интерфейса с использованием результата
                                            try {
                                                JSONObject answer = new JSONObject(response);
                                                if(answer.getBoolean("status")){
                                                    Toast.makeText(getApplicationContext(), "Вы зарегестрированы",
                                                            Toast.LENGTH_SHORT).show();
                                                    Intent enter_act = new Intent(RegisterActivity.this, EnterActivity.class);
                                                    startActivity(enter_act);
                                                    finish();
                                                }
                                                else{
                                                    Toast.makeText(getApplicationContext(), "Пользователь с таким логином уже существует",
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
                            Toast.makeText(getApplicationContext(), "Пароль должен быть больше n символов",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Пароли не совпадают",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Заполните все поля",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}
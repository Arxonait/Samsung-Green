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

public class EnterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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




                new Thread(new Runnable() {
                    public void run() {
                        // выполнение сетевого запроса
                        String res = Enter_db.run(login, password);
                        // передача результата в главный поток
                        handler.post(new Runnable() {
                            public void run() {
                                // обновление пользовательского интерфейса с использованием результата
                                result_element.setText(res);
                            }
                        });
                    }
                }).start();

                if(login.length() > 0 && password.length() > 0 ){
                    if (login.equals("admin") && password.equals("admin")){
                        result_element.setText("Успешно");
                        result_element.setTextColor(Color.GREEN);
                        Intent MapsActivity = new Intent(EnterActivity.this, MapsActivity.class);
                        startActivity(MapsActivity);
                    }
                    else{
                        result_element.setText("Логин или пароль не верен");
                        result_element.setTextColor(Color.RED);
                    }
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
                Register.getData(5);
                Intent Register = new Intent(EnterActivity.this, Register.class);
                startActivity(Register);
            }
        });
    }

}
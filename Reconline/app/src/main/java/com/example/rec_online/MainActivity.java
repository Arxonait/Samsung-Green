package com.example.rec_online;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                if(login.length() > 0 && password.length() > 0){
                    if (login.equals("admin") && password.equals("00")){
                        result_element.setText("Успешно");
                        result_element.setTextColor(Color.GREEN);
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
                Intent Register = new Intent(MainActivity.this, Register.class);
                startActivity(Register);
            }
        });
    }

}
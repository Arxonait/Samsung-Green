package com.example.rec_online;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class SendMessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mess);
        Handler handler = new Handler(Looper.getMainLooper());

        EditText ed_title = findViewById(R.id.ed_sendMess_title);
        EditText ed_mainText = findViewById(R.id.ed_sendMess_mainText);

        Button bt_send = findViewById(R.id.bt_sendMess_send);

        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = String.valueOf(ed_title.getText());
                String main_text = String.valueOf(ed_mainText.getText());
                if (title.length() * main_text.length() > 0) {
                    new Thread(new Runnable() {
                        public void run() {
                            Mes_obj mess_new = new Mes_obj(EnterActivity.get_dataEnter().id, title, main_text);
                            String answer_fromServer = Main_server.send_mess(mess_new);
                            // передача результата в главный поток
                            handler.post(new Runnable() {
                                public void run() {
                                    // обновление пользовательского интерфейса с использованием результата
                                    try {
                                        JSONObject answer = new JSONObject(answer_fromServer);
                                        if (answer.getString("status").equals("true")) {
                                            Toast.makeText(getApplicationContext(), "Сообщение отправлено",
                                                    Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {

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
        });
    }
}
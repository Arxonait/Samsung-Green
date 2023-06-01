package com.example.rec_online;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.SimpleDateFormat;

public class ItemActivity extends AppCompatActivity {

    private static Oper_obj oper_s = null;
    private static Mes_obj mess_s = null;
    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        if(oper_s != null){
            load_sec_desOper();
        }
        if(mess_s != null){

        }

    }

    private void load_sec_desOper() {
        TextView tv_fio = findViewById(R.id.tv_desOper_fio);
        TextView tv_login = findViewById(R.id.tv_desOper_login);
        TextView tv_name_fact = findViewById(R.id.tv_desOper_nameFact);
        TextView tv_glass = findViewById(R.id.tv_desOper_glass);
        TextView tv_plastic = findViewById(R.id.tv_desOper_plastic);
        TextView tv_metal = findViewById(R.id.tv_desOper_metal);
        TextView tv_totalBalls = findViewById(R.id.tv_desOper_totalBalls);
        TextView tv_time = findViewById(R.id.tv_desOper_time);
        TextView tv_idOper = findViewById(R.id.tv_desOper_idOper);

        tv_fio.setText(oper_s.user_name);
        tv_login.setText(oper_s.login);
        tv_name_fact.setText(oper_s.name_fact);

        System.out.println(oper_s.glass);
        tv_glass.setText(String.valueOf(oper_s.glass));
        tv_plastic.setText(String.valueOf(oper_s.plastic));
        tv_metal.setText(String.valueOf(oper_s.metal));
        tv_totalBalls.setText(String.valueOf(oper_s.ball));

        tv_time.setText(oper_s.time);

        tv_idOper.setText(String.valueOf(oper_s.id));

        Button bt_ok = findViewById(R.id.bt_desOper_ok);

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int status = 11;
               createTread(status);
            }
        });

        Button bt_cansel = findViewById(R.id.bt_desOper_cancel);

        bt_cansel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] Reasons = {"Вариант 1", "Вариант 2", "Вариант 3"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ItemActivity.this);
                builder.setTitle("Причина отмены");
                final RadioGroup radioGroup = new RadioGroup(ItemActivity.this);

                // Создание RadioButton для каждого варианта
                for (int i = 0; i < Reasons.length; i++) {
                    RadioButton radioButton = new RadioButton(ItemActivity.this);
                    radioButton.setText(Reasons[i]);
                    radioButton.setId(i);
                    radioGroup.addView(radioButton);
                }
                // Установка RadioGroup в AlertDialog
                builder.setView(radioGroup);

                // Установка кнопок "ОК" и "Отмена"
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                        RadioButton selectedRadioButton = radioGroup.findViewById(selectedRadioButtonId);
                        String selectedRes = selectedRadioButton.getText().toString();
                        // Выполнение действия с выбранной опцией
                        int status = 10;
                        createTread(status, selectedRes);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    private void createTread(int status){
        String reason = "";
        createTread(status, reason);
    }
    private void createTread(int status, String reason)  {

        new Thread(new Runnable() {
            public void run() {
                // выполнение сетевого запроса
                oper_s.status = status;
                String res = null;
                try {
                    res = Main_server.desOper(oper_s, reason);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                // передача результата в главный поток
                String finalRes = res;
                handler.post(new Runnable() {
                    public void run() {
                        //обновление пользовательского интерфейса с использованием результата
                        try {
                            JSONParser parser = new JSONParser();
                            JSONObject answer = (JSONObject) parser.parse(finalRes);
                            if(answer.get("status").toString().equals("true")){
                                Toast.makeText(getApplicationContext(), "Успешно",
                                        Toast.LENGTH_SHORT).show();

                                finish();

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


    public static void setData(Oper_obj oper){
        oper_s = oper;
        mess_s = null;
    }

    public void setData(Mes_obj mess){
        oper_s = null;
        mess_s = mess;
    }
}
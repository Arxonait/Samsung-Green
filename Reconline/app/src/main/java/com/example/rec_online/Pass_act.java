package com.example.rec_online;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class Pass_act {
    public static List<Factory_obj> factories = new ArrayList<>();


    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void main() {
        factory();
    }



    public static void factory(){

        new Thread(new Runnable() {
            public void run() {
                // выполнение сетевого запроса
                String answer = Main_server.maps();
                // передача результата в главный поток
                handler.post(new Runnable() {
                    public void run() {
                        // обновление пользовательского интерфейса с использованием результата
                        JSONParser parser = new JSONParser();
                        try {
                            JSONObject combinedJson = (JSONObject) parser.parse(answer);
                            JSONArray jsonArray = (JSONArray) combinedJson.get("data");

                            for (Object element : jsonArray) {
                                JSONObject jsonObject = (JSONObject) element;

                                Factory_obj new_factory = new Factory_obj();
                                new_factory.parseJson(jsonObject);

                                factories.add(new_factory);
                            }

                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        }).start();
    }


//    public static void prof_oper_rec(final PassActCallback callback) {
//        new Thread(new Runnable() {
//            public void run() {
//                // выполнение сетевого запроса
//                final String answer = Main_server.prof_oper_rec(Integer.parseInt(EnterActivity.Data_enter().id));
//                // передача результата в главный поток
//                handler.post(new Runnable() {
//                    public void run() {
//                        // обновление пользовательского интерфейса с использованием результата
//                        JSONParser parser = new JSONParser();
//                        try {
//                            json = (JSONObject) parser.parse(answer);
//                            // вызываем метод обратного вызова и передаем результат
//                            callback.onPassActComplete(json);
//                        } catch (ParseException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                });
//            }
//        }).start();
//    }


//    public interface PassActCallback {
//        void onPassActComplete(JSONObject result);
//    }




    public static void show_image(Factory_obj near_factory, ImageView glass_i, ImageView metal_i, ImageView plastic_i){
        if(!near_factory.glass){
            glass_i.setVisibility(View.GONE);
        }
        if(!near_factory.metal){
            metal_i.setVisibility(View.GONE);
        }
        if(!near_factory.plastic){
            plastic_i.setVisibility(View.GONE);
        }
    }


}

package com.example.rec_online;

import android.os.Handler;
import android.os.Looper;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class pass_act {
    public static List<factory> factories = new ArrayList<>();


    public static void main() {
        factory();
    }



    public static void factory(){
        Handler handler = new Handler(Looper.getMainLooper());
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

                                factory new_factory = new factory();
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


}

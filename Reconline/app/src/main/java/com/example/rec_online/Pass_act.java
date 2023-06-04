package com.example.rec_online;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Pass_act {
    public static List<Factory_obj> factories = new ArrayList<>();


    private static final Handler handler = new Handler(Looper.getMainLooper());

    public static void main() {
        factory();
    }



    public static void factory(){

        new Thread(new Runnable() {
            public void run() {
                // выполнение сетевого запроса
                String response = Main_server.factories();
                // передача результата в главный поток
                handler.post(new Runnable() {
                    public void run() {
                        // обновление пользовательского интерфейса с использованием результата
                        try {
                            JSONObject json = new JSONObject(response);
                            JSONArray jsonArray = json.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject;
                                jsonObject = jsonArray.getJSONObject(i);
                                Factory_obj newFact = new Factory_obj();
                                newFact.parseJson(jsonObject);
                                factories.add(newFact);
                            }
                        }
                        catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    });
            }
        }).start();
    }






    public static void show_image(Factory_obj near_factory, ImageView glass_i, ImageView metal_i, ImageView plastic_i){
        if(!near_factory.isGlass){
            glass_i.setVisibility(View.GONE);
        }
        if(!near_factory.isMetal){
            metal_i.setVisibility(View.GONE);
        }
        if(!near_factory.isPlastic){
            plastic_i.setVisibility(View.GONE);
        }
    }


}

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
    public static List<Factory_obj> factories = new ArrayList<>();
    public static List<Gift_obj> gifts_view = new ArrayList<>();
    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void main() {
        factory();
        //gift_view();
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



    public static void gift_view(){
        new Thread(new Runnable() {
            public void run() {
                // выполнение сетевого запроса

                String answer_from_server = Main_server.veiw_gift(Integer.parseInt(EnterActivity.Data_enter().id));
                // передача результата в главный поток
                handler.post(new Runnable() {
                    public void run() {
                        // обновление пользовательского интерфейса с использованием результата
                        JSONParser parser = new JSONParser();
                        try {
                            JSONObject combinedJson = (JSONObject) parser.parse(answer_from_server);
                            JSONArray jsonArray = (JSONArray) combinedJson.get("data");

                            for (Object element : jsonArray) {
                                JSONObject jsonObject = (JSONObject) element;


                                Gift_obj new_gift = new Gift_obj();
                                new_gift.parseJson(jsonObject);

                                gifts_view.add(new_gift);

                            }



                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        }).start();
    }


}

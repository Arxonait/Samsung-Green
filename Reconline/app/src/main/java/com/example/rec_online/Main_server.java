package com.example.rec_online;



import com.google.gson.Gson;

import org.json.simple.JSONObject;

import java.io.IOException;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Main_server {
    public static String Enter(String login, String password) {
        String result;


        String url = "http://10.0.2.2:8080/enter";
        String json = "{\"login\":\"" + login + "\",\"password\":\"" + password + "\"}";
        result = to_server(url, json);

        return result;

    }
    public static String reg(User_obj user_new){
        String result;

        Gson gson = new Gson();

        // Преобразование объекта в JSON-строку
        String jsonString = gson.toJson(user_new);

        String url = "http://10.0.2.2:8080/reg";


        result = to_server(url, jsonString);

        return result;
    }

    public static String maps(){
        String result;
        String url = "http://10.0.2.2:8080/maps";
        String json = "{\"login\":\"" + 111 + "\",\"password\":\"" + 222 + "\"}";

        result = to_server(url, json);

        return result;
    }

    private static String to_server(String url, String json){
        String result;
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String responseBody = response.body().string();
            result = responseBody;


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static String gift(Gift_obj gift_new) {
        String result;

        Gson gson = new Gson();

        // Преобразование объекта в JSON-строку
        String jsonString = gson.toJson(gift_new);

        String url = "http://10.0.2.2:8080/stat/gift_new";


        result = to_server(url, jsonString);

        return result;
    }

    public static String veiw_gift(int id_user) {
        String result;

        JSONObject json = new JSONObject();
        json.put("id_user", id_user);


        String url = "http://10.0.2.2:8080/stat/view_gift";


        result = to_server(url, json.toJSONString());

        return result;
    }

    public static String prof_oper_rec(int id_user) {
        String result;

        JSONObject json = new JSONObject();
        json.put("id_user", id_user);


        String url = "http://10.0.2.2:8080/prof/prof_oper_rec";


        result = to_server(url, json.toJSONString());

        return result;
    }
}
package com.example.rec_online;



import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;


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
    public static String reg(User user_new){
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
}
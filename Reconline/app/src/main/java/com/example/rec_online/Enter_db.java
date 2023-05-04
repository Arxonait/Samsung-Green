package com.example.rec_online;



import java.io.IOException;



import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Enter_db {
    public static String run(String login, String password) {
        String result;
        OkHttpClient client = new OkHttpClient();
        String url = "http://10.0.2.2:8080";
        String json = "{\"login\":\"" + login + "\",\"password\":\"" + password + "\"}";
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
package com.example.rec_online;


import org.json.JSONObject;
import com.google.gson.Gson;

import org.json.JSONException;


import java.io.IOException;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Main_server {
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
            result = "server";
        }
        return result;
    }

    public static String Enter(String login, String password) throws JSONException {
        String result;
        JSONObject json = new JSONObject();
        json.put("login", login);
        json.put("password", password);
        String url = "http://10.0.2.2:8080/enter";
        result = json.toString();
        result = to_server(url, result);
        return result;

    }
    public static String reg(User_obj newUser){
        String result;

        Gson gson = new Gson();

        // Преобразование объекта в JSON-строку
        String jsonString = gson.toJson(newUser);

        String url = "http://10.0.2.2:8080/reg";


        result = to_server(url, jsonString);

        return result;
    }

    public static String maps(){
        String result;
        String url = "http://10.0.2.2:8080/maps";
        JSONObject json = new JSONObject();
        //String json = "{\"login\":\"" + 111 + "\",\"password\":\"" + 222 + "\"}";
        //String json = "";
        result = json.toString();

        result = to_server(url, result);

        return result;
    }



    public static String newOper(Oper_obj newOper) {
        String result;

        Gson gson = new Gson();
        // Преобразование объекта в JSON-строку
        String jsonString = gson.toJson(newOper);
        String url = "http://10.0.2.2:8080/rec/newOper";

        result = to_server(url, jsonString);
        return result;
    }

    public static String historyOper(int id_user) throws JSONException {
        String result;

        JSONObject json = new JSONObject();
        json.put("id_user", id_user);


        String url = "http://10.0.2.2:8080/rec/historyOper";


        result = to_server(url, json.toString());

        return result;
    }

    public static String prof_oper_rec(int id_user) throws JSONException {
        String result;

        JSONObject json = new JSONObject();
        json.put("id_user", id_user);


        String url = "http://10.0.2.2:8080/prof/prof_oper_rec";


        result = to_server(url, json.toString());

        return result;
    }

    public static String veiw_mess(int id_user) throws JSONException {
        String result;

        JSONObject json = new JSONObject();
        json.put("id_user", id_user);


        String url = "http://10.0.2.2:8080/prof/view_mess";


        result = to_server(url, json.toString());

        return result;
    }

    public static String update_is_read(long id, boolean is_read) throws JSONException {
        String result;

        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("id_read", is_read);


        String url = "http://10.0.2.2:8080/prof/update_is_read";


        result = to_server(url, json.toString());

        return result;
    }

    public static String admin_select_oper() {
        String result;

        JSONObject json = new JSONObject();


        String url = "http://10.0.2.2:8080/prof/admin_select_oper";


        result = to_server(url, json.toString());

        return result;
    }

    public static String desOper(Oper_obj oper) throws JSONException {
        String result;
        Gson gson = new Gson();
        // Преобразование объекта в JSON-строку
        String jsonString = gson.toJson(oper);

        //JSONObject jsonObject = new JSONObject(jsonString);
        //jsonObject.put("reason", reason);
       //jsonString = jsonObject.toString();


        String url = "http://10.0.2.2:8080/prof/admin_desOper";

        //System.out.println(jsonString);
        result = to_server(url, jsonString);

        return result;
    }

    public static String send_mess(Mes_obj mess_new) {
        String result;
        Gson gson = new Gson();
        // Преобразование объекта в JSON-строку
        String jsonString = gson.toJson(mess_new);

        String url = "http://10.0.2.2:8080/prof/send_mess";

        result = to_server(url, jsonString);

        return result;
    }

    public static String admin_select_mess() {
        String result;

        JSONObject json = new JSONObject();
        String url = "http://10.0.2.2:8080/prof/admin_select_mess";
        result = to_server(url, json.toString());

        return result;
    }

    public static String send_ansAdmin(Mes_obj new_mess) {
        String result;
        Gson gson = new Gson();
        // Преобразование объекта в JSON-строку
        String jsonString = gson.toJson(new_mess);

        String url = "http://10.0.2.2:8080/prof/send_ansAdmin";

        result = to_server(url, jsonString);

        return result;
    }
}
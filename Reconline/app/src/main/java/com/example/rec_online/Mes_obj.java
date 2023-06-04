package com.example.rec_online;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Mes_obj {
    public int id;
    public int id_user;
    String user_name;
    public int id_prev_mes;
    String title;
    String main_text;


    boolean is_read;
    String time;

    String type;



    Mes_obj(int id_user, String title, String main_text){
        this.id_user = id_user;
        this.title = title;
        this.main_text = main_text;

    }

    Mes_obj(){

    }

    public void parseJson(JSONObject json) throws JSONException, ParseException {
        this.id = json.getInt("id");
        this.id_user = json.getInt("id_user");
        this.id_prev_mes = json.getInt("id_prev_mes");

        this.title = json.getString("title");
        this.main_text = json.getString("main_text");
        this.type = json.getString("type");
        this.is_read = json.getBoolean("is_read");


        String time_bd =  json.getString("time");
        SimpleDateFormat dateFormat_bd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = dateFormat_bd.parse(time_bd);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        this.time = dateFormat.format(date);

        if(!json.isNull("user_name")){
            this.user_name = json.getString("user_name");
        }




    }
}

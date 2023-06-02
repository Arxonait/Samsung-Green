package com.example.rec_online;

import org.json.JSONException;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Mes_obj {
    public int num_cont;
    long id;
    long id_user;
    long id_prev_mes;
    String title;
    String main_text;


    boolean is_read;
    Date time;

    String type;



    Mes_obj(int id_user, String title, String main_text){
        this.id_user = id_user;
        this.title = title;
        this.main_text = main_text;

    }

    Mes_obj(){

    }

    public void parseJson(JSONObject json) throws JSONException, ParseException {
        this.id = (long) json.get("id");
        this.id_user = (long) json.get("id_user");
        this.id_prev_mes = (long) json.get("id_prev_mes");

        this.title = (String) json.get("title");
        this.main_text = (String) json.get("main_text");
        this.type = (String) json.get("type");
        this.is_read = (boolean) json.get("is_read");


        String timeString = (String) json.get("date");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(timeString);
        this.time = date;




    }
}

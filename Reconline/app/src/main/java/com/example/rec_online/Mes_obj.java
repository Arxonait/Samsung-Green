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



//    Mes_obj(String id_user, String id_prev_mes, int metal, int plastic, int glass){
//        this.id_user = id_user;
//        this.id_prev_mes = id_prev_mes;
//
//        this.glass = glass;
//        this.metal = metal;
//        this.plastic = plastic;
//
//    }

    Mes_obj(){

    }

    public void parseJson(JSONObject answer) throws JSONException, ParseException {
        this.id = (long) answer.get("id");
        this.id_user = (long) answer.get("id_user");
        this.id_prev_mes = (long) answer.get("id_prev_mes");

        this.title = (String) answer.get("title");
        this.main_text = (String) answer.get("main_text");

        this.is_read = (boolean) answer.get("is_read");


        String timeString = (String) answer.get("date");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(timeString);
        this.time = date;




    }
}

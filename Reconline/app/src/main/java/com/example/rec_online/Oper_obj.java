package com.example.rec_online;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Oper_obj {
    String login;
    int id_user;
    String user_name;

    String reason;

    int id;

    String name_fact;
    int id_fact;
    int ball = 0;

    int glass;
    int metal;
    int plastic;

    int codeStatus = 1;
    String time;



    Oper_obj(int id_user, int id_fact, int metal, int plastic, int glass){
        this.id_user = id_user;
        this.id_fact = id_fact;

        this.glass = glass;
        this.metal = metal;
        this.plastic = plastic;

    }

    Oper_obj(){

    }

    public void parseJson(JSONObject json) throws JSONException, ParseException {
        this.id = json.getInt("id");
        this.id_user = json.getInt("id_user");
        if(!json.isNull("user_name")){
            this.user_name = json.getString("user_name");
            this.login = json.getString("login");

        }
        if(!json.isNull("reason")){
            this.reason = json.getString("reason");
        }

        this.name_fact =  json.getString("name_fact");
        this.codeStatus = json.getInt("codeStatus");

        this.ball = json.getInt("ball");

        this.glass = json.getInt("glass");
        this.plastic = json.getInt("plastic");
        this.metal = json.getInt("metal");

        String time_bd = json.getString("time");
        SimpleDateFormat dateFormat_bd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = dateFormat_bd.parse(time_bd);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        this.time = dateFormat.format(date);
    }
}

package com.example.rec_online;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Oper_obj {
    String login;
    String id_user;
    String user_name;

    String reason;

    String id;

    String name_fact;
    String id_fact;
    int ball = 0;

    int glass;
    int metal;
    int plastic;

    int status = 1;
    String time;
    int num_cont;


    Oper_obj(String id_user, String id_fact, int metal, int plastic, int glass){
        this.id_user = id_user;
        this.id_fact = id_fact;

        this.glass = glass;
        this.metal = metal;
        this.plastic = plastic;

    }

    Oper_obj(){

    }

    public void parseJson(JSONObject json) throws JSONException, ParseException {
        this.id = json.getString("id");
        this.id_user = json.getString("id_user");
        if(!json.isNull("user_name")){
            this.user_name = json.getString("user_name");
            this.login = json.getString("login");

        }
        if(!json.isNull("reason")){
            this.reason = json.getString("reason");
        }

        this.name_fact = (String) json.get("name_fact");
        this.status = Integer.parseInt((String) json.get("status"));

        this.ball = Integer.parseInt((String) json.get("ball"));

        this.glass = Integer.parseInt((String) json.get("glass"));
        this.plastic = Integer.parseInt((String) json.get("plastic"));
        this.metal = Integer.parseInt((String) json.get("metal"));

        String time_bd = json.getString("time");
        SimpleDateFormat dateFormat_bd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = dateFormat_bd.parse(time_bd);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        this.time = dateFormat.format(date);
    }
}

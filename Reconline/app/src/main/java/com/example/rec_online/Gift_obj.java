package com.example.rec_online;

import org.json.JSONException;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Gift_obj {
    String id;
    String id_user;
    String name_fact;
    String id_fact;
    int ball = 0;

    int glass;
    int metal;
    int plastic;

    int status = 1;
    Date time;
    int num_cont;


    Gift_obj(String id_user, String id_fact, int metal, int plastic, int glass){
        this.id_user = id_user;
        this.id_fact = id_fact;

        this.glass = glass;
        this.metal = metal;
        this.plastic = plastic;

    }

    Gift_obj(){

    }

    public void parseJson(JSONObject answer) throws JSONException, ParseException {
        this.id = (String) answer.get("id");
        this.id_user = (String) answer.get("id_user");
        this.name_fact = (String) answer.get("name_fact");
        this.status = Integer.parseInt((String) answer.get("status"));

        this.ball = Integer.parseInt((String) answer.get("ball"));

        this.glass = Integer.parseInt((String) answer.get("glass"));
        this.plastic = Integer.parseInt((String) answer.get("plastic"));
        this.metal = Integer.parseInt((String) answer.get("metal"));

        String timeString = (String) answer.get("time");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = dateFormat.parse(timeString);
        this.time = date;




    }
}

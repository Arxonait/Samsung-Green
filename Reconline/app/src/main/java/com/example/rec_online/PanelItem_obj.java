package com.example.rec_online;

import org.json.JSONException;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PanelItem_obj {
    long id;
    long id_user;
    String name_fact;
    long id_fact;
    int ball = 0;

    int glass;
    int metal;
    int plastic;

    int status;
    Date time;


    public void parseJson(JSONObject answer) throws JSONException, ParseException {
        this.id = (long) answer.get("id");
        this.id_user = (long) answer.get("id_user");
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

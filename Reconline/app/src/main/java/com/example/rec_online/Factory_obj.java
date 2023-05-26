package com.example.rec_online;

import org.json.JSONException;
import org.json.simple.JSONObject;

public class Factory_obj {

    String id;
    String name;
    String adres;
    double x;
    double y;
    String work_time;
    String mobile;

    boolean glass;
    boolean metal;
    boolean plastic;


    public void parseJson(JSONObject answer) throws JSONException {
        this.id = (String) answer.get("id");
        this.name = (String) answer.get("name");
        this.adres = (String) answer.get("adres");
        this.mobile = (String) answer.get("mobile");

        this.x = Double.parseDouble((String) answer.get("x"));
        this.y = Double.parseDouble((String) answer.get("y"));

        this.work_time = (String) answer.get("work_time");

        this.glass = String.valueOf(answer.get("glass")).equals("t");
        this.plastic = String.valueOf(answer.get("plastic")).equals("t");
        this.metal = String.valueOf(answer.get("metal")).equals("t");


    }

}

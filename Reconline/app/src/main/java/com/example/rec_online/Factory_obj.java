package com.example.rec_online;

import org.json.JSONException;
import org.json.JSONObject;

public class Factory_obj {

    int id;
    String name;
    String adres;
    double x;
    double y;
    String work_time;
    String mobile;

    boolean glass;
    boolean metal;
    boolean plastic;


    public void parseJson(JSONObject json) throws JSONException {
        this.id = json.getInt("id");
        this.name = json.getString("name");
        this.adres = json.getString("adres");
        this.mobile = json.getString("mobile");

        this.x = json.getDouble("x");
        this.y = json.getDouble("y");

        this.work_time = json.getString("work_time");

        this.glass = json.getBoolean("glass");
        this.plastic = json.getBoolean("plastic");
        this.metal = json.getBoolean("metal");


    }

}

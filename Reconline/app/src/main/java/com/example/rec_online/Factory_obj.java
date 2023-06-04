package com.example.rec_online;

import org.json.JSONException;
import org.json.JSONObject;

public class Factory_obj {

    public int id;
    public String name;
    public String address;
    public double x;
    public double y;
    public String work_time;
    public String phone;

    public boolean isGlass;
    public boolean isMetal;
    public boolean isPlastic;


    public void parseJson(JSONObject json) throws JSONException {
        this.id = json.getInt("id");
        this.name = json.getString("name");
        this.address = json.getString("adres");
        this.phone = json.getString("mobile");

        this.x = json.getDouble("x");
        this.y = json.getDouble("y");

        this.work_time = json.getString("work_time");

        this.isGlass = json.getBoolean("glass");
        this.isPlastic = json.getBoolean("plastic");
        this.isMetal = json.getBoolean("metal");


    }

}

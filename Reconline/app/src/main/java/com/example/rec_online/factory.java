package com.example.rec_online;

import org.json.JSONException;
import org.json.simple.JSONObject;

public class factory {


    String name;
    String adres;
    double x;
    double y;
    String work_time;
    String mobile;


    public void parseJson(JSONObject answer) throws JSONException {
        this.name = (String) answer.get("name");
        this.adres = (String) answer.get("adres");
        this.mobile = (String) answer.get("mobile");

        this.x = Double.parseDouble((String) answer.get("x"));
        this.y = Double.parseDouble((String) answer.get("y"));

        this.work_time = (String) answer.get("work_time");
    }

}

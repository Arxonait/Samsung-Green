package com.example.rec_online;

import org.json.JSONException;
import org.json.simple.JSONObject;

public class Gift_obj {
    String id;
    String id_user;
    String id_fact;
    int ball = 0;

    int glass;
    int metal;
    int plastic;

    int status = 1;


    Gift_obj(String id_user, String id_fact, int metal, int plastic, int glass){
        this.id_user = id_user;
        this.id_fact = id_fact;

        this.glass = glass;
        this.metal = metal;
        this.plastic = plastic;

    }

    Gift_obj(){

    }

    public void parseJson(JSONObject answer) throws JSONException {
        this.id = (String) answer.get("id");
        this.id_user = (String) answer.get("id_user");
        this.id_fact = (String) answer.get("id_fact");
        this.status = Integer.parseInt((String) answer.get("status"));

        this.ball = Integer.parseInt((String) answer.get("ball"));

        this.glass = Integer.parseInt((String) answer.get("glass"));
        this.plastic = Integer.parseInt((String) answer.get("plastic"));
        this.metal = Integer.parseInt((String) answer.get("metal"));


    }
}

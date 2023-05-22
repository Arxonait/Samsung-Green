package com.example.rec_online;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    String name;
    String fam;

    String mnumber;
    String login;
    String password;

    int max_balls;
    int current_balls;

    int glass;
    int metal;
    int plastic;

    boolean admin;



    User(String name, String fam, String mnumber, String login, String password, int max_balls, int current_balls, int glass,
         int metal, int plastic, boolean admin){
        this.name = name;
        this.fam = fam;
        this.mnumber = mnumber;

        this.login = login;
        this.password = password;

        this.max_balls = max_balls;
        this.current_balls = current_balls;

        this.glass = glass;
        this.metal = metal;
        this.plastic = plastic;

        this.admin = admin;

    }

    User(){

    }

    public void parseJson(JSONObject answer) throws JSONException {

        System.out.println(answer.getBoolean("admin"));
        this.name = answer.getString("name");
        this.fam = answer.getString("fam");
        this.mnumber = answer.getString("mnumber");

        this.login = answer.getString("login");
        this.password = answer.getString("password");

        this.max_balls = answer.getInt("max_balls");
        this.current_balls = answer.getInt("balls");

        this.glass = answer.getInt("glass");
        this.metal = answer.getInt("metal");
        this.plastic = answer.getInt("plastic");

        this.admin = answer.getBoolean("admin");
    }
}

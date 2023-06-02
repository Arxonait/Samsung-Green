package com.example.rec_online;

import org.json.JSONException;
import org.json.JSONObject;

public class User_obj {

    int id;
    String name;
    String fam;

    String mnumber;
    String login;
    String password;

    boolean admin;



    User_obj(String name, String fam, String mnumber, String login, String password, boolean admin){
        this.name = name;
        this.fam = fam;
        this.mnumber = mnumber;

        this.login = login;
        this.password = password;


        this.admin = admin;

    }

    User_obj(){

    }

    public void parseJson(JSONObject answer) throws JSONException {

        this.id = answer.getInt("id");
        this.name = answer.getString("name");
        this.fam = answer.getString("fam");
        this.mnumber = answer.getString("mnumber");

        this.login = answer.getString("login");
        this.password = answer.getString("password");


        this.admin = answer.getBoolean("admin");
    }
}

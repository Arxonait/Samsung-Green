package com.example.rec_online;

import org.json.JSONException;
import org.json.JSONObject;

public class User_obj {

    public int id;
    public String name;
    public String surname;

    public String phone;
    public String login;
    public String password;

    public boolean admin;



    User_obj(String name, String surname, String phone, String login, String password, boolean admin){
        this.name = name;
        this.surname = surname;
        this.phone = phone;

        this.login = login;
        this.password = password;


        this.admin = admin;

    }

    User_obj(){

    }

    public void parseJson(JSONObject json) throws JSONException {

        this.id = json.getInt("id");
        this.name = json.getString("name");
        this.surname = json.getString("surname");
        this.phone = json.getString("phone");

        this.login = json.getString("login");
        this.password = json.getString("password");


        this.admin = json.getBoolean("admin");
    }
}

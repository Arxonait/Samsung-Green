package com.example.servak;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.http.HttpServlet;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Map;


@RestController
public class HttpControllerREST extends HttpServlet {


    @RequestMapping("/enter")
    public String Enter(@RequestBody Map<String, String> body) throws SQLException {
        String login = body.get("login");
        String password = body.get("password");


        return DB_act.enter(login, password);
    }

    @RequestMapping("/reg")
    public String reg(@RequestBody String body) throws SQLException {
        JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
        System.out.println(1);
        //JSONObject jsonObject = new JSONObject(body);

        return DB_act.reg(jsonObject);
    }

    @RequestMapping("/maps")
    public String maps(@RequestBody String body) throws SQLException {
        return DB_act.factory();
    }

}



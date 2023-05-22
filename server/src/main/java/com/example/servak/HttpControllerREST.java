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
    public String Enter(@RequestBody String body) {
        JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
        System.out.println(jsonObject.get("fam").toString());
        return "true";
    }

}



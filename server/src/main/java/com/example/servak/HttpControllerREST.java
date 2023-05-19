package com.example.servak;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.http.HttpServlet;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class HttpControllerREST extends HttpServlet {

    @RequestMapping("/enter")
    public String Enter(@RequestBody Map<String, String> body) {
        String login = body.get("login");
        String password = body.get("password");

        JSONObject answer_to_mob = new JSONObject();



        // проверяем логин и пароль и возвращаем ответ в зависимости от результата
        if (login.equals("admin") && password.equals("admin")) {
            answer_to_mob.put("status", "true");
            answer_to_mob.put("login", "John");
            return answer_to_mob.toString();
        } else {
            answer_to_mob.put("status", "false");
            return answer_to_mob.toString();
        }
    }

    @RequestMapping("/reg")
    public String Enter(@RequestBody String body) {
        JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
        System.out.println(jsonObject.get("fam").toString());
        return "true";
    }

}



package com.example.servak;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.http.HttpServlet;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.text.ParseException;
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
        //JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
        JSONObject jsonObject = new JSONObject(body);
        //System.out.println(1);
        //JSONObject jsonObject = new JSONObject(body);

        return DB_act.reg(jsonObject);
    }

    @RequestMapping("/maps")
    public String maps(@RequestBody String body) throws SQLException {
        return DB_act.factory();
    }

    @RequestMapping("/stat/gift_new")
    public String gift(@RequestBody String body) throws SQLException {
        JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
        return DB_act.insert_gift(jsonObject);
    }

    @RequestMapping("/stat/view_gift")
    public String view_gift(@RequestBody String body) throws SQLException {
        JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
        return DB_act.select_gift(jsonObject);
    }

    @RequestMapping("/prof/prof_oper_rec")
    public String prof_oper_rec(@RequestBody String body) throws SQLException {
        JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
        return DB_act.prof_oper_rec(jsonObject);


    }

    @RequestMapping("/prof/view_mess")
    public String view_mess(@RequestBody String body) throws SQLException {
        JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
        return DB_act.select_mess(jsonObject);
    }

    @RequestMapping("/prof/update_is_read")
    public String update_is_read(@RequestBody String body) throws SQLException {
        JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
        return DB_act.update_is_read(jsonObject);
    }


    @RequestMapping("/prof/admin_select_oper")
    public String admin_select_oper(@RequestBody String body) throws SQLException {
        JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
        return DB_act.admin_select_oper(jsonObject);
    }

    @RequestMapping("/prof/admin_desOper")
    public String admin_desOper(@RequestBody String body) throws SQLException, ParseException {
        JSONObject jsonObject = new JSONObject(body);
        return DB_act.admin_desOper(jsonObject);
    }

    @RequestMapping("/prof/send_mess")
    public String send_mess(@RequestBody String body) throws SQLException, ParseException {
        JSONObject jsonObject = new JSONObject(body);
        return DB_act.send_mess(jsonObject);
    }
}



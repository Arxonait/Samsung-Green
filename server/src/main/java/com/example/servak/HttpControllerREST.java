package com.example.servak;
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
        JSONObject jsonObject = new JSONObject(body);
        return DB_act.reg(jsonObject);
    }

    @RequestMapping("/maps")
    public String maps(@RequestBody String body) throws SQLException {
        return DB_act.factory();
    }

    @RequestMapping("/rec/oper_new")
    public String oper(@RequestBody String body) throws SQLException {
        JSONObject json = new JSONObject(body);
        return DB_act.insert_oper(json);
    }

    @RequestMapping("/rec/view_oper")
    public String view_oper(@RequestBody String body) throws SQLException {
        JSONObject json = new JSONObject(body);
        return DB_act.select_oper(json);
    }

    @RequestMapping("/prof/prof_oper_rec")
    public String prof_oper_rec(@RequestBody String body) throws SQLException {
        JSONObject json = new JSONObject(body);
        return DB_act.prof_oper_rec(json);


    }

    @RequestMapping("/prof/view_mess")
    public String view_mess(@RequestBody String body) throws SQLException {
        JSONObject json = new JSONObject(body);
        return DB_act.select_mess(json);
    }

    @RequestMapping("/prof/update_is_read")
    public String update_is_read(@RequestBody String body) throws SQLException {
        JSONObject json = new JSONObject(body);
        return DB_act.update_is_read(json);
    }


    @RequestMapping("/prof/admin_select_oper")
    public String admin_select_oper(@RequestBody String body) throws SQLException {
        JSONObject json = new JSONObject(body);
        return DB_act.admin_select_oper(json);
    }

    @RequestMapping("/prof/admin_desOper")
    public String admin_desOper(@RequestBody String body) throws SQLException, ParseException {
        JSONObject json = new JSONObject(body);
        return DB_act.admin_desOper(json);
    }

    @RequestMapping("/prof/send_mess")
    public String send_mess(@RequestBody String body) throws SQLException, ParseException {
        JSONObject json = new JSONObject(body);
        return DB_act.send_mess(json);
    }

    @RequestMapping("/prof/admin_select_mess")
    public String admin_select_mess(@RequestBody String body) throws SQLException {
        JSONObject json = new JSONObject(body);
        return DB_act.admin_select_mess(json);
    }

    @RequestMapping("/prof/send_ansAdmin")
    public String send_ansAdmin(@RequestBody String body) throws SQLException, ParseException {
        JSONObject json = new JSONObject(body);
        return DB_act.send_ansAdmin(json);
    }
}



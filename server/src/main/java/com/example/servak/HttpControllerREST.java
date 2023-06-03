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

    @RequestMapping("/factories")
    public String factories(@RequestBody String body) throws SQLException {
        return DB_act.factory();
    }

    @RequestMapping("/rec/newOper")
    public String newOper(@RequestBody String body) throws SQLException {
        JSONObject json = new JSONObject(body);
        return DB_act.newOper(json);
    }

    @RequestMapping("/rec/historyOper")
    public String historyOper(@RequestBody String body) throws SQLException {
        JSONObject json = new JSONObject(body);
        return DB_act.historyOper(json);
    }

    @RequestMapping("/prof/profSummaryStat")
    public String profSummaryStat(@RequestBody String body) throws SQLException {
        JSONObject json = new JSONObject(body);
        return DB_act.profSummaryStat(json);


    }

    @RequestMapping("/prof/historyMess")
    public String historyMess(@RequestBody String body) throws SQLException {
        JSONObject json = new JSONObject(body);
        return DB_act.historyMess(json);
    }

    @RequestMapping("/prof/update_isRead")
    public String update_isRead(@RequestBody String body) throws SQLException {
        JSONObject json = new JSONObject(body);
        return DB_act.update_isRead(json);
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



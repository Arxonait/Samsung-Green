package com.example.servak;

import com.google.gson.JsonObject;
import org.json.simple.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DB_act {
    static final String URL = "jdbc:postgresql://localhost:5432/Reconline";
    static final String USERNAME = "postgres";
    static final String PassWord = "1234";

    public static String enter(String login, String password) throws SQLException {
        String result;
        Connection connection;
        JSONObject answer_to_mob = new JSONObject();

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PassWord);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Statement statement = connection.createStatement();
        String SQL = String.format("select * from users WHERE login = '%s'", login);
        ResultSet resultSet = statement.executeQuery(SQL);
        if (resultSet.next()) {
            if (resultSet.getString("password").equals(password)) {
                answer_to_mob.put("status", "true");
                answer_to_mob.put("login", login);
                answer_to_mob.put("password", password);
                answer_to_mob.put("id", resultSet.getString("id"));
                answer_to_mob.put("fam", resultSet.getString("fam"));
                answer_to_mob.put("name", resultSet.getString("name"));
                answer_to_mob.put("mnumber", resultSet.getString("mnumber"));

                answer_to_mob.put("admin", resultSet.getBoolean("admin"));
            } else {
                answer_to_mob.put("status", "false");
            }
        } else {
            answer_to_mob.put("status", "false");
        }
        result = answer_to_mob.toString();
        resultSet.close();
        statement.close();

        return result;
    }

    public static String reg(JsonObject json) throws SQLException {
        String result;
        Connection connection;
        JSONObject answer_to_mob = new JSONObject();

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PassWord);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Statement statement = connection.createStatement();
        String SQL = String.format("select * from users WHERE login = '%s'", json.get("login"));
        ResultSet resultSet = statement.executeQuery(SQL);

        if (resultSet.next()) {
            answer_to_mob.put("status", "false");
        } else {
            answer_to_mob.put("status", "true");
            SQL = String.format("INSERT INTO public.users (fam, name, mnumber, login, password, admin) VALUES ('%s','%s','%s','%s','%s',%b)",
                    json.get("fam").toString().replace("\"", ""), json.get("name").toString().replace("\"", ""),
                    json.get("mnumber").toString().replace("\"", ""), json.get("login").toString().replace("\"", ""),
                    json.get("password").toString().replace("\"", ""), false);
            statement.executeUpdate(SQL);
        }
        result = answer_to_mob.toString();
        resultSet.close();
        statement.close();


        return result;
    }


    public static String factory() throws SQLException {
        List<JSONObject> jsonObjects = new ArrayList<>();
        String result;
        Connection connection;


        try {
            connection = DriverManager.getConnection(URL, USERNAME, PassWord);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Statement statement = connection.createStatement();
        String SQL = String.format("select * from factory");
        ResultSet resultSet = statement.executeQuery(SQL);
        while (resultSet.next()) {
            JSONObject json = new JSONObject();
            json.put("id", resultSet.getString("id"));
            json.put("name", resultSet.getString("name"));
            json.put("adres", resultSet.getString("adres"));
            json.put("x", resultSet.getString("x"));
            json.put("y", resultSet.getString("y"));
            json.put("work_time", resultSet.getString("work_time"));
            json.put("mobile", resultSet.getString("mobile"));
            json.put("glass", resultSet.getString("glass"));
            json.put("metal", resultSet.getString("metal"));
            json.put("plastic", resultSet.getString("plastic"));


            jsonObjects.add(json);
        }

        JSONObject combinedJson = new JSONObject();
        combinedJson.put("data", jsonObjects);


        result = combinedJson.toString();
        resultSet.close();
        statement.close();

        return result;
    }


    public static String insert_gift(JsonObject json) throws SQLException {
        String result;
        int glass = Integer.parseInt(json.get("glass").toString());
        int metal = Integer.parseInt(json.get("metal").toString());
        int plastic = Integer.parseInt(json.get("plastic").toString());

        int ball_new = glass * 10 + plastic * 5 + metal * 2;
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        System.out.println(time);




        Connection connection;
        JSONObject answer_to_mob = new JSONObject();

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PassWord);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Statement statement = connection.createStatement();

        String SQL = String.format("INSERT INTO public.gift (id_user, id_fact, metal, plastic, glass, ball, status, timee) VALUES ('%s', " +
                "'%s', '%d', '%d', '%d', '%d', '%d', '%s')", json.get("id_user").toString().replace("\"", ""),
                json.get("id_fact").toString().replace("\"", ""), metal, plastic, glass, ball_new, 1, time );
        statement.executeUpdate(SQL);

        answer_to_mob.put("status", "true");
        result = answer_to_mob.toString();
        return result;
    }

    public static String select_gift(JsonObject jsonObject) throws SQLException {
        String result;
        Connection connection;
        List<JSONObject> jsonObjects = new ArrayList<>();
        int cont_row = 0;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PassWord);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Statement statement = connection.createStatement();
        String SQL = String.format("select * from gift INNER JOIN factory ON factory.id = gift.id_fact WHERE   id_user = '%s' ORDER BY gift.id DESC", jsonObject.get("id_user"));
        ResultSet resultSet = statement.executeQuery(SQL);
        while (resultSet.next()) {
            JSONObject json = new JSONObject();
            json.put("id", resultSet.getString("id"));
            json.put("id_fact", resultSet.getString("id_fact"));
            json.put("name_fact", resultSet.getString("name"));
            json.put("id_user", resultSet.getString("id_user"));
            json.put("ball", resultSet.getString("ball"));
            json.put("plastic", resultSet.getString("plastic"));
            json.put("glass", resultSet.getString("glass"));
            json.put("metal", resultSet.getString("metal"));

            json.put("status", resultSet.getString("status"));
            json.put("time", resultSet.getString("timee"));


            cont_row++;


            jsonObjects.add(json);
        }



        JSONObject combinedJson = new JSONObject();
        combinedJson.put("data", jsonObjects);
        if(cont_row>0){
            combinedJson.put("status", "true");
        }
        else {
            combinedJson.put("status", "false");
        }



        result = combinedJson.toString();
        //System.out.println(result);
        resultSet.close();
        statement.close();

        return result;

    }

    public static String prof_oper_rec(JsonObject jsonObject) throws SQLException {
        String result;
        Connection connection;
        JSONObject json = new JSONObject();

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PassWord);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Statement statement = connection.createStatement();
        String SQL = String.format("SELECT id_user, SUM(ball) AS totalBall, SUM(plastic) AS totalPlastic, SUM(glass) AS totalGlass, SUM(metal) AS totalMetal " +
                "FROM gift WHERE id_user = '%s' and status = 11 GROUP BY id_user", jsonObject.get("id_user"));
        ResultSet resultSet = statement.executeQuery(SQL);
        if (resultSet.next()) {
            int totalBall = resultSet.getInt("totalBall");
            int totalPlastic = resultSet.getInt("totalPlastic");
            int totalGlass = resultSet.getInt("totalGlass");
            int totalMetal = resultSet.getInt("totalMetal");

            json.put("ball", totalBall);
            json.put("plastic", totalPlastic);
            json.put("glass", totalGlass);
            json.put("metal", totalMetal);
            json.put("status", "true");
        }
        else {
            json.put("status", "false");
        }






        result = json.toString();

        resultSet.close();
        statement.close();

        return result;
    }
}

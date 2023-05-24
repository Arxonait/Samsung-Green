package com.example.servak;

import com.google.gson.JsonObject;
import org.json.simple.JSONObject;

import java.security.PublicKey;
import java.sql.*;
import java.util.ArrayList;
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
                answer_to_mob.put("fam", resultSet.getString("fam"));
                answer_to_mob.put("name", resultSet.getString("name"));
                answer_to_mob.put("mnumber", resultSet.getString("mnumber"));
                answer_to_mob.put("max_balls", resultSet.getString("max_balls"));
                answer_to_mob.put("balls", resultSet.getString("balls"));
                answer_to_mob.put("plastic", resultSet.getString("plastic"));
                answer_to_mob.put("glass", resultSet.getString("glass"));
                answer_to_mob.put("metal", resultSet.getString("metal"));

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
            SQL = "INSERT INTO public.users (fam, name, mnumber, login, password, max_balls, balls, plastic, glass, metal, admin) VALUES ('" +
                    json.get("fam").toString().replace("\"", "") + "', '" + json.get("name").toString().replace("\"", "") + "', '" + json.get("mnumber").toString().replace("\"", "") + "', '" +
                    json.get("login").toString().replace("\"", "") + "', '" + json.get("password").toString().replace("\"", "") + "', 0, 0, 0, 0, 0, false)";

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
        JSONObject answer_to_mob = new JSONObject();

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

            jsonObjects.add(json);
        }

        JSONObject combinedJson = new JSONObject();
        combinedJson.put("data", jsonObjects);


        result = combinedJson.toString();
        resultSet.close();
        statement.close();

        return result;
    }



}

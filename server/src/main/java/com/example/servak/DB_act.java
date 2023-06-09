package com.example.servak;

import org.json.JSONObject;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DB_act {
    static final String URL = "jdbc:postgresql://localhost:5432/Reconline";
    static final String USERNAME = "postgres";
    static final String PassWord = "1234";

    public static String enter(String login, String password) throws SQLException {
        Connection connection;
        JSONObject response = new JSONObject();

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
                response.put("status", true);
                response.put("login", login);
                response.put("password", password);
                response.put("id", resultSet.getInt("id"));
                response.put("surname", resultSet.getString("surname"));
                response.put("name", resultSet.getString("name"));
                response.put("phone", resultSet.getString("phone"));

                response.put("admin", resultSet.getBoolean("admin"));
            } else {
                response.put("status", false);
            }
        } else {
            response.put("status", false);
        }

        resultSet.close();
        statement.close();

        return response.toString();
    }

    public static String reg(JSONObject json) throws SQLException {
        Connection connection;
        JSONObject response = new JSONObject();

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PassWord);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Statement statement = connection.createStatement();
        String SQL = String.format("select * from users WHERE login = '%s'", json.getString("login"));
        ResultSet resultSet = statement.executeQuery(SQL);

        if (resultSet.next()) {
            response.put("status", false);
        } else {
            response.put("status", true);
            SQL = String.format("INSERT INTO users (surname, name, phone, login, password, admin) VALUES ('%s','%s','%s','%s','%s',%b)",
                    json.getString("surname"), json.getString("name"),
                    json.getString("phone"), json.getString("login"), json.getString("password"), false);
            statement.executeUpdate(SQL);
        }
        resultSet.close();
        statement.close();


        return response.toString();
    }


    public static String factory() throws SQLException {
        List<JSONObject> jsonObjects = new ArrayList<>();
        Connection connection;


        try {
            connection = DriverManager.getConnection(URL, USERNAME, PassWord);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Statement statement = connection.createStatement();
        String SQL = "select * from factory";
        ResultSet resultSet = statement.executeQuery(SQL);
        while (resultSet.next()) {
            JSONObject json = new JSONObject();
            json.put("id", resultSet.getInt("id"));
            json.put("name", resultSet.getString("name"));
            json.put("adres", resultSet.getString("adres"));
            json.put("x", resultSet.getDouble("x"));
            json.put("y", resultSet.getDouble("y"));
            json.put("work_time", resultSet.getString("work_time"));
            json.put("mobile", resultSet.getString("mobile"));
            json.put("glass", resultSet.getBoolean("glass"));
            json.put("metal", resultSet.getBoolean("metal"));
            json.put("plastic", resultSet.getBoolean("plastic"));


            jsonObjects.add(json);
        }

        JSONObject combJson = new JSONObject();
        combJson.put("data", jsonObjects);



        resultSet.close();
        statement.close();

        return combJson.toString();
    }


    public static String newOper(JSONObject json) throws SQLException {
        String result;
        int glass = json.getInt("glass");
        int metal = json.getInt("metal");
        int plastic = json.getInt("plastic");

        int ball_new = glass * 10 + plastic * 5 + metal * 2;

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String current_time = dateFormat.format(currentDate);


        Connection connection;
        JSONObject answer_to_mob = new JSONObject();

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PassWord);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Statement statement = connection.createStatement();

        String SQL = String.format("INSERT INTO operations (id_user, id_fact, metal, plastic, glass, ball, codestatus, timeoper) VALUES ('%d', " +
                        "'%d', '%d', '%d', '%d', '%d', '%d', '%s')", json.getInt("id_user"),
                json.getInt("id_fact"), metal, plastic, glass, ball_new, 1, current_time);
        statement.executeUpdate(SQL);

        answer_to_mob.put("status", true);
        result = answer_to_mob.toString();
        return result;
    }

    public static String historyOper(JSONObject json) throws SQLException {
        Connection connection;
        List<JSONObject> jsonObjects = new ArrayList<>();
        int cont_row = 0;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PassWord);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Statement statement = connection.createStatement();
        String SQL = String.format("select * from operations INNER JOIN factory ON factory.id = operations.id_fact WHERE id_user = '%d' ORDER BY operations.id DESC",
                json.getInt("id_user"));
        ResultSet resultSet = statement.executeQuery(SQL);
        while (resultSet.next()) {
            org.json.JSONObject json_i = new org.json.JSONObject();
            json_i.put("id", resultSet.getInt("id"));
            json_i.put("id_fact", resultSet.getInt("id_fact"));
            json_i.put("name_fact", resultSet.getString("name"));
            json_i.put("id_user", resultSet.getInt("id_user"));
            json_i.put("ball", resultSet.getInt("ball"));
            json_i.put("plastic", resultSet.getInt("plastic"));
            json_i.put("glass", resultSet.getInt("glass"));
            json_i.put("metal", resultSet.getInt("metal"));

            json_i.put("reason", resultSet.getString("reason"));
            json_i.put("codeStatus", resultSet.getInt("codestatus"));
            json_i.put("time", resultSet.getString("timeoper"));


            cont_row++;


            jsonObjects.add(json_i);
        }


        JSONObject combJson = new JSONObject();
        combJson.put("data", jsonObjects);
        combJson.put("status", cont_row > 0);

        resultSet.close();
        statement.close();

        return combJson.toString();

    }

    public static String profSummaryStat(JSONObject json) throws SQLException {
        Connection connection;
        JSONObject answer_to_mob = new JSONObject();

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PassWord);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Statement statement = connection.createStatement();
        String SQL = String.format("SELECT id_user, SUM(ball) AS totalBall, SUM(plastic) AS totalPlastic, SUM(glass) AS totalGlass, SUM(metal) AS totalMetal " +
                "FROM operations WHERE id_user = '%d' and codestatus = 11 GROUP BY id_user", json.getInt("id_user"));
        ResultSet resultSet = statement.executeQuery(SQL);
        if (resultSet.next()) {
            int totalBall = resultSet.getInt("totalBall");
            int totalPlastic = resultSet.getInt("totalPlastic");
            int totalGlass = resultSet.getInt("totalGlass");
            int totalMetal = resultSet.getInt("totalMetal");

            answer_to_mob.put("ball", totalBall);
            answer_to_mob.put("plastic", totalPlastic);
            answer_to_mob.put("glass", totalGlass);
            answer_to_mob.put("metal", totalMetal);
            answer_to_mob.put("status", true);
        } else {
            answer_to_mob.put("status", false);
        }


        resultSet.close();
        statement.close();

        return answer_to_mob.toString();
    }


    public static String historyMess(JSONObject json) throws SQLException {
        Connection connection;
        List<JSONObject> jsonObjects = new ArrayList<>();
        int cont_row = 0;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PassWord);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Statement statement = connection.createStatement();
        String SQL = String.format("select * from messages WHERE id_user = '%s' and type != 'ansMess' ORDER BY messages.id DESC", json.getInt("id_user"));
        ResultSet resultSet = statement.executeQuery(SQL);
        while (resultSet.next()) {
            org.json.JSONObject json_i = new org.json.JSONObject();
            json_i.put("id", resultSet.getInt("id"));
            json_i.put("id_user", resultSet.getInt("id_user"));
            json_i.put("id_prev_mes", resultSet.getInt("id_prev_mes"));
            json_i.put("is_read", resultSet.getBoolean("is_read"));
            json_i.put("time", resultSet.getString("timemess"));
            json_i.put("title", resultSet.getString("title"));
            json_i.put("main_text", resultSet.getString("main_text"));
            json_i.put("type", resultSet.getString("type"));




            cont_row++;


            jsonObjects.add(json_i);
        }


        JSONObject combJson = new JSONObject();
        combJson.put("data", jsonObjects);
        combJson.put("status", cont_row > 0);


        resultSet.close();
        statement.close();

        return combJson.toString();


    }

    public static String update_isRead(JSONObject json) throws SQLException {
        Connection connection;
        JSONObject answer_to_mob = new JSONObject();

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PassWord);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String sql = "UPDATE messages SET is_read = true WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, json.getInt("id"));
        statement.executeUpdate();


        answer_to_mob.put("status", true);

        statement.close();


        return answer_to_mob.toString();

    }

    public static String admin_select_oper(JSONObject json) throws SQLException {
        Connection connection;
        List<JSONObject> jsonObjects = new ArrayList<>();
        int cont_row = 0;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PassWord);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Statement statement = connection.createStatement();
        String SQL = String.format("SELECT operations.id, operations.id_fact, factory.name AS factory_name, " +
                "operations.id_user, users.name AS user_name, login, operations.ball, operations.plastic, operations.glass, operations.metal, " +
                "operations.codestatus, operations.timeoper " +
                "FROM operations " +
                "INNER JOIN factory ON factory.id = operations.id_fact " +
                "INNER JOIN users ON users.id = operations.id_user " +
                "WHERE operations.codestatus = 1 " +
                "ORDER BY operations.timeoper DESC");

        ResultSet resultSet = statement.executeQuery(SQL);
        while (resultSet.next()) {
            JSONObject json_i = new JSONObject();
            json_i.put("id", resultSet.getInt("id"));
            json_i.put("id_fact", resultSet.getInt("id_fact"));
            json_i.put("name_fact", resultSet.getString("factory_name"));

            json_i.put("id_user", resultSet.getInt("id_user"));
            json_i.put("user_name", resultSet.getString("user_name"));
            json_i.put("login", resultSet.getString("login"));

            json_i.put("ball", resultSet.getInt("ball"));
            json_i.put("plastic", resultSet.getInt("plastic"));
            json_i.put("glass", resultSet.getInt("glass"));
            json_i.put("metal", resultSet.getInt("metal"));
            json_i.put("codeStatus", resultSet.getString("codestatus"));
            json_i.put("time", resultSet.getString("timeoper"));



            cont_row++;


            jsonObjects.add(json_i);
        }


        JSONObject combJson = new JSONObject();
        combJson.put("data", jsonObjects);
        if (cont_row > 0) {
            combJson.put("status", "true");
        } else {
            combJson.put("status", "false");
        }


        resultSet.close();
        statement.close();

        return combJson.toString();
    }

    public static String admin_desOper(JSONObject json) throws SQLException {
        Connection connection;
        JSONObject answer_to_mob = new JSONObject();

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PassWord);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String sql = "UPDATE operations SET codestatus = ?, reason = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, json.getInt("codeStatus"));
        statement.setString(2, json.getString("reason"));
        statement.setInt(3, json.getInt("id"));
        statement.executeUpdate();

        if(json.getInt("codeStatus") == 11){
            CreateMess.acc_balls(json);
        }
        else {
            CreateMess.ref_balls(json);
        }
        sql = String.format("INSERT INTO messages (id_user, id_prev_mes, timemess, is_read, title, main_text, type) VALUES ('%d', " +
                        "'%d', '%s', '%b', '%s', '%s', '%s')", json.getInt("id_user"), -1,
                json.getString("time"), false, CreateMess.title, CreateMess.main_text, "balls");
        statement = connection.prepareStatement(sql);
        statement.executeUpdate();


        answer_to_mob.put("status", true);

        statement.close();


        return answer_to_mob.toString();

    }

    public static String send_mess(JSONObject json) throws SQLException {
        Connection connection;
        JSONObject answer_to_mob = new JSONObject();

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PassWord);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String current_time = dateFormat.format(currentDate);

        String sql = String.format("INSERT INTO messages (id_user, id_prev_mes, timemess, is_read, title, main_text, type) VALUES ('%d', " +
                        "'%d', '%s', '%b', '%s', '%s', '%s')", json.getInt("id_user"), -1,
                current_time, false, json.getString("title"), json.getString("main_text"), "mess");
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();


        answer_to_mob.put("status", true);

        statement.close();


        return answer_to_mob.toString();
    }

    public static String admin_select_mess(JSONObject jsonObject) throws SQLException {
        Connection connection;
        List<JSONObject> jsonObjects = new ArrayList<>();
        int cont_row = 0;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PassWord);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Statement statement = connection.createStatement();
        String SQL = String.format("SELECT *" +
                "FROM messages INNER JOIN users ON messages.id_user = users.id " +
                "WHERE id_prev_mes = -1 and type = 'mess' " +
                "ORDER BY timemess DESC");

        ResultSet resultSet = statement.executeQuery(SQL);
        while (resultSet.next()) {
            org.json.JSONObject json = new org.json.JSONObject();
            json.put("id", resultSet.getInt("id"));
            json.put("id_prev_mes", resultSet.getInt("id_prev_mes"));

            json.put("id_user", resultSet.getInt("id_user"));
            json.put("user_name", resultSet.getString("login"));


            json.put("title", resultSet.getString("title"));
            json.put("main_text", resultSet.getString("main_text"));
            json.put("type", resultSet.getString("type"));
            json.put("is_read", resultSet.getBoolean("is_read"));
            json.put("time", resultSet.getString("timemess"));


            cont_row++;


            jsonObjects.add(json);
        }
        JSONObject combJson = new JSONObject();
        combJson.put("data", jsonObjects);
        if (cont_row > 0) {
            combJson.put("status", "true");
        } else {
            combJson.put("status", "false");
        }



        resultSet.close();
        statement.close();

        return combJson.toString();
    }

    public static String send_ansAdmin(JSONObject json) throws SQLException {
        Connection connection;
        JSONObject answer_to_mob = new JSONObject();

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PassWord);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String current_time = dateFormat.format(currentDate);

        String title = String.format("Ответ на \"%s\"", json.getString("title"));

        String sql = String.format("INSERT INTO messages (id_user, id_prev_mes, timemess, is_read, title, main_text, type) VALUES ('%d', " +
                        "'%d', '%s', '%b', '%s', '%s', '%s')", json.getInt("id_user"), json.getInt("id_prev_mes"),
                current_time, false, title, json.getString("main_text"), "mess");
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();

        sql = String.format("UPDATE messages SET type = 'ansMess' WHERE id = %d", json.getInt("id_prev_mes"));
        statement = connection.prepareStatement(sql);
        statement.executeUpdate();

        answer_to_mob.put("status", true);


        statement.close();


        return answer_to_mob.toString();
    }
}

package com.example.servak;

import com.google.gson.JsonObject;
import org.json.simple.JSONObject;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
                answer_to_mob.put("id", resultSet.getInt("id"));
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

    public static String reg(org.json.JSONObject json) throws SQLException {
        String result;
        Connection connection;
        JSONObject answer_to_mob = new JSONObject();

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PassWord);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Statement statement = connection.createStatement();
        String SQL = String.format("select * from users WHERE login = '%s'", json.getString("login"));
        ResultSet resultSet = statement.executeQuery(SQL);

        if (resultSet.next()) {
            answer_to_mob.put("status", "false");
        } else {
            answer_to_mob.put("status", "true");
            SQL = String.format("INSERT INTO users (fam, name, mnumber, login, password, admin) VALUES ('%s','%s','%s','%s','%s',%b)",
                    json.getString("fam"), json.getString("name"),
                    json.getString("mnumber"), json.getString("login"), json.getString("password"), false);
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

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String current_time = dateFormat.format(currentDate);
        System.out.println(current_time);


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
                json.get("id_fact").toString().replace("\"", ""), metal, plastic, glass, ball_new, 1, current_time);
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
            json.put("id", resultSet.getInt("id"));
            json.put("id_fact", resultSet.getInt("id_fact"));
            json.put("name_fact", resultSet.getString("name"));
            json.put("id_user", resultSet.getInt("id_user"));
            json.put("ball", resultSet.getInt("ball"));
            json.put("plastic", resultSet.getInt("plastic"));
            json.put("glass", resultSet.getInt("glass"));
            json.put("metal", resultSet.getInt("metal"));

            json.put("reason", resultSet.getString("reason"));
            json.put("status", resultSet.getString("status"));
            json.put("time", resultSet.getString("timee"));


            cont_row++;


            jsonObjects.add(json);
        }


        JSONObject combinedJson = new JSONObject();
        combinedJson.put("data", jsonObjects);
        if (cont_row > 0) {
            combinedJson.put("status", "true");
        } else {
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
        } else {
            json.put("status", "false");
        }


        result = json.toString();

        resultSet.close();
        statement.close();

        return result;
    }


    public static String select_mess(JsonObject jsonObject) throws SQLException {
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
        String SQL = String.format("select * from messages WHERE   id_user = '%s' ORDER BY messages.id DESC", jsonObject.get("id_user"));
        ResultSet resultSet = statement.executeQuery(SQL);
        while (resultSet.next()) {
            JSONObject json = new JSONObject();
            json.put("id", resultSet.getInt("id"));
            json.put("id_user", resultSet.getInt("id_user"));
            json.put("id_prev_mes", resultSet.getInt("id_prev_mes"));
            json.put("is_read", resultSet.getBoolean("is_read"));
            json.put("time", resultSet.getString("date"));
            json.put("title", resultSet.getString("title"));
            json.put("main_text", resultSet.getString("main_text"));
            json.put("type", resultSet.getString("type"));




            cont_row++;


            jsonObjects.add(json);
        }


        JSONObject combinedJson = new JSONObject();
        combinedJson.put("data", jsonObjects);
        if (cont_row > 0) {
            combinedJson.put("status", "true");
        } else {
            combinedJson.put("status", "false");
        }


        result = combinedJson.toString();
        //System.out.println(result);
        resultSet.close();
        statement.close();

        return result;


    }

    public static String update_is_read(JsonObject json) throws SQLException {
        String result;
        Connection connection;
        JSONObject answer_to_mob = new JSONObject();

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PassWord);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String sql = "UPDATE messages SET is_read = true WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, Integer.parseInt(json.get("id").toString()));
        statement.executeUpdate();


        answer_to_mob.put("status", true);

        result = answer_to_mob.toString();
        statement.close();


        return result;

    }

    public static String admin_select_oper(JsonObject json_obj) throws SQLException {
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
        String SQL = String.format("SELECT gift.id, gift.id_fact, factory.name AS factory_name, " +
                "gift.id_user, users.name AS user_name, login, gift.ball, gift.plastic, gift.glass, gift.metal, " +
                "gift.status, gift.timee " +
                "FROM gift " +
                "INNER JOIN factory ON factory.id = gift.id_fact " +
                "INNER JOIN users ON users.id = gift.id_user " +
                "WHERE gift.status = 1 " +
                "ORDER BY gift.timee DESC");

        ResultSet resultSet = statement.executeQuery(SQL);
        while (resultSet.next()) {
            JSONObject json = new JSONObject();
            json.put("id", resultSet.getString("id"));
            json.put("id_fact", resultSet.getString("id_fact"));
            json.put("name_fact", resultSet.getString("factory_name"));

            json.put("id_user", resultSet.getInt("id_user"));
            json.put("user_name", resultSet.getString("user_name"));
            json.put("login", resultSet.getString("login"));

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
        if (cont_row > 0) {
            combinedJson.put("status", "true");
        } else {
            combinedJson.put("status", "false");
        }


        result = combinedJson.toString();
        //System.out.println(result);
        resultSet.close();
        statement.close();

        return result;
    }

    public static String admin_desOper(org.json.JSONObject json) throws SQLException, ParseException {
        String result;
        Connection connection;
        JSONObject answer_to_mob = new JSONObject();

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PassWord);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String sql = "UPDATE gift SET status = ?, reason = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, json.getInt("status"));
        statement.setString(2, json.getString("reason"));
        statement.setInt(3, json.getInt("id"));
        statement.executeUpdate();

        if(json.getInt("status") == 11){
            CreateMess.acc_balls(json);
        }
        else {
            CreateMess.ref_balls(json);
        }
        sql = String.format("INSERT INTO messages (id_user, id_prev_mes, date, is_read, title, main_text, type) VALUES ('%d', " +
                        "'%d', '%s', '%b', '%s', '%s', '%s')", json.getInt("id_user"), -1,
                json.getString("time"), false, CreateMess.title, CreateMess.main_text, "balls");
        statement = connection.prepareStatement(sql);
        statement.executeUpdate();


        answer_to_mob.put("status", true);

        result = answer_to_mob.toString();
        statement.close();


        return result;

    }

    public static String send_mess(org.json.JSONObject json) throws SQLException {
        String result;
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

        String sql = String.format("INSERT INTO messages (id_user, id_prev_mes, date, is_read, title, main_text, type) VALUES ('%d', " +
                        "'%d', '%s', '%b', '%s', '%s', '%s')", json.getInt("id_user"), -1,
                current_time, false, json.getString("title"), json.getString("main_text"), "mess");
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();


        answer_to_mob.put("status", true);

        result = answer_to_mob.toString();
        statement.close();


        return result;
    }

    public static String admin_select_mess(org.json.JSONObject jsonObject) throws SQLException {
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
        String SQL = String.format("SELECT *" +
                "FROM messages INNER JOIN users ON messages.id_user = users.id " +
                "WHERE id_prev_mes = -1 and type = 'mess' " +
                "ORDER BY date DESC");

        ResultSet resultSet = statement.executeQuery(SQL);
        while (resultSet.next()) {
            JSONObject json = new JSONObject();
            json.put("id", resultSet.getInt("id"));
            json.put("id_prev_mes", resultSet.getInt("id_prev_mes"));

            json.put("id_user", resultSet.getInt("id_user"));
            json.put("user_name", resultSet.getString("login"));


            json.put("title", resultSet.getString("title"));
            json.put("main_text", resultSet.getString("main_text"));
            json.put("type", resultSet.getString("type"));
            json.put("is_read", resultSet.getBoolean("is_read"));
            json.put("time", resultSet.getString("date"));


            cont_row++;


            jsonObjects.add(json);
        }
        JSONObject combinedJson = new JSONObject();
        combinedJson.put("data", jsonObjects);
        if (cont_row > 0) {
            combinedJson.put("status", "true");
        } else {
            combinedJson.put("status", "false");
        }


        result = combinedJson.toString();
        //System.out.println(result);
        resultSet.close();
        statement.close();

        return result;
    }

    public static String send_ansAdmin(org.json.JSONObject json) throws SQLException {
        String result;
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

        String sql = String.format("INSERT INTO messages (id_user, id_prev_mes, date, is_read, title, main_text, type) VALUES ('%d', " +
                        "'%d', '%s', '%b', '%s', '%s', '%s')", json.getInt("id_user"), json.getInt("id_prev_mes"),
                current_time, false, title, json.getString("main_text"), "mess");
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();

        sql = String.format("UPDATE messages SET type = 'ansMess' WHERE id = %d", json.getInt("id_prev_mes"));
        statement = connection.prepareStatement(sql);
        statement.executeUpdate();

        answer_to_mob.put("status", true);

        result = answer_to_mob.toString();
        statement.close();


        return result;
    }
}

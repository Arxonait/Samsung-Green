package com.example.servak;

import org.json.simple.JSONObject;

import java.sql.*;

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
            } else {
                answer_to_mob.put("status", "false");
            }
        }
        result = answer_to_mob.toString();
        resultSet.close();
        statement.close();
        //Statement statement = connection.createStatement();
        //String SQL = String.format("INSERT INTO main VALUES ('%s', %d, %d)", user.fio, user.num_class, 20);
        //statement.executeUpdate(SQL);




        return result;
    }


}

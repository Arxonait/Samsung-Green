package com.example.servak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) throws SQLException {
        DB_act.createDB();
        SpringApplication.run(ServerApplication.class, args);
    }
}




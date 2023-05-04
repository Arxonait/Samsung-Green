package com.example.servak;
import jakarta.servlet.http.HttpServlet;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class HttpControllerREST extends HttpServlet {

    @RequestMapping("/")
    public String Enter(@RequestBody Map<String, String> body) {
        String login = body.get("login");
        String password = body.get("password");
        // проверяем логин и пароль и возвращаем ответ в зависимости от результата
        if (login.equals("admin") && password.equals("admin")) {
            return "Login successful";
        } else {
            return "Invalid login";
        }
    }

}



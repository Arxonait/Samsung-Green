package com.example.servak;


import org.json.JSONObject;


import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateMess {
    static String title;
    static String main_text;

    public static void acc_balls(JSONObject json) {

        int new_ball = Integer.parseInt(json.get("ball").toString().replace("\"",""));



        String name_fact = json.get("name_fact").toString().replace("\"","");


        int glass = Integer.parseInt(json.get("glass").toString());
        int metal = Integer.parseInt(json.get("metal").toString());
        int plastic = Integer.parseInt(json.get("plastic").toString());


        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String current_time = dateFormat.format(currentDate);

        String date_prev_mess = json.getString("time");




        title = "Начисление баллов";
        main_text = String.format("Вам начисленны баллы в размере %d\n" +
                "Дата зачисления %s\n\n" +
                "За сдачу в центр %s от %s\n" +
                "Стекло - %d ед.\n" +
                "Пластик - %d ед.\n" +
                "Металл - %d ед.\n", new_ball, current_time, name_fact, date_prev_mess, glass, plastic, metal);
    }

    public static void ref_balls(JSONObject json) {
        String name_fact = json.getString("name_fact");


        int glass = json.getInt("glass");;
        int metal = json.getInt("metal");;
        int plastic = json.getInt("plastic");


        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String current_time = dateFormat.format(currentDate);

        String date_prev_mess = json.getString("time");
        String reason = json.getString("reason");


        title = "Отказ в начислении баллов";
        main_text = String.format("Вам отказано в начислении баллов\n" +
                "Причина: %s\n" +
                "Дата обработки вашей операции %s\n\n" +
                "Отказ был произведен за сдачу в центр %s от %s\n" +
                "Стекло - %d ед.\n" +
                "Пластик - %d ед.\n" +
                "Металл - %d ед.\n", reason, current_time, name_fact, date_prev_mess, glass, plastic, metal);
    }

}

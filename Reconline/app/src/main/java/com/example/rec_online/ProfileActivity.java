package com.example.rec_online;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        load_sec_info();


    }


    private void load_sec_info() {
        TextView tv_login = findViewById(R.id.prof_login);
        TextView tv_name = findViewById(R.id.prof_name);
        TextView tv_fam = findViewById(R.id.prof_fam);

        tv_login.setText(EnterActivity.Data_enter().login);
        tv_name.setText(EnterActivity.Data_enter().name);
        tv_fam.setText(EnterActivity.Data_enter().fam);


        TextView tv_count_balls = findViewById(R.id.prof_count_ball);
        TextView tv_plastic_count = findViewById(R.id.count_p);
        TextView tv_glass_count = findViewById(R.id.count_g);
        TextView tv_metal_count = findViewById(R.id.count_m);

        if((Pass_act.json.get("status").toString().equals("true"))){
            tv_count_balls.setText(Pass_act.json.get("ball").toString());
            tv_glass_count.setText(Pass_act.json.get("glass").toString());
            tv_metal_count.setText(Pass_act.json.get("metal").toString());
            tv_plastic_count.setText(Pass_act.json.get("plastic").toString());

        }
        else {
            tv_count_balls.setText("0");
            tv_glass_count.setText("0");
            tv_metal_count.setText("0");
            tv_plastic_count.setText("0");
        }

    }
}
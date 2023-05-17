package com.example.rec_online;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Register extends AppCompatActivity {
    static int data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextView test = (TextView) findViewById(R.id.textView2);
        test.setText(String.valueOf(data));
    }

    public static void getData(int data0){
        data = data0;
    }
}
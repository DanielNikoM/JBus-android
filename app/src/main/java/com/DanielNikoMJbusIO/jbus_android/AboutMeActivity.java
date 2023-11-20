package com.DanielNikoMJbusIO.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AboutMeActivity extends AppCompatActivity {
    private TextView Uname;
    private TextView email;
    private TextView balance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);


        Uname = findViewById(R.id.username);
        email = findViewById(R.id.emailIn);
        balance = findViewById(R.id.balance);

        Uname.setText("Daniel.Niko");
        email.setText("Daniel@gmail.com");
        balance.setText("IDR 999999");
    }
}
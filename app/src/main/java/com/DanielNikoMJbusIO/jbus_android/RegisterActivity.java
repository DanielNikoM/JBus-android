package com.DanielNikoMJbusIO.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    private Button Register = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Register= findViewById(R.id.RegisterButton);

        Register.setOnClickListener(v -> {moveActivity(this, MainActivity.class);});
        getSupportActionBar().hide();
    }
    private void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }
}
package com.DanielNikoMJbusIO.jbus_android;

import static com.DanielNikoMJbusIO.jbus_android.LoginActivity.loggedAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.DanielNikoMJbusIO.jbus_android.model.Account;
import com.DanielNikoMJbusIO.jbus_android.model.BaseResponse;
import com.DanielNikoMJbusIO.jbus_android.request.BaseApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMeActivity extends AppCompatActivity {
    private BaseApiService mApiService;
    private Context mContext;
    private TextView Uname;
    private TextView email;
    private TextView balance;
    private EditText Amount;
    private Button TopUpButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);


        Uname = findViewById(R.id.username);
        email = findViewById(R.id.emailIn);
        balance = findViewById(R.id.balance);
        Amount = findViewById(R.id.TopUpAmount);


        Uname.setText(loggedAccount.name);
        email.setText(loggedAccount.email);
        balance.setText(String.valueOf(loggedAccount.balance));


        TopUpButton = findViewById(R.id.TopUp);
        TopUpButton.setOnClickListener(V->handleTopUp());
    }

    protected void handleTopUp() {
        String balanceS = Amount.getText().toString();

        if (balanceS.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        double topUpD = Double.valueOf(balanceS);
        mApiService.topUp(loggedAccount.id, topUpD).enqueue(new Callback<BaseResponse<Double>>() {
            @Override
            public void onResponse(Call<BaseResponse<Double>> call, Response<BaseResponse<Double>> response) {
                // handle the potential 4xx & 5xx error
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseResponse<Double> res = response.body();
                if (res.success) {
                    finish();
                    loggedAccount.balance += res.payload;
                    startActivity(getIntent());
                    Toast.makeText(mContext, "Berhasil Top Up", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(mContext, "Gagal Top Up", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<BaseResponse<Double>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
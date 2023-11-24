package com.DanielNikoMJbusIO.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.DanielNikoMJbusIO.jbus_android.model.Account;
import com.DanielNikoMJbusIO.jbus_android.model.BaseResponse;
import com.DanielNikoMJbusIO.jbus_android.request.BaseApiService;
import com.DanielNikoMJbusIO.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextView registerNow = null;
    private Button loginButton = null;
    private BaseApiService mApiService;
    private Context mContext;
    private EditText email, password;

    public static Account loggedAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerNow = findViewById(R.id.RegisterNow);
        loginButton = findViewById(R.id.Login);

        registerNow.setOnClickListener(v -> {moveActivity(this, RegisterActivity.class);});

        mContext = this;
        mApiService = UtilsApi.getApiService();
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);

        loginButton.setOnClickListener(V-> {handlelogin();});
        getSupportActionBar().hide();
    }
    private void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    private void viewToast(Context ctx, String message){
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    protected void handlelogin() {
// handling empty field
        String emailS = email.getText().toString();
        String passwordS = password.getText().toString();
        if (emailS.isEmpty() || passwordS.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        mApiService.Login(emailS, passwordS).enqueue(new Callback<BaseResponse<Account>>() {
            @Override
            public void onResponse(Call<BaseResponse<Account>> call, Response<BaseResponse<Account>> response) {
            // handle the potential 4xx & 5xx error
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Email does not exist" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseResponse<Account> res = response.body();

                if (res.success) {
                    finish();
                    Toast.makeText(mContext, "Welcome", Toast.LENGTH_SHORT).show();
                    loggedAccount = res.payload;
                    moveActivity(mContext, MainActivity.class);
                }
                else{
                    Toast.makeText(mContext, "Email does not exist" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<BaseResponse<Account>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }




}
package com.DanielNikoMJbusIO.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.DanielNikoMJbusIO.jbus_android.model.Account;
import com.DanielNikoMJbusIO.jbus_android.model.BaseResponse;
import com.DanielNikoMJbusIO.jbus_android.request.BaseApiService;
import com.DanielNikoMJbusIO.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private BaseApiService mApiService;
    private Context mContext;
    private EditText name, email, password;
    private Button registerButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        mContext = this;
        mApiService = UtilsApi.getApiService();
        name = findViewById(R.id.RegUsername);
        email = findViewById(R.id.RegEmail);
        password = findViewById(R.id.RegPassword);
        registerButton = findViewById(R.id.RegisterButton);

        registerButton.setOnClickListener(V->handleRegister());
    }
    private void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    protected void handleRegister() {
// handling empty field
        String nameS = name.getText().toString();
        String emailS = email.getText().toString();
        String passwordS = password.getText().toString();
        if (nameS.isEmpty() || emailS.isEmpty() || passwordS.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        mApiService.register(nameS, emailS, passwordS).enqueue(new Callback<BaseResponse<Account>>() {
            @Override
            public void onResponse(Call<BaseResponse<Account>> call, Response<BaseResponse<Account>> response) {
                // handle the potential 4xx & 5xx error
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseResponse<Account> res = response.body();
                if (res.success) finish();
                Toast.makeText(mContext, "Berhasil Register ", Toast.LENGTH_SHORT).show();
                registerButton.setOnClickListener(v -> {moveActivity(mContext, LoginActivity.class);});
                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<BaseResponse<Account>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
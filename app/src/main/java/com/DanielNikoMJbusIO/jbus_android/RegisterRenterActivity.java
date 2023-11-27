package com.DanielNikoMJbusIO.jbus_android;

import static com.DanielNikoMJbusIO.jbus_android.LoginActivity.loggedAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.DanielNikoMJbusIO.jbus_android.model.Account;
import com.DanielNikoMJbusIO.jbus_android.model.BaseResponse;
import com.DanielNikoMJbusIO.jbus_android.model.Renter;
import com.DanielNikoMJbusIO.jbus_android.request.BaseApiService;
import com.DanielNikoMJbusIO.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRenterActivity extends AppCompatActivity {

    private BaseApiService mApiService;
    private Context mContext;
    private EditText ComName, ComAddress, ComPhone;
    private Button registerRenterButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_renter);
        getSupportActionBar().hide();

        mContext = this;
        mApiService = UtilsApi.getApiService();
        ComName = findViewById(R.id.RegCompany);
        ComAddress = findViewById(R.id.RegAdd);
        ComPhone = findViewById(R.id.RegPhoneNum);
        registerRenterButton = findViewById(R.id.RegisterButtonCom);

        registerRenterButton.setOnClickListener(V->handleRegisterRenter());
    }

    private void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    protected void handleRegisterRenter() {
// handling empty field
        String ComNameS = ComName.getText().toString();
        String ComAddressS = ComAddress.getText().toString();
        String ComPhoneS = ComPhone.getText().toString();
        if (ComNameS.isEmpty() || ComAddressS.isEmpty() || ComPhoneS.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        mApiService.registerRenter(loggedAccount.id,ComNameS, ComAddressS, ComPhoneS).enqueue(new Callback<BaseResponse<Renter>>() {
            @Override
            public void onResponse(Call<BaseResponse<Renter>> call, Response<BaseResponse<Renter>> response) {
                // handle the potential 4xx & 5xx error
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseResponse<Renter> res = response.body();
                if (res.success) finish();
                Toast.makeText(mContext, "Berhasil Register Renter ", Toast.LENGTH_SHORT).show();
                moveActivity(mContext, LoginActivity.class);
                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<BaseResponse<Renter>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
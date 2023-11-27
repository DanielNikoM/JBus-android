package com.DanielNikoMJbusIO.jbus_android;

import static com.DanielNikoMJbusIO.jbus_android.LoginActivity.loggedAccount;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.DanielNikoMJbusIO.jbus_android.model.Account;
import com.DanielNikoMJbusIO.jbus_android.model.BaseResponse;
import com.DanielNikoMJbusIO.jbus_android.model.Bus;
import com.DanielNikoMJbusIO.jbus_android.request.BaseApiService;
import com.DanielNikoMJbusIO.jbus_android.request.UtilsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageBusActivity extends AppCompatActivity {
    private BusArrayAdaptor busArrayAdapter;
    private BaseApiService mApiService;
    private Context mContext;
    private ImageView plusButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bus);

        plusButton = findViewById(R.id.plus);
        plusButton.setOnClickListener(v -> {moveActivity(this, AddBusActivity.class);});
        mContext = this;
        mApiService = UtilsApi.getApiService();
        busArrayAdapter = new BusArrayAdaptor(this, Bus.sampleBusList(20),1);
        getMyBuses();
    }
    private void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }
    private void getMyBuses() {
        mApiService.getMyBus(loggedAccount.id).enqueue(new Callback<BaseResponse<List<Bus>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Bus>>> call, Response<BaseResponse<List<Bus>>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Bus tidak ada" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseResponse<List<Bus>> res = response.body();

                if (res.success) {
                    List<Bus> myBuses = res.payload;
                    Toast.makeText(mContext, "Berhasil mendapatkan Bus" + res.message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Gagal mendapatkan Bus" + res.message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Bus>>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

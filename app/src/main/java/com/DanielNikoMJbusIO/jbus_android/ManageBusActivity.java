package com.DanielNikoMJbusIO.jbus_android;

import static com.DanielNikoMJbusIO.jbus_android.LoginActivity.loggedAccount;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ManageBusActivity extends AppCompatActivity {
    private BusArrayAdaptor busArrayAdapter;
    private BaseApiService mApiService;
    private Context mContext;
    private ImageView plusButton;
    private ListView Buslistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bus);

        plusButton = findViewById(R.id.plus);
        plusButton.setOnClickListener(v -> {moveActivity(this, AddBusActivity.class);});
        mContext = this;
        mApiService = UtilsApi.getApiService();
        busArrayAdapter = new BusArrayAdaptor(this, Bus.sampleBusList(20),1);
        Buslistview = findViewById(R.id.list);
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
                List<Bus> myBusList = (List<Bus>) response.body();
                BaseResponse<List<Bus>> res = response.body();
                MyArrayAdapter adapter = new MyArrayAdapter(mContext, myBusList);
                Buslistview.setAdapter(adapter);

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

    private class MyArrayAdapter extends ArrayAdapter<Bus> {

        public MyArrayAdapter(@NonNull Context context, @NonNull List<Bus> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View currentItemView = convertView;

            // of the recyclable view is null then inflate the custom layout for the same
            if (currentItemView == null) {
                currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.bus_view, parent, false);
            }

            // get the position of the view from the ArrayAdapter
            Bus currentBus = getItem(position);

            // then according to the position of the view assign the desired TextView 1 for the same
            TextView busName = currentItemView.findViewById(R.id.Bus_name);
            busName.setText(currentBus.name);

            // then according to the position of the view assign the desired TextView 2 for the same
            ImageView addSched = currentItemView.findViewById(R.id.calendar);
            addSched.setOnClickListener(v->{
                Intent i = new Intent(mContext, ManageBusSchedule.class);
                i.putExtra("busId", currentBus.id);
                mContext.startActivity(i);
            });

            // then return the recyclable view
            return currentItemView;
        }
    }

}

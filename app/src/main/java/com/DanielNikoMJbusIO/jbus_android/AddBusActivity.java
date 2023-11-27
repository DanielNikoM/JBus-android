package com.DanielNikoMJbusIO.jbus_android;

import  androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.DanielNikoMJbusIO.jbus_android.model.Account;
import com.DanielNikoMJbusIO.jbus_android.model.BaseResponse;
import com.DanielNikoMJbusIO.jbus_android.model.BusType;
import com.DanielNikoMJbusIO.jbus_android.model.Facility;
import com.DanielNikoMJbusIO.jbus_android.model.Station;
import com.DanielNikoMJbusIO.jbus_android.request.BaseApiService;
import com.DanielNikoMJbusIO.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBusActivity extends AppCompatActivity {
    private BusType[] busType = BusType.values();
    private BusType selectedBusType;
    private Spinner busTypeSpinner;
    private Spinner departureSpinner;
    private Spinner arrivalSpinner;
    private List<Station> stationList = new ArrayList<>();
    private int selectedDeptStationID;
    private int selectedArrStationID;
    private Context mContext;
    private BaseApiService mApiService;

    private CheckBox acCheckBox, wifiCheckBox, toiletCheckBox, lcdCheckBox;
    private CheckBox coolboxCheckBox, lunchCheckBox, baggageCheckBox, electricCheckBox;
    private Button submitButton;
    private List<Facility> selectedFacilities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bus);
        acCheckBox = findViewById(R.id.AC);
        wifiCheckBox = findViewById(R.id.WIFI);
        toiletCheckBox = findViewById(R.id.TOILET);
        lcdCheckBox = findViewById(R.id.TV);
        coolboxCheckBox = findViewById(R.id.BOX);
        lunchCheckBox = findViewById(R.id.LUNCH);
        baggageCheckBox = findViewById(R.id.BAGGAGE);
        electricCheckBox = findViewById(R.id.ELEC);

        submitButton = findViewById(R.id.ADD);


        selectedFacilities.clear(); // Clear the list before updating
        if (acCheckBox.isChecked()) { selectedFacilities.add(Facility.AC);}
        if (wifiCheckBox.isChecked()) { selectedFacilities.add(Facility.WIFI);}
        if (toiletCheckBox.isChecked()) { selectedFacilities.add(Facility.TOILET);}
        if (lcdCheckBox.isChecked()) { selectedFacilities.add(Facility.LCD_TV);}
        if (coolboxCheckBox.isChecked()) { selectedFacilities.add(Facility.COOL_BOX);}
        if (lunchCheckBox.isChecked()) { selectedFacilities.add(Facility.LUNCH);}
        if (baggageCheckBox.isChecked()) { selectedFacilities.add(Facility.LARGE_BAGGAGE);}
        if (electricCheckBox.isChecked()) { selectedFacilities.add(Facility.ELECTRIC_SOCKET);}



        busTypeSpinner = findViewById(R.id.bus_type_dropdown);
        ArrayAdapter adBus = new ArrayAdapter(this, android.R.layout.simple_list_item_1, busType);
        adBus.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        busTypeSpinner.setAdapter(adBus);

        // Initialize departureSpinner and arrivalSpinner
        departureSpinner = findViewById(R.id.departure_dropdown);
        arrivalSpinner = findViewById(R.id.arrival_dropdown);

        busTypeSpinner.setOnItemSelectedListener(busTypeOISL);
        mContext = this;
        mApiService = UtilsApi.getApiService();

        mApiService.getAllStation().enqueue(new Callback<List<Station>>() {
            @Override
            public void onResponse(Call<List<Station>> call, Response<List<Station>> response) {
                if (response.isSuccessful()) {
                    stationList = response.body();

                    // Populate departureSpinner
                    ArrayAdapter<Station> deptAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, stationList);
                    deptAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                    departureSpinner.setAdapter(deptAdapter);
                    departureSpinner.setOnItemSelectedListener(deptOISL);

                    // Populate arrivalSpinner
                    ArrayAdapter<Station> arrAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, stationList);
                    arrAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                    arrivalSpinner.setAdapter(arrAdapter);
                    arrivalSpinner.setOnItemSelectedListener(arrOISL);
                } else {
                    Toast.makeText(mContext, "Failed to fetch stations: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {
                // Handle failure
                Toast.makeText(mContext, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    AdapterView.OnItemSelectedListener busTypeOISL = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            selectedBusType = busType[position];
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    AdapterView.OnItemSelectedListener deptOISL = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            selectedBusType = busType[position];
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    AdapterView.OnItemSelectedListener arrOISL = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            selectedBusType = busType[position];
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    /*protected void handleADD() {
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
        });*/
}
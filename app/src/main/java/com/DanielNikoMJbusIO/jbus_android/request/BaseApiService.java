package com.DanielNikoMJbusIO.jbus_android.request;
import com.DanielNikoMJbusIO.jbus_android.model.Account;
import com.DanielNikoMJbusIO.jbus_android.model.BaseResponse;
import com.DanielNikoMJbusIO.jbus_android.model.Bus;
import com.DanielNikoMJbusIO.jbus_android.model.BusType;
import com.DanielNikoMJbusIO.jbus_android.model.Facility;
import com.DanielNikoMJbusIO.jbus_android.model.Renter;
import com.DanielNikoMJbusIO.jbus_android.model.Station;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {
    @GET("account/{id}")
    Call<Account> getAccountbyId (@Path("id") int id);

    @POST("account/register")
    Call<BaseResponse<Account>> register (
            @Query("name") String name,
            @Query("email") String email,
            @Query("password") String password
    );

    @POST("account/login")
    Call<BaseResponse<Account>> Login (
            @Query("email") String email,
            @Query("password") String password
    );

    @POST("account/{id}/topUp")
    Call<BaseResponse<Double>> topUp (
            @Path("id") int id,
            @Query("amount") double amount
    );
    @POST("account/{id}/registerRenter")
    Call<BaseResponse<Renter>> registerRenter (
            @Path("id") int id,
            @Query("companyName") String companyName,
            @Query("address") String address,
            @Query("phoneNumber") String phoneNumber
    );

    @GET("bus/getMyBus")
    Call<BaseResponse<List<Bus>>> getMyBus (
            @Query("accountId") int accountId
    );

    @GET("station/getAll")
    Call<List<Station>> getAllStation();

    @POST("bus/create")
    Call<BaseResponse<Bus>> addBus(
            @Query("accountId") int accountId,
            @Query("name") String name,
            @Query("capacity") int capacity,
            @Query("facilities") List<Facility> facilities,
            @Query("busType") BusType busType,
            @Query("price") int price,
            @Query("stationDepartureId") int stationDepartureId,
            @Query("stationArrivalId") int stationArrivalId
    );

    @POST("bus/addSchedule")
    Call<BaseResponse<Bus>> addSchedule(
            @Query("busId") int busId,
            @Query("time") String time
    );

    @GET("bus/{id}")
    Call<Bus> getBusbyId(@Path("id") int busId);
}

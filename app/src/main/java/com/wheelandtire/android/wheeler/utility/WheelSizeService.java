package com.wheelandtire.android.wheeler.utility;

import com.wheelandtire.android.wheeler.model.Vehicle;
import com.wheelandtire.android.wheeler.model.VehicleMake;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WheelSizeService {

    @GET("makes/")
    Call<List<VehicleMake>> getVehicleMake(@Query("user_key") String apiKey);

    @GET("models/")
    Call<List<VehicleMake>> getVehicleModel(@Query("user_key") String apiKey,
                                            @Query("make") String make);

    @GET("years/")
    Call<List<VehicleMake>> getVehicleYear(@Query("user_key") String apiKey,
                                           @Query("make") String make,
                                           @Query("model") String model);

    @GET("trims/")
    Call<List<VehicleMake>> getVehicleTrim(@Query("user_key") String apiKey,
                                           @Query("make") String make,
                                           @Query("model") String model,
                                           @Query("year") String year);

    @GET("vehicles/")
    Call<List<Vehicle>> getVehicle(@Query("user_key") String apiKey,
                                   @Query("make") String make,
                                   @Query("model") String model,
                                   @Query("year") String year,
                                   @Query("trim") String trim);

    @GET("vehicles/")
    Call<List<Vehicle>> getVehicle(@Query("user_key") String apiKey,
                                   @Query("make") String make,
                                   @Query("model") String model,
                                   @Query("year") String year);
}

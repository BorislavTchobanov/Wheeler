package com.wheelandtire.android.wheeler.utility;

import com.wheelandtire.android.wheeler.model.Vehicle;
import com.wheelandtire.android.wheeler.model.VehicleMake;
import com.wheelandtire.android.wheeler.model.Wheel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WheelSizeService {

    @GET("makes/?user_key=ba910526cedac48d05b34c37bbeeb90f")
    public Call<List<VehicleMake>> getVehicleMake();

    @GET("models/?user_key=ba910526cedac48d05b34c37bbeeb90f")
    public Call<List<VehicleMake>> getVehicleModel(@Query("make") String make);

    @GET("years/?user_key=ba910526cedac48d05b34c37bbeeb90f")
    public Call<List<VehicleMake>> getVehicleYear(@Query("make") String make,
                                                  @Query("model") String model);

    @GET("trims/?user_key=ba910526cedac48d05b34c37bbeeb90f")
    public Call<List<VehicleMake>> getVehicleTrim(@Query("make") String make,
                                                  @Query("model") String model,
                                                  @Query("year") String year);

    @GET("vehicles/?user_key=ba910526cedac48d05b34c37bbeeb90f")
    public Call<List<Vehicle>> getVehicle(@Query("make") String make,
                                          @Query("model") String model,
                                          @Query("year") String year,
                                          @Query("trim") String trim);

    @GET("vehicles/?user_key=ba910526cedac48d05b34c37bbeeb90f")
    public Call<List<Vehicle>> getVehicle(@Query("make") String make,
                                          @Query("model") String model,
                                          @Query("year") String year);
}

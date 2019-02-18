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

//    @GET("search/by_model/?make=bmw&model=3-series&trim=328i-iv-e46&year=1998&user_key=ba910526cedac48d05b34c37bbeeb90f")
    @GET("vehicles/?user_key=ba910526cedac48d05b34c37bbeeb90f")
    public Call<List<Vehicle>> getVehicle(@Query("make") String make,
                                          @Query("model") String model,
                                          @Query("year") String year);
}

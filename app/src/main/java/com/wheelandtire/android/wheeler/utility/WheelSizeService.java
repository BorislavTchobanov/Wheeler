package com.wheelandtire.android.wheeler.utility;

import com.wheelandtire.android.wheeler.model.Vehicle;
import com.wheelandtire.android.wheeler.model.VehicleMake;
import com.wheelandtire.android.wheeler.model.Wheel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WheelSizeService {
    @GET("search/by_model/?make=bmw&model=3-series&trim=328i-iv-e46&year=1998&user_key=ba910526cedac48d05b34c37bbeeb90f")
    public Call<List<Vehicle>> getVehicle();

    @GET("makes/?user_key=ba910526cedac48d05b34c37bbeeb90f")
    public Call<List<VehicleMake>> getVehicleMake();
}

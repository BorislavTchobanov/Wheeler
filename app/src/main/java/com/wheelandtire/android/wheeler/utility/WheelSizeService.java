package com.wheelandtire.android.wheeler.utility;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WheelSizeService {
    @GET("search/by_model/?make=bmw&model=3-series&trim=328i-iv-e46&year=1998")
    public Call<WheelSizeResponse> listVehicles();
}

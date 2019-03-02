package com.wheelandtire.android.wheeler.utility;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.wheelandtire.android.wheeler.model.VehicleMake;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleRepository {

    //TODO continue working on that https://medium.com/@amtechnovation/android-architecture-component-mvvm-part-1-a2e7cff07a76

    private WheelSizeService apiService;

    private static class SingletonHelper {
        private static final VehicleRepository INSTANCE = new VehicleRepository();
    }

    public static VehicleRepository getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public VehicleRepository() {
        apiService = RetrofitClientInstance.getRetrofitInstance().create(WheelSizeService.class);
    }

    public LiveData<List<VehicleMake>> getVehicleMake() {
        final MutableLiveData<List<VehicleMake>> data = new MutableLiveData<>();
        apiService.getVehicleMake().enqueue(new Callback<List<VehicleMake>>() {
            @Override
            public void onResponse(Call<List<VehicleMake>> call, Response<List<VehicleMake>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<VehicleMake>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
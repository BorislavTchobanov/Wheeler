package com.wheelandtire.android.wheeler;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.wheelandtire.android.wheeler.database.VehicleProfileDatabase;
import com.wheelandtire.android.wheeler.model.VehicleProfile;

public class VehicleViewModel extends AndroidViewModel {

    private final LiveData<VehicleProfile> vehicleProfile;

    public VehicleViewModel(@NonNull Application application) {
        super(application);
        VehicleProfileDatabase database = VehicleProfileDatabase.getInstance(this.getApplication());
        vehicleProfile = database.vehicleProfileDao().loadAllFavouriteMoves();
    }

    LiveData<VehicleProfile> getVehicleProfile() {
        return vehicleProfile;
    }
}
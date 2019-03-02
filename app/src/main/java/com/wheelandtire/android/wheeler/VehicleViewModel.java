package com.wheelandtire.android.wheeler;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.wheelandtire.android.wheeler.model.VehicleMake;
import com.wheelandtire.android.wheeler.utility.VehicleRepository;

import java.util.List;

public class VehicleViewModel extends AndroidViewModel {

    private LiveData<List<VehicleMake>> vehicleResponseObservable;
    private static final MutableLiveData MUTABLE_LIVE_DATA = new MutableLiveData();

    public VehicleViewModel(@NonNull Application application) {
        super(application);
        vehicleResponseObservable = VehicleRepository.getInstance().getVehicleMake();
    }

    public LiveData<List<VehicleMake>> getVehicleMakes() {
        return vehicleResponseObservable;
    }


//    private
//    private static final MutableLiveData MUTABLE_LIVE_DATA = new MutableLiveData();
//
//    {
//        MUTABLE_LIVE_DATA.setValue(null);
//    }
//
//    public final ObservableField<List<VehicleMake>> project = new ObservableField<>();
//
//    public VehicleViewModel(@NonNull VehicleRepository vehicleRepository,
//                            @NonNull Application application) {
//        super(application);
//        vehicleResponseObservable = VehicleRepository.getInstance().getVehicleMake();
//    }
//
//    public LiveData<List<VehicleMake>> getNewsResponseObservable() {
//        return vehicleResponseObservable;
//    }
}
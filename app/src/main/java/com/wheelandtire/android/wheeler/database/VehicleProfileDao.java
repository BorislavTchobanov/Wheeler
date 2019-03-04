package com.wheelandtire.android.wheeler.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.wheelandtire.android.wheeler.model.VehicleProfile;

import java.util.List;

@Dao
public interface VehicleProfileDao {

//    @Query("SELECT * FROM VehicleProfile ORDER BY id")
//    LiveData<List<VehicleProfile>> loadAllFavouriteMoves();
//
//    @Query("SELECT * FROM VehicleProfile WHERE id=:id")
//    VehicleProfile loadMovieById(int id);

    @Query("SELECT * FROM VehicleProfile")
    LiveData<VehicleProfile> loadAllFavouriteMoves();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveVehicleProfile(VehicleProfile vehicleProfile);

    @Update
    void updateVehicleProfile(VehicleProfile vehicleProfile);

    @Delete
    void deleteVehicleProfile(VehicleProfile vehicleProfile);

}

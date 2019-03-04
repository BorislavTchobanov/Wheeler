package com.wheelandtire.android.wheeler.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.wheelandtire.android.wheeler.model.VehicleProfile;

@Database(entities = {VehicleProfile.class}, version = 1, exportSchema = false)
public abstract class VehicleProfileDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "vehicle_profile";
    private static VehicleProfileDatabase sInstance;

    public static VehicleProfileDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        VehicleProfileDatabase.class,
                        VehicleProfileDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }

    public abstract VehicleProfileDao vehicleProfileDao();
}

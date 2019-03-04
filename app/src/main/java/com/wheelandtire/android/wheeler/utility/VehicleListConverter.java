package com.wheelandtire.android.wheeler.utility;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wheelandtire.android.wheeler.model.Vehicle;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class VehicleListConverter {

    static Gson gson = new Gson();

    @TypeConverter
    public static List<Vehicle> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Vehicle>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<Vehicle> someObjects) {
        return gson.toJson(someObjects);
    }
}
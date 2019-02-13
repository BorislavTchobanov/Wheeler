package com.wheelandtire.android.wheeler.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.wheelandtire.android.wheeler.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class WheelSizeResponse {

    @SerializedName("vehicles")
    List<Vehicle> vehicleList;

//    // public constructor is necessary for collections
//    public WheelSizeResponse() {
//        vehicleList = new ArrayList<Vehicle>();
//    }
//
//    public List<Vehicle> getVehicleList() {
//        return vehicleList;
//    }
//
//    public static WheelSizeResponse parseJSON(String response) {
//        Gson gson = new GsonBuilder().create();
//        WheelSizeResponse wheelSizeResponse = gson.fromJson(response, WheelSizeResponse.class);
//        return wheelSizeResponse;
//    }
}

package com.wheelandtire.android.wheeler;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wheelandtire.android.wheeler.adapter.CustomAdapter;
import com.wheelandtire.android.wheeler.database.VehicleProfileDatabase;
import com.wheelandtire.android.wheeler.model.Vehicle;
import com.wheelandtire.android.wheeler.model.VehicleMake;
import com.wheelandtire.android.wheeler.model.VehicleProfile;
import com.wheelandtire.android.wheeler.utility.RetrofitClientInstance;
import com.wheelandtire.android.wheeler.utility.WheelSizeService;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class VehicleSearch {


    private static final String SHARED_PREFS_FILE = "wheelerPrefs";
    private static final String PREF_VEHICLE_MAKES_LIST = "pref_vehicle_makes_list";
    private Context context;
    ProgressDialog progressDoalog;
    private WheelSizeService service;
    private String make;
    private String model;
    private String year;
    private String trim;
    private List<Vehicle> vehicleList;
    private Spinner spinner;
    private VehicleProfileDatabase vehicleProfileDatabase;
    private View rootView;
    private int numOfDropDowns;
    private VehicleProfile vehicleProfile;

    public VehicleSearch(final Context context, View rootView, int numOfDropDowns){

        this.context = context;
        this.rootView = rootView;
        this.numOfDropDowns = numOfDropDowns;
        progressDoalog = new ProgressDialog(context);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        service = RetrofitClientInstance.getRetrofitInstance().create(WheelSizeService.class);

//        Call<List<VehicleMake>> call = service.getVehicleModel("bmw", "1998");
        Call<List<VehicleMake>> call = makeCall(1, null, null, null);
        List<VehicleMake> vehicleMakeList = getVehicleMakeList(PREF_VEHICLE_MAKES_LIST);
        if (vehicleMakeList == null) {
            makeInitialServiceCall(call, 2);
        } else {
            generateDropDownList(vehicleMakeList, 2);
        }

    }

    private void makeInitialServiceCall(Call<List<VehicleMake>> call, int nextCallNumber) {
        call.enqueue(new Callback<List<VehicleMake>>() {
            @Override
            public void onResponse(@NonNull Call<List<VehicleMake>> call, @NonNull Response<List<VehicleMake>> response) {
                progressDoalog.dismiss();
                if (response.body() != null) {
                    if (nextCallNumber == 2) {
                        saveVehicleMakeList(response.body());
                    }
                    generateDropDownList(response.body(), nextCallNumber);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<VehicleMake>> call, @NonNull Throwable t) {
                progressDoalog.dismiss();
            }
        });
    }

    public void makeFinalServiceCall(Call<List<Vehicle>> call, String profileName) {
        call.enqueue(new Callback<List<Vehicle>>() {
            @Override
            public void onResponse(@NonNull Call<List<Vehicle>> call, @NonNull Response<List<Vehicle>> response) {
                progressDoalog.dismiss();
                vehicleList = response.body();
//                setupRecyclerView(recyclerView);
                saveToProfile(profileName);
                Log.i("TEST","SUCCESS!!! = " + vehicleList);

                //TODO Maybe make observer for vehicleList, to notify when changed (will be used both in profile and fitment)

            }

            @Override
            public void onFailure(@NonNull Call<List<Vehicle>> call, @NonNull Throwable t) {
                progressDoalog.dismiss();
                Log.i("TEST","Something went wrong...Please try later! = " + t);
            }
        });
    }

    public void saveToProfile(String profileName) {
//        String profileName = "ProfileTest";
        String tireWidth = String.valueOf(vehicleList.get(0).getWheels().get(0).getFront().getTireWidth());
        String tireHeight = String.valueOf(vehicleList.get(0).getWheels().get(0).getFront().getTireAspectRatio());
        String rimAndTireDiameter = String.valueOf(vehicleList.get(0).getWheels().get(0).getFront().getRimDiameter());
        String rimWidth = String.valueOf(vehicleList.get(0).getWheels().get(0).getFront().getRimWidth());
        String rimOffset = String.valueOf(vehicleList.get(0).getWheels().get(0).getFront().getRimOffset());

        vehicleProfile = new VehicleProfile(0, profileName, make, model, year,
                trim, tireWidth, tireHeight, rimAndTireDiameter, rimWidth, rimOffset, vehicleList);
        vehicleProfileDatabase = VehicleProfileDatabase.getInstance(context.getApplicationContext());
        AppExecutor.getInstance().discIO().execute(() -> vehicleProfileDatabase
                .vehicleProfileDao().saveVehicleProfile(vehicleProfile));

        SharedPreferences sharedpreferences = context.getSharedPreferences("myWheelerPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("profile_name_key", profileName);
        editor.apply();
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getYear() {
        return year;
    }

    public String getTrim() {
        return trim;
    }

    private void generateDropDownList(List<VehicleMake> vehicleMakeList,
                                      int nextCallNumber) {

        VehicleMake makeHint = new VehicleMake();
        makeHint.setName("Make");
        makeHint.setSlug("Make");
        vehicleMakeList.add(0, makeHint);

        final CustomAdapter dataAdapter = new CustomAdapter(context, android.R.layout.simple_spinner_item, vehicleMakeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, final View view, int i, long l) {
                if (i > 0) {
                    String item = Objects.requireNonNull(dataAdapter.getItem(i)).getSlug();
                    Toast.makeText(context, item, Toast.LENGTH_LONG).show();

//                    if (callNumber > numOfDropDowns) {
//                        trim = item;
//                        Call<List<Vehicle>> call;
//                        if (numOfDropDowns == 3) {
//                            call = service.getVehicle(make, model, year);
//                        } else {
//                            call = service.getVehicle(make, model, year, item);
//                        }
//                        makeFinalServiceCall(call);
//                    } else {
                        if (nextCallNumber <= numOfDropDowns) {
                            Call<List<VehicleMake>> listCall = makeCall(nextCallNumber, item, item, item);
                            makeInitialServiceCall(listCall, nextCallNumber + 1);
                        } else {
                            trim = item;
                        }
//                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
    }

    private Call<List<VehicleMake>> makeCall(int caseIndex, String make, String model, String year) {
        Call<List<VehicleMake>> call = null;
        switch (caseIndex) {
            case 1:
                spinner = rootView.findViewById(R.id.spinnerMake);
                call = service.getVehicleMake();
                break;
            case 2:
                spinner = rootView.findViewById(R.id.spinnerModel);
                this.make = make;
                call = service.getVehicleModel(make);
                break;
            case 3:
                spinner = rootView.findViewById(R.id.spinnerYear);
                this.model = model;
                call = service.getVehicleYear(this.make, model);
                break;
            case 4:
                spinner = rootView.findViewById(R.id.spinnerTrim);
                this.year = year;
                call = service.getVehicleTrim(this.make, this.model, year);
                break;
            default:
        }
        return call;
    }

    public void saveVehicleMakeList(List<VehicleMake> vehicleMakeList) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(vehicleMakeList);
        editor.putString(PREF_VEHICLE_MAKES_LIST, json);
        editor.apply();
    }

    public ArrayList<VehicleMake> getVehicleMakeList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<List<VehicleMake>>() {}.getType();
        return gson.fromJson(json, type);
    }
}

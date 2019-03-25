package com.wheelandtire.android.wheeler;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wheelandtire.android.wheeler.adapter.SpinnerAdapter;
import com.wheelandtire.android.wheeler.model.VehicleMake;
import com.wheelandtire.android.wheeler.utility.RetrofitClientInstance;
import com.wheelandtire.android.wheeler.utility.WheelSizeService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


class VehicleSearch {

    private static final String PREF_VEHICLE_MAKES_LIST = "pref_vehicle_makes_list";

    private Context context;
    private ProgressDialog progressDialog;
    private WheelSizeService service;
    private String make;
    private String model;
    private String year;
    private String trim;
    private List<VehicleMake> vehicleList;
    private Spinner spinner;
    private View rootView;
    private int numOfDropDowns;

    VehicleSearch(final Context context, View rootView, int numOfDropDowns) {

        this.context = context;
        this.rootView = rootView;
        this.numOfDropDowns = numOfDropDowns;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        service = RetrofitClientInstance.getRetrofitInstance().create(WheelSizeService.class);

        Call<List<VehicleMake>> call = makeCall(1, null, null, null);
        List<VehicleMake> vehicleMakeList = getVehicleMakeList();
        if (vehicleMakeList == null) {
            makeInitialServiceCall(call, 2);
        } else {
            generateDropDownList(vehicleMakeList, 2);
            dismissProgressDialog();
        }

    }

    private void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void makeInitialServiceCall(Call<List<VehicleMake>> call, int nextCallNumber) {
        progressDialog.show();
        call.enqueue(new Callback<List<VehicleMake>>() {
            @Override
            public void onResponse(@NonNull Call<List<VehicleMake>> call, @NonNull Response<List<VehicleMake>> response) {
                dismissProgressDialog();
                if (response.body() != null) {
                    if (nextCallNumber == 2) {
                        saveVehicleMakeList(response.body());
                    }
                    vehicleList = response.body();
                    generateDropDownList(vehicleList, nextCallNumber);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<VehicleMake>> call, @NonNull Throwable t) {
                Toast.makeText(context,
                        R.string.service_call_failure_message,
                        Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    String getMake() {
        return make;
    }

    String getModel() {
        return model;
    }

    String getYear() {
        return year;
    }

    String getTrim() {
        return trim;
    }

    private VehicleMake makeHint(int currentDropdown) {
        VehicleMake makeHint = new VehicleMake();

        switch (currentDropdown) {
            case 1:
                makeHint.setName(context.getString(R.string.hint_text_make));
                break;
            case 2:
                makeHint.setName(context.getString(R.string.hint_text_model));
                break;
            case 3:
                makeHint.setName(context.getString(R.string.hint_text_year));
                break;
            case 4:
                makeHint.setName(context.getString(R.string.hint_text_trim));
                break;
            default:
                makeHint.setName(context.getString(R.string.hint_text_make));
        }
        return makeHint;
    }

    private void generateDropDownList(List<VehicleMake> vehicleMakeList,
                                      int nextCallNumber) {

        vehicleMakeList.add(0, makeHint(nextCallNumber - 1));

        final SpinnerAdapter dataAdapter = new SpinnerAdapter(context, android.R.layout.simple_spinner_item, vehicleMakeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, final View view, int i, long l) {
                if (i > 0) {
                    String item = Objects.requireNonNull(dataAdapter.getItem(i)).getSlug();

                    if (nextCallNumber <= numOfDropDowns) {
                        Call<List<VehicleMake>> listCall = makeCall(nextCallNumber, item, item, item);
                        makeInitialServiceCall(listCall, nextCallNumber + 1);
                    } else {
                        trim = item;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
    }

    int getCurrentSpinnerPosition() {
        if (spinner.getId() == R.id.spinnerTrim) {
            return 1;
        }
        return spinner.getSelectedItemPosition();
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

    void requiredFiled() {
        TextView errorText = (TextView) spinner.getSelectedView();
        if (errorText == null) {
            return;
        }
        errorText.setError(context.getString(R.string.field_required_error_text));
        errorText.setTextColor(Color.RED);//just to highlight that this is an error
        errorText.setText(context.getString(R.string.field_required_error_text));
    }

    private void saveVehicleMakeList(List<VehicleMake> vehicleMakeList) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(vehicleMakeList);
        editor.putString(PREF_VEHICLE_MAKES_LIST, json);
        editor.apply();
    }

    private ArrayList<VehicleMake> getVehicleMakeList() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(PREF_VEHICLE_MAKES_LIST, null);
        Type type = new TypeToken<List<VehicleMake>>() {
        }.getType();
        return gson.fromJson(json, type);
    }
}

package com.wheelandtire.android.wheeler.utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.wheelandtire.android.wheeler.FitmentActivity;
import com.wheelandtire.android.wheeler.R;
import com.wheelandtire.android.wheeler.adapter.CustomAdapter;
import com.wheelandtire.android.wheeler.model.Vehicle;
import com.wheelandtire.android.wheeler.model.VehicleMake;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleSearch {


    private static ProgressDialog progressDoalog;
    private static WheelSizeService service;
    private static boolean isInitCall = true;
    private static View rootView;
    private static String make;
    private static String model;
    private static List<Vehicle> vehicleList;
    private static List<VehicleMake> vehicleListTest;

    public static void testRetrofit(final Context context) {

        progressDoalog = new ProgressDialog(context);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        /*Create handle for the RetrofitClientInstance interface*/
        service = RetrofitClientInstance.getRetrofitInstance().create(WheelSizeService.class);
//        Call<List<VehicleMake>> call = service.getVehicleModel("bmw", "1998");
        Call<List<VehicleMake>> call = service.getVehicleMake();
        call.enqueue(new Callback<List<VehicleMake>>() {
            @Override
            public void onResponse(Call<List<VehicleMake>> call, Response<List<VehicleMake>> response) {
                progressDoalog.dismiss();
//                generateDataList(response.body());
                if (isInitCall && response.body() != null) {
                    generateMakeDropDownList(response.body(), context, rootView);
                    isInitCall = false;
                }
            }

            @Override
            public void onFailure(Call<List<VehicleMake>> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static List<VehicleMake> generateDropDownList(final Context context, List<VehicleMake> vehicleMakeList, Spinner spinner, final Call<List<VehicleMake>> call) {

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
                    make = Objects.requireNonNull(dataAdapter.getItem(i)).getName();
                    Toast.makeText(context, make, Toast.LENGTH_LONG).show();

//                    Call<List<VehicleMake>> call = service.getVehicleModel(make);
                    call.enqueue(new Callback<List<VehicleMake>>() {
                        @Override
                        public void onResponse(Call<List<VehicleMake>> call, Response<List<VehicleMake>> response) {
                            progressDoalog.dismiss();
                            generateModelDropDownList(response.body(), context, rootView);
                            vehicleListTest = response.body();
                        }

                        @Override
                        public void onFailure(Call<List<VehicleMake>> call, Throwable t) {
                            progressDoalog.dismiss();
                            Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        return vehicleListTest;
    }

    private static void generateMakeDropDownList(final List<VehicleMake> vehicleMakeList, final Context context, final View rootView) {
        // Spinner element
        final Spinner spinner = (Spinner) ((Activity) context).findViewById(R.id.spinnerMake);
        // Spinner click listener
//        spinner.setOnItemSelectedListener(this);

        VehicleMake makeHint = new VehicleMake();
        makeHint.setName("Make");
        makeHint.setSlug("Make");
        vehicleMakeList.add(0, makeHint);
        // Creating adapter for spinner
        final CustomAdapter dataAdapter = new CustomAdapter(context, android.R.layout.simple_spinner_item, vehicleMakeList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

//        spinner.setSelection(0,false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, final View view, int i, long l) {
                if (i > 0) {
                    make = Objects.requireNonNull(dataAdapter.getItem(i)).getName();
                    Toast.makeText(context, make, Toast.LENGTH_LONG).show();

                    Call<List<VehicleMake>> call = service.getVehicleModel(make);
                    call.enqueue(new Callback<List<VehicleMake>>() {
                        @Override
                        public void onResponse(Call<List<VehicleMake>> call, Response<List<VehicleMake>> response) {
                            progressDoalog.dismiss();
                            generateModelDropDownList(response.body(), context, rootView);
                        }

                        @Override
                        public void onFailure(Call<List<VehicleMake>> call, Throwable t) {
                            progressDoalog.dismiss();
                            Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
    }

    private static void generateModelDropDownList(List<VehicleMake> vehicleModelList, final Context context, final View rootView) {
        // Spinner element
        Spinner spinner = (Spinner) ((Activity) context).findViewById(R.id.spinnerModel);
        // Spinner click listener
//        spinner.setOnItemSelectedListener(this);
        VehicleMake modelHint = new VehicleMake();
        modelHint.setName("Model");
        modelHint.setSlug("Model");
        vehicleModelList.add(0, modelHint);
        // Creating adapter for spinner
        final CustomAdapter dataAdapter = new CustomAdapter(context, android.R.layout.simple_spinner_item, vehicleModelList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

//        spinner.setSelection(0,false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    final String model = Objects.requireNonNull(dataAdapter.getItem(i)).getSlug();
                    Toast.makeText(context, model, Toast.LENGTH_LONG).show();

                    Call<List<VehicleMake>> call = service.getVehicleYear(make, model);
                    call.enqueue(new Callback<List<VehicleMake>>() {
                        @Override
                        public void onResponse(Call<List<VehicleMake>> call, Response<List<VehicleMake>> response) {
                            progressDoalog.dismiss();
//                generateDataList(response.body());
                            generateYearDropDownList(response.body(), model, context, rootView);
                        }

                        @Override
                        public void onFailure(Call<List<VehicleMake>> call, Throwable t) {
                            progressDoalog.dismiss();
                            Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
    }

    private static List<Vehicle> generateYearDropDownList(final List<VehicleMake> vehicleYearList, final String model, final Context context, View rootView) {
        // Spinner element
        Spinner spinner = (Spinner) ((Activity) context).findViewById(R.id.spinnerYear);
        // Spinner click listener
//        spinner.setOnItemSelectedListener(this);
        VehicleMake yearHint = new VehicleMake();
        yearHint.setName("Year");
        yearHint.setSlug("Year");
        vehicleYearList.add(0, yearHint);
        // Creating adapter for spinner
        final CustomAdapter dataAdapter = new CustomAdapter(context, android.R.layout.simple_spinner_item, vehicleYearList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    String year = Objects.requireNonNull(dataAdapter.getItem(i)).getSlug();
                    Toast.makeText(context, year, Toast.LENGTH_LONG).show();

                    Call<List<Vehicle>> call = service.getVehicle(make, model, year);
                    call.enqueue(new Callback<List<Vehicle>>() {
                        @Override
                        public void onResponse(Call<List<Vehicle>> call, Response<List<Vehicle>> response) {
                            progressDoalog.dismiss();
                            vehicleList = response.body();
//                            setupRecyclerView(recyclerView);
                            Log.i("TEST","SUCCESS!!! = " + vehicleList);

                        }

                        @Override
                        public void onFailure(Call<List<Vehicle>> call, Throwable t) {
                            progressDoalog.dismiss();
                            Log.i("TEST","Something went wrong...Please try later! = " + t);
                            Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
        return vehicleList;
    }
}

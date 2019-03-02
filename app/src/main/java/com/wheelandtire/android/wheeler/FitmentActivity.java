package com.wheelandtire.android.wheeler;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.wheelandtire.android.wheeler.adapter.CustomAdapter;
import com.wheelandtire.android.wheeler.adapter.FitmentAdapter;
import com.wheelandtire.android.wheeler.model.Vehicle;
import com.wheelandtire.android.wheeler.model.VehicleMake;
import com.wheelandtire.android.wheeler.utility.RetrofitClientInstance;
import com.wheelandtire.android.wheeler.utility.VehicleSearch;
import com.wheelandtire.android.wheeler.utility.WheelSizeService;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FitmentActivity extends AppCompatActivity
        implements FitmentAdapter.ListItemClickListener {

    public static final String EXTRA_VEHICLE = "extra_vehicle";
    public static final String EXTRA_CURRENT_TRIM_INDEX = "extra_current_trim_index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, calculateNoOfColumns(this));
        recyclerView.setLayoutManager(gridLayoutManager);

//        testRetrofit();
//        VehicleSearch.testRetrofit(this);
//        vehicleList = response.body();
//        setupRecyclerView(recyclerView);

        final VehicleViewModel viewModel = ViewModelProviders.of(this).get(VehicleViewModel.class);
        viewModel.getVehicleMakes().observe(this, vehicleMakesResponse -> {
                    if (vehicleMakesResponse != null) {
                        String test = vehicleMakesResponse.get(0).getName();
                    }
                });
    }

    private static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;

        return (int) (dpWidth / scalingFactor);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new FitmentAdapter(vehicleList, this));
    }

    private RecyclerView recyclerView;
    ProgressDialog progressDoalog;
    private WheelSizeService service;
    private String make;
    private boolean isInitCall = true;
    private List<Vehicle> vehicleList;



//    public void testRetrofit() {
//
//        progressDoalog = new ProgressDialog(FitmentActivity.this);
//        progressDoalog.setMessage("Loading....");
//        progressDoalog.show();
//
//        /*Create handle for the RetrofitClientInstance interface*/
//        service = RetrofitClientInstance.getRetrofitInstance().create(WheelSizeService.class);
////        Call<List<VehicleMake>> call = service.getVehicleModel("bmw", "1998");
//        Call<List<VehicleMake>> call = service.getVehicleMake();
//        call.enqueue(new Callback<List<VehicleMake>>() {
//            @Override
//            public void onResponse(Call<List<VehicleMake>> call, Response<List<VehicleMake>> response) {
//                progressDoalog.dismiss();
////                generateDataList(response.body());
//                if (isInitCall && response.body() != null) {
//                    generateMakeDropDownList(response.body());
//                    isInitCall = false;
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<VehicleMake>> call, Throwable t) {
//                progressDoalog.dismiss();
//                Toast.makeText(FitmentActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void generateMakeDropDownList(final List<VehicleMake> vehicleMakeList) {
//        // Spinner element
//        final Spinner spinner = (Spinner) findViewById(R.id.spinnerMake);
//        // Spinner click listener
////        spinner.setOnItemSelectedListener(this);
//        VehicleSearch.
//
//        VehicleMake makeHint = new VehicleMake();
//        makeHint.setName("Make");
//        makeHint.setSlug("Make");
//        vehicleMakeList.add(0, makeHint);
//        // Creating adapter for spinner
//        final CustomAdapter dataAdapter = new CustomAdapter(this, android.R.layout.simple_spinner_item, vehicleMakeList);
//
//        // Drop down layout style - list view with radio button
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // attaching data adapter to spinner
//        spinner.setAdapter(dataAdapter);
//
////        spinner.setSelection(0,false);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (i > 0) {
//                    make = Objects.requireNonNull(dataAdapter.getItem(i)).getName();
//                    Toast.makeText(getApplicationContext(), make, Toast.LENGTH_LONG).show();
//
//                    Call<List<VehicleMake>> call = service.getVehicleModel(make);
//                    call.enqueue(new Callback<List<VehicleMake>>() {
//                        @Override
//                        public void onResponse(Call<List<VehicleMake>> call, Response<List<VehicleMake>> response) {
//                            progressDoalog.dismiss();
//                            generateModelDropDownList(response.body());
//                        }
//
//                        @Override
//                        public void onFailure(Call<List<VehicleMake>> call, Throwable t) {
//                            progressDoalog.dismiss();
//                            Toast.makeText(FitmentActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                // DO Nothing here
//            }
//        });
//    }
//
//    private void generateModelDropDownList(List<VehicleMake> vehicleModelList) {
//        // Spinner element
//        Spinner spinner = (Spinner) findViewById(R.id.spinnerModel);
//        // Spinner click listener
////        spinner.setOnItemSelectedListener(this);
//        VehicleMake modelHint = new VehicleMake();
//        modelHint.setName("Model");
//        modelHint.setSlug("Model");
//        vehicleModelList.add(0, modelHint);
//        // Creating adapter for spinner
//        final CustomAdapter dataAdapter = new CustomAdapter(this, android.R.layout.simple_spinner_item, vehicleModelList);
//
//        // Drop down layout style - list view with radio button
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // attaching data adapter to spinner
//        spinner.setAdapter(dataAdapter);
//
////        spinner.setSelection(0,false);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (i > 0) {
//                    final String model = Objects.requireNonNull(dataAdapter.getItem(i)).getSlug();
//                    Toast.makeText(getApplicationContext(), model, Toast.LENGTH_LONG).show();
//
//                    Call<List<VehicleMake>> call = service.getVehicleYear(make, model);
//                    call.enqueue(new Callback<List<VehicleMake>>() {
//                        @Override
//                        public void onResponse(Call<List<VehicleMake>> call, Response<List<VehicleMake>> response) {
//                            progressDoalog.dismiss();
////                generateDataList(response.body());
//                            generateYearDropDownList(response.body(), model);
//                        }
//
//                        @Override
//                        public void onFailure(Call<List<VehicleMake>> call, Throwable t) {
//                            progressDoalog.dismiss();
//                            Toast.makeText(FitmentActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                // DO Nothing here
//            }
//        });
//    }
//
//    private void generateYearDropDownList(final List<VehicleMake> vehicleYearList, final String model) {
//        // Spinner element
//        Spinner spinner = (Spinner) findViewById(R.id.spinnerYear);
//        // Spinner click listener
////        spinner.setOnItemSelectedListener(this);
//        VehicleMake yearHint = new VehicleMake();
//        yearHint.setName("Year");
//        yearHint.setSlug("Year");
//        vehicleYearList.add(0, yearHint);
//        // Creating adapter for spinner
//        final CustomAdapter dataAdapter = new CustomAdapter(this, android.R.layout.simple_spinner_item, vehicleYearList);
//
//        // Drop down layout style - list view with radio button
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // attaching data adapter to spinner
//        spinner.setAdapter(dataAdapter);
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (i > 0) {
//                    String year = Objects.requireNonNull(dataAdapter.getItem(i)).getSlug();
//                    Toast.makeText(getApplicationContext(), year, Toast.LENGTH_LONG).show();
//
//                    Call<List<Vehicle>> call = service.getVehicle(make, model, year);
//                    call.enqueue(new Callback<List<Vehicle>>() {
//                        @Override
//                        public void onResponse(Call<List<Vehicle>> call, Response<List<Vehicle>> response) {
//                            progressDoalog.dismiss();
//                            vehicleList = response.body();
//                            setupRecyclerView(recyclerView);
//                            Log.i("TEST","SUCCESS!!! = " + vehicleList);
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<List<Vehicle>> call, Throwable t) {
//                            progressDoalog.dismiss();
//                            Log.i("TEST","Something went wrong...Please try later! = " + t);
//                            Toast.makeText(FitmentActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                // DO Nothing here
//            }
//        });
//    }

//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
//        if (mTwoPane) {
//            Bundle arguments = new Bundle();
//            arguments.putSerializable(EXTRA_RECIPE, recipe);
//            arguments.putSerializable(EXTRA_STEP, step);
//            arguments.putInt(EXTRA_CURRENT_STEP_INDEX, clickedItemIndex);
//            arguments.putBoolean(EXTRA_TWO_PANE, mTwoPane);
//
//            StepDetailFragment fragment = new StepDetailFragment();
//            fragment.setArguments(arguments);
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.recipe_detail_container, fragment)
//                    .commit();
//        } else {
            Intent intent = new Intent(this, FitmentDetailActivity.class);
            intent.putExtra(EXTRA_VEHICLE, vehicleList.get(clickedItemIndex));
            intent.putExtra(EXTRA_CURRENT_TRIM_INDEX, clickedItemIndex);

            startActivity(intent);
//        }
    }
}
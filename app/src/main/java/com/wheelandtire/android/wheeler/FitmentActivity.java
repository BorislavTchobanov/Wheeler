package com.wheelandtire.android.wheeler;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;

import com.wheelandtire.android.wheeler.adapter.FitmentAdapter;
import com.wheelandtire.android.wheeler.model.Vehicle;
import com.wheelandtire.android.wheeler.model.VehicleProfile;
import com.wheelandtire.android.wheeler.utility.RetrofitClientInstance;
import com.wheelandtire.android.wheeler.utility.WheelSizeService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.wheelandtire.android.wheeler.MainActivity.PROFILE_NAME_KEY;
import static com.wheelandtire.android.wheeler.MainActivity.PROFILE_NAME_PREFS;

public class FitmentActivity extends AppCompatActivity
        implements FitmentAdapter.ListItemClickListener {

    public static final String EXTRA_VEHICLE = "extra_vehicle";
    public static final String EXTRA_CURRENT_TRIM_INDEX = "extra_current_trim_index";

    private RecyclerView recyclerView;
    private List<Vehicle> vehicleList;
    private WheelSizeService service;
    private Button goButton;
    private VehicleSearch vehicleSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Wheel&Tire");

        goButton = findViewById(R.id.goButton);

        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, calculateNoOfColumns());
        recyclerView.setLayoutManager(gridLayoutManager);
//        progressDoalog = new ProgressDialog(this);
//        progressDoalog.setMessage("Loading....");
//        progressDoalog.show();

        service = RetrofitClientInstance.getRetrofitInstance().create(WheelSizeService.class);
//        testRetrofit();
        vehicleSearch = new VehicleSearch(this, getWindow().getDecorView().getRootView(), 4);
        retrieveVehicleProfile();

    }

    private void retrieveVehicleProfile() {
        VehicleViewModel viewModel = ViewModelProviders.of(this).get(VehicleViewModel.class);
        viewModel.getVehicleProfile().observe(this, this::populateViews);
    }

    private void populateViews(VehicleProfile vehicleProfile) {

        if (vehicleProfile != null) {
            vehicleList = vehicleProfile.getVehicleList();
            setupRecyclerView(recyclerView);
        }

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkRequiredFileds()) {
                    Call<List<Vehicle>> call = service.getVehicle(vehicleSearch.getMake(), vehicleSearch.getModel(),
                            vehicleSearch.getYear(), vehicleSearch.getTrim());
                    makeFinalServiceCall(call);
                }
            }
        });

    }

    private boolean checkRequiredFileds() {
        int pos = vehicleSearch.getCurrentSpinnerPosition();
        if (pos == 0 || vehicleSearch.getMake() == null || vehicleSearch.getModel() == null || vehicleSearch.getYear() == null) {
            vehicleSearch.requiredFiled();
            return false;
        }

        return true;
    }

    private void makeFinalServiceCall(Call<List<Vehicle>> call) {
        call.enqueue(new Callback<List<Vehicle>>() {
            @Override
            public void onResponse(@NonNull Call<List<Vehicle>> call, @NonNull Response<List<Vehicle>> response) {
                vehicleList = response.body();
                setupRecyclerView(recyclerView);
                Log.i("TEST","SUCCESS!!! = " + vehicleList);
            }

            @Override
            public void onFailure(@NonNull Call<List<Vehicle>> call, @NonNull Throwable t) {
                Log.i("TEST","Something went wrong...Please try later! = " + t);
            }
        });
    }

    private int calculateNoOfColumns() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;

        return (int) (dpWidth / scalingFactor);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new FitmentAdapter(vehicleList, this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_button, menu);
        SharedPreferences sharedPreferences = getSharedPreferences(PROFILE_NAME_PREFS, Context.MODE_PRIVATE);
        String profileName = sharedPreferences.getString(PROFILE_NAME_KEY, getString(R.string.profile_name_default_value));
        menu.getItem(0).setTitle(profileName);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
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
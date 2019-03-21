package com.wheelandtire.android.wheeler;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wheelandtire.android.wheeler.database.VehicleProfileDatabase;
import com.wheelandtire.android.wheeler.model.Vehicle;
import com.wheelandtire.android.wheeler.model.VehicleProfile;
import com.wheelandtire.android.wheeler.utility.RetrofitClientInstance;
import com.wheelandtire.android.wheeler.utility.WheelSizeService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileActivity extends AppCompatActivity {

    private VehicleViewModel viewModel;
    private VehicleSearch vehicleSearch;
    private WheelSizeService service;
    private EditText profileNameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.vehicle_profile_title);

        profileNameTv = findViewById(R.id.profileName);
//        SharedPreferences sharedpreferences = getSharedPreferences("myWheelerPrefs", Context.MODE_PRIVATE);
//        String profileName = sharedpreferences.getString("profile_name_key", "notfound");
//        if (!profileName.equals("notfound")) {
//            profileNameTv.setText(profileName);
//        }

        viewModel = ViewModelProviders.of(this).get(VehicleViewModel.class);

        service = RetrofitClientInstance.getRetrofitInstance().create(WheelSizeService.class);
        vehicleSearch = new VehicleSearch(this, getWindow().getDecorView().getRootView(), 4);
        retrieveVehicleProfile();
//        testRetrofit();
    }

    private void retrieveVehicleProfile() {
        viewModel.getVehicleProfile().observe(this, this::populateViews);
    }

    private void populateViews(VehicleProfile vehicleProfile) {
        EditText rimDiameter = findViewById(R.id.rimDiameterView);
        EditText rimWidth = findViewById(R.id.rimWidthView);
        EditText rimOffset = findViewById(R.id.rimOffsetView);
        EditText tireWidth = findViewById(R.id.tireWidthView);
        EditText tireHeight = findViewById(R.id.tireHeightView);
        EditText tireDiameter = findViewById(R.id.tireDiameterView);

        if (vehicleProfile != null) {

//            vehicleSearch.populateVehicleSpinners(vehicleProfile);
            profileNameTv.setText(vehicleProfile.getProfileName());
            String tireAndRimDiameter = vehicleProfile.getProfileTireAndRimDiameter();
            rimDiameter.setText(tireAndRimDiameter);
            rimWidth.setText(vehicleProfile.getProfileRimWidth());
            rimOffset.setText(vehicleProfile.getProfileRimOffset());
            tireWidth.setText(vehicleProfile.getProfileTireWidth());
            tireHeight.setText(vehicleProfile.getProfileTireHeight());
            tireDiameter.setText(tireAndRimDiameter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_toolbar_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_settings:
                if(!checkRequiredFileds(profileNameTv.getText().toString())) {
                    break;
                } else {
                    Call<List<Vehicle>> call = service.getVehicle(vehicleSearch.getMake(), vehicleSearch.getModel(),
                            vehicleSearch.getYear(), vehicleSearch.getTrim());

                    makeFinalServiceCall(call, profileNameTv.getText().toString());
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<Vehicle> vehicleList;


    public void makeFinalServiceCall(Call<List<Vehicle>> call, String profileName) {
        call.enqueue(new Callback<List<Vehicle>>() {
            @Override
            public void onResponse(@NonNull Call<List<Vehicle>> call, @NonNull Response<List<Vehicle>> response) {
//                if (progressDoalog != null) {
//                    progressDoalog.dismiss();
//                }
                vehicleList = response.body();
                saveToProfile(profileName);

                Log.i("TEST","SUCCESS!!! = " + vehicleList);

                //TODO Maybe make observer for vehicleList, to notify when changed (will be used both in profile and fitment)

            }

            @Override
            public void onFailure(@NonNull Call<List<Vehicle>> call, @NonNull Throwable t) {
//                progressDoalog.dismiss();
                Log.i("TEST","Something went wrong...Please try later! = " + t);
            }
        });
    }

    private boolean checkRequiredFileds(String profileName) {
        if (profileName.isEmpty()) {
            profileNameTv.setError("Profile Name is required!");
            return false;
        }
        if (vehicleSearch.getMake() == null || vehicleSearch.getModel() == null || vehicleSearch.getYear() == null) {
            vehicleSearch.requiredFiled();
            return false;
        }

        return true;
    }

    public void saveToProfile(String profileName) {
        String tireWidth = String.valueOf(vehicleList.get(0).getWheels().get(0).getFront().getTireWidth());
        String tireHeight = String.valueOf(vehicleList.get(0).getWheels().get(0).getFront().getTireAspectRatio());
        String rimAndTireDiameter = String.valueOf(vehicleList.get(0).getWheels().get(0).getFront().getRimDiameter());
        String rimWidth = String.valueOf(vehicleList.get(0).getWheels().get(0).getFront().getRimWidth());
        String rimOffset = String.valueOf(vehicleList.get(0).getWheels().get(0).getFront().getRimOffset());

        VehicleProfile vehicleProfile = new VehicleProfile(0, profileName,
                vehicleSearch.getMake(),
                vehicleSearch.getModel(),
                vehicleSearch.getYear(),
                vehicleSearch.getTrim(), tireWidth, tireHeight, rimAndTireDiameter, rimWidth, rimOffset, vehicleList);
        VehicleProfileDatabase vehicleProfileDatabase = VehicleProfileDatabase.getInstance(getApplicationContext());
        AppExecutor.getInstance().discIO().execute(() -> vehicleProfileDatabase
                .vehicleProfileDao().saveVehicleProfile(vehicleProfile));

        SharedPreferences sharedpreferences = getSharedPreferences("myWheelerPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("profile_name_key", profileName);
        editor.apply();
    }

}
package com.wheelandtire.android.wheeler;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wheelandtire.android.wheeler.database.VehicleProfileDatabase;
import com.wheelandtire.android.wheeler.model.Vehicle;
import com.wheelandtire.android.wheeler.model.VehicleProfile;
import com.wheelandtire.android.wheeler.utility.RetrofitClientInstance;
import com.wheelandtire.android.wheeler.utility.WheelSizeService;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.wheelandtire.android.wheeler.MainActivity.PROFILE_NAME_KEY;
import static com.wheelandtire.android.wheeler.MainActivity.PROFILE_NAME_PREFS;


public class ProfileActivity extends AppCompatActivity {

    private VehicleViewModel viewModel;
    private VehicleSearch vehicleSearch;
    private WheelSizeService service;
    private EditText profileNameTv;
    private List<Vehicle> vehicleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.vehicle_profile_title);

        if (!getResources().getBoolean(R.bool.isTablet)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }

        profileNameTv = findViewById(R.id.profileName);
        viewModel = ViewModelProviders.of(this).get(VehicleViewModel.class);
        service = RetrofitClientInstance.getRetrofitInstance().create(WheelSizeService.class);
        vehicleSearch = new VehicleSearch(this, getWindow().getDecorView().getRootView(), 4);
        retrieveVehicleProfile();
    }

    private void retrieveVehicleProfile() {
        viewModel.getVehicleProfile().observe(this, this::populateViews);
    }

    private void populateViews(VehicleProfile vehicleProfile) {
        TextView rimDiameter = findViewById(R.id.rimDiameterView);
        TextView rimWidth = findViewById(R.id.rimWidthView);
        TextView rimOffset = findViewById(R.id.rimOffsetView);
        TextView tireWidth = findViewById(R.id.tireWidthView);
        TextView tireHeight = findViewById(R.id.tireHeightView);
        TextView tireDiameter = findViewById(R.id.tireDiameterView);

        if (vehicleProfile != null) {
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
        getMenuInflater().inflate(R.menu.toolbar_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_toolbar_button:
                if (!checkRequiredFields(profileNameTv.getText().toString())) {
                    break;
                } else {
                    Call<List<Vehicle>> call = service.getVehicle(vehicleSearch.getMake(),
                            vehicleSearch.getModel(),
                            vehicleSearch.getYear(),
                            vehicleSearch.getTrim());

                    makeFinalServiceCall(call, profileNameTv.getText().toString());
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void makeFinalServiceCall(Call<List<Vehicle>> call, String profileName) {
        call.enqueue(new Callback<List<Vehicle>>() {
            @Override
            public void onResponse(@NonNull Call<List<Vehicle>> call, @NonNull Response<List<Vehicle>> response) {
                vehicleList = response.body();
                saveToProfile(profileName);
            }

            @Override
            public void onFailure(@NonNull Call<List<Vehicle>> call, @NonNull Throwable t) {
                Toast.makeText(ProfileActivity.this,
                        R.string.service_call_failure_message,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkRequiredFields(String profileName) {
        if (profileName.isEmpty()) {
            profileNameTv.setError(getString(R.string.profile_name_field_required));
            return false;
        }
        int pos = vehicleSearch.getCurrentSpinnerPosition();
        if (pos == 0
                || vehicleSearch.getMake() == null
                || vehicleSearch.getModel() == null
                || vehicleSearch.getYear() == null) {
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

        VehicleProfile vehicleProfile = new VehicleProfile(0,
                profileName,
                vehicleSearch.getMake(),
                vehicleSearch.getModel(),
                vehicleSearch.getYear(),
                vehicleSearch.getTrim(),
                tireWidth,
                tireHeight,
                rimAndTireDiameter,
                rimWidth,
                rimOffset,
                vehicleList);

        VehicleProfileDatabase vehicleProfileDatabase = VehicleProfileDatabase.getInstance(getApplicationContext());
        AppExecutor.getInstance().discIO().execute(() -> vehicleProfileDatabase
                .vehicleProfileDao().saveVehicleProfile(vehicleProfile));

        SharedPreferences sharedpreferences = getSharedPreferences(PROFILE_NAME_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(PROFILE_NAME_KEY, profileName);
        editor.apply();
    }

}
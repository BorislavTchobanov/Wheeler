package com.wheelandtire.android.wheeler;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.wheelandtire.android.wheeler.model.VehicleProfile;


public class CalculatorActivity extends AppCompatActivity {

    private VehicleViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCalculator);
//        setSupportActionBar(toolbar);
        setTitle("Calculator");

        viewModel = ViewModelProviders.of(this).get(VehicleViewModel.class);
        retrieveVehicleProfile();
//        vehicleSearch = new VehicleSearch(this, getWindow().getDecorView().getRootView(), 4);
//        testRetrofit();
//        menuItem.setTitle(profileName);
    }

    private void retrieveVehicleProfile() {
        viewModel.getVehicleProfile().observe(this, this::populateViews);
    }

    private void populateViews(VehicleProfile vehicleProfile) {

        EditText rimWidth = findViewById(R.id.rimWidthView);
        EditText rimOffset = findViewById(R.id.rimOffsetView);
        EditText tireWidth = findViewById(R.id.tireWidthView);
        EditText tireHeight = findViewById(R.id.tireHeightView);
        EditText tireDiameter = findViewById(R.id.tireDiameterView);

        if (vehicleProfile != null) {
            String tireAndRimDiameter = vehicleProfile.getProfileTireAndRimDiameter();
//            rimDiameter.setText(tireAndRimDiameter);
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
        SharedPreferences sharedpreferences = getSharedPreferences("myWheelerPrefs", Context.MODE_PRIVATE);
        String profileName = sharedpreferences.getString("profile_name_key", "notfound");
        menu.getItem(0).setTitle(profileName);
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

}
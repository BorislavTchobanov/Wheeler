package com.wheelandtire.android.wheeler;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.wheelandtire.android.wheeler.model.VehicleProfile;


public class CalculatorActivity extends AppCompatActivity {

    private VehicleViewModel viewModel;
    private VehicleSearch vehicleSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        viewModel = ViewModelProviders.of(this).get(VehicleViewModel.class);

//        vehicleSearch = new VehicleSearch(this, getWindow().getDecorView().getRootView(), 4);
//        retrieveVehicleProfile();
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
            vehicleSearch.saveToProfile();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
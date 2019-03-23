package com.wheelandtire.android.wheeler;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wheelandtire.android.wheeler.databinding.ActivityCalculatorBinding;
import com.wheelandtire.android.wheeler.databinding.ContentCalculatorBinding;
import com.wheelandtire.android.wheeler.model.Vehicle;
import com.wheelandtire.android.wheeler.model.VehicleProfile;

import java.util.List;

import retrofit2.Call;


public class CalculatorActivity extends AppCompatActivity {

    private VehicleViewModel viewModel;
    private ActivityCalculatorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_calculator);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_calculator);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

        if (vehicleProfile != null) {
            String tireAndRimDiameter = vehicleProfile.getProfileTireAndRimDiameter();
//            rimDiameter.setText(tireAndRimDiameter);
            binding.included.rimWidthView.setText(vehicleProfile.getProfileRimWidth());
            binding.included.rimOffsetView.setText(vehicleProfile.getProfileRimOffset());
            binding.included.tireWidthView.setText(vehicleProfile.getProfileTireWidth());
            binding.included.tireHeightView.setText(vehicleProfile.getProfileTireHeight());
            binding.included.rimDiameterView.setText(tireAndRimDiameter);
        }

        binding.included.goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateRimDiameter();
            }
        });
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
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private String addUnitOfMeasure(String initalText, boolean isPercentage) {
        return isPercentage ? initalText.concat("%") : initalText.concat("mm");
    }

    private int convertInchToMm(EditText currentView) {
        int currRimDiamInches = Integer.parseInt(currentView.getText().toString());
        return (int) (currRimDiamInches * 25.4 + .5f);
    }

    private float calculateDifferencePercentage(int currentSize, int newSize) {
        float diff = currentSize - newSize;
        return diff / currentSize * 100;
    }

    private void calculateRimDiameter() {
        int currentRimDiameterSize = convertInchToMm(binding.included.rimDiameterView);
        int newRimDiameterSize = convertInchToMm(binding.included.rimDiameterViewNew);
        String currentRimDiameterText = addUnitOfMeasure(String.valueOf(currentRimDiameterSize), false);
        String newRimDiameterText = addUnitOfMeasure(String.valueOf(newRimDiameterSize), false);
        binding.included.rimDiameterCurrentTv.setText(currentRimDiameterText);
        binding.included.rimDiameterNewTv.setText(newRimDiameterText);

        int diff = (int) calculateDifferencePercentage(currentRimDiameterSize, newRimDiameterSize);
        Drawable arrow = diff > 0 ? getDrawable(R.drawable.ic_decrease) : getDrawable(R.drawable.ic_increase);

        String diffRimDiameter = addUnitOfMeasure(String.valueOf(Math.abs(diff)), true);
        binding.included.rimDiameterDiffTv.setCompoundDrawablesWithIntrinsicBounds(arrow, null, null, null);
        binding.included.rimDiameterDiffTv.setText(diffRimDiameter);
    }
    private void calculateRimWidth() {

    }
}
package com.wheelandtire.android.wheeler;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wheelandtire.android.wheeler.databinding.ActivityCalculatorBinding;
import com.wheelandtire.android.wheeler.model.VehicleProfile;


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
                calculateRimWidth();
                calculateOffset();
                calculateTireWidth();
                calculateTireSidewall();
                calculateOverallDiameterAndSpeedo();
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
        return (int) Math.round(currRimDiamInches * 25.4);
    }

    private void calculateDifferencePercentage(int currentSize, int newSize, TextView textView) {
        float diff = currentSize - newSize;
        int diffPercentage = Math.round(diff / currentSize * 100);

        String diffRimOffset = addUnitOfMeasure(String.valueOf(Math.abs(diffPercentage)), true);
        textView.setCompoundDrawablesWithIntrinsicBounds(getArrowDrawable(diffPercentage), null, null, null);
        textView.setText(diffRimOffset);
    }

    private Drawable getArrowDrawable(int diff) {
        return diff > 0 ? getDrawable(R.drawable.ic_decrease) : getDrawable(R.drawable.ic_increase);
    }

    private void calculateRimDiameter() {
        int currentRimDiameterSize = 0;
        int newRimDiameterSize = 0;
        try {
            currentRimDiameterSize = convertInchToMm(binding.included.rimDiameterView);
            newRimDiameterSize = convertInchToMm(binding.included.rimDiameterViewNew);
        } catch (NumberFormatException e) {}

        String currentRimDiameterText = addUnitOfMeasure(String.valueOf(currentRimDiameterSize), false);
        String newRimDiameterText = addUnitOfMeasure(String.valueOf(newRimDiameterSize), false);
        binding.included.rimDiameterCurrentTv.setText(currentRimDiameterText);
        binding.included.rimDiameterNewTv.setText(newRimDiameterText);

        calculateDifferencePercentage(currentRimDiameterSize, newRimDiameterSize, binding.included.rimDiameterDiffTv);
    }
    private void calculateRimWidth() {
        int currentRimWidthSize = 0;
        int newRimWidthSize = 0;
        try {
            currentRimWidthSize = convertInchToMm(binding.included.rimWidthView);
            newRimWidthSize = convertInchToMm(binding.included.rimWidthViewNew);
        } catch (NumberFormatException e) {}

        String currentRimWidthText = addUnitOfMeasure(String.valueOf(currentRimWidthSize), false);
        String newRimWidthText = addUnitOfMeasure(String.valueOf(newRimWidthSize), false);
        binding.included.rimWidthCurrentTv.setText(currentRimWidthText);
        binding.included.rimWidthNewTv.setText(newRimWidthText);

        calculateDifferencePercentage(currentRimWidthSize, newRimWidthSize, binding.included.rimWidthDiffTv);
    }

    private void calculateOffset() {
        int currentOffsetSize = 0;
        int newOffsetSize = 0;
        try {
            currentOffsetSize = Integer.parseInt(binding.included.rimOffsetView.getText().toString());
            newOffsetSize = Integer.parseInt(binding.included.rimOffsetViewNew.getText().toString());
        } catch (NumberFormatException e) {}

        String currentOffset = addUnitOfMeasure(String.valueOf(currentOffsetSize), false);
        String newOffset = addUnitOfMeasure(String.valueOf(newOffsetSize), false);
        binding.included.offsetCurrentTv.setText(currentOffset);
        binding.included.offsetNewTv.setText(newOffset);

        calculateDifferencePercentage(currentOffsetSize, newOffsetSize, binding.included.offsetDiffTv);
    }

    private void calculateTireWidth() {
        int currentTireWidthSize = 0;
        int newTireWidthSize = 0;
        try {
            currentTireWidthSize = Integer.parseInt(binding.included.tireWidthView.getText().toString());
            newTireWidthSize = Integer.parseInt(binding.included.tireWidthViewNew.getText().toString());
        } catch (NumberFormatException e) {}

        String currentTireWidth = addUnitOfMeasure(String.valueOf(currentTireWidthSize), false);
        String newTireWidth = addUnitOfMeasure(String.valueOf(newTireWidthSize), false);
        binding.included.tireWidthCurrentTv.setText(currentTireWidth);
        binding.included.tireWidthNewTv.setText(newTireWidth);

        calculateDifferencePercentage(currentTireWidthSize, newTireWidthSize, binding.included.tireWidthDiffTv);
    }

    private void calculateTireSidewall() {
        int currentTireWidthSize = 0;
        int newTireWidthSize = 0;
        int currentTireHeightSize = 0;
        int newTireHeightSize = 0;
        try {
            currentTireWidthSize = Integer.parseInt(binding.included.tireWidthView.getText().toString());
            newTireWidthSize = Integer.parseInt(binding.included.tireWidthViewNew.getText().toString());
            currentTireHeightSize = Integer.parseInt(binding.included.tireHeightView.getText().toString());
            newTireHeightSize = Integer.parseInt(binding.included.tireHeightViewNew.getText().toString());
        } catch (NumberFormatException e) {}

        int sidewallCalculationCurrent = Math.round((float)currentTireHeightSize / 100 * currentTireWidthSize);
        int sidewallCalculationNew = Math.round((float)newTireHeightSize / 100 * newTireWidthSize);
        String currentTireHeight = addUnitOfMeasure(String.valueOf(sidewallCalculationCurrent), false);
        String newTireHeight = addUnitOfMeasure(String.valueOf(sidewallCalculationNew), false);

        binding.included.sidewallCurrentTv.setText(currentTireHeight);
        binding.included.sidewallNewTv.setText(newTireHeight);

        calculateDifferencePercentage(sidewallCalculationCurrent, sidewallCalculationNew, binding.included.sidewallDiffTv);
    }

    private void calculateOverallDiameterAndSpeedo() {
        int currentTireWidthSize = 0;
        int newTireWidthSize = 0;
        int currentTireHeightSize = 0;
        int newTireHeightSize = 0;
        int currentRimDiameterSize = 0;
        int newRimDiameterSize = 0;
        try {
            currentTireWidthSize = Integer.parseInt(binding.included.tireWidthView.getText().toString());
            newTireWidthSize = Integer.parseInt(binding.included.tireWidthViewNew.getText().toString());
            currentTireHeightSize = Integer.parseInt(binding.included.tireHeightView.getText().toString());
            newTireHeightSize = Integer.parseInt(binding.included.tireHeightViewNew.getText().toString());
            currentRimDiameterSize = convertInchToMm(binding.included.rimDiameterView);
            newRimDiameterSize = convertInchToMm(binding.included.rimDiameterViewNew);
        } catch (NumberFormatException e) {}

        int sidewallCalculationCurrent = Math.round((float)currentTireHeightSize / 100 * currentTireWidthSize);
        int sidewallCalculationNew = Math.round((float)newTireHeightSize / 100 * newTireWidthSize);

        int overallDiameterCurrentSize = sidewallCalculationCurrent * 2 + currentRimDiameterSize;
        int overallDiameterNewSize = sidewallCalculationNew * 2 + newRimDiameterSize;

        String currentOverallDiameter = addUnitOfMeasure(String.valueOf(overallDiameterCurrentSize), false);
        String newOverallDiameter = addUnitOfMeasure(String.valueOf(overallDiameterNewSize), false);

        binding.included.overallDiameterCurrentTv.setText(currentOverallDiameter);
        binding.included.overallDiameterNewTv.setText(newOverallDiameter);

        double diff = overallDiameterCurrentSize - overallDiameterNewSize;
        if (overallDiameterCurrentSize == 0) {
            overallDiameterCurrentSize = 1;
        }
        double diffPercentage = Math.round(diff / overallDiameterCurrentSize * 100 * 10) / 10.0;

        String diffOverallDiameter = addUnitOfMeasure(String.valueOf(Math.abs(diffPercentage)), true);
        binding.included.overallDiameterDiffTv.setCompoundDrawablesWithIntrinsicBounds(getArrowDrawable((int) diffPercentage), null, null, null);
        binding.included.overallDiameterDiffTv.setText(diffOverallDiameter);

        String newSpeedometerReading = String.valueOf( Math.round((float)overallDiameterNewSize / overallDiameterCurrentSize * 60));
        binding.included.speedometerNewTv.setText("When speedometer reads 60km/h actual speed will be " + newSpeedometerReading + "km/h");
    }
}
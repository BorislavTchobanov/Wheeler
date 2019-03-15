package com.wheelandtire.android.wheeler;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.wheelandtire.android.wheeler.model.Vehicle;
import com.wheelandtire.android.wheeler.model.VehicleProfile;
import com.wheelandtire.android.wheeler.utility.RetrofitClientInstance;
import com.wheelandtire.android.wheeler.utility.WheelSizeService;

import java.util.List;

import retrofit2.Call;


public class ProfileActivity extends AppCompatActivity {

    private VehicleViewModel viewModel;
    private VehicleSearch vehicleSearch;
    private WheelSizeService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView image = findViewById(R.id.profileImage);
        image.setImageDrawable(getDrawable(R.drawable.test_image));
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
            Call<List<Vehicle>> call = service.getVehicle(vehicleSearch.getMake(), vehicleSearch.getModel(),
                    vehicleSearch.getYear(), vehicleSearch.getTrim());
            vehicleSearch.makeFinalServiceCall(call);
//            vehicleSearch.saveToProfile();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
package com.wheelandtire.android.wheeler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.wheelandtire.android.wheeler.model.Vehicle;
import com.wheelandtire.android.wheeler.utility.ImageHandler;

import java.util.Random;

import static com.wheelandtire.android.wheeler.FitmentActivity.EXTRA_VEHICLE;

public class FitmentDetailActivity extends AppCompatActivity {


    private Vehicle vehicle;
    private int trimIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitment_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Fitment List");

        vehicle = (Vehicle) getIntent().getSerializableExtra(EXTRA_VEHICLE);
//        trimIndex = getIntent().getIntExtra(EXTRA_CURRENT_STEP_INDEX, 0);

        int vehicleImagesListSize = vehicle.getGeneration().getBodies().size();
        String vehicleImageUrl = vehicle.getGeneration().getBodies()
                .get(new Random().nextInt(vehicleImagesListSize))
                .getImage();

        ImageView vehicleImage = findViewById(R.id.vehicleBodyIv);
        ImageHandler.loadImage(vehicleImageUrl, vehicleImage);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                .add(R.id.recipe_detail_container, setupFragment(trimIndex))
                .commit();
        }

    }

    private Fragment setupFragment(int index) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(EXTRA_VEHICLE, vehicle);

        FitmentDetailFragment fragment = new FitmentDetailFragment();
        fragment.setArguments(arguments);

        return fragment;
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
}

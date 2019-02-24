package com.wheelandtire.android.wheeler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.wheelandtire.android.wheeler.model.Vehicle;

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


        vehicle = (Vehicle) getIntent().getSerializableExtra(EXTRA_VEHICLE);
//        trimIndex = getIntent().getIntExtra(EXTRA_CURRENT_STEP_INDEX, 0);

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
}

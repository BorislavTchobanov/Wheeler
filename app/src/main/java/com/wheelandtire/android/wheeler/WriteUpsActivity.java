package com.wheelandtire.android.wheeler;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.wheelandtire.android.wheeler.adapter.FitmentAdapter;
import com.wheelandtire.android.wheeler.adapter.WriteUpsAdapter;
import com.wheelandtire.android.wheeler.model.VehicleProfile;

import java.util.ArrayList;
import java.util.List;


public class WriteUpsActivity extends AppCompatActivity {

    private VehicleViewModel viewModel;
    private List<String> terminologyList;
    private List<String> writeUpsContentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_ups);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Write-ups");

        terminologyList = new ArrayList<>();
        terminologyList.add("test1");
        terminologyList.add("test2");
        terminologyList.add("test3");
        terminologyList.add("test4");

        writeUpsContentList = new ArrayList<>();
        writeUpsContentList.add("The offset of a wheel is the distance from the hub mounting surface to the center" +
                "line of the wheel. The wheel offset is measured in millimeters and results in a positive, negative," +
                "or zero offset. Positive offset is when the hub mounting surface is toward the front or wheel side of the wheel." +
                "It is common to find a positive offset in newer and front wheel drive vehicles. Negative offset" +
                "is when the hub mounting surface is toward the back or brake side of the wheels centerline." +
                "A negative offset wheel usually has a deep lip Zero offset The hub mounting surface is even with the centerline of the wheel.");
        writeUpsContentList.add("The offset of a wheel is the distance from the hub mounting surface to the center" +
                "line of the wheel. The wheel offset is measured in millimeters and results in a positive, negative," +
                "or zero offset. Positive offset is when the hub mounting surface is toward the front or wheel side of the wheel." +
                "It is common to find a positive offset in newer and front wheel drive vehicles. Negative offset" +
                "is when the hub mounting surface is toward the back or brake side of the wheels centerline." +
                "A negative offset wheel usually has a deep lip Zero offset The hub mounting surface is even with the centerline of the wheel.");
        writeUpsContentList.add("The offset of a wheel is the distance from the hub mounting surface to the center" +
                "line of the wheel. The wheel offset is measured in millimeters and results in a positive, negative," +
                "or zero offset. Positive offset is when the hub mounting surface is toward the front or wheel side of the wheel." +
                "It is common to find a positive offset in newer and front wheel drive vehicles. Negative offset" +
                "is when the hub mounting surface is toward the back or brake side of the wheels centerline." +
                "A negative offset wheel usually has a deep lip Zero offset The hub mounting surface is even with the centerline of the wheel.");
        writeUpsContentList.add("The offset of a wheel is the distance from the hub mounting surface to the center" +
                "line of the wheel. The wheel offset is measured in millimeters and results in a positive, negative," +
                "or zero offset. Positive offset is when the hub mounting surface is toward the front or wheel side of the wheel." +
                "It is common to find a positive offset in newer and front wheel drive vehicles. Negative offset" +
                "is when the hub mounting surface is toward the back or brake side of the wheels centerline." +
                "A negative offset wheel usually has a deep lip Zero offset The hub mounting surface is even with the centerline of the wheel.");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        setupRecyclerView(recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new WriteUpsAdapter(terminologyList, writeUpsContentList));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_toolbar_button, menu);
        menu.getItem(0).setVisible(false);

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

}
package com.wheelandtire.android.wheeler;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.wheelandtire.android.wheeler.adapter.WriteUpsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class WriteUpsActivity extends AppCompatActivity {

    private List<String> writeUpsTitleList;
    private List<String> writeUpsContentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_ups);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.main_menu_writeups_button_text));

        if (!getResources().getBoolean(R.bool.isTablet)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }

        writeUpsTitleList = new ArrayList<>();
        writeUpsTitleList.add(getString(R.string.write_up_offset_title));
        writeUpsTitleList.add(getString(R.string.write_up_center_bore_title));
        writeUpsTitleList.add(getString(R.string.write_up_pcd_title));

        writeUpsContentList = new ArrayList<>();
        writeUpsContentList.add(getString(R.string.write_up_offset_text));
        writeUpsContentList.add(getString(R.string.write_up_center_bore_text));
        writeUpsContentList.add(getString(R.string.write_up_pcd_text));

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        setupRecyclerView(recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new WriteUpsAdapter(writeUpsTitleList, writeUpsContentList));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_button, menu);
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
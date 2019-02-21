package com.wheelandtire.android.wheeler;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
//import com.wheelandtire.android.wheeler.adapter.CustomAdapter;
import com.wheelandtire.android.wheeler.adapter.CustomAdapter;
import com.wheelandtire.android.wheeler.adapter.VehicleGenerationAdapter;
import com.wheelandtire.android.wheeler.adapter.VehicleMakeAdapter;
import com.wheelandtire.android.wheeler.model.Generation;
import com.wheelandtire.android.wheeler.model.Vehicle;
import com.wheelandtire.android.wheeler.model.VehicleMake;
import com.wheelandtire.android.wheeler.utility.RetrofitClientInstance;
import com.wheelandtire.android.wheeler.utility.WheelSizeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, VehicleGenerationAdapter.ListItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, calculateNoOfColumns(this));
        recyclerView.setLayoutManager(gridLayoutManager);

        testRetrofit();
    }

    public void fabComponent() {

        FloatingActionButton fab = new FloatingActionButton.Builder(this)
//                .setContentView(icon)
                .setBackgroundDrawable(getDrawable(R.drawable.wheel))
                .setLayoutParams(new FloatingActionButton.LayoutParams(800, 800))
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

        ImageView item1 = new ImageView(this);
        item1.setImageResource(R.mipmap.ic_launcher);

        ImageView item2 = new ImageView(this);
        item2.setImageResource(R.mipmap.ic_launcher);

        ImageView item3 = new ImageView(this);
        item3.setImageResource(R.mipmap.ic_launcher);

        SubActionButton button1 = itemBuilder.setContentView(item1).build();
        SubActionButton button2 = itemBuilder.setContentView(item2).build();
        SubActionButton button3 = itemBuilder.setContentView(item3).build();



        //attach the sub buttons
        new FloatingActionMenu.Builder(this)
                .setRadius(500)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .addSubActionView(button3)
                .attachTo(fab)
                .build();

    }

    private static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;

        return (int) (dpWidth / scalingFactor);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new VehicleGenerationAdapter(vehicleList, this));
    }

    private VehicleMakeAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDoalog;
    private WheelSizeService service;
    private String make;
    private boolean isInitCall = true;
    private List<Vehicle> vehicleList;



    public void testRetrofit() {

        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        /*Create handle for the RetrofitClientInstance interface*/
        service = RetrofitClientInstance.getRetrofitInstance().create(WheelSizeService.class);
//        Call<List<VehicleMake>> call = service.getVehicleModel("bmw", "1998");
        Call<List<VehicleMake>> call = service.getVehicleMake();
        call.enqueue(new Callback<List<VehicleMake>>() {
            @Override
            public void onResponse(Call<List<VehicleMake>> call, Response<List<VehicleMake>> response) {
                progressDoalog.dismiss();
//                generateDataList(response.body());
                if (isInitCall && response.body() != null) {
                    generateMakeDropDownList(response.body());
                    isInitCall = false;
                }
            }

            @Override
            public void onFailure(Call<List<VehicleMake>> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateMakeDropDownList(final List<VehicleMake> vehicleMakeList) {
        // Spinner element
        final Spinner spinner = (Spinner) findViewById(R.id.spinnerMake);
        // Spinner click listener
//        spinner.setOnItemSelectedListener(this);

        VehicleMake makeHint = new VehicleMake();
        makeHint.setName("Make");
        makeHint.setSlug("Make");
        vehicleMakeList.add(0, makeHint);
        // Creating adapter for spinner
        final CustomAdapter dataAdapter = new CustomAdapter(this, android.R.layout.simple_spinner_item, vehicleMakeList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

//        spinner.setSelection(0,false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    make = Objects.requireNonNull(dataAdapter.getItem(i)).getName();
                    Toast.makeText(getApplicationContext(), make, Toast.LENGTH_LONG).show();

                    Call<List<VehicleMake>> call = service.getVehicleModel(make);
                    call.enqueue(new Callback<List<VehicleMake>>() {
                        @Override
                        public void onResponse(Call<List<VehicleMake>> call, Response<List<VehicleMake>> response) {
                            progressDoalog.dismiss();
//                generateDataList(response.body());
                            generateModelDropDownList(response.body());
                        }

                        @Override
                        public void onFailure(Call<List<VehicleMake>> call, Throwable t) {
                            progressDoalog.dismiss();
                            Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
    }

    private void generateModelDropDownList(List<VehicleMake> vehicleModelList) {
        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinnerModel);
        // Spinner click listener
//        spinner.setOnItemSelectedListener(this);
        VehicleMake modelHint = new VehicleMake();
        modelHint.setName("Model");
        modelHint.setSlug("Model");
        vehicleModelList.add(0, modelHint);
        // Creating adapter for spinner
        final CustomAdapter dataAdapter = new CustomAdapter(this, android.R.layout.simple_spinner_item, vehicleModelList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

//        spinner.setSelection(0,false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    final String model = Objects.requireNonNull(dataAdapter.getItem(i)).getSlug();
                    Toast.makeText(getApplicationContext(), model, Toast.LENGTH_LONG).show();

                    Call<List<VehicleMake>> call = service.getVehicleYear(make, model);
                    call.enqueue(new Callback<List<VehicleMake>>() {
                        @Override
                        public void onResponse(Call<List<VehicleMake>> call, Response<List<VehicleMake>> response) {
                            progressDoalog.dismiss();
//                generateDataList(response.body());
                            generateYearDropDownList(response.body(), model);
                        }

                        @Override
                        public void onFailure(Call<List<VehicleMake>> call, Throwable t) {
                            progressDoalog.dismiss();
                            Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
    }

    private void generateYearDropDownList(final List<VehicleMake> vehicleYearList, final String model) {
        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinnerYear);
        // Spinner click listener
//        spinner.setOnItemSelectedListener(this);
        VehicleMake yearHint = new VehicleMake();
        yearHint.setName("Year");
        yearHint.setSlug("Year");
        vehicleYearList.add(0, yearHint);
        // Creating adapter for spinner
        final CustomAdapter dataAdapter = new CustomAdapter(this, android.R.layout.simple_spinner_item, vehicleYearList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    String year = Objects.requireNonNull(dataAdapter.getItem(i)).getSlug();
                    Toast.makeText(getApplicationContext(), year, Toast.LENGTH_LONG).show();

                    Call<List<Vehicle>> call = service.getVehicle(make, model, year);
                    call.enqueue(new Callback<List<Vehicle>>() {
                        @Override
                        public void onResponse(Call<List<Vehicle>> call, Response<List<Vehicle>> response) {
                            progressDoalog.dismiss();
                            vehicleList = response.body();
                            setupRecyclerView(recyclerView);
                            Log.i("TEST","SUCCESS!!! = " + vehicleList);
                        }

                        @Override
                        public void onFailure(Call<List<Vehicle>> call, Throwable t) {
                            progressDoalog.dismiss();
                            Log.i("TEST","Something went wrong...Please try later! = " + t);
                            Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

    }
}

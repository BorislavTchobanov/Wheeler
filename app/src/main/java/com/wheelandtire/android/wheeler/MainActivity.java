package com.wheelandtire.android.wheeler;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
//import com.wheelandtire.android.wheeler.adapter.CustomAdapter;


public class MainActivity extends AppCompatActivity {


    private InterstitialAd interstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        TextView textView = findViewById(R.id.title_tv);
        Typeface face;
        face = Typeface.createFromAsset(getAssets(), "fonts/Spantaran.otf");
        textView.setTypeface(face);

        fabComponent();
    }


    private void startCalculator() {
        setupInterstitialAd();
        final AdRequest request = new AdRequest
                .Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        interstitialAd.loadAd(request);
    }

    private void showCalculator() {
        Intent intent = new Intent(MainActivity.this, CalculatorActivity.class);
        startActivity(intent);
    }

    private void setupInterstitialAd() {
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                interstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                showCalculator();
            }

            @Override
            public void onAdClosed() {
                showCalculator();
            }
        });
    }

    public void fabComponent() {

        float factor = getResources().getDisplayMetrics().density;

        FloatingActionButton fab = new FloatingActionButton.Builder(this)
//                .setContentView(icon)
                .setBackgroundDrawable(getDrawable(R.drawable.wheel))
                .setLayoutParams(new FloatingActionButton.LayoutParams((int) (300 * factor), (int) (300 * factor)))
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.fab_sub_button_nobackground));

//        ImageView item1 = new ImageView(this);
//        item1.setImageResource(R.drawable.ic_write_ups);
//
//        ImageView item2 = new ImageView(this);
//        item2.setImageResource(R.drawable.ic_calculator);

//        ImageView item3 = new ImageView(this);
//        item3.setImageResource(R.drawable.ic_fitment);

//        ImageView item4 = new ImageView(this);
//        item4.setImageResource(R.drawable.ic_vehicle_profile);

//        ImageView item5 = new ImageView(this);
//        item5.setImageResource(R.mipmap.ic_launcher);

        TextView item4 = new TextView(this);
        item4.setText("Profile");
        item4.setGravity(Gravity.CENTER_HORIZONTAL);
        item4.setCompoundDrawablesWithIntrinsicBounds(null, getDrawable(R.drawable.ic_vehicle_profile), null, null);

        TextView item3 = new TextView(this);
        item3.setText("Sizes");
        item3.setGravity(Gravity.CENTER_HORIZONTAL);
        item3.setCompoundDrawablesWithIntrinsicBounds(null, getDrawable(R.drawable.ic_fitment), null, null);

        TextView item2 = new TextView(this);
        item2.setText("Fitment");
        item2.setGravity(Gravity.CENTER_HORIZONTAL);
        item2.setCompoundDrawablesWithIntrinsicBounds(null, getDrawable(R.drawable.ic_calculator), null, null);

        TextView item1 = new TextView(this);
        item1.setText("Write-ups");
        item1.setGravity(Gravity.CENTER_HORIZONTAL);
        item1.setCompoundDrawablesWithIntrinsicBounds(null, getDrawable(R.drawable.ic_write_ups), null, null);

        SubActionButton button1 = itemBuilder.setContentView(item1).setLayoutParams(new SubActionButton.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)).build();
        SubActionButton button2 = itemBuilder.setContentView(item2).build();
        SubActionButton button3 = itemBuilder.setContentView(item3).build();
        SubActionButton button4 = itemBuilder.setContentView(item4).build();


//        SubActionButton button5 = itemBuilder.setContentView(item5).build();
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startCalculator();
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FitmentActivity.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalculatorActivity.class);
                startActivity(intent);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //attach the sub buttons
        new FloatingActionMenu.Builder(this)
                .setRadius((int)(165*factor))
                .setStartAngle(175)
                .setEndAngle(283)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .addSubActionView(button3)
                .addSubActionView(button4)
//                .addSubActionView(button5)
                .attachTo(fab)
                .build();

        Animation translate = new TranslateAnimation(Animation.ABSOLUTE, 800, Animation.ABSOLUTE, 100,Animation.ABSOLUTE,0, Animation.ABSOLUTE, 100);
        translate.setDuration(1500);

        RotateAnimation rotate = new RotateAnimation(180,0f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        rotate.setDuration(1500);
        rotate.setRepeatCount(0);

        final AnimationSet moveAnimation = new AnimationSet(true);
        moveAnimation.addAnimation(rotate);
        moveAnimation.addAnimation(translate);
        moveAnimation.setFillAfter(true);
        fab.setAnimation(moveAnimation);
        moveAnimation.start();

        moveAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                fab.callOnClick();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.profile_toolbar_button, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//
//        return true;
//    }
//
//    @Override
//    public void onListItemClick(int clickedItemIndex) {
//
//    }
}

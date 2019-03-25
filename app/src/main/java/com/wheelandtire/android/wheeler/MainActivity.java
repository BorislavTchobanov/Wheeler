package com.wheelandtire.android.wheeler;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

public class MainActivity extends AppCompatActivity {

    public static final String PROFILE_NAME_PREFS = "profileNamePrefs";
    public static final String PROFILE_NAME_KEY = "profileNameKey";

    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, getString(R.string.admob_application_code));

        TextView textView = findViewById(R.id.title_tv);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Spantaran.otf");
        textView.setTypeface(font);

        fabComponent();
    }


    private void startWriteUps() {
        setupInterstitialAd();
        final AdRequest request = new AdRequest
                .Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        interstitialAd.loadAd(request);
    }

    private void showWriteUps() {
        Intent intent = new Intent(MainActivity.this, WriteUpsActivity.class);
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
                showWriteUps();
            }

            @Override
            public void onAdClosed() {
                showWriteUps();
            }
        });
    }

    public void fabComponent() {
        float factor = getResources().getDisplayMetrics().density;

        FloatingActionButton fab = new FloatingActionButton.Builder(this)
                .setBackgroundDrawable(getDrawable(R.drawable.wheel))
                .setLayoutParams(new FloatingActionButton.LayoutParams((int) (300 * factor), (int) (300 * factor)))
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.fab_sub_button_nobackground));

        TextView item4 = new TextView(this);
        item4.setText(R.string.main_menu_profile_button_text);
        item4.setGravity(Gravity.CENTER_HORIZONTAL);
        item4.setCompoundDrawablesWithIntrinsicBounds(null, getDrawable(R.drawable.ic_vehicle_profile), null, null);

        TextView item3 = new TextView(this);
        item3.setText(R.string.main_menu_fitment_button_text);
        item3.setGravity(Gravity.CENTER_HORIZONTAL);
        item3.setCompoundDrawablesWithIntrinsicBounds(null, getDrawable(R.drawable.ic_fitment), null, null);

        TextView item2 = new TextView(this);
        item2.setText(R.string.main_menu_calculator_button_text);
        item2.setGravity(Gravity.CENTER_HORIZONTAL);
        item2.setCompoundDrawablesWithIntrinsicBounds(null, getDrawable(R.drawable.ic_calculator), null, null);

        TextView item1 = new TextView(this);
        item1.setText(R.string.main_menu_writeups_button_text);
        item1.setGravity(Gravity.CENTER_HORIZONTAL);
        item1.setCompoundDrawablesWithIntrinsicBounds(null, getDrawable(R.drawable.ic_write_ups), null, null);

        SubActionButton writeUpsButton = itemBuilder
                .setContentView(item1)
                .setLayoutParams(new SubActionButton
                        .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .build();
        SubActionButton calculatorButton = itemBuilder.setContentView(item2).build();
        SubActionButton fitmentButton = itemBuilder.setContentView(item3).build();
        SubActionButton profileButton = itemBuilder.setContentView(item4).build();

        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        fitmentButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FitmentActivity.class);
            startActivity(intent);
        });
        calculatorButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CalculatorActivity.class);
            startActivity(intent);
        });
        writeUpsButton.setOnClickListener(v -> startWriteUps());

        //attach the sub buttons
        new FloatingActionMenu.Builder(this)
                .setRadius((int) (170 * factor))
                .setStartAngle(175)
                .setEndAngle(283)
                .addSubActionView(writeUpsButton)
                .addSubActionView(calculatorButton)
                .addSubActionView(fitmentButton)
                .addSubActionView(profileButton)
                .attachTo(fab)
                .build();

        handleAnimation(fab);
    }

    private void handleAnimation(FloatingActionButton fab) {
        Animation translate = new TranslateAnimation(Animation.ABSOLUTE,
                800, Animation.ABSOLUTE,
                100, Animation.ABSOLUTE,
                0, Animation.ABSOLUTE,
                100);
        translate.setDuration(1500);

        RotateAnimation rotate = new RotateAnimation(180,
                0f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
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
}

package com.example.Seller_Interface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class BottleSizes extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar navTool;

    ImageView bottlesizeProfile;
    Button generate;

    ConstraintLayout contentView;

    //Set animation when drawer is being dragged
    static final float END_SCALE = 0.8f;

    private TextView textView;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;

    private NumberPicker quantityPicker;
    private NumberPicker quantityPicker1;
    private NumberPicker quantityPicker2;
    private NumberPicker quantityPicker3;
    private NumberPicker quantityPicker4;
    private NumberPicker quantityPicker5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottle_sizes);

        navTool = findViewById(R.id.bottlesizesToolbar);
        contentView = findViewById(R.id.tradebottleContent);
        bottlesizeProfile = findViewById(R.id.bottlesizeProfile);
        generate = findViewById(R.id.Generate);



        //hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);


        navigationDrawer();

        quantityPicker = findViewById(R.id.quantityPicker);
        quantityPicker1 = findViewById(R.id.quantityPicker1);
        quantityPicker2 = findViewById(R.id.quantityPicker2);
        quantityPicker3 = findViewById(R.id.quantityPicker3);
        quantityPicker4 = findViewById(R.id.quantityPicker4);
        quantityPicker5 = findViewById(R.id.quantityPicker5);

        textView = findViewById(R.id.quant1);
        textView1 = findViewById(R.id.quant2);
        textView2 = findViewById(R.id.quant3);
        textView3 = findViewById(R.id.quant4);
        textView4 = findViewById(R.id.quant5);
        textView5 = findViewById(R.id.quant6);


        quantityPicker.setMaxValue(100);
        quantityPicker.setMinValue(0);
        quantityPicker.setValue(0);

        quantityPicker1.setMaxValue(100);
        quantityPicker1.setMinValue(0);
        quantityPicker1.setValue(0);

        quantityPicker2.setMaxValue(100);
        quantityPicker2.setMinValue(0);
        quantityPicker2.setValue(0);

        quantityPicker3.setMaxValue(100);
        quantityPicker3.setMinValue(0);
        quantityPicker3.setValue(0);

        quantityPicker4.setMaxValue(100);
        quantityPicker4.setMinValue(0);
        quantityPicker4.setValue(0);

        quantityPicker5.setMaxValue(100);
        quantityPicker5.setMinValue(0);
        quantityPicker5.setValue(0);


        bottlesizeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent class will help to go to next activity using
                // it's object named intent.
                // SecondActivty is the name of new created EmptyActivity.
                Intent bottlesize = new Intent(BottleSizes.this, Profile.class);
                startActivity(bottlesize);
            }
        });

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent class will help to go to next activity using
                // it's object named intent.
                // SecondActivty is the name of new created EmptyActivity.
                Intent generate = new Intent(BottleSizes.this, CodeGenerator.class);
                startActivity(generate);
            }
        });

        quantityPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {

                textView.setText("Quantity: " + newValue);
            }
        });

        quantityPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {

                textView1.setText("Quantity: " + newValue);
            }
        });

        quantityPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {

                textView2.setText("Quantity: " + newValue);
            }
        });

        quantityPicker3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {

                textView3.setText("Quantity: " + newValue);
            }
        });

        quantityPicker4.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {

                textView4.setText("Quantity: " + newValue);
            }
        });

        quantityPicker5.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {

                textView5.setText("Quantity: " + newValue);
            }
        });

        //NavigationBar Menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent home = new Intent(BottleSizes.this, MainActivity.class);
                        startActivity(home);
                        break;

                    case R.id.location:
                        Intent location = new Intent(BottleSizes.this, MapLocation.class);
                        startActivity(location);
                        break;

                    case R.id.Profile:
                        Intent prof = new Intent(BottleSizes.this, Profile.class);
                        startActivity(prof);
                        break;


                    case R.id.settings:
                        Intent settings = new Intent(BottleSizes.this, Settings.class);
                        startActivity(settings);
                        break;

                    case R.id.about:
                        Intent aboutUS = new Intent(BottleSizes.this, AboutUs.class);
                        startActivity(aboutUS);
                        break;

                    case R.id.signout:
                        Intent signOUT = new Intent();
                        startActivity(signOUT);
                        finish();
                        System.exit(0);
                        break;

                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    private void navigationDrawer() {
        //Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        navTool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);

                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

                //Scale the view on current slide offset
                final float diffScaleoffset =  slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaleoffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                //Translate the View, accounting for the scaled wdith
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaleoffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }
}
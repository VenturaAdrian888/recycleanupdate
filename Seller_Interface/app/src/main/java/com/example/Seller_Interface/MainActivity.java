package com.example.Seller_Interface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;

    //Images
    ImageView HomeProfile;
    Toolbar navTool;
    ImageView soda;
    ConstraintLayout contentView;

    //CardViews
    CardView cardMap;
    CardView cardTradeBottle;
    CardView cardLeaderboards;
    CardView cardFAQ;

    //Set animation when drawer is being dragged
    static final float END_SCALE = 0.8f;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Home Images
        HomeProfile = findViewById(R.id.HomeProfile);
        navTool = findViewById(R.id.toolbar);

        //Home CardViews
        cardMap = findViewById(R.id.locView);
        cardTradeBottle = findViewById(R.id.bottleView);
        cardLeaderboards = findViewById(R.id.leaderView);
        cardFAQ = findViewById(R.id.faqView);

        contentView = findViewById(R.id.content);

        //Home ImagesViews
        HomeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent class will help to go to next activity using
                // it's object named intent.
                // SecondActivty is the name of new created EmptyActivity.
                Intent profile = new Intent(MainActivity.this, Profile.class);
                startActivity(profile);

            }
        });

        //Home CardViews
        cardMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent class will help to go to next activity using
                // it's object named intent.
                // SecondActivty is the name of new created EmptyActivity.
                Intent c1 = new Intent(MainActivity.this, MapLocation.class);
                startActivity(c1);
            }
        });

        cardTradeBottle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent class will help to go to next activity using
                // it's object named intent.
                // SecondActivty is the name of new created EmptyActivity.
                Intent c2 = new Intent(MainActivity.this, TradeBottle.class);
                startActivity(c2);
            }
        });

        cardLeaderboards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent class will help to go to next activity using
                // it's object named intent.
                // SecondActivty is the name of new created EmptyActivity.
                Intent c3 = new Intent(MainActivity.this, Leaderboards.class);
                startActivity(c3);
            }
        });

        cardFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent class will help to go to next activity using
                // it's object named intent.
                // SecondActivty is the name of new created EmptyActivity.
                Intent c4 = new Intent(MainActivity.this, AboutUs.class);
                startActivity(c4);
            }
        });


        //hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        navTool =  findViewById(R.id.toolbar);
        soda = findViewById(R.id.imageView2);


        //Navigation Drawer Menu
        actionBarDrawerToggle  = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_open, R.string.menu_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationDrawer();

        //Navigation Menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {


                    case R.id.location:
                        Intent location = new Intent(MainActivity.this, MapLocation.class);
                        startActivity(location);
                        break;

                    case R.id.Profile:
                        Intent prof = new Intent(MainActivity.this, Profile.class);
                        startActivity(prof);
                        break;


                    case R.id.settings:
                        Intent settings = new Intent(MainActivity.this, Settings.class);
                        startActivity(settings);
                        break;

                    case R.id.about:
                        Intent aboutUS = new Intent(MainActivity.this, AboutUs.class);
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
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.home);

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
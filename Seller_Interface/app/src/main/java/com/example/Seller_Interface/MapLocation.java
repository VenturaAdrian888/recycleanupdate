package com.example.Seller_Interface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

import com.google.android.gms.location.GeofencingClient;

import java.io.IOException;
import java.util.List;

public class MapLocation extends FragmentActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener, GoogleMap.OnMapLongClickListener {

    //Navigation Bar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar navTool1;
    ImageView MapLocation;


    //Drop-off Points
    ImageView SM;
    ImageView airport;
    ImageView caniezo;
    ImageView uc;

    //Search view
    private GoogleMap map;
    SearchView searchView;
    SupportMapFragment mapFragment;

    //Adding GEO-Fencing
    private GeofencingClient geofencingClient;
    private float GEOFENCERADIUS = 200;

    ConstraintLayout contentView;
    //Set animation when drawer is being dragged
    static final float END_SCALE = 0.8f;


    private int ACCESS_LOCATION_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_location);

        navTool1 = findViewById(R.id.maplocationToolbar);
        contentView = findViewById(R.id.maplocationContent);
        MapLocation = findViewById(R.id.maplocationProfile);


        SM = findViewById(R.id.SMBaguio);
        airport = findViewById(R.id.LoakanAirport);
        caniezo = findViewById(R.id.CaniezoJunkshop);
        uc = findViewById(R.id.UC);

        //hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);


        MapLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent class will help to go to next activity using
                // it's object named intent.
                // SecondActivty is the name of new created EmptyActivity.
                Intent maplocation = new Intent(MapLocation.this, Profile.class);
                startActivity(maplocation);
            }
        });


        navigationDrawer();

        //Navigation Menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent home = new Intent(MapLocation.this, MainActivity.class);
                        startActivity(home);
                        break;

                    case R.id.Profile:
                        Intent prof = new Intent(MapLocation.this, Profile.class);
                        startActivity(prof);
                        break;


                    case R.id.settings:
                        Intent settings = new Intent(MapLocation.this, Settings.class);
                        startActivity(settings);
                        break;

                    case R.id.about:
                        Intent aboutUS = new Intent(MapLocation.this, AboutUs.class);
                        startActivity(aboutUS);
                        break;

                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        searchView = findViewById(R.id.sv_location);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String location = searchView.getQuery().toString();
                List<Address> addressesList = null;

                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(MapLocation.this);
                    try {
                        addressesList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressesList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    map.addMarker(new MarkerOptions().position(latLng).title(location));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);

    }

    private void navigationDrawer() {
        //Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.location);

        navTool1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
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
                final float diffScaleoffset = slideOffset * (1 - END_SCALE);
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

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.setOnMapLongClickListener(this);
        enableuserlocation();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == getPackageManager().PERMISSION_GRANTED) {
        }

         else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);
            }
        }

        LatLng SMBaguio = new LatLng(16.4089, 120.5992);
        map.moveCamera(CameraUpdateFactory.newLatLng(SMBaguio));
        MarkerOptions mark1 = new MarkerOptions().position(SMBaguio).title("SM Baguio");
        mark1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        map.addMarker(mark1);

        LatLng LoakanAirport = new LatLng(16.3755, 120.6130);
        map.moveCamera(CameraUpdateFactory.newLatLng(LoakanAirport));
        MarkerOptions mark2 = new MarkerOptions().position(LoakanAirport).title("Loakan Airport");
        mark2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        map.addMarker(mark2);

        LatLng Junkshop = new LatLng(16.409295875685704, 120.58747924628034);
        map.moveCamera(CameraUpdateFactory.newLatLng(Junkshop));
        MarkerOptions mark3 = new MarkerOptions().position(Junkshop).title("Caniezo JunkShop");
        mark3.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        map.addMarker(mark3);

        LatLng Cordilleras = new LatLng(16.4087, 120.5978);
        map.addMarker(new MarkerOptions().position(Cordilleras).title("University of the Cordilleras"));
        map.moveCamera(CameraUpdateFactory.newLatLng(Cordilleras));
        MarkerOptions mark4 = new MarkerOptions().position(Cordilleras).title("University of the Cordilleras");
        mark4.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        map.addMarker(mark4);

        LatLng Philippines = new LatLng(12.8797, 121.7740);
        map.addMarker(new MarkerOptions().position(Philippines).title("Philippines"));
        map.moveCamera(CameraUpdateFactory.newLatLng(Philippines));

        LatLng Baguio = new LatLng(16.4023, 120.5960);
        map.moveCamera(CameraUpdateFactory.newLatLng(Baguio));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(Baguio, 15));
        MarkerOptions baguio = new MarkerOptions().position(Baguio).title("Baguio City");
        baguio.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        map.addMarker(baguio);



        //DROP-OFF POINTS
        SM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent class will help to go to next activity using
                // it's object named intent.
                // SecondActivty is the name of new created EmptyActivity.
                LatLng SMBaguio = new LatLng(16.4089, 120.5992);
                map.moveCamera(CameraUpdateFactory.newLatLng(SMBaguio));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(SMBaguio, 17));
                MarkerOptions bag = new MarkerOptions().position(SMBaguio).title("SM Baguio");
                bag.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                map.addMarker(bag);
            }
        });

        airport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent class will help to go to next activity using
                // it's object named intent.
                // SecondActivty is the name of new created EmptyActivity.
                LatLng LoakanAirport = new LatLng(16.3755, 120.6130);
                map.moveCamera(CameraUpdateFactory.newLatLng(LoakanAirport));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(LoakanAirport, 16));
                MarkerOptions mark2 = new MarkerOptions().position(LoakanAirport).title("Loakan Airport");
                mark2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                map.addMarker(mark2);
            }
        });

        caniezo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent class will help to go to next activity using
                // it's object named intent.
                // SecondActivty is the name of new created EmptyActivity.
                LatLng Junkshop = new LatLng(16.409295875685704, 120.58747924628034);
                map.moveCamera(CameraUpdateFactory.newLatLng(Junkshop));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(Junkshop, 30));
                MarkerOptions mark3 = new MarkerOptions().position(Junkshop).title("Caniezo JunkShop");
                mark3.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                map.addMarker(mark3);
            }
        });

        uc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent class will help to go to next activity using
                // it's object named intent.
                // SecondActivty is the name of new created EmptyActivity.
                LatLng Cordilleras = new LatLng(16.4087, 120.5978);
                map.moveCamera(CameraUpdateFactory.newLatLng(Cordilleras));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(Cordilleras, 19));
                MarkerOptions mark4 = new MarkerOptions().position(Cordilleras).title("University of the Cordilleras");
                mark4.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                map.addMarker(mark4);
            }
        });

    }

    private void enableuserlocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == ACCESS_LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == getPackageManager().PERMISSION_GRANTED) {
                enableuserlocation();
            }
            else {

            }
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        addMarker(latLng);
        addCircle(latLng, GEOFENCERADIUS);
    }


    private void addMarker(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
        map.addMarker(markerOptions);
    }
    private void addCircle(LatLng latLng, float radius){
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latLng);
        circleOptions.radius(radius);
        circleOptions.strokeColor(Color.argb(255, 255,0,0));
        circleOptions.fillColor(Color.argb(64,255,0,0));
        circleOptions.strokeWidth(4);
        map.addCircle(circleOptions);
    }
}
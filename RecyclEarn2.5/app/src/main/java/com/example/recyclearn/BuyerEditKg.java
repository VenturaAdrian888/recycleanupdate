package com.example.recyclearn;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class BuyerEditKg extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    public TextView navHeadername;

    public CircleImageView nav_profbuyer;

    private FirebaseUser user;
    FirebaseFirestore fStore;
    private String userID;
    private FirebaseAuth fAuth;
    private StorageReference storageReference;


    private CircleImageView Buyer_Profile;

    private ImageView btn_drawer;

    ConstraintLayout contentView;

    //Set animation when drawer is being dragged
    static final float END_SCALE = 0.8f;
    public static EditText InputKg;
    public Button SaveInputKg;
    boolean isAllFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_edit_kg);


        //drawer
        drawerLayout = findViewById(R.id.buyer_drawer_layout2);
        navigationView = findViewById(R.id.nav_view2);
        btn_drawer = findViewById(R.id.btn_drawer);
        navigationView.setItemIconTintList(null);

        contentView = findViewById(R.id.bfaqContent);
        navigationBuyerDrawer();

        user = FirebaseAuth.getInstance().getCurrentUser();
        fStore = FirebaseFirestore.getInstance();
        userID = user.getUid();

// display onclick on sellerprofile
        Buyer_Profile = (CircleImageView) findViewById(R.id.Buyer_Profile1);
        Buyer_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openbuyerprofile();
            }
        });

//Display user info in drawer
        final DocumentReference[] documentReference = {fStore.collection("Buyer_Users").document(userID)};
        documentReference[0].addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                String username = value.getString("username");
                navHeadername = navigationView.getHeaderView(0).findViewById(R.id.nav_usernamebuyer);
                navHeadername.setText(username);
            }
        });


// display profile picture in navigation drawer
        nav_profbuyer = navigationView.getHeaderView(0).findViewById(R.id.nav_buyerprofile2);
        storageReference = FirebaseStorage.getInstance().getReference().child("user_buyer/" + fAuth.getInstance().getCurrentUser().getUid() + "/profile.jpg");
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(nav_profbuyer);
            }
        });
//display profile picture

        fAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        Buyer_Profile = (CircleImageView) findViewById(R.id.Buyer_Profile1);

        StorageReference profileRef1 = storageReference.child("user_buyer/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(Buyer_Profile);
            }
        });

        InputKg = findViewById(R.id.Buyer_InputKg);
        SaveInputKg = findViewById(R.id.btn_SaveInputKg);
        SaveInputKg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyerinput();

                Intent intent = new Intent(BuyerEditKg.this, BuyerDashboard.class);
                startActivity(intent);

            }
        });
    }
    public boolean updatekg(){
        String p = InputKg.getText().toString().trim();

        if(p.length() > 3){
            InputKg.setError("Maximum of 2 numbers");
            InputKg.requestFocus();
            return false;
        }
        if(p.isEmpty()){
            InputKg.setError("Input is required");
            InputKg.requestFocus();
            return false;
        }
        return true;
    }

    public void buyerinput(){
        isAllFieldsChecked = updatekg();
        if(isAllFieldsChecked){
            String q = InputKg.getText().toString().trim();
            double a = Double.parseDouble(InputKg.getText().toString().trim()); //1kg
            String aa = Double.toString(a);
            double b = a/2; //0.5
            String bb = Double.toString(b);
            double c = b + a; // 1.5
            String cc = Double.toString(c);
            double d = b + c; // 2
            String dd = Double.toString(d);
            double e = b + d; // 2.5
            String ee = Double.toString(e);
            double f = b + e; // 3
            String ff = Double.toString(f);
            double g = b + f; // 3.5
            String gg = Double.toString(g);
            double h = b + g; // 4
            String hh = Double.toString(h);
            double i = b + h; // 4.5
            String ii = Double.toString(i);
            double j = b + i; // 5
            String jj = Double.toString(j);

           Map<String, Object> one = new HashMap<>(); //1kg
            one.put("1 Kilogram",  aa);
            DocumentReference documentReference = fStore.collection("Buyer_Users").document(userID);
           documentReference.collection("Points_Per_KG").document("PointsKG").update(one);

            Map<String, Object> two = new HashMap<>(); //0.5
            two.put("0_5 Kilogram", bb);
            documentReference.collection("Points_Per_KG").document("PointsKG").update(two);

            Map<String, Object> three = new HashMap<>(); //1.5
            three.put("1_5 Kilogram", cc);
            documentReference.collection("Points_Per_KG").document("PointsKG").update(three);

            Map<String, Object> four = new HashMap<>(); //1.5
            four.put("2 Kilogram", dd);
            documentReference.collection("Points_Per_KG").document("PointsKG").update(four);

            Map<String, Object> five = new HashMap<>(); //1.5
            five.put("2_5 Kilogram", ee);
            documentReference.collection("Points_Per_KG").document("PointsKG").update(five);

            Map<String, Object> six = new HashMap<>(); //1.5
            six.put("3 Kilogram", ff);
            documentReference.collection("Points_Per_KG").document("PointsKG").update(six);

            Map<String, Object> seven = new HashMap<>(); //1.5
            seven.put("3_5 Kilogram", gg);
            documentReference.collection("Points_Per_KG").document("PointsKG").update(seven);

            Map<String, Object> eight = new HashMap<>(); //1.5
            eight.put("4 Kilogram", hh);
            documentReference.collection("Points_Per_KG").document("PointsKG").update(eight);

            Map<String, Object> nine = new HashMap<>(); //1.5
            nine.put("4_5 Kilogram", ii);
            documentReference.collection("Points_Per_KG").document("PointsKG").update(nine);

            Map<String, Object> ten = new HashMap<>(); //1.5
            ten.put("5 Kilogram", jj);
            documentReference.collection("Points_Per_KG").document("PointsKG").update(ten);

            Toast.makeText(BuyerEditKg.this,"Updated",Toast.LENGTH_SHORT).show();

        }
    }

    private void navigationBuyerDrawer() {
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
// navigation menu items declaration
        navigationView.setNavigationItemSelectedListener(this);



        btn_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else drawerLayout.openDrawer(GravityCompat.START);
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

    // back button on drawer navigation
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
    //drawer link to other activity
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {

        switch (menuitem.getItemId()) {
            case R.id.nav_buyerhome:
                Intent intent = new Intent(this, BuyerDashboard.class);
                startActivity(intent);
                break;
            case  R.id.nav_buyerprofile:
                Intent intent1 = new Intent(this, BuyerProfile.class);
                startActivity(intent1);
                break;
            case R.id.nav_buyerinfo:
                Intent intent3 = new Intent(this, BuyerAboutUs.class);
                startActivity(intent3);
                break;
            case R.id.nav_buyerSecurity:
                Intent security = new Intent(this, Buyer_Security.class);
                startActivity(security);
                break;
            case R.id.nav_buyerlogout:
                // YUNG CODE PARA DI NA BUMALIK ULIT SA ACTIVITY AFTER NAG LOG OUT YUNG USER-------------
                Intent intent4 = new Intent(getBaseContext(),MainActivity.class);
                intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // YUNG CODE PARA DI NA BUMALIK ULIT SA ACTIVITY AFTER NAG LOG OUT YUNG USER-------------
                startActivity(intent4);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    // Open seller profile
    public void openbuyerprofile(){
        Intent intent = new Intent(this, BuyerProfile.class);
        startActivity(intent);
    }
}
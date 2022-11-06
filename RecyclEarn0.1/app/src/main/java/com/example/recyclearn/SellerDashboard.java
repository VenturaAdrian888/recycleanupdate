package com.example.recyclearn;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class SellerDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    public TextView navHeadername;

    public CircleImageView nav_profseller;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private FirebaseAuth fAuth;
    private StorageReference storageReference;

    private CircleImageView Seller_Profile;

    private Button sellerRedeem;

    private ImageView dash_seller_location, dash_seller_location1;
    private TextView dash_seller_location2;

    private ImageView dash_seller_trade,dash_seller_trade1;
    private TextView dash_seller_trade2;

    private ImageView dash_seller_leaderboards, dash_seller_leaderboards1;
    private TextView dash_seller_leaderboards2;

    private ImageView dash_seller_faqs, dash_seller_faqs1;
    private TextView dash_seller_faqs2;

    private ImageView btn_drawer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_dashboard);
//drawer navigation
        drawerLayout = findViewById(R.id.seller_drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        btn_drawer = findViewById(R.id.btn_drawer);

        navigationSellerDrawer();


//display username in welcome back,
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Seller_Users");
        userID = user.getUid();

        final TextView usernameTextView = (TextView) findViewById(R.id.usernameseller);
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User_Seller userprofile = snapshot.getValue(User_Seller.class);

                if (userprofile != null) {
                    String username = userprofile.username;
                    usernameTextView.setText(username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SellerDashboard.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
//display onClcik to redeem seller
        sellerRedeem = (Button) findViewById(R.id.btn_seller_redeem);
        sellerRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opensellerRedeem();
            }
        });

//display onlick to seller profile
        Seller_Profile = (CircleImageView) findViewById(R.id.Seller_Profile1);
        Seller_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opensellerprofile();
            }
        });
//display onclick on seller location
        dash_seller_location = (ImageView) findViewById(R.id.dash_seller_location);
        dash_seller_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opensellerlocation();
            }
        });
        dash_seller_location1 = (ImageView) findViewById(R.id.dash_seller_location1);
        dash_seller_location1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opensellerlocation();
            }
        });
        dash_seller_location2 = (TextView) findViewById(R.id.dash_seller_location2);
        dash_seller_location2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opensellerlocation();
            }
        });
//Display onclick on seller trade
        dash_seller_trade = (ImageView) findViewById(R.id.dash_seller_trade);
        dash_seller_trade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opensellertrade();
            }
        });
        dash_seller_trade1 = (ImageView) findViewById(R.id.dash_seller_trade1);
        dash_seller_trade1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opensellertrade();
            }
        });
        dash_seller_trade2 = (TextView) findViewById(R.id.dash_seller_trade2);
        dash_seller_trade2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opensellertrade();
            }
        });
//Display onClick on seller leaderboards
        dash_seller_leaderboards = (ImageView) findViewById(R.id.dash_seller_leaderboards);
        dash_seller_leaderboards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opensellerLeaderboards();
            }
        });
        dash_seller_leaderboards1 = (ImageView) findViewById(R.id.dash_seller_leaderboards1);
        dash_seller_leaderboards1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opensellerLeaderboards();
            }
        });
        dash_seller_leaderboards2 = (TextView) findViewById(R.id.dash_seller_leaderboards2);
        dash_seller_leaderboards2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opensellerLeaderboards();
            }
        });
//Display onclick seller faqs
        dash_seller_faqs = (ImageView) findViewById(R.id.dash_seller_faqs);
        dash_seller_faqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opensellerFaqs();
            }
        });
        dash_seller_faqs1 = (ImageView) findViewById(R.id.dash_seller_faqs1);
        dash_seller_faqs1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opensellerFaqs();
            }
        });
        dash_seller_faqs2 = (TextView) findViewById(R.id.dash_seller_faqs2);
        dash_seller_faqs2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opensellerFaqs();
            }
        });

//Display user info in drawer
        navHeadername = navigationView.getHeaderView(0).findViewById(R.id.nav_usernameseller);
        reference = FirebaseDatabase.getInstance().getReference().child("Seller_Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                navHeadername.setText(snapshot.child("username").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SellerDashboard.this, "Something wrong happened!", Toast.LENGTH_SHORT).show();
            }
        });
// dispaly profile picture in navigation drawer
        nav_profseller = navigationView.getHeaderView(0).findViewById(R.id.nav_sellerprofile2);
        storageReference = FirebaseStorage.getInstance().getReference().child("user_seller/"+ fAuth.getInstance().getCurrentUser().getUid()+"/profile.jpg");
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(nav_profseller);
            }
        });
//display profile picture
        fAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        Seller_Profile =(CircleImageView) findViewById(R.id.Seller_Profile1);

        StorageReference profileRef1 = storageReference.child("user_seller/"+ fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(Seller_Profile);
            }
        });


    }

    private void navigationSellerDrawer() {
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
// navigation menu items declaration
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_sellerhome);
        navigationView.setCheckedItem(R.id.nav_sellerprofile);
        navigationView.setCheckedItem(R.id.nav_sellerlocation);
        navigationView.setCheckedItem(R.id.nav_sellerinfo);
        navigationView.setCheckedItem(R.id.nav_sellerlogout);
        btn_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }


    //drawer backbutton
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
            case R.id.nav_sellerhome:
                break;
            case  R.id.nav_sellerprofile:
                Intent intent = new Intent(SellerDashboard.this, SellerProfile.class);
                startActivity(intent);
                break;
            case R.id.nav_sellerlocation:
                Intent intent2 = new Intent(SellerDashboard.this, SellerLocation.class);
                startActivity(intent2);
                break;
            case R.id.nav_sellerinfo:
                Intent intent1 = new Intent(SellerDashboard.this, SellerFaqs.class);
                startActivity(intent1);
                break;
            case R.id.nav_sellerlogout:
                Intent intent3 = new Intent(SellerDashboard.this, MainActivity.class);
                startActivity(intent3);
                break;

            // case R.id.: "dito ilalagay yung pag na click tas ma direct to other activity"
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;



    }
    //Open seller Redeem page
    public void opensellerRedeem(){
        Intent intent = new Intent(SellerDashboard.this, SellerRedeem.class);
        startActivity(intent);
    }
    // Open seller profile
     public void opensellerprofile(){
         Intent intent = new Intent(SellerDashboard.this, SellerProfile.class);
         startActivity(intent);
     }
     //Open seller location
    public void opensellerlocation(){
        Intent intent = new Intent(SellerDashboard.this, SellerLocation.class);
        startActivity(intent);
    }
    //Open seller trade a bottle
    public void opensellertrade(){
        Intent intent = new Intent(SellerDashboard.this, SellerTrade.class);
        startActivity(intent);
    }
    //Open seller Leaderboards
    public void opensellerLeaderboards(){
        Intent intent = new Intent(SellerDashboard.this, SellerLeaderboards.class);
        startActivity(intent);
    }
    //Open seller FAQS
    public void opensellerFaqs(){
        Intent intent = new Intent(SellerDashboard.this, SellerFaqs.class);
        startActivity(intent);
    }
}
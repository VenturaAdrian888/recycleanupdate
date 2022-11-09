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
import pl.droidsonroids.gif.GifImageView;

public class BuyerDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public DrawerLayout bdrawerLayout;
    public NavigationView bnavigationView;
    public TextView bnavHeadername;

    public CircleImageView nav_buyerprofile;

    private FirebaseUser buser;
    private DatabaseReference breference;
    private String buserID;
    private FirebaseAuth fAuth;
    private StorageReference bstorageReference;

    private CircleImageView Buyer_Profile;

    private Button buyerRedeem;

    private ImageView btn_drawer;

    private ImageView dash_buyer_location, dash_buyer_location1;
    private TextView dash_buyer_location2;

    private ImageView dash_buyer_trade, dash_buyer_trade1;
    private TextView dash_buyer_trade2;

    private ImageView dash_buyer_leaderboards, dash_buyer_leaderboards1;
    private TextView dash_buyer_leaderboards2;

    private ImageView dash_buyer_faqs, dash_buyer_faqs1;
    private TextView dash_buyer_faqs2;

    private GifImageView coin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_dashboard);

        coin = findViewById(R.id.coin);



        //drawer navigation
        bdrawerLayout = findViewById(R.id.buyer_drawer_layout);
        bnavigationView = findViewById(R.id.nav_view);
        btn_drawer = findViewById(R.id.btn_drawer);


        navigationBuyerDrawer();
//display username in welcome back,
        buser = FirebaseAuth.getInstance().getCurrentUser();
        breference = FirebaseDatabase.getInstance().getReference("Buyer_Users");
        buserID = buser.getUid();

        final TextView usernameTextView = (TextView) findViewById(R.id.usernamebuyer);
        breference.child(buserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User_Buyer userprofile = snapshot.getValue(User_Buyer.class);

                if (userprofile != null) {
                    String username = userprofile.username;
                    usernameTextView.setText(username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BuyerDashboard.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
//display onClcik to redeem buyer
        buyerRedeem = (Button) findViewById(R.id.btn_buyer_redeem);
        buyerRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openbuyerRedeem();
            }
        });


//display onlick to buyer profile
        Buyer_Profile = (CircleImageView) findViewById(R.id.Buyer_Profile);
        Buyer_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openbuyerprofile();
            }
        });
//display onclick on buyer location
        dash_buyer_location = (ImageView) findViewById(R.id.dash_buyer_location);
        dash_buyer_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openbuyerlocation();
            }
        });
        dash_buyer_location1 = (ImageView) findViewById(R.id.dash_buyer_location1);
        dash_buyer_location1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openbuyerlocation();
            }
        });
        dash_buyer_location2 = (TextView) findViewById(R.id.dash_buyer_location2);
        dash_buyer_location2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openbuyerlocation();
            }
        });
//Display onclick on buyer trade
        dash_buyer_trade = (ImageView) findViewById(R.id.dash_buyer_trade);
        dash_buyer_trade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openbuyertrade();
            }
        });
        dash_buyer_trade1 = (ImageView) findViewById(R.id.dash_buyer_trade1);
        dash_buyer_trade1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openbuyertrade();
            }
        });
        dash_buyer_trade2 = (TextView) findViewById(R.id.dash_buyer_trade2);
        dash_buyer_trade2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openbuyertrade();
            }
        });
//Display onClick on buyer leaderboards
        dash_buyer_leaderboards = (ImageView) findViewById(R.id.dash_buyer_leaderboards);
        dash_buyer_leaderboards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openbuyerLeaderboards();
            }
        });
        dash_buyer_leaderboards1 = (ImageView) findViewById(R.id.dash_buyer_leaderboards1);
        dash_buyer_leaderboards1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openbuyerLeaderboards();
            }
        });
        dash_buyer_leaderboards2 = (TextView) findViewById(R.id.dash_buyer_leaderboards2);
        dash_buyer_leaderboards2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openbuyerLeaderboards();
            }
        });
//Display onclick buyer faqs
        dash_buyer_faqs = (ImageView) findViewById(R.id.dash_buyer_faqs);
        dash_buyer_faqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openbuyerFaqs();
            }
        });
        dash_buyer_faqs1 = (ImageView) findViewById(R.id.dash_buyer_faqs1);
        dash_buyer_faqs1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openbuyerFaqs();
            }
        });
        dash_buyer_faqs2 = (TextView) findViewById(R.id.dash_buyer_faqs2);
        dash_buyer_faqs2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openbuyerFaqs();

            }
        });

//Display user info in drawer
        bnavHeadername = bnavigationView.getHeaderView(0).findViewById(R.id.nav_usernamebuyer);
        breference = FirebaseDatabase.getInstance().getReference().child("Buyer_Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        breference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bnavHeadername.setText(snapshot.child("username").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BuyerDashboard.this, "Something wrong happened!", Toast.LENGTH_SHORT).show();
            }
        });
// dispaly profile picture in navigation drawer
        nav_buyerprofile = bnavigationView.getHeaderView(0).findViewById(R.id.nav_buyerprofile2);
        bstorageReference = FirebaseStorage.getInstance().getReference().child("user_buyer/" + fAuth.getInstance().getCurrentUser().getUid() + "/profile.jpg");
        bstorageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(nav_buyerprofile);
            }
        });
//display profile picture
        fAuth = FirebaseAuth.getInstance();
        bstorageReference = FirebaseStorage.getInstance().getReference();
        Buyer_Profile = (CircleImageView) findViewById(R.id.Buyer_Profile);

        StorageReference profileRef1 = bstorageReference.child("user_buyer/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(Buyer_Profile);
            }
        });


    }

    private void navigationBuyerDrawer() {
        bnavigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, bdrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        bdrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
// navigation menu items declaration
        bnavigationView.setNavigationItemSelectedListener(this);
        bnavigationView.setCheckedItem(R.id.nav_buyerhome);
        bnavigationView.setCheckedItem(R.id.nav_buyerprofile);
        bnavigationView.setCheckedItem(R.id.nav_buyerlocation);
        bnavigationView.setCheckedItem(R.id.nav_buyerinfo);
        bnavigationView.setCheckedItem(R.id.nav_buyerlogout);

        btn_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bdrawerLayout.isDrawerVisible(GravityCompat.START)) {
                    bdrawerLayout.closeDrawer(GravityCompat.START);
                } else bdrawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    //drawer backbutton
    @Override
    public void onBackPressed() {
        if (bdrawerLayout.isDrawerOpen(GravityCompat.START)) {
            bdrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    //drawer link to other activity
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {

        switch (menuitem.getItemId()) {
            case R.id.nav_buyerhome:
                break;
            case R.id.nav_buyerprofile:
                Intent intent = new Intent(BuyerDashboard.this, BuyerProfile.class);
                startActivity(intent);
                break;
            case R.id.nav_buyerlocation:
                Intent intent2 = new Intent(BuyerDashboard.this, BuyerLocation.class);
                startActivity(intent2);
                break;
            case R.id.nav_buyerinfo:
                Intent intent1 = new Intent(BuyerDashboard.this, XXX.class);
                startActivity(intent1);
                break;
            case R.id.nav_buyerlogout:
                Intent intent3 = new Intent(BuyerDashboard.this, MainActivity.class);
                startActivity(intent3);
                break;

            // case R.id.: "dito ilalagay yung pag na click tas ma direct to other activity"
        }
        bdrawerLayout.closeDrawer(GravityCompat.START);
        return true;


    }

    //Open buyer Redeem page
    public void openbuyerRedeem() {
        Intent intent = new Intent(BuyerDashboard.this, BuyerRedeem.class);
        startActivity(intent);


    }

    // Open buyer profile
    public void openbuyerprofile() {
        Intent intent = new Intent(BuyerDashboard.this, BuyerProfile.class);
        startActivity(intent);
    }

    //Open buyer location
    public void openbuyerlocation() {
        Intent intent = new Intent(BuyerDashboard.this, BuyerLocation.class);
        startActivity(intent);
    }

    //Open buyer trade a bottle
    public void openbuyertrade() {
        Intent intent = new Intent(BuyerDashboard.this, BuyerTrade.class);
        startActivity(intent);
    }

    //Open buyer Leaderboards
    public void openbuyerLeaderboards() {
        Intent intent = new Intent(BuyerDashboard.this, BuyerLearderboards.class);
        startActivity(intent);
    }

    //Open buyer FAQS
    public void openbuyerFaqs() {
        Intent intent = new Intent(BuyerDashboard.this, BuyerFaqs.class);
        startActivity(intent);
    }
}
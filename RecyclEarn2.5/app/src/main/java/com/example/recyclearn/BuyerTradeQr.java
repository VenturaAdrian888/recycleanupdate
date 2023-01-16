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
import com.google.firebase.database.Query;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class BuyerTradeQr extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

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

    public static TextView user_output;
    public static Button scanqr, ok, accept;
    public static String sIdNum;
    public Query query;
    public Integer detect;
    String sellerPoints, sellerKG;
    String userIDSeller, name;
    Boolean okButtonIsClicked = false;


    ConstraintLayout contentView;

    //Set animation when drawer is being dragged
    static final float END_SCALE = 0.8f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_trade_qr);

        user = FirebaseAuth.getInstance().getCurrentUser();
        fStore = FirebaseFirestore.getInstance();
        userID = user.getUid();

        //drawer
        drawerLayout = findViewById(R.id.buyer_drawer_layout2);
        navigationView = findViewById(R.id.nav_view2);
        btn_drawer = findViewById(R.id.btn_drawer);
        navigationView.setItemIconTintList(null);


        contentView = findViewById(R.id.qrtradeContent);
        navigationBuyerDrawer();

// display onclick on buyerprofile
        Buyer_Profile = (CircleImageView) findViewById(R.id.Buyer_Profile1);
        Buyer_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openbuyerprofile();
            }
        });

//Display user info in drawer
        DocumentReference documentReference = fStore.collection("Buyer_Users").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                String username = value.getString("username");
                navHeadername = navigationView.getHeaderView(0).findViewById(R.id.nav_usernamebuyer);
                navHeadername.setText(username);
            }
        });

// display profile picture in navigation drawer
        nav_profbuyer = navigationView.getHeaderView(0).findViewById(R.id.nav_buyerprofile2);
        storageReference = FirebaseStorage.getInstance().getReference().child("user_buyer/"+ fAuth.getInstance().getCurrentUser().getUid()+"/profile.jpg");
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(nav_profbuyer);
            }
        });
//display profile picture

        fAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        Buyer_Profile =(CircleImageView) findViewById(R.id.Buyer_Profile1);

        StorageReference profileRef1 = storageReference.child("user_buyer/"+ fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(Buyer_Profile);
            }
        });

        user_output = findViewById(R.id.user_output);
        scanqr = findViewById(R.id.scanqr);

        scanqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),BuyerScanner.class));
            }
        });

        ok = findViewById(R.id.Ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = user_output.getText().toString();
                fStore.collection("Seller_Users").whereEqualTo("fullName", name).get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        // Getting the User ID Of seller based off of the name scanned through QR
                        for(DocumentSnapshot snapshot: snapshotList)
                            userIDSeller = snapshot.getId();

                        if(user_output.length() <= 0)
                            Toast.makeText(BuyerTradeQr.this, "Please scan qr code", Toast.LENGTH_SHORT).show();
                        else{
                            Toast.makeText(BuyerTradeQr.this, "User exist, please proceed on accepting bottles", Toast.LENGTH_SHORT).show();

                            DocumentReference documentReference2 = fStore.collection("Seller_Users").document(userIDSeller);
                            documentReference2.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                    sellerPoints = value.getString("sellerPoints");
                                    sellerKG = value.getString("sellerKgInput");
                                    okButtonIsClicked = true;
                                }
                            });
                        }
                    }
                });
            }
        });

        accept = findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(okButtonIsClicked == false){
                    Toast.makeText(BuyerTradeQr.this, "Please Scan Seller's QR Code", Toast.LENGTH_SHORT).show();

                } else{
                    //Gets the current points of the seller
                    Double currentPoints = Double.parseDouble(sellerPoints);

                    //Gets the new kg(new points based on kg input) input of the seller
                    Double newPointsBasedOnKGInput = Double.parseDouble(sellerKG) * 20;

                    //Adds the current points of the user and the new points based on the kg input of the seller
                    Double finalSellerPoints =  currentPoints + newPointsBasedOnKGInput;
                    Toast.makeText(BuyerTradeQr.this, "Bottles Accepted", Toast.LENGTH_SHORT).show();

                    // Apply changes on seller (Adding of points)
                    Map<String, Object> user = new HashMap<>();
                    user.put("sellerPoints", finalSellerPoints.toString());
                    DocumentReference documentReference = fStore.collection("Seller_Users").document(userIDSeller);
                    documentReference.update(user);

                    Toast.makeText(BuyerTradeQr.this,+newPointsBasedOnKGInput+" Points Sent to " +name+ ", Transaction Successful!",Toast.LENGTH_SHORT).show();

                    Intent same = new Intent(BuyerTradeQr.this,BuyerTradeQr.class);
                    same.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(same);
                }
            }
        });
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
                Intent intent = new Intent(BuyerTradeQr.this, BuyerDashboard.class);
                startActivity(intent);
                break;
            case  R.id.nav_buyerprofile:
                Intent intent1 = new Intent(BuyerTradeQr.this, BuyerProfile.class);
                startActivity(intent1);
                break;
            case R.id.nav_buyerinfo:
                Intent intent3 = new Intent(BuyerTradeQr.this, BuyerAboutUs.class);
                startActivity(intent3);
                break;
            case R.id.nav_buyerSecurity:
                Intent security = new Intent(BuyerTradeQr.this, Buyer_Security.class);
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
        Intent intent = new Intent(BuyerTradeQr.this, BuyerProfile.class);
        startActivity(intent);
    }
}
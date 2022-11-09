package com.example.recyclearn;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
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
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class SellerProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    public TextView navHeadername;
    public CircleImageView nav_profseller;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private FirebaseAuth fAuth;

    private CircleImageView profilepic, headerprofilepic, nav_profile;
    private Button changeprofilepic;
    private StorageReference storageReference;
    
    private ImageView btn_drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_profile);

        drawerLayout = findViewById(R.id.seller_drawer_layout1);
        navigationView = findViewById(R.id.nav_view1);
        btn_drawer = findViewById(R.id.btn_drawer);

        navigationSellerDrawer();

//displayname
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Seller_Users");
        userID = user.getUid();

        final TextView usernameTextView = (TextView) findViewById(R.id.usernameseller);
        final TextView fullnameTextView = (TextView) findViewById(R.id.fullnameseller);
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User_Seller userprofile = snapshot.getValue(User_Seller.class);

                if (userprofile != null) {
                    String username = userprofile.username;
                    String fullname = userprofile.fullName;
                    usernameTextView.setText(username);
                    fullnameTextView.setText(fullname);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SellerProfile.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
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
                Toast.makeText(SellerProfile.this, "Something wrong happened!", Toast.LENGTH_SHORT).show();
            }
        });

// dispaly profile picture in navigation drawer
        nav_profseller = navigationView.getHeaderView(0).findViewById(R.id.nav_sellerprofile2);
        storageReference = FirebaseStorage.getInstance().getReference().child("user_seller/" + fAuth.getInstance().getCurrentUser().getUid() + "/profile.jpg");
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(nav_profseller);
            }
        });
//change profile
        profilepic = findViewById(R.id.profilepicseller);
        headerprofilepic = findViewById(R.id.profilepicseller1);
        changeprofilepic = findViewById(R.id.btn_changeprofilepicseller);
        fAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef1 = storageReference.child("user_seller/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(headerprofilepic);
            }
        });
//change profile
        StorageReference profileRef = storageReference.child("user_seller/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profilepic);
            }
        });

        changeprofilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1000);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imageUri = data.getData();
                //profilepic.setImageURI(imageUri);

                uploadImagetoFirebase(imageUri);
            }
        }
    }

    private void uploadImagetoFirebase(Uri imageUri) {
        final StorageReference fileRef = storageReference.child("user_seller/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri).into(profilepic);
                            }
                        });

                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SellerProfile.this, "Image Uploaded Fail!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    //drawer back button
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    //drawer elements
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {

        switch (menuitem.getItemId()) {
            case R.id.nav_sellerhome:
                Intent intent = new Intent(SellerProfile.this, SellerDashboard.class);
                startActivity(intent);
                break;
            case R.id.nav_sellerprofile:
                break;
            case R.id.nav_sellerlocation:
                Intent intent2 = new Intent(SellerProfile.this, SellerLocation.class);
                startActivity(intent2);
                break;
            case R.id.nav_sellerinfo:
                Intent intent3 = new Intent(SellerProfile.this, SellerFaqs.class);
                startActivity(intent3);
                break;
            case R.id.nav_sellerlogout:
                Intent intent4 = new Intent(SellerProfile.this, MainActivity.class);
                startActivity(intent4);
                break;
                // case R.id.: "dito ilalagay yung pag na click tas ma direct to other activity"
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}
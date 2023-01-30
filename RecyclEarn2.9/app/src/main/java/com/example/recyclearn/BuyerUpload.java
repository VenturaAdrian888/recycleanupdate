package com.example.recyclearn;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class BuyerUpload extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    public TextView navHeadername;

    public CircleImageView nav_profbuyer;

    private FirebaseUser user;
    FirebaseFirestore fStore;
    private String userID;
    private FirebaseAuth fAuth;
    private StorageReference storageReference, storageReference0;

    private CircleImageView Buyer_Profile;

    private ImageView btn_drawer;

    ConstraintLayout contentView;

    private ImageView btn_UploadProduct, ProductPicture;
    private EditText ProductTitle, ProductPoints;
    private Button btn_Upload, btn_viewproducts;

    public Uri imageUri, url;
    private FirebaseStorage storage;


    //Set animation when drawer is being dragged
    static final float END_SCALE = 0.8f;

    private static final String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_upload);


        btn_viewproducts = findViewById(R.id.btn_viewproducts);
        btn_viewproducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reward = new Intent(BuyerUpload.this, BuyerViewProductList.class);
                startActivity(reward);
            }
        });

        storage = FirebaseStorage.getInstance();
        storageReference0 = storage.getReference();

        btn_UploadProduct = findViewById(R.id.btn_UploadProduct);
        btn_UploadProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });

        ProductPicture = findViewById(R.id.ProductPicture);

        ProductTitle = findViewById(R.id.ProductTitle);
        ProductPoints = findViewById(R.id.ProductPoints);

        btn_Upload = findViewById(R.id.btn_Upload);


        btn_Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputData();
                clearData();
            }
        });


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


    }

    //end of oncreate

    final String timestamp = "" + System.nanoTime();


    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && requestCode == RESULT_OK && data != null && data.getData() != null)
            ;

        imageUri = data.getData();
        ProductPicture.setImageURI(imageUri);

    }

    private String productTitle, productPoints;

    private void inputData() {
        //1input data
        productTitle = ProductTitle.getText().toString().trim();
        productPoints = ProductPoints.getText().toString().trim();

        if (TextUtils.isEmpty(productTitle)) {
            ProductTitle.setError("Title is required!");
            ProductTitle.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(productPoints)) {
            ProductPoints.setError("Points is required!");
            ProductPoints.requestFocus();
            return;
        }
        uploadProduct();
    }

    private String getRandomString(final int sizeOfRandomString) {
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(sizeOfRandomString);
        for (int i = 0; i < sizeOfRandomString; ++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();

    }


    private void uploadProduct() {


        String randomID = getRandomString(20);

        if (imageUri == null) {

            DocumentReference documentReference4 = fStore.collection("Buyer_Users").document(userID);
            documentReference4.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    String shopname1 = value.getString("username");

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("productId", "" + randomID);
                    hashMap.put("productTitle", "" + productTitle);
                    hashMap.put("productPoints", "" + productPoints + " " + "Points");
                    hashMap.put("productIcon", "");
                    hashMap.put("timestamp", "" + timestamp);
                    hashMap.put("uid", "" + fAuth.getUid());
                    hashMap.put("buyershopname", "" + shopname1);

                    DocumentReference reference = FirebaseFirestore.getInstance().collection("Buyer_Users").document(userID);
                    reference.collection("products").document(randomID).set(hashMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(BuyerUpload.this, "Product Added!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(BuyerUpload.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                }
                });
        }
        else

                {
                    StorageReference store = FirebaseStorage.getInstance().getReference("user_buyer/" + userID + "/Products/" + "" + randomID);
                    store.putFile(imageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                    DocumentReference documentReference0 = fStore.collection("Buyer_Users").document(userID);
                                    documentReference0.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                            String shopname = value.getString("username");


                                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                            while (!uriTask.isSuccessful()) ;
                                            Uri downloadImageUri = uriTask.getResult();

                                            if (uriTask.isSuccessful()) {
                                                HashMap<String, Object> hashMap = new HashMap<>();
                                                hashMap.put("productId", "" + randomID);
                                                hashMap.put("productTitle", "" + productTitle);
                                                hashMap.put("productPoints", "" + productPoints + " " + "Points");
                                                hashMap.put("productIcon", "" + downloadImageUri);
                                                hashMap.put("timestamp", "" + timestamp);
                                                hashMap.put("uid", "" + fAuth.getUid());
                                                hashMap.put("ShopName", "" + shopname);

                                                DocumentReference reference = FirebaseFirestore.getInstance().collection("Buyer_Users").document(userID);
                                                reference.collection("products").document(randomID).set(hashMap)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                Toast.makeText(BuyerUpload.this, "Product Added!", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(BuyerUpload.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            }
                                        }
                                    });


                                }
                            });
                }

    }



            private void clearData () {
                //clear data after uploading
                ProductTitle.setText("");
                ProductPoints.setText("");
                ProductPicture.setImageResource(R.drawable.put);
            }


            //////////////////////////////////////////////////////////////
            private void navigationBuyerDrawer () {
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

            private void animateNavigationDrawer () {
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

            // back button on drawer navigation
            @Override
            public void onBackPressed () {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    super.onBackPressed();
                }

            }

            //drawer link to other activity
            @Override
            public boolean onNavigationItemSelected (@NonNull MenuItem menuitem){

                switch (menuitem.getItemId()) {
                    case R.id.nav_buyerhome:
                        Intent intent = new Intent(BuyerUpload.this, BuyerDashboard.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_buyerprofile:
                        Intent intent1 = new Intent(BuyerUpload.this, BuyerProfile.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_buyerinfo:
                        Intent intent3 = new Intent(BuyerUpload.this, BuyerAboutUs.class);
                        startActivity(intent3);
                        break;
                    case R.id.nav_buyerSecurity:
                        Intent security = new Intent(BuyerUpload.this, Buyer_Security.class);
                        startActivity(security);
                        break;
                    case R.id.nav_buyerlogout:
                        // YUNG CODE PARA DI NA BUMALIK ULIT SA ACTIVITY AFTER NAG LOG OUT YUNG USER-------------
                        Intent intent4 = new Intent(getBaseContext(), MainActivity.class);
                        intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        // YUNG CODE PARA DI NA BUMALIK ULIT SA ACTIVITY AFTER NAG LOG OUT YUNG USER-------------
                        startActivity(intent4);
                        break;

                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }

            // Open seller profile
            public void openbuyerprofile () {
                Intent intent = new Intent(BuyerUpload.this, BuyerProfile.class);
                startActivity(intent);
            }
        }
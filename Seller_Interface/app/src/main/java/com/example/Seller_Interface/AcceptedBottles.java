package com.example.Seller_Interface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AcceptedBottles extends AppCompatActivity {

    ImageView acceptedbottlesProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_bottles);

        acceptedbottlesProfile = findViewById(R.id.acceptedbottlesProfile);

        acceptedbottlesProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent class will help to go to next activity using
                // it's object named intent.
                // SecondActivty is the name of new created EmptyActivity.
                Intent acceptedbottles = new Intent(AcceptedBottles.this, Profile.class);
                startActivity(acceptedbottles);
            }
        });
    }
}
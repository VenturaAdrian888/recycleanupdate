package com.example.Seller_Interface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class CodeGenerator extends AppCompatActivity {

    TextView nearestLoc;
    Button qr;
    EditText text;
    ImageView codeImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_generator);


        nearestLoc = findViewById(R.id.locations);
        qr = findViewById(R.id.QrCode);
        (text) = findViewById(R.id.Text);
        text.setText("KG: ", TextView.BufferType.EDITABLE);
        codeImage = findViewById(R.id.imageCode);


        nearestLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent class will help to go to next activity using
                // it's object named intent.
                // SecondActivty is the name of new created EmptyActivity.
                Intent nearest = new Intent(CodeGenerator.this, MapLocation.class);
                startActivity(nearest);
            }
        });

        qr.setOnClickListener(view -> {

            String myText1 = text.getText().toString().trim();
            MultiFormatWriter mWriter = new MultiFormatWriter();

            try {

                BitMatrix mMatrix1 = mWriter.encode(myText1, BarcodeFormat.QR_CODE, 400,400);

                BarcodeEncoder mEncoder = new BarcodeEncoder();
                Bitmap bitmap1 = mEncoder.createBitmap(mMatrix1);//creating bitmap of code
                codeImage.setImageBitmap(bitmap1);

                //to Hide the keyboard
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(text.getApplicationWindowToken(),0);

            } catch (WriterException e) {
                e.printStackTrace();
            }

        });

    }
}
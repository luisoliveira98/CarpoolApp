package com.example.android.carpoolapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class PartilharViagem extends AppCompatActivity {
    private String keyViagem;
    private ImageView qrCode;
    private TextView viage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partilhar_viagem);

        qrCode = (ImageView) findViewById(R.id.qrCode);
        viage = (TextView) findViewById(R.id.infViagem);

        keyViagem = (String) getIntent().getExtras().getSerializable("key");
        Viagem v = (Viagem) getIntent().getExtras().getSerializable("viagem");

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        DatabaseReference mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();


        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(keyViagem, BarcodeFormat.QR_CODE, 600, 600);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrCode.setImageBitmap(bitmap);
            viage.setText(v.toString());
        } catch (WriterException e) {
            e.printStackTrace();
        }


    }
}

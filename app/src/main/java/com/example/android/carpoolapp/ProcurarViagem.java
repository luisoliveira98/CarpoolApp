package com.example.android.carpoolapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.Serializable;

public class ProcurarViagem extends AppCompatActivity {

    private TextView showdate;
    private TextView showtime;
    private EditText mPartida;
    private EditText mDestino;

    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    public static final String PARTIDA_MESSAGE = "com.example.android.carpoolapp.extra.MESSAGE1";
    public static final String DESTINO_MESSAGE = "com.example.android.carpoolapp.extra.MESSAGE2";
    public static final String HORA_MESSAGE = "com.example.android.carpoolapp.extra.MESSAGE3";
    public static final String DATA_MESSAGE = "com.example.android.carpoolapp.extra.MESSAGE4";
    //public static final String KEY = "com.example.android.extra.KEY";
    private Button scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procurar_viagem);
        showdate = (TextView) findViewById(R.id.showDate);
        showtime = (TextView) findViewById(R.id.showTime);
        mPartida = (EditText) findViewById(R.id.editPPartida);
        mDestino = (EditText) findViewById(R.id.inputPDestino);
        //FirebaseUser
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        //FirebaseDatabase
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        scan = (Button) findViewById(R.id.qr_code);
        final Activity activity = this;
        scan.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Camera Scan");
                integrator.setCameraId(0);
                integrator.initiateScan();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result!=null){
            if (result.getContents()!=null){
                final Intent intent = new Intent(ProcurarViagem.this, DetalhesViagem2.class);
                Query query = mFirebaseDatabaseReference.child("viagens").child(result.getContents());
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Viagem viagem = dataSnapshot.getValue(Viagem.class);
                        intent.putExtra("viagem", viagem);
                        intent.putExtra("key", result.getContents());
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        } else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void showResults(View view) {
        Intent intent = new Intent(this, Resultados.class);
        String date = showdate.getText().toString();
        String hm = showtime.getText().toString();
        String partida = mPartida.getText().toString();
        String destino = mDestino.getText().toString();
        intent.putExtra(PARTIDA_MESSAGE, partida);
        intent.putExtra(DESTINO_MESSAGE, destino);
        intent.putExtra(HORA_MESSAGE, hm);
        intent.putExtra(DATA_MESSAGE, date);
        startActivity(intent);
    }

    public void showDataPicker(View view) {
        DialogFragment dataPicker = new DataPickerFragment();
        dataPicker.show(getSupportFragmentManager(), getString(R.string.dataPicker));
    }

    public void getDateFromPicker(int year, int month, int day) {
        String data = Integer.toString(day) + "/" + Integer.toString(month + 1) + "/" + Integer.toString(year);
        showdate.setText(data);
    }

    public void showTimePicker(View view) {
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), getString(R.string.timePicker));
    }

    public void getTimeFromPicker(int hourOfDay, int minute) {
        String time = Integer.toString(hourOfDay) + ":" + Integer.toString(minute);
        showtime.setText(time);
    }
}

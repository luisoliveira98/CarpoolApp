package com.example.android.carpoolapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProcurarViagem extends AppCompatActivity {

    private TextView showdate;
    private TextView showtime;
    private EditText mPartida;
    private EditText mDestino;
    private EditText mHora;

    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    /*public static String PARTIDA = "com.example.android.carpoolapp.extra.MESSAGE";
    public static String DESTINO = "com.example.android.carpoolapp.extra.MESSAGE";
    public static String HORA = "com.example.android.carpoolapp.extra.MESSAGE";
    public static String DATA = "com.example.android.carpoolapp.extra.MESSAGE";*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procurar_viagem);
        showdate = (TextView) findViewById(R.id.showDate);
        showtime = (TextView) findViewById(R.id.showTime);
        mPartida = (EditText) findViewById(R.id.inputPartida);
        mDestino = (EditText) findViewById(R.id.inputDestino);
        //mList = findViewById(R.id.listResultados);
        //FirebaseUser
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        //FirebaseDatabase
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

    }

    public void showResults(View view) {
        Intent intent = new Intent(this, Resultados.class);
        String date = showdate.toString();
        String hm = showtime.toString();
        String partida = mPartida.getText().toString();
        String destino = mDestino.getText().toString();
        String hora = mHora.getText().toString();
        /*intent.putExtra(PARTIDA, partida);
        intent.putExtra(DESTINO, destino);
        intent.putExtra(HORA, hora);
        intent.putExtra(DATA, date);*/
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

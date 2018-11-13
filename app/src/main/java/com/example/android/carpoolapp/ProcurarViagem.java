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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProcurarViagem extends AppCompatActivity {

    TextView showdate;
    private EditText mPartida;
    private EditText mDestino;
    private EditText mHora;

    public static String PARTIDA = "com.example.android.carpoolapp.extra.MESSAGE";
    public static String DESTINO = "com.example.android.carpoolapp.extra.MESSAGE";
    public static String HORA = "com.example.android.carpoolapp.extra.MESSAGE";
    public static String DATA = "com.example.android.carpoolapp.extra.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procurar_viagem);
        showdate = (TextView) findViewById(R.id.showDate);
        mPartida = findViewById(R.id.inputPartida);
        mDestino = findViewById(R.id.inputDestino);
        mHora = findViewById(R.id.inputHora);

    }

    public void showResults(View view) {
        Intent intent = new Intent(this, Resultados.class);
        String date = showdate.toString();
        String partida = mPartida.getText().toString();
        String destino = mDestino.getText().toString();
        String hora = mHora.getText().toString();
        intent.putExtra(PARTIDA, partida);
        intent.putExtra(DESTINO, destino);
        intent.putExtra(HORA, hora);
        intent.putExtra(DATA, date);
        startActivity(intent);
    }

    public void showDataPicker(View view) {
        DialogFragment dataPicker = new DataPickerFragment();
        dataPicker.show(getSupportFragmentManager(), getString(R.string.dataPicker));
    }

    public void getDateFromPicker(int year, int month, int day){
        String data = Integer.toString(day) + "/" + Integer.toString(month+1) + "/" + Integer.toString(year);
        showdate.setText(data);
    }

}

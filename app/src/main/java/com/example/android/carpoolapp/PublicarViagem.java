package com.example.android.carpoolapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PublicarViagem extends AppCompatActivity {

    private EditText partida,
                    destino,
                    lugares,
                    preco,
                    comentarios;
    private Button publicar;
    private TextView data;
    private TextView showtime;

    //firebase
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private Viagem viagem;
    private String keyviagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar_viagem);
        partida = findViewById(R.id.inputPartida);
        destino = findViewById(R.id.inputDestino);
        data = (TextView) findViewById(R.id.showData);
        showtime = findViewById(R.id.showTimee);
        lugares = findViewById(R.id.inputLugares);
        preco = findViewById(R.id.inputPreco);
        comentarios = findViewById(R.id.inputComentario);
        publicar = findViewById(R.id.buttonPublicar);

        viagem = (Viagem) getIntent().getExtras().getSerializable("viagem");
        keyviagem = (String) getIntent().getExtras().getSerializable("key");


        if(!viagem.getEmailUser().equals(""))
            mostrarDetalhes();

        //FirebaseUser
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        //FirebaseDatabase
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void mostrarDetalhes() {
        partida.setText(viagem.getPontoPartida());
        destino.setText(viagem.getPontoDestino());
        data.setText(viagem.getData());
        showtime.setText(viagem.getHora());
        lugares.setText(viagem.getLugaresDisponiveis()+"");
        preco.setText(viagem.getPrecoPassageiro()+"");
        comentarios.setText(viagem.getComentarios());
    }

    public void publicar(View view) {

        if (partida.getText().toString().equals("") || destino.getText().toString().equals("") || data.getText().toString().equals("") || showtime.getText().toString().equals("") || lugares.getText().toString().equals("") || preco.getText().toString().equals("")) {
            AlertDialog.Builder alertaCamposVazios = new AlertDialog.Builder(PublicarViagem.this);
            alertaCamposVazios.setTitle("Aviso");
            alertaCamposVazios
                    .setIcon(R.mipmap.ic_warning)
                    .setMessage("Há campos por preencher!").setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            AlertDialog alertDialog = alertaCamposVazios.create();
            alertDialog.show();
            return;
        }

        viagem.setEmailUser(mFirebaseUser.getEmail());
        viagem.setPontoPartida(partida.getText().toString().toUpperCase());
        viagem.setPontoDestino(destino.getText().toString().toUpperCase());
        viagem.setData(data.getText().toString());
        viagem.setHora(showtime.getText().toString());
        viagem.setLugaresDisponiveis(Integer.parseInt(lugares.getText().toString()));
        viagem.setPrecoPassageiro(Double.parseDouble(preco.getText().toString()));
        viagem.setComentarios(comentarios.getText().toString());
        viagem.setEstado(Viagem.State.CREATED);

        AlertDialog.Builder alerta = new AlertDialog.Builder(PublicarViagem.this);
        alerta.setTitle("Aviso");
        alerta
                .setIcon(R.mipmap.ic_warning)
                .setMessage("Submeter a viagem?")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*if (getIntent().getClass().equals(DetalhesViagem.class)) {
                            mFirebaseDatabaseReference.child("viagens").child(keyviagem).removeValue();
                        }*/
                        mFirebaseDatabaseReference.child("viagens").push().setValue(viagem);
                        Toast.makeText(PublicarViagem.this, "Viagem Publicada!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(PublicarViagem.this, MainActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog alertDialog = alerta.create();
        alertDialog.show();
    }

    public void showDataPicker(View view) {
        DialogFragment dataPicker = new DataPickerFragment2();
        dataPicker.show(getSupportFragmentManager(), getString(R.string.dataPicker));
    }

    public void getDateFromPicker(int year, int month, int day){
        String date = Integer.toString(day) + "/" + Integer.toString(month+1) + "/" + Integer.toString(year);
        data.setText(date);
    }

    public void showTimePicker(View view) {
        DialogFragment timePicker = new TimePickerFragment2();
        timePicker.show(getSupportFragmentManager(), getString(R.string.timePicker));
    }

    public void getTimeFromPicker(int hourOfDay, int minute) {
        String time = Integer.toString(hourOfDay) + ":" + Integer.toString(minute);
        showtime.setText(time);
    }


}

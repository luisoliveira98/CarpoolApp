package com.example.android.carpoolapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DetalhesViagem2 extends AppCompatActivity {

    private TextView textPontoPartida,
            textPontoChegada,
            textData,
            textHora,
            textLugares,
            textPreco,
            textComentarios;

    private String keyViagem;
    private Viagem viagem;
    private DatabaseReference mFirebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_viagem2);
        textPontoChegada = findViewById(R.id.textPontoChegada2);
        textPontoPartida = findViewById(R.id.textPontoPartida2);
        textData = findViewById(R.id.textData2);
        textHora = findViewById(R.id.textHora2);
        textLugares = findViewById(R.id.textLugaresViagem2);
        textPreco = findViewById(R.id.textPrecoViagem2);
        textComentarios = findViewById(R.id.textComentarios2);

        viagem = (Viagem) getIntent().getExtras().getSerializable("viagem");
        keyViagem = (String) getIntent().getExtras().getSerializable("key");

        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference().child("viagens");

        textPontoPartida.setText(viagem.getPontoPartida());
        textPontoChegada.setText(viagem.getPontoDestino());
        textData.setText(viagem.getData());
        textHora.setText(viagem.getHora());
        textLugares.setText(viagem.getLugaresDisponiveis() + "");
        textPreco.setText(viagem.getPrecoPassageiro() + "€");
        if (!viagem.getComentarios().equals(""))
            textComentarios.setText(viagem.getComentarios());
    }

    public void reservarViagem(View view) {
    }
}

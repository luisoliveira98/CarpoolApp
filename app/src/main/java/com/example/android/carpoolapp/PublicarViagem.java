package com.example.android.carpoolapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PublicarViagem extends AppCompatActivity {
    private EditText partida,
                    destino,
                    data,
                    hora,
                    lugares,
                    preco,
                    comentarios;
    private Button publicar;

    //firebaseDatabase
    private DatabaseReference mFirebaseDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar_viagem);
        partida = findViewById(R.id.inputPartida);
        destino = findViewById(R.id.inputDestino);
        data = findViewById(R.id.inputDate);
        hora = findViewById(R.id.inputHora);
        lugares = findViewById(R.id.inputLugares);
        preco = findViewById(R.id.inputPreco);
        comentarios = findViewById(R.id.inputComentario);
        publicar = findViewById(R.id.buttonPublicar);

        //FirebaseDatabase
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void publicar(View view) {
        Viagem viagem = new Viagem();
        viagem.setPontoPartida(partida.getText().toString());
        viagem.setPontoDestino(destino.getText().toString());
        viagem.setData(data.getText().toString());
        viagem.setHora(hora.getText().toString());
        viagem.setLugaresDisponiveis(Integer.parseInt(lugares.getText().toString()));
        viagem.setPrecoPassageiro(Double.parseDouble(preco.getText().toString()));
        viagem.setComentarios(comentarios.getText().toString());

        mFirebaseDatabaseReference.child("viagens").push().setValue(viagem);
    }
}

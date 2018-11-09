package com.example.android.carpoolapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PublicarViagem extends AppCompatActivity {
    private EditText partida,
                    destino,
                    data,
                    hora,
                    lugares,
                    preco,
                    comentarios;
    private Button publicar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar_viagem);
        partida = findViewById(R.id.inputPartida);
        destino = findViewById(R.id.inputDestino);
        hora = findViewById(R.id.inputHora);
        lugares = findViewById(R.id.inputLugares);
        preco = findViewById(R.id.inputPreco);
        comentarios = findViewById(R.id.inputComentario);
        publicar = findViewById(R.id.buttonPublicar);
    }

    public void publicar(View view) {

    }
}

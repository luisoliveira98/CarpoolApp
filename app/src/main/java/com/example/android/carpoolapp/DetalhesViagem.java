package com.example.android.carpoolapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetalhesViagem extends AppCompatActivity {

    private TextView textPontoPartida,
                    textPontoChegada,
                    textData,
                    textHora,
                    textLugares,
                    textPreco,
                    textComentarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_viagem);

        textPontoChegada = findViewById(R.id.textPontoChegada);
        textPontoPartida = findViewById(R.id.textPontoPartida);
        textData = findViewById(R.id.textData);
        textHora = findViewById(R.id.textHora);
        textLugares = findViewById(R.id.textLugares);
        textPreco = findViewById(R.id.textPreco);
        textComentarios = findViewById(R.id.textComentarios);

        Viagem viagem = (Viagem) getIntent().getExtras().getSerializable("viagem");

        textPontoPartida.setText(viagem.getPontoPartida());
        textPontoChegada.setText(viagem.getPontoDestino());
        textData.setText(viagem.getData());
        textHora.setText(viagem.getHora());
        //textLugares.setText(viagem.getLugaresDisponiveis());
        textPreco.setText(viagem.getPrecoPassageiro() + "");
        textComentarios.setText(viagem.getComentarios());

    }
}

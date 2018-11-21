package com.example.android.carpoolapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetalhesViagem extends AppCompatActivity {

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
        setContentView(R.layout.activity_detalhes_viagem);

        textPontoChegada = findViewById(R.id.textPontoChegada);
        textPontoPartida = findViewById(R.id.textPontoPartida);
        textData = findViewById(R.id.textData);
        textHora = findViewById(R.id.textHora);
        textLugares = findViewById(R.id.textLugaresViagem);
        textPreco = findViewById(R.id.textPrecoViagem);
        textComentarios = findViewById(R.id.textComentarios);

        viagem = (Viagem) getIntent().getExtras().getSerializable("viagem");
        keyViagem = (String) getIntent().getExtras().getSerializable("key");
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();

        textPontoPartida.setText(viagem.getPontoPartida());
        textPontoChegada.setText(viagem.getPontoDestino());
        textData.setText(viagem.getData());
        textHora.setText(viagem.getHora());
        textLugares.setText(viagem.getLugaresDisponiveis() + "");
        textPreco.setText(viagem.getPrecoPassageiro() + "€");
        if (!viagem.getComentarios().equals(""))
            textComentarios.setText(viagem.getComentarios());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalhe_viagem, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_mostrarMapa:
                return true;

            case R.id.action_editar:
                return true;

            case R.id.action_eliminar:
                mFirebaseDatabase.child("viagens").child(keyViagem).removeValue();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

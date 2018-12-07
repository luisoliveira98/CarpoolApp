package com.example.android.carpoolapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

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

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
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
        Intent intent = new Intent(DetalhesViagem2.this, ReservarViagem.class);
        intent.putExtra("viagem", viagem);
        intent.putExtra("key", keyViagem);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalhe_viagem2, menu);
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

            case R.id.action_verPercurso:
                Intent i = new Intent(DetalhesViagem2.this, PathViagem.class);
                i.putExtra("viagem", viagem);
                i.putExtra("key", keyViagem);
                startActivity(i);
                return true;

            case R.id.action_verPerfil:
                intent = new Intent(DetalhesViagem2.this, Perfil2.class);
                intent.putExtra("email", viagem.getEmailUser());
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

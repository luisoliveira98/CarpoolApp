package com.example.android.carpoolapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DetalhesViagem extends AppCompatActivity {

    private TextView textPontoPartida,
                    textPontoChegada,
                    textData,
                    textHora,
                    textLugares,
                    textPreco,
                    textComentarios;
    private Button updateStateViagem;

    private String keyViagem;
    private Viagem viagem;
    private DatabaseReference mFirebaseDatabase;
    private String buttonComecar = "Começar viagem";
    private String buttonTerminar = "Terminar viagem";

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
        updateStateViagem = findViewById(R.id.buttonUpdateState);

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

        if (viagem.getEstado() == Viagem.State.CREATED) {
            updateStateViagem.setText(buttonComecar);
        }

        if (viagem.getEstado() == Viagem.State.STARTED) {
            updateStateViagem.setText(buttonTerminar);
        }

        if (viagem.getEstado() == Viagem.State.FINISHED) {
            updateStateViagem.setVisibility(View.INVISIBLE);
        }

    }

    public void updateState(View view) {
        if (viagem.getEstado() == Viagem.State.CREATED) {
            viagem.setEstado(Viagem.State.STARTED);
            mFirebaseDatabase.child("viagens").child(keyViagem).setValue(viagem);
        }

        else if (viagem.getEstado() == Viagem.State.STARTED) {
            viagem.setEstado(Viagem.State.FINISHED);
            mFirebaseDatabase.child("viagens").child(keyViagem).setValue(viagem);
        }
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
            case R.id.action_partilhar:
                intent = new Intent(DetalhesViagem.this, PartilharViagem.class);
                intent.putExtra("key", keyViagem);
                startActivity(intent);

            case R.id.action_mostrarMapa:
                Intent i = new Intent(DetalhesViagem.this, PathViagem.class);
                i.putExtra("viagem", viagem);
                i.putExtra("key", keyViagem);
                startActivity(i);
                return true;

            case R.id.action_editar:
                mFirebaseDatabase.child("viagens").child(keyViagem).removeValue();
                intent = new Intent(DetalhesViagem.this, PublicarViagem.class);
                intent.putExtra("viagem", viagem);
                intent.putExtra("key", keyViagem);
                startActivity(intent);
                return true;

            case R.id.action_eliminar:
                mFirebaseDatabase.child("viagens").child(keyViagem).removeValue();
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

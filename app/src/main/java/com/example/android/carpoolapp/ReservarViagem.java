package com.example.android.carpoolapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class ReservarViagem extends AppCompatActivity {

    private Viagem viagem;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabase;
    private String emailUser;
    private EditText nOcupantes;
    private Integer n;
    private Map<String, Integer> reservas;
    private String keyViagem;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_viagem);
        nOcupantes = (EditText) findViewById(R.id.nOcupantes);
        viagem = (Viagem) getIntent().getExtras().getSerializable("viagem");
        keyViagem = (String) getIntent().getExtras().getSerializable("key");
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference().child("viagens");
        emailUser = mFirebaseUser.getEmail();
        uid = mFirebaseUser.getUid();
        reservas = viagem.getReservas();

    }

    public void confReserva(View view) {
        n = Integer.parseInt(nOcupantes.getText().toString());
        if (n>viagem.getLugaresDisponiveis()){
            AlertDialog.Builder alerta = new AlertDialog.Builder(ReservarViagem.this);
            alerta.setTitle("Aviso");
            alerta
                    .setIcon(R.mipmap.ic_warning)
                    .setMessage("Não há lugares disponíveis suficientes!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            AlertDialog alertDialog = alerta.create();
            alertDialog.show();
        }
        else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(ReservarViagem.this);
            alerta.setTitle("Aviso");
            alerta
                    .setIcon(R.mipmap.ic_warning)
                    .setMessage("Confirmar Reserva?")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                reservas.put(uid, n);
                                viagem.setLugaresDisponiveis(viagem.getLugaresDisponiveis() - n);
                                if (viagem.getLugaresDisponiveis()==0){
                                    viagem.setEstado(Viagem.State.FULL);
                                }
                                viagem.setReservas(reservas);
                                mFirebaseDatabase.child(keyViagem).setValue(viagem);
                                Toast.makeText(ReservarViagem.this, "Viagem Reservada!", Toast.LENGTH_LONG).show();
                                finish();
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });

            AlertDialog alertDialog = alerta.create();
            alertDialog.show();
        }
    }
}

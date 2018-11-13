package com.example.android.carpoolapp;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Resultados extends AppCompatActivity {

    private ListView listVProcurar;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private List<Viagem> viagemList = new ArrayList<Viagem>();
    private ArrayAdapter<Viagem> viagemArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
        initializeComponents();
        initializeFirebase();

    }

    //String part, String dest, String date, String hr

    private void procurarViagem(String part, String dest, String date, String hr){
        Query query = databaseReference.child("Viagem");

        viagemList.clear();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Viagem v = objSnapshot.getValue(Viagem.class);
                    viagemList.add(v);
                }

                viagemArrayAdapter = new ArrayAdapter<Viagem>(Resultados.this, android.R.layout.simple_list_item_1, viagemList);
                listVProcurar.setAdapter(viagemArrayAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void initializeFirebase() {
        FirebaseApp.initializeApp(Resultados.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void initializeComponents(){
        listVProcurar = (ListView) findViewById(R.id.listVProcurar);
        Intent intent = getIntent();
        String partida = intent.getStringExtra(ProcurarViagem.PARTIDA);
        String data = intent.getStringExtra(ProcurarViagem.DATA);
        String destino = intent.getStringExtra(ProcurarViagem.DESTINO);
        String hora = intent.getStringExtra(ProcurarViagem.HORA);
    }

}

package com.example.android.carpoolapp;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Resultados extends AppCompatActivity {

    private DatabaseReference mRef;
    private ListView mList;
    private List<Viagem> vList = new ArrayList<Viagem>();
    private ArrayAdapter<Viagem> arrayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_results);

        mRef = FirebaseDatabase.getInstance().getReference().child("viagens");
        mList = (ListView) findViewById(R.id.list_resultado);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                Viagem v = dataSnapshot1.getValue(Viagem.class);
                vList.add(v);
                }

                arrayAdapter = new ArrayAdapter<Viagem>(Resultados.this, android.R.layout.simple_list_item_1, vList);
                mList.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mViagemReference;
    private String mViagemKey;

    private ListView mListView;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_results);

        mListView = (ListView) findViewById(R.id.listview);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mViagemReference = mFirebaseDatabase.getReference().child("viagens");
        //FirebaseUser user = mFirebaseAuth.getCurrentUser();

        mViagemReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showViagens(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void showViagens(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds: dataSnapshot.getChildren()){
            Viagem v = new Viagem();
            v.setPontoPartida(ds.child(mViagemKey).getValue(Viagem.class).getPontoPartida());
            v.setPontoDestino(ds.child(mViagemKey).getValue(Viagem.class).getPontoDestino());
            v.setData(ds.child(mViagemKey).getValue(Viagem.class).getData());
            v.setHora(ds.child(mViagemKey).getValue(Viagem.class).getHora());

            ArrayList<String> array = new ArrayList<>();
            array.add(v.getPontoDestino());
            array.add(v.getPontoDestino());
            array.add(v.getData());
            array.add(v.getHora());
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);
            mListView.setAdapter(adapter);
        }
    }
*/
}

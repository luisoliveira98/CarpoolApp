package com.example.android.carpoolapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Historico extends AppCompatActivity {

    private DatabaseReference mRef;
    private ListView vlist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        mRef = FirebaseDatabase.getInstance().getReference().child("viagens");
        vlist = (ListView) findViewById(R.id.viagensLst);
        final List<Viagem> viagens = new LinkedList<>();
        final ArrayAdapter<Viagem> arrayAdapter = new ArrayAdapter<Viagem>(this, android.R.layout.two_line_list_item, viagens){
            @Override
            public View getView(int position, View view, ViewGroup parent){
                if (view == null) {
                    view = getLayoutInflater().inflate(android.R.layout.two_line_list_item, parent, false);
                }
                Viagem v = viagens.get(position);
                ((TextView) view.findViewById(android.R.id.text1)).setText(v.getPontoPartida());
                ((TextView) view.findViewById(android.R.id.text2)).setText(v.getPontoDestino());
                return view;
            }
        };
        vlist.setAdapter(arrayAdapter);

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Viagem viagem = dataSnapshot.getValue(Viagem.class);
                viagens.add(viagem);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}

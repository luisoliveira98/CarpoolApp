package com.example.android.carpoolapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Historico extends AppCompatActivity {

    private DatabaseReference mRef;
    private ListView vlist;
    private FirebaseUser mFireBaseUser;
    private FirebaseAuth mFireBaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        mFireBaseAuth = FirebaseAuth.getInstance();
        mFireBaseUser = mFireBaseAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("viagens");
        vlist = (ListView) findViewById(R.id.viagensLst);
        final Map<Viagem, String> keys = new HashMap<>();
        final List<Viagem> viagens = new LinkedList<>();
        final ArrayAdapter<Viagem> arrayAdapter = new ArrayAdapter<Viagem>(this, android.R.layout.simple_list_item_1, viagens){
            @Override
            public View getView(int position, View view, ViewGroup parent){
                if (view == null) {
                    view = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
                }
                Viagem v = viagens.get(position);
                ((TextView) view.findViewById(android.R.id.text1)).setText(v.toString());
                return view;
            }
        };
        vlist.setAdapter(arrayAdapter);
        vlist.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Historico.this, DetalhesViagem.class);
                intent.putExtra("viagem", viagens.get(position));
                intent.putExtra("key", keys.get(viagens.get(position)));
                startActivity(intent);
            }
        });

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Viagem viagem = dataSnapshot.getValue(Viagem.class);
                if (viagem.getEmailUser().equals(mFireBaseUser.getEmail()) && viagem.getEstado().equals(Viagem.State.FINISHED)){
                    viagens.add(viagem);
                    keys.put(viagem, dataSnapshot.getKey());
                    arrayAdapter.notifyDataSetChanged();
                }
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

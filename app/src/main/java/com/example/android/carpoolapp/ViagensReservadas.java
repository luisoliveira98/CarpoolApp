package com.example.android.carpoolapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ViagensReservadas extends AppCompatActivity {

    private DatabaseReference mRef;
    private ListView vlist;
    private FirebaseUser mFireBaseUser;
    private FirebaseAuth mFireBaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viagens_reservadas);
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
                Intent intent = new Intent(ViagensReservadas.this, DetalhesViagem2.class);
                intent.putExtra("viagem", viagens.get(position));
                intent.putExtra("key", keys.get(viagens.get(position)));
                startActivity(intent);
            }
        });

        Query query = mRef;
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Viagem temp = (Viagem) data.getValue(Viagem.class);
                    if (temp.getReservas().containsKey(mFireBaseUser.getUid())) {
                        viagens.add(temp);
                        keys.put(temp, dataSnapshot.getKey());
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}

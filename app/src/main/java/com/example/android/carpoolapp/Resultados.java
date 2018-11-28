package com.example.android.carpoolapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Resultados extends AppCompatActivity {

    private DatabaseReference mRef;
    private ListView vlist;
    private FirebaseUser mFireBaseUser;
    private FirebaseAuth mFireBaseAuth;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_results);
        Intent intent = getIntent();
        final String pPartida = intent.getStringExtra(ProcurarViagem.PARTIDA_MESSAGE);
        final String pDestino = intent.getStringExtra(ProcurarViagem.DESTINO_MESSAGE);
        final String pHora = intent.getStringExtra(ProcurarViagem.HORA_MESSAGE);
        final String pData = intent.getStringExtra(ProcurarViagem.DATA_MESSAGE);

        vlist = (ListView) findViewById(R.id.list_resultado);
        mFireBaseAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference().child("viagens");
        mFireBaseUser = mFireBaseAuth.getCurrentUser();

        final Map<Viagem, String> keys = new HashMap<>();
        final List<Viagem> viagens = new LinkedList<>();
        final ArrayAdapter<Viagem> arrayAdapter = new ArrayAdapter<Viagem>(this, android.R.layout.simple_list_item_1, viagens){
            @Override
            public View getView(int position, View view, ViewGroup parent){
                if (view == null) {
                    view = getLayoutInflater().inflate(android.R.layout.two_line_list_item, parent, false);
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
                Intent intent = new Intent(Resultados.this, DetalhesViagem2.class);
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
                    Viagem viagem = data.getValue(Viagem.class);
                    String[] vdat = viagem.getData().split("/");
                    String[] vh = viagem.getHora().split(":");
                    Integer ano = Integer.parseInt(vdat[2]);
                    Integer mes = Integer.parseInt(vdat[1]);
                    Integer dia = Integer.parseInt(vdat[0]);
                    Integer hora = Integer.parseInt(vh[0]);
                    Integer min = Integer.parseInt(vh[1]);
                    if (viagem.getPontoPartida().equals(pPartida) && viagem.getPontoDestino().equals(pDestino) && viagem.getEstado().equals(Viagem.State.CREATED)) {
                        if (pData.equals("")){
                            if (pHora.equals("")) {
                                viagens.add(viagem);
                                keys.put(viagem, data.getKey());
                                arrayAdapter.notifyDataSetChanged();
                            }
                            else if (!pHora.equals("")){
                                Integer ph = Integer.parseInt(pHora.split(":")[0]);
                                Integer pmin = Integer.parseInt(pHora.split(":")[1]);
                                if (hora>ph || (hora.equals(ph) && min>=pmin)) {
                                    viagens.add(viagem);
                                    keys.put(viagem, data.getKey());
                                    arrayAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                        else if (!pData.equals("")) {
                            Integer pano = Integer.parseInt(pData.split("/")[2]);
                            Integer pmes = Integer.parseInt(pData.split("/")[1]);
                            Integer pdia = Integer.parseInt(pData.split("/")[0]);
                            if (pHora.equals("")) {
                                if ((ano>pano) || (ano.equals(pano) && mes>=pmes) || (ano.equals(pano) && mes.equals(pmes) && dia>=pdia)) {
                                    viagens.add(viagem);
                                    keys.put(viagem, data.getKey());
                                    arrayAdapter.notifyDataSetChanged();
                                }
                            } else if (!pHora.equals("")) {
                                Integer ph = Integer.parseInt(pHora.split(":")[0]);
                                Integer pmin = Integer.parseInt(pHora.split(":")[1]);
                                if ((ano>pano) || (ano.equals(pano) && mes>=pmes) || (ano.equals(pano) && mes.equals(pmes) && dia>=pdia)){
                                    if (hora>ph || (hora.equals(ph) && min>=pmin)) {
                                        viagens.add(viagem);
                                        keys.put(viagem, data.getKey());
                                        arrayAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}

package com.example.android.carpoolapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.Menu;

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

public class Perfil extends AppCompatActivity {

    private TextView textNome,
                    textEmail,
                    textNumero,
                    textCidade,
                    textMarcaCarro,
                    textModeloAno,
                    textMatricula;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabase;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        textNome = findViewById(R.id.textNome);
        textEmail = findViewById(R.id.textEmail);
        textNumero = findViewById(R.id.textNumero);
        textCidade = findViewById(R.id.textCidade);
        textMarcaCarro = findViewById(R.id.textMarcaCarro);
        textModeloAno = findViewById(R.id.textModeloCarro);
        textMatricula = findViewById(R.id.textMatriculaCarro);

        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        user = (User) getIntent().getExtras().getSerializable("user");

        textNome.setText(user.getNome());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_perfil, menu);
        return true;
    }
}

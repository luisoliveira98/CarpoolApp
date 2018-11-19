package com.example.android.carpoolapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.Menu;

import com.google.android.gms.auth.api.Auth;
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
                    textModelo,
                    textMatricula,
                    textCor,
                    textAno;

    private ImageView userImage;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabase;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        userImage = findViewById(R.id.imageUser);
        textNome = findViewById(R.id.textNome);
        textEmail = findViewById(R.id.textEmail);
        textNumero = findViewById(R.id.textNumero);
        textCidade = findViewById(R.id.textCidade);
        textMarcaCarro = findViewById(R.id.textMarcaCarro);
        textModelo = findViewById(R.id.textModeloCarro);
        textMatricula = findViewById(R.id.textMatriculaCarro);
        textCor = findViewById(R.id.textCorCarro);
        textAno = findViewById(R.id.textAnoCarro);

        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        user = (User) getIntent().getExtras().getSerializable("user");


        textNome.setText(user.getNome());
        textEmail.setText(user.getEmail());
        textCidade.setText(user.getCidade());
        textNumero.setText(user.getTelemovel());
        //Carro informações
        textMarcaCarro.setText(user.getCarro().getMarca());
        textModelo.setText(user.getCarro().getModelo());
        textMatricula.setText(user.getCarro().getMatricula());
        textCor.setText(user.getCarro().getCor());
        textAno.setText(user.getCarro().getAno());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_perfil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.action_updatePerfil:
                Intent intent = new Intent(this, UpdatePerfil.class);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

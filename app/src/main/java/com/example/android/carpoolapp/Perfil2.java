package com.example.android.carpoolapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Perfil2 extends AppCompatActivity {

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

    private DatabaseReference mFirebaseDatabase;
    private String emailcondutor;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil2);
        userImage = findViewById(R.id.imageUser2);
        textNome = findViewById(R.id.textNome2);
        textEmail = findViewById(R.id.textEmail2);
        textNumero = findViewById(R.id.textNumero2);
        textCidade = findViewById(R.id.textCidade2);
        textMarcaCarro = findViewById(R.id.textMarcaCarro2);
        textModelo = findViewById(R.id.textModeloCarro2);
        textMatricula = findViewById(R.id.textMatriculaCarro2);
        textCor = findViewById(R.id.textCorCarro2);
        textAno = findViewById(R.id.textAnoCarro2);
        emailcondutor = (String) getIntent().getExtras().getSerializable("email");

        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        user = new User();
        Query query = mFirebaseDatabase.child("users");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User temp = data.getValue(User.class);
                    if (temp.getEmail().equals(emailcondutor)) {
                        user = temp;
                        break;
                    }
                }
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
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}

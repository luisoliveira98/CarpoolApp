package com.example.android.carpoolapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdatePerfil extends AppCompatActivity {
    User user;
    EditText editNome,
            editTelemovel,
            editCidade,
            editMarcaCarro,
            editModeloCarro,
            editAnoCarro,
            editCorCarro,
            editMatriculaCarro;

    DatabaseReference firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_perfil);

        user = (User) getIntent().getExtras().getSerializable("user");

        firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        editNome = findViewById(R.id.editNomePerfil);
        editCidade = findViewById(R.id.editCidadePerfil);
        editTelemovel = findViewById(R.id.editTelemovelPerfil);
        editMarcaCarro = findViewById(R.id.editMarcaCarro);
        editModeloCarro = findViewById(R.id.editModeloCarro);
        editMatriculaCarro = findViewById(R.id.editMatriculaCarro);
        editAnoCarro = findViewById(R.id.editAnoCarro);
        editCorCarro = findViewById(R.id.editCorCarro);

        editNome.setText(user.getNome());

        if (!user.getCidade().equals(""))
            editCidade.setText(user.getCidade());
        if (!user.getTelemovel().equals(""))
            editTelemovel.setText(user.getTelemovel());

        if(user.getCarro()!=null) {
            editModeloCarro.setText(user.getCarro().getModelo());
            editMarcaCarro.setText(user.getCarro().getMarca());
            editMatriculaCarro.setText(user.getCarro().getMatricula());
            editCorCarro.setText(user.getCarro().getCor());
            editAnoCarro.setText(user.getCarro().getAno());
        }
    }

    public void updatePerfil(View view) {
        //Dados Pessoais
        String nomeUser = editNome.getText().toString();
        String cidade = editCidade.getText().toString();
        String telemovel = editTelemovel.getText().toString();

        //Dados do carro
        String marcaCarro = editMarcaCarro.getText().toString();
        String modeloCarro = editModeloCarro.getText().toString();
        String matriculaCarro = editMatriculaCarro.getText().toString();
        String corCarro = editCorCarro.getText().toString();
        String anoCarro = editAnoCarro.getText().toString();

        Carro carro = new Carro(marcaCarro, modeloCarro, corCarro, matriculaCarro, anoCarro);
        user.setNome(nomeUser);
        user.setCidade(cidade);
        user.setTelemovel(telemovel);
        user.setCarro(carro);

        firebaseDatabase.child("users").child(user.getEmail().split("@")[0]).setValue(user);

        finish();
    }
}

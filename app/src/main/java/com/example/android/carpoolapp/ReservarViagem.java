package com.example.android.carpoolapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

public class ReservarViagem extends AppCompatActivity {

    private Viagem viagem;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabase;
    private String emailUser;
    private EditText nOcupantes;
    private Integer n;
    private Map<String, Integer> reservas;
    private String keyViagem;
    private String uid;
    static String currentUserEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_viagem);
        nOcupantes = (EditText) findViewById(R.id.nOcupantes);
        viagem = (Viagem) getIntent().getExtras().getSerializable("viagem");
        keyViagem = (String) getIntent().getExtras().getSerializable("key");
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference().child("viagens");
        currentUserEmail = mFirebaseUser.getEmail();
        uid = mFirebaseUser.getUid();
        reservas = viagem.getReservas();

    }

    public void confReserva(View view) {
        n = Integer.parseInt(nOcupantes.getText().toString());
        if (n>viagem.getLugaresDisponiveis()){
            AlertDialog.Builder alerta = new AlertDialog.Builder(ReservarViagem.this);
            alerta.setTitle("Aviso");
            alerta
                    .setIcon(R.mipmap.ic_warning)
                    .setMessage("Não há lugares disponíveis suficientes!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
            AlertDialog alertDialog = alerta.create();
            alertDialog.show();
        }
        else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(ReservarViagem.this);
            alerta.setTitle("Aviso");
            alerta
                    .setIcon(R.mipmap.ic_warning)
                    .setMessage("Confirmar Reserva?")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                reservas.put(uid, n);
                                viagem.setLugaresDisponiveis(viagem.getLugaresDisponiveis() - n);
                                if (viagem.getLugaresDisponiveis()==0){
                                    viagem.setEstado(Viagem.State.FULL);
                                }
                                viagem.setReservas(reservas);
                                mFirebaseDatabase.child(keyViagem).setValue(viagem);
                                Toast.makeText(ReservarViagem.this, "Viagem Reservada!", Toast.LENGTH_LONG).show();
                                sendNotification();
                                finish();
                                startActivity(new Intent(ReservarViagem.this, ViagensReservadas.class));
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });

            AlertDialog alertDialog = alerta.create();
            alertDialog.show();
        }
    }

    private void sendNotification()
    {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    String send_email = viagem.getEmailUser();

                    try {
                        String jsonResponse;

                        URL url = new URL("https://onesignal.com/api/v1/notifications");
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setUseCaches(false);
                        con.setDoOutput(true);
                        con.setDoInput(true);

                        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                        con.setRequestProperty("Authorization", "Basic MTI2NTIzMzItMWFlYS00Yjc5LWIzODgtNjJlOTk2ZTg4MDM0");
                        con.setRequestMethod("POST");

                        String strJsonBody = "{"
                                + "\"app_id\": \"aaee6e49-b52b-42c6-8b30-e8debb41bd59\","

                                + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" + send_email + "\"}],"

                                + "\"data\": {\"foo\": \"bar\"},"
                                + "\"contents\": {\"en\": \"Tem novas reservas numa das suas viagens!\"}"
                                + "}";


                        System.out.println("strJsonBody:\n" + strJsonBody);

                        byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                        con.setFixedLengthStreamingMode(sendBytes.length);

                        OutputStream outputStream = con.getOutputStream();
                        outputStream.write(sendBytes);

                        int httpResponse = con.getResponseCode();
                        System.out.println("httpResponse: " + httpResponse);

                        if (httpResponse >= HttpURLConnection.HTTP_OK
                                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        } else {
                            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        }
                        System.out.println("jsonResponse:\n" + jsonResponse);

                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }
        });
    }

}

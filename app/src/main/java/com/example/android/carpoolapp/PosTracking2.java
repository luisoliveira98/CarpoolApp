package com.example.android.carpoolapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PosTracking2 extends AppCompatActivity {

    private Button startTracking;
    private EditText keyUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos_tracking2);
        startTracking = (Button) findViewById(R.id.buttonStart);
        keyUser = (EditText) findViewById(R.id.editUserKey);

    }

    public void startTracking(View view) {
        Intent intent = new Intent(PosTracking2.this, PosTracking.class);
        intent.putExtra("keyUser", keyUser.getText().toString());
        startActivity(intent);
    }
}

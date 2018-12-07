package com.example.android.carpoolapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PosTracking extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private DatabaseReference mDatabaseRef;
    private Marker currentLocationMaker;
    private String keyUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos_tracking);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        keyUser = (String) getIntent().getExtras().getSerializable("keyUser");
    }

    @Override
    protected void onStart() {
        super.onStart();
        getLocation();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }

    private void getLocation() {

        mDatabaseRef.child("location").child(keyUser).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //System.out.println("Aquiiiiiiii: " + dataSnapshot.getValue());
                        ViagemLocalizacao viagemLocalizacao = dataSnapshot.getValue(ViagemLocalizacao.class);
                        addMarker(viagemLocalizacao);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }

    private void addMarker(ViagemLocalizacao viagemLocalizacao) {
        Date newDate = new Date(Long.valueOf(viagemLocalizacao.getTimestamp()));
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        MarkerOptions markerOptions = new MarkerOptions();
        LatLng latLng = new LatLng(viagemLocalizacao.getLatitude(), viagemLocalizacao.getLongitude());
        markerOptions.position(latLng);
        markerOptions.title(date.format(newDate));

        if(currentLocationMaker != null) {
            currentLocationMaker.remove();
        }

        currentLocationMaker = mMap.addMarker(markerOptions);

        CameraPosition cameraPosition = new CameraPosition.Builder().zoom(8).target(latLng).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}

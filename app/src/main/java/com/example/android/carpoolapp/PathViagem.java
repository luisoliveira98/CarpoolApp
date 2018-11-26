package com.example.android.carpoolapp;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;

public class PathViagem extends AppCompatActivity implements OnMapReadyCallback, ConnectionCallbacks {

    private GoogleMap mMap;
    private GoogleApiClient client;
    private Marker p, d;
    private LocationManager locationManager;
    private Viagem viagem;
    private String keyViagem;
    private DatabaseReference mFirebaseDatabase;
    private String partida;
    private String destino;
    private GetDirectionsData getDirectionsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maplayout);
        viagem = (Viagem) getIntent().getExtras().getSerializable("viagem");
        keyViagem = (String) getIntent().getExtras().getSerializable("key");
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference().child("viagens");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.pathmap);
        mapFragment.getMapAsync(this);
        destino = (String) viagem.getPontoDestino();
        partida = (String) viagem.getPontoPartida();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        client = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this).build();
        client.connect();
        Geocoder geocoder = new Geocoder(this);
        StringBuilder sb;
        Object[] dataTransfer = new Object[4];
        try{
            List<Address> parts = geocoder.getFromLocationName(this.destino, 1);
            List<Address> dests = geocoder.getFromLocationName(this.partida, 1);
            Address paddress = parts.get(0);
            Address daddress = dests.get(0);
            String plocality = paddress.getLocality();
            String dlocality = daddress.getLocality();
            MarkerOptions options = new MarkerOptions();
            MarkerOptions options1 = new MarkerOptions();
            options.title(plocality);
            options1.title(dlocality);
            options.draggable(false);
            options1.draggable(false);
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            options1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            options.snippet(plocality);
            options1.snippet(dlocality);
            options.position(new LatLng(paddress.getLatitude(), paddress.getLongitude()));
            options1.position(new LatLng(daddress.getLatitude(), daddress.getLongitude()));
            p = mMap.addMarker(options);
            d = mMap.addMarker(options1);

            Criteria criteria = new Criteria();
            sb = new StringBuilder();
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            String provider = locationManager.getBestProvider(criteria, true);
            sb.append("https://maps.googleapis.com/maps/api/directions/json?");
            sb.append("origin=" + paddress.getLatitude() + "," + paddress.getLongitude());
            sb.append("&destination=" + daddress.getLatitude() + "," + daddress.getLongitude());
            sb.append("&key=" + "AIzaSyBTzxbpr_Nt7MMc45BoWkkw_qKQAMyYubg");

            getDirectionsData = new GetDirectionsData(getApplicationContext());
            dataTransfer[0]=mMap;
            dataTransfer[1]=sb.toString();
            dataTransfer[2]=new LatLng(paddress.getLatitude(), paddress.getLongitude());
            dataTransfer[3]=new LatLng(daddress.getLatitude(), daddress.getLongitude());
            getDirectionsData.execute(dataTransfer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    /*public void drawRoute(){
        Geocoder geocoder = new Geocoder(this);
        StringBuilder sb;
        Object[] dataTransfer = new Object[4];
        try{
            List<Address> part = geocoder.getFromLocationName(partida, 1);
            List<Address> dest = geocoder.getFromLocationName(destino, 1);
            Address paddress = part.get(0);
            Address daddress = dest.get(0);
            String plocality = paddress.getLocality();
            String dlocality = daddress.getLocality();
            MarkerOptions options = new MarkerOptions();
            MarkerOptions options1 = new MarkerOptions();
            options.title(plocality);
            options1.title(plocality);
            options.draggable(false);
            options1.draggable(false);
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            options1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            options.snippet(plocality);
            options1.snippet(dlocality);
            options.position(new LatLng(paddress.getLatitude(), paddress.getLongitude()));
            options1.position(new LatLng(daddress.getLatitude(), daddress.getLongitude()));
            p = mMap.addMarker(options);
            d = mMap.addMarker(options1);

            Criteria criteria = new Criteria();
            sb = new StringBuilder();
            String provider = locationManager.getBestProvider(criteria, true);
            sb.append("https://maps.googleapis.com/maps/api/directions/json?");
            sb.append("origin=" + paddress.getLatitude() + "," + paddress.getLongitude());
            sb.append("&destination=" + daddress.getLatitude() + "," + daddress.getLongitude());
            sb.append("&key=" + "AIzaSyCV0dWVE669D1rCtB7N4tgtI4LN1nI_V1s");

            getDirectionsData = new GetDirectionsData(getApplicationContext());
            dataTransfer[0]=mMap;
            dataTransfer[1]=sb.toString();
            dataTransfer[2]=new LatLng(paddress.getLatitude(), paddress.getLongitude());
            dataTransfer[3]=new LatLng(daddress.getLatitude(), daddress.getLongitude());
            getDirectionsData.execute(dataTransfer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}

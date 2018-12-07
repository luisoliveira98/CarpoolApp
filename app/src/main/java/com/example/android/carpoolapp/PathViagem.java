package com.example.android.carpoolapp;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;

public class PathViagem extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    private GoogleMap mMap;
    private MarkerOptions p, d;
    private Polyline polyline;
    private Viagem viagem;
    private String partida, destino;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maplayout);
        viagem = (Viagem) getIntent().getExtras().getSerializable("viagem");
        destino = (String) viagem.getPontoDestino();
        partida = (String) viagem.getPontoPartida();
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> parts = geocoder.getFromLocationName(this.partida, 1);
            List<Address> dests = geocoder.getFromLocationName(this.destino, 1);
            Address paddress = parts.get(0);
            Address daddress = dests.get(0);
            String plocality = paddress.getLocality();
            String dlocality = daddress.getLocality();
            p = new MarkerOptions().position(new LatLng(paddress.getLatitude(), paddress.getLongitude())).title(plocality).snippet("Partida").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            d = new MarkerOptions().position(new LatLng(daddress.getLatitude(), daddress.getLongitude())).title(dlocality).snippet("Destino").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            LatLng latLng = new LatLng(paddress.getLatitude(), paddress.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 8);
            mMap.addMarker(p);
            mMap.addMarker(d);
            mMap.moveCamera(update);
            new FetchURL(PathViagem.this).execute(getUrl(p.getPosition(), d.getPosition(), "driving"), "driving");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=key here";
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (polyline != null)
            polyline.remove();
        polyline = mMap.addPolyline((PolylineOptions) values[0]);
    }

}

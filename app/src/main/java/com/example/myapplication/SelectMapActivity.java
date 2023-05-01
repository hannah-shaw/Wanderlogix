package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class SelectMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_map);
        //Intent intent = getIntent();
        //location = intent.getStringExtra("location");
        SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);

        location=preferences.getString("location", "");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        double lat = 0.0;
        double lng = 0.0;
        LatLng appointLoc = new LatLng(lat, lng);

        // 移动地图到指定经度的位置
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(appointLoc));

        //添加标记到指定经纬度
        googleMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("Marker"));
        //.icon(BitmapDescriptorFactory.fromResource(R.drawable.img_1)));

        //String location="";
        Geocoder geocoder = new Geocoder(SelectMapActivity.this, Locale.getDefault());
        try {
            List<Address> listAddress = geocoder.getFromLocationName(location, 1);
            if (listAddress.size() > 0) {
                LatLng latLng = new LatLng(listAddress.get(0).getLatitude(), listAddress.get(0).getLongitude());
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.addMarker(new MarkerOptions().position(latLng).title("input"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(SelectMapActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);

            ActivityCompat.requestPermissions(SelectMapActivity.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
            return;
        }
        googleMap.setMyLocationEnabled(true);
    }
}
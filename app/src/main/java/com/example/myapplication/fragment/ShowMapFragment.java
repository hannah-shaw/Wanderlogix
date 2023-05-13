package com.example.myapplication.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.SelectMapActivity;
import com.example.myapplication.database.DiaryDatabase;
import com.example.myapplication.viewmodel.DiaryViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ShowMapFragment extends Fragment {
    private String sLocation;
    SupportMapFragment smf;
    FusedLocationProviderClient client;

    private DiaryViewModel diaryViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.show_map_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        smf = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.showmap);
        client = LocationServices.getFusedLocationProviderClient(getContext());

        Dexter.withContext(getContext())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        getmylocation();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }
    public void getmylocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                smf.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(@NonNull GoogleMap googleMap) {
                        LatLng latlng=new LatLng(location.getLatitude(),location.getLongitude());
                        googleMap.addMarker(new MarkerOptions().position(latlng).title("user"));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));

                        diaryViewModel= ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(DiaryViewModel.class);
                        List<String> locations=diaryViewModel.getLocation();
                        for(int i=0;i<locations.size();i++){
                            sLocation= locations.get(i);
                            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                            try {
                                List<Address> listAddress = geocoder.getFromLocationName(sLocation, 1);
                                if (listAddress.size() > 0) {
                                    LatLng latLng = new LatLng(listAddress.get(0).getLatitude(), listAddress.get(0).getLongitude());
                                    //googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                    googleMap.addMarker(new MarkerOptions().position(latLng).title("travel"));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
//                        Handler mHandler = new Handler(Looper.getMainLooper()) {
//                            @Override
//                            public void handleMessage(Message msg) {
//                                // 在主线程中更新 UI 界面
//                                List<String> locations= (List<String>)msg.obj;
//                                for(int i=0;i<locations.size();i++){
//                                    sLocation= locations.get(i);
//                                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
//                                    try {
//                                        List<Address> listAddress = geocoder.getFromLocationName(sLocation, 1);
//                                        if (listAddress.size() > 0) {
//                                            LatLng latLng = new LatLng(listAddress.get(0).getLatitude(), listAddress.get(0).getLongitude());
//                                            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                                            googleMap.addMarker(new MarkerOptions().position(latLng).title("travel"));
//                                        }
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//                        };
//
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                // 在子线程中执行一些操作，并获取数据
//                                // 执行数据库操作
//                                DiaryDatabase db = DiaryDatabase.getInstance(getContext());
//                                List<String> locations = db.diaryDao().getLocation();
//
//                                // 将数据通过 Handler 传递给主线程
//                                Message msg = mHandler.obtainMessage();
//                                msg.obj = locations;
//                                mHandler.sendMessage(msg);
//                            }
//                        }).start();
//                        Executor executor = Executors.newSingleThreadExecutor();
//                        executor.execute(new Runnable() {
//                            @Override
//                            public void run() {
//                                // 执行数据库操作
//                                DiaryDatabase db = DiaryDatabase.getInstance(getContext());
//                                List<String> locations = db.diaryDao().getLocation();
//                                for(int i=0;i<locations.size();i++){
//                                    sLocation= locations.get(i);
//                                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
//                                    try {
//                                        List<Address> listAddress = geocoder.getFromLocationName(sLocation, 1);
//                                        if (listAddress.size() > 0) {
//                                            LatLng latLng = new LatLng(listAddress.get(0).getLatitude(), listAddress.get(0).getLongitude());
//                                            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                                            googleMap.addMarker(new MarkerOptions().position(latLng).title("travel"));
//                                        }
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//                        });
//                        String[] locations = new String[] {"Newyork", "Beijing", "London"};
//                        for(int i=0;i<locations.length;i++){
//                            sLocation=locations[i];
//                            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
//                            try {
//                                List<Address> listAddress = geocoder.getFromLocationName(sLocation, 1);
//                                if (listAddress.size() > 0) {
//                                    LatLng latLng = new LatLng(listAddress.get(0).getLatitude(), listAddress.get(0).getLongitude());
//                                    //googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                                    googleMap.addMarker(new MarkerOptions().position(latLng).title("travel"));
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }

                        googleMap.getUiSettings().setZoomControlsEnabled(true);

                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                    1);

                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                    1);
                            return;
                        }
                        googleMap.setMyLocationEnabled(true);
                    }
                });
            }
        });
    }
}
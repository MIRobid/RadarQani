package com.example.radarqani;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.app.LoaderManager;
import android.content.Loader;

import android.Manifest;
import android.content.Context;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.radarqani.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.libraries.places.api.Places;

import java.util.Arrays;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private static final int REQUEST_LOCATION=1;
    private static final String TAG="MapsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng tashkent = new LatLng(41.2995, 69.2401);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tashkent));

        //Data is from https://data.gov.uz/ru/datasets/16884
        double[] arr={
                41.529928,60.61665,41.544931,60.615516,41.558318,60.621204,41.529928,60.61665,41.544931,60.615516,41.542529,60.63189,41.558318,60.621204,41.558318,60.621204,41.544931,60.615516,41.542529,60.63189,41.544931,60.615516,41.542529,60.63189,41.542529,60.63189,41.558318,60.621204,41.529928,60.61665,41.529928,60.61665,40.527389,68.21746,39.819339,64.43334299999999,39.781655,64.390074,39.743228,64.486441,39.781655,64.390074,39.773669,64.45607699999999,39.743072,64.48634199999999,39.773669,64.45607699999999,39.819339,64.43334299999999,40.098174,64.681905,40.095999,64.683131,40.096327,64.682982,40.093426,64.669169,40.096104,64.68373699999999,40.101602,64.680531,40.095821,64.682613,40.093426,64.669169,40.093426,64.669169,40.095999,64.683131,40.092227,64.706397,40.093426,64.669169,40.095999,64.683131,40.096694,64.686328,40.095999,64.683131,40.093426,64.669169,39.748896,64.424319,40.095999,64.683131,39.749714,64.437167,40.093426,64.669169,39.749696,64.438014,40.093426,64.669169,40.093426,64.669169,40.095999,64.683131,39.749109,64.423670,39.748477,64.423808,40.095999,64.683131,39.749269,64.437014,39.749101,64.437741,39.748808,64.423138,40.095999,64.683131,40.082021,67.92964499999999,40.082021,67.92964499999999,39.97834,68.411058,39.97834,68.411058,40.0983970,67.7929080,40.151761,67.750931,40.095764,68.043594,40.0983970,67.7929080,40.095943,67.91247,40.033754,67.62634300000001,40.1825170,67.8622760,40.1825170,67.8622760,39.961351,67.54766600000001,40.540536,68.05054699999999,40.0874790,68.1675280,38.847019,66.070395,38.845893,65.796730,38.845893,65.796730,38.845893,65.796730,38.845893,65.796730,38.845893,65.796730,38.845893,65.796730,42.135767,60.066425,41.713654,60.70516,42.875214,59.760614,42.524557,59.619448,43.063258,58.860174,42.821412,58.991153,41.445938,59.630697,42.441583,59.624188,42.456578,59.585886,42.474216,59.617286,42.471997,59.602172,42.465281,59.619812,42.454621,59.641472,42.457675,59.621973,42.458529,59.605798,41.553222,60.990336,40.144257,65.37817699999999,40.130964,65.350521,39.944957,64.65789700000001,40.091707,65.825165,40.117775,65.120909,40.140167,65.356343,40.135324,65.24835400000001,40.053852,64.806899,40.123639,65.36243,40.116237,65.370068,40.123639,65.36243,40.115245,65.366596,40.116237,65.370068,40.116237,65.370068,40.116237,65.370068,40.123639,65.36243,40.123639,65.36243,40.115245,65.366596,40.115245,65.366596,40.115245,65.366596};
        for(int i=0; i<arr.length;i=i+2){
            LatLng loc=new LatLng(arr[i],arr[i+1]);
            mMap.addMarker(new MarkerOptions().position(loc).title("radar"));
        }
    }
}
package com.example.hong.alchul;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class googlemap extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googlemap);

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(googlemap.this);


    }



    @Override
    public void onMapReady(final GoogleMap map) {
        mMap = map;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.i("test", "click은됨");
                MarkerOptions mOption = new MarkerOptions();
                //title
                mOption.title("가게");
                Double latitude = latLng.latitude;
                Double longitude = latLng.longitude;
                String lat = latitude.toString();
                String lon = longitude.toString();
                Log.i("test좌표",latitude.toString() );

                mOption.snippet(latitude.toString()+","+longitude.toString());

                //latlng: 위도 경도쌍
                mOption.position(new LatLng(latitude,longitude));
                map.addMarker(mOption);



            }
        });

        LatLng seoul = new LatLng(37.5197889, 126.9403083);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul,14));
    }
}




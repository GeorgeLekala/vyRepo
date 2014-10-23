package com.jeremyfeinstein.slidingmenu.example;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainMap extends Activity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapfragment);

//        // Get a handle to the Map Fragment
//        GoogleMap map = ((MapFragment) getFragmentManager()
//                .findFragmentById(R.id.map)).getMap();
//
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
//                new LatLng(41.889, -87.622), 16));
//
//        // You can customize the marker image using images bundled with
//        // your application, or dynamically generated bitmaps. 
//        map.addMarker(new MarkerOptions()
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.transitcar))
//                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
//                .position(new LatLng(41.889, -87.622)));
//        
//        GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
//
//        LatLng mapCenter = new LatLng(-26.189486, 28.031666);
//
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 13));

        // Flat markers will rotate when the map is rotated,
        // and change perspective when the map is tilted.
//        map.addMarker(new MarkerOptions()
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.appbartruck))
//                .position(mapCenter)
//                .flat(true)
//                .rotation(245));
//
//        CameraPosition cameraPosition = CameraPosition.builder()
//                .target(mapCenter)
//                .zoom(13)
//                .bearing(90)
//                .build();
//        
//        // Animate the change in camera view over 2 seconds
//        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
//                2000, null);
        
    }
    
}

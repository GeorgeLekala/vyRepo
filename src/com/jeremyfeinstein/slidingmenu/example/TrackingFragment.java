package com.jeremyfeinstein.slidingmenu.example;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class TrackingFragment extends Fragment {
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		    
			
			View view = inflater.inflate(R.layout.mapfragment, container, false);
			
//			GoogleMap map = ((MapFragment) getActivity().getFragmentManager().findFragmentById(
//					R.id.map)).getMap();
//			
//			LocationAdapter gps = new LocationAdapter(getActivity());
//			if(gps.canGetLocation){
//
//			LatLng mapCenter = new LatLng(gps.getLatitude(), gps.getLongitude());
//
//			map.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 13));

			// Flat markers will rotate when the map is rotated,
			// and change perspective when the map is tilted.;

//			map.addMarker(new MarkerOptions()
//			        .position(new LatLng(gps.getLatitude(), gps.getLongitude()))
//			        .title("Hello world"));
			
//			CameraPosition cameraPosition = CameraPosition.builder()
//					.target(mapCenter).zoom(13).bearing(90).build();
//
//			// Animate the change in camera view over 2 seconds
//			map.animateCamera(
//					CameraUpdateFactory.newCameraPosition(cameraPosition), 2000,
//					null);
//			}
//	        
//	        GoogleMap map = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map)).getMap();
//
//	        LatLng mapCenter = new LatLng(-26.189486, 28.031666);
//
//	        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 13));
//
//	        // Flat markers will rotate when the map is rotated,
//	        // and change perspective when the map is tilted.
//	        map.addMarker(new MarkerOptions()
//	                .icon(BitmapDescriptorFactory.fromResource(R.drawable.appbartruck))
//	                .position(mapCenter)
//	                .flat(true)
//	                .rotation(245));
//
//	        CameraPosition cameraPosition = CameraPosition.builder()
//	                .target(mapCenter)
//	                .zoom(13)
//	                .bearing(90)
//	                .build();
//	        
//	        // Animate the change in camera view over 2 seconds
//	        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
//	                2000, null);
		   	    
		    return view;
	}
}

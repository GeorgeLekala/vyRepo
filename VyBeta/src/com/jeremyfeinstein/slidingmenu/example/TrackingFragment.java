package com.jeremyfeinstein.slidingmenu.example;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class TrackingFragment extends Fragment implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener, LocationListener, com.google.android.gms.location.LocationListener {
    
	 	MapView mapView;
	    GoogleMap map;
	    Button btnShowMyLocation;
	    LocationListener locationlisten;
	    private LocationRequest lr;
	    private LocationClient lc;
	

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View v = inflater.inflate(R.layout.show_my_location, container, false);

			btnShowMyLocation = (Button) v.findViewById(R.id.button_show_my_location);
	        // Gets the MapView from the XML layout and creates it
	        mapView = (MapView) v.findViewById(R.id.maplocation);
	        mapView.onCreate(savedInstanceState);

	        // Gets to GoogleMap from the MapView and does initialization stuff
	        map = mapView.getMap();
	        map.getUiSettings().setMyLocationButtonEnabled(true);
	        map.getUiSettings().setAllGesturesEnabled(true);
	        map.setMyLocationEnabled(true);
	        map.getUiSettings().setZoomControlsEnabled(true);

	        MapsInitializer.initialize(this.getActivity());	
	       
	        btnShowMyLocation.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
			        try {
						LatLng mapCenter = new LatLng(-26.189486, 28.031666);
			        	
			        				        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 13));
			        	
			        				        // Flat markers will rotate when the map is rotated,
			        				        // and change perspective when the map is tilted.
			        				        map.addMarker(new MarkerOptions()
			        				                .icon(BitmapDescriptorFactory.fromResource(R.drawable.carmain))
			        				                .position(mapCenter)
			        				                .flat(true)
			        				                .rotation(245));
			        	map.getMyLocation();
			        	Toast.makeText(getActivity(),"Lat :"+map.getMyLocation().getLatitude() +" Lon : "+map.getMyLocation().getLongitude(), Toast.LENGTH_LONG).show();	
					} catch (Exception e) {
						Toast.makeText(getActivity(),"Lat/Lon : "+e.getMessage(), Toast.LENGTH_LONG).show();			
						// TODO: handle exception
					}
			        
			        // Updates the location and zoom of the MapView
//			        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(-26.189486, 28.031666), 10);
//			        map.animateCamera(cameraUpdate);
			
//			        
//					LatLng mapCenter = new LatLng(-26.189486, 28.031666);
//
//			        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 13));
//
//			        // Flat markers will rotate when the map is rotated,
//			        // and change perspective when the map is tilted.
//			        map.addMarker(new MarkerOptions()
//			                .icon(BitmapDescriptorFactory.fromResource(R.drawable.appbartruck))
//			                .position(mapCenter)
//			                .flat(true)
//			                .rotation(245));
//
//			        CameraPosition cameraPosition = CameraPosition.builder()
//			                .target(mapCenter)
//			                .zoom(13)
//			                .bearing(90)
//			                .build();
//			        
//			        // Animate the change in camera view over 2 seconds
//			        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
//			                2000, null);

					
				}
			});

	        return v;
	    }
	  
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        lr = LocationRequest.create();
	        lr.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	        lc = new LocationClient(getActivity(),this, this);
	        lc.connect();
	    }
	    
	    @Override
	    public void onResume() {
	        mapView.onResume();
	        super.onResume();
	    }

	    @Override
	    public void onDestroy() {
	        super.onDestroy();
	        mapView.onDestroy();
	    }

	    @Override
	    public void onLowMemory() {
	        super.onLowMemory();
	        mapView.onLowMemory();
	    }

		@Override
		public void onConnectionFailed(ConnectionResult arg0) {
			// TODO Auto-generated method stub
		     
		}

		@Override
		public void onConnected(Bundle connectionHint) {
			// TODO Auto-generated method stub
			 lc.requestLocationUpdates(lr, this);
		}

		@Override
		public void onDisconnected() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			 
		    		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom( new LatLng(location.getLatitude(), location.getLongitude()), 15);
	        		
	        		
			        map.addMarker(new MarkerOptions()
	                .icon(BitmapDescriptorFactory.fromResource(R.drawable.appbaruser))
	                .position(new LatLng(location.getLatitude(), location.getLongitude()))
	                .flat(true)
	                .rotation(245));
			        
			        map.animateCamera(cameraUpdate);
			 			
		}


}

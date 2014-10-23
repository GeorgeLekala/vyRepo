package com.jeremyfeinstein.slidingmenu.example;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.w3c.dom.Document;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.QueryOrder;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;


public class TrackingFragment extends Fragment implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener, LocationListener, com.google.android.gms.location.LocationListener {
    
		private MobileServiceClient mClient;
		private MobileServiceTable<BookingItem> mCommandTable;
		public List<BookingItem> mComItem;
		public BookingItem mGetUserItem;
		public SharedPreferences sharedpreferences;
		public String session_username;
		public BookingItem mItem;
		public String toast;
		Circle circle=null;
		/////////////////////////////////////////////////////
		
	 	MapView mapView;
	    GoogleMap map;
	    private LocationRequest lr;
	    private LocationClient lc;
	    double radius;
	    LatLng mapCenter1;
	    double Haversineradius;
	    Random rColour;
	    int executeonce=0;
	    double tempx=0;
	    double tempy=0;
	    boolean commandexecuted;
	    LatLng fromPosition;
	    LatLng toPosition;
	    GMapV2Direction md;
	    
		

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	      
	    	View v = inflater.inflate(R.layout.show_my_location, container, false);
 		
	    	  if (android.os.Build.VERSION.SDK_INT > 9) {
	              StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	              StrictMode.setThreadPolicy(policy);
	          }
	    	  
	    	sharedpreferences = this.getActivity().getSharedPreferences("user_session", Context.MODE_PRIVATE);		
		    session_username = sharedpreferences.getAll().get("username").toString();
		
		    mGetUserItem = new BookingItem();
	
			// Create the Mobile Service Client instance, using the provided
			// Mobile Service URL and key
			try {
				mClient = new MobileServiceClient(
						"https://locationawarepm.azure-mobile.net/",
						"FOySPsltTolaITxbZQmzvbOgHsnzSr93",
						getActivity());	
				
				mCommandTable = mClient.getTable(BookingItem.class);
				
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				Toast.makeText(getActivity(),"Error in MobileServiceClient: "+e1.getMessage(),Toast.LENGTH_LONG).show();
	    		
			}
			
	    	  
	    	  
			// Gets the MapView from the XML layout and creates it
	        mapView = (MapView) v.findViewById(R.id.maplocation);
	        mapView.onCreate(savedInstanceState);
	        
	        // Gets to GoogleMap from the MapView and does initialization stuff
	        map = mapView.getMap();
	        map.getUiSettings().setMyLocationButtonEnabled(true);
	        map.getUiSettings().setAllGesturesEnabled(true);
	        map.setMyLocationEnabled(true);
	        map.getUiSettings().setZoomControlsEnabled(true);
	        
	    	md = new GMapV2Direction();
	 	
			HashMap<String, Double> latlongpick =  Utility.getLocationFromAddress(Utility.getPreference("pickup_key"), getActivity());
			HashMap<String, Double> latlongdrop =  Utility.getLocationFromAddress(Utility.getPreference("drop_key"), getActivity());
			
			fromPosition = new LatLng(latlongpick.get("getLatitude"), latlongpick.get("getLongitude"));	
			toPosition = new LatLng(latlongdrop.get("getLatitude"), latlongdrop.get("getLongitude"));	
		
	   		map.addMarker(new MarkerOptions()
	   		.position(fromPosition)
	   		.title("Rishal"));
	   		
	   		map.addMarker(new MarkerOptions()
	   		.position(toPosition)
			.title("Driver"));
	   		
	   		Document doc = md.getDocument(fromPosition, toPosition, GMapV2Direction.MODE_DRIVING);
	   		
	   		//get distance and duration
	   		int duration = md.getDurationValue(doc);
			String distance = md.getDistanceText(doc); 
			
			//calling utility class to draw direction
	   		Utility.drawDirection(doc, md, map);
	    
	        MapsInitializer.initialize(this.getActivity());	
   
	        return v;
	    }
	  
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        lr = LocationRequest.create().setInterval(10).setFastestInterval(5).setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
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
			
			 double x,y;
			 x= location.getLatitude();
			 y= location.getLongitude();

		 	 if(executeonce==0)
		 	 {  
				   map.addMarker(new MarkerOptions()
			       .draggable(true)
			       .position(new LatLng(x, y))
			       .title("Simulated Vehicle"));
				   
					LatLng fromUserPosition = new LatLng(x,y);	
					Utility.drawDirection(fromUserPosition, toPosition, md, map);
				
					
				   tempx = x;
			 	   tempy = y;
			 	   executeonce++;		 
		 	 }

		
		
		}

}

		        



package com.jeremyfeinstein.slidingmenu.example;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainView extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		    
			View view = inflater.inflate(R.layout.bookingapplication, container, false);;
		    
		     Button buttonRegister = (Button) view.findViewById(R.id.btnNextbook);
		        
		     buttonRegister.setOnClickListener(new View.OnClickListener() {
		    		
		            public void onClick(View arg0) {
		     
		        		try {
		        		
		    		    	Fragment newFragment = new ChooseRide();
		    		  		FragmentManager fm = getFragmentManager();
		    		  		FragmentTransaction ft = fm.beginTransaction();	
		    		  		ft.replace(R.id.content_frame, newFragment);
		    		  		ft.addToBackStack(null)
		    		  		.commit();
		    		  		return;
			     
		        		} catch (Exception e) {
		        			Toast.makeText(getActivity(),"Error in BookTripFragment: "+e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
		        		}
		            	
		            }
			});
		   	    
		    return view;
	}
}

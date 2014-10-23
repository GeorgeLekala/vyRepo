package com.jeremyfeinstein.slidingmenu.example;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class BookTripFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		     View view = inflater.inflate(R.layout.bookingapplication, container, false);
		    
		     Button buttonRegister = (Button) getActivity().findViewById(R.id.btnfinishreg);
		        
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

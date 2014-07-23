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

public class SummaryFrag extends Fragment {
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
				 View view = inflater.inflate(R.layout.summary, container, false);
			    
			     Button buttonRegister = (Button) view.findViewById(R.id.btnPay);
			        
			     buttonRegister.setOnClickListener(new View.OnClickListener() {
			    		
			            public void onClick(View arg0) {
			     
			        		try {
			
			    		    	Fragment newFragment = new TrackingFragment();
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


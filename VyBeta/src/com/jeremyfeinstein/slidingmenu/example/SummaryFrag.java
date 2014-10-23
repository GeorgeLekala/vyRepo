package com.jeremyfeinstein.slidingmenu.example;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SummaryFrag extends Fragment {

	public SharedPreferences summarypreferences;
    public ImageLoader imageLoader;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
				View row = inflater.inflate(R.layout.summary, container, false);
			    
				summarypreferences = getActivity().getSharedPreferences("summary_session", Context.MODE_PRIVATE);	
				
				TextView destination = (TextView) row.findViewById(R.id.txtpaydestination); 
				ImageView imageVehicle = (ImageView) row.findViewById(R.id.imagepaymentvehicle); 
				ImageView imageperson = (ImageView) row.findViewById(R.id.imagepayperson); 
				TextView txt_trip_price = (TextView) row.findViewById(R.id.txtpayprice); 
				TextView txtvehiclename = (TextView) row.findViewById(R.id.txtpayvehicle); 
				TextView txtpaytime = (TextView) row.findViewById(R.id.txtpaytime); 
				TextView txtnumberpeople = (TextView) row.findViewById(R.id.txtpaypassengers);
				TextView txtpayname = (TextView) row.findViewById(R.id.txtpayname);
			    
				Button buttonRegister = (Button) row.findViewById(R.id.btnPay);

				txtpaytime.setText(summarypreferences.getAll().get("getTime").toString()); 
			    destination.setText(summarypreferences.getAll().get("getDestination").toString()); 
			    txtnumberpeople.setText(summarypreferences.getAll().get("getNpassengers").toString()); 
				txt_trip_price.setText(summarypreferences.getAll().get("getTripPrice").toString()); 
				txtvehiclename.setText(summarypreferences.getAll().get("getVehicleName").toString()); 
				txtpayname.setText(summarypreferences.getAll().get("getDriverName").toString()); 

				int position = Integer.parseInt(Utility.getPreference("storeposition"));
				imageLoader.DisplayImage(Utility.mVehicles[position], imageVehicle);
			    imageLoader.DisplayImage(Utility.mDrivers[position], imageperson);
				
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
			        
			    return row;
		}
}
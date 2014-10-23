package com.jeremyfeinstein.slidingmenu.example;

import android.app.ProgressDialog;
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

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponseCallback;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;

public class addRides extends Fragment {
	 /**
	* Mobile Service Client reference
	*/
	private MobileServiceClient mClient;
	/**
	 * Mobile Service Table used to access data
	 */
	private MobileServiceTable<ShuttleServices> mCommandTable;
	public ShuttleServices mGetShuttleItem;
	public ShuttleServices mItem;
	public SharedPreferences userpreferences;
	public ProgressDialog ringProgressDialog;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
			View row = inflater.inflate(R.layout.addingride, container, false);
	
			final TextView addcapacity = (TextView) row.findViewById(R.id.addcapacity); 
			final TextView addcompanyname = (TextView) row.findViewById(R.id.addcompanyname); 
			final TextView adddriverimage = (TextView) row.findViewById(R.id.adddriverimage); 
			final TextView adddrivername = (TextView) row.findViewById(R.id.adddrivername); 
			final TextView addvehicleimg = (TextView) row.findViewById(R.id.addvehicleimg); 
			final TextView addvehiclename = (TextView) row.findViewById(R.id.addvehiclename); 
			final TextView addrideprice = (TextView) row.findViewById(R.id.addrideprice); 
			
			
			Button btnAddRide = (Button) row.findViewById(R.id.btnAddRide);

			try
			{	
				mGetShuttleItem = new ShuttleServices();
				mItem = new ShuttleServices();
			
	      			  
	  			mClient = new MobileServiceClient(
	  					"https://locationawarepm.azure-mobile.net/",
	  					"FOySPsltTolaITxbZQmzvbOgHsnzSr93",
	  					getActivity()).withFilter(new ProgressFilter());		
	  			
	  			mCommandTable = mClient.getTable(ShuttleServices.class);
	  			
			    btnAddRide.setOnClickListener(new View.OnClickListener() {
			    		
			            public void onClick(View arg0) {
			     
			        		try {

			        			
			    	      		mGetShuttleItem.setmId("");
			    				mGetShuttleItem.setCompanyName(addcompanyname.getText().toString());
			    				mGetShuttleItem.setDriverImage(adddriverimage.getText().toString());
			    				mGetShuttleItem.setDriverName(adddrivername.getText().toString());
			    				mGetShuttleItem.setRating("3");
			    				mGetShuttleItem.setSeatNumber(addcapacity.getText().toString());
			    				mGetShuttleItem.setTripPrice(addrideprice.getText().toString());
			    				mGetShuttleItem.setVehicleimage(addvehicleimg.getText().toString());
			    				mGetShuttleItem.setVehicleName(addvehiclename.getText().toString());
			    				
			    				addNewItem(mGetShuttleItem);
			    	
			        		} catch (Exception e) {
			        			Toast.makeText(getActivity(),"Error in BookTripFragment: "+e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
			        		}
			            	
			            }
				});
			
			} catch (Exception e) {
				Toast.makeText(getActivity(),"Error in Credit Card: "+e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
			}
			return row;
	}
	
	public void addNewItem(ShuttleServices item) {
		
		if (mClient == null) {
			return;
		}
	
		// Insert the new item
		mCommandTable.insert(item, new TableOperationCallback<ShuttleServices>() {

			public void onCompleted(ShuttleServices entity, Exception exception, ServiceFilterResponse response) {
				
				if (exception == null) {
					
    		    	Fragment newFragment = new BookTripFragment();
    		  		FragmentManager fm = getFragmentManager();
    		  		FragmentTransaction ft = fm.beginTransaction();	
    		  		ft.replace(R.id.content_frame, newFragment);
    		  		ft.addToBackStack(null)
    		  		.commit();
    		  		return;
	     
				} else {
					Toast.makeText(getActivity(),"Error "+exception.getMessage(),Toast.LENGTH_LONG).show();
		        	
				}

			}
		});

	}
	
	private class ProgressFilter implements ServiceFilter {
		
		@Override
		public void handleRequest(ServiceFilterRequest request, NextServiceFilterCallback nextServiceFilterCallback,final ServiceFilterResponseCallback responseCallback) {

			getActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {
			        try {
			        	
					 ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...", "Loading ...", true);
					    
				     ringProgressDialog.setCancelable(false);
				        
						
					} catch (Exception e) {
				
						e.printStackTrace();
					}
			    
				}
			});
			
			nextServiceFilterCallback.onNext(request, new ServiceFilterResponseCallback() {
				
				@Override
				public void onResponse(ServiceFilterResponse response, Exception exception) {
					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							try {
								
							 ringProgressDialog.dismiss();
							    
							} catch (Exception e) {
								
								e.printStackTrace();
							}
						
						}
					});
					
					if (responseCallback != null)  responseCallback.onResponse(response, exception);
				}
			});
		}
	}

}

package com.jeremyfeinstein.slidingmenu.example;

import java.util.List;
import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.Toast;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.QueryOrder;
import com.microsoft.windowsazure.mobileservices.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponseCallback;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;


public class MainView extends Fragment {
	
	private MobileServiceClient mClient;
	private MobileServiceTable<BookingItem> mCommandTable;
	public String userId = "";
	public String userpass = "";
	public String username = "";
	public String usercommand = "";
	public String userauth = "";
	public String userlast = "";
	public static final String SENDER_ID = "197130149089";
	public List<BookingItem> mComItem;
	public BookingItem mGetUserItem;
	public SharedPreferences sharedpreferences;
	public String session_username;
	public BookingItem mItem;
	public String toast;
	private ProgressDialog ringProgressDialog;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		    View view = inflater.inflate(R.layout.bookingapplication, container, false);
		    
		    Button btnNextbook = (Button) view.findViewById(R.id.btnNextbook);
		        
	        final EditText reg_txtpickup = (EditText) view.findViewById(R.id.reg_txtpickup);
	        final EditText reg_txtdropoff = (EditText) view.findViewById(R.id.reg_txtdropoff);
	        final EditText reg_txtnpassenger = (EditText) view.findViewById(R.id.reg_txtnpassenger);
	        final EditText reg_txtDD = (EditText) view.findViewById(R.id.reg_txtDD);
	        final EditText reg_txtdatemonth = (EditText) view.findViewById(R.id.reg_txtdatemonth);
	        final EditText reg_txtyears = (EditText) view.findViewById(R.id.reg_txtyears);

		     setHasOptionsMenu(true);
				
				// Create the Mobile Service Client instance, using the provided
				// Mobile Service URL and key
				try {
					
				    sharedpreferences = this.getActivity().getSharedPreferences("user_session", Context.MODE_PRIVATE);	
				    
					mClient = new MobileServiceClient(
							"https://locationawarepm.azure-mobile.net/",
							"FOySPsltTolaITxbZQmzvbOgHsnzSr93",
							getActivity()).withFilter(new ProgressFilter());		
					
					mCommandTable = mClient.getTable(BookingItem.class);
					
				    session_username = sharedpreferences.getAll().get("username").toString();
					mGetUserItem = new BookingItem();
					mItem = new BookingItem();
				 	
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					Toast.makeText(getActivity(),"Error in BookingFragmentt: "+e1.getMessage(),Toast.LENGTH_LONG).show();
		    		
				}
				
				btnNextbook.setOnClickListener(new View.OnClickListener() {
		    		
		            public void onClick(View arg0) {
		     
		        		try {
		        			
	        			mItem.setPickuplocation(reg_txtpickup.getText().toString());
						mItem.setDroplocation(reg_txtdropoff.getText().toString());
						mItem.setNpassengers(reg_txtnpassenger.getText().toString());
						mItem.setDd(reg_txtDD.getText().toString());
						mItem.setMm(reg_txtdatemonth.getText().toString());
						mItem.setYyyy(reg_txtyears.getText().toString());
						mItem.setUsername(session_username);

				 
						addNewItem(mItem);
						
			     
		        		} catch (Exception e) {
		        			Toast.makeText(getActivity(),"Error in BookTripFragment: "+e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
		        		}
		            	
		            }
			});
		        
		    return view;
		    
	}
	
	
	public void refreshFrag()
	{
	      FragmentManager manager = getActivity().getSupportFragmentManager();
          FragmentTransaction ft = manager.beginTransaction();
          Fragment newFragment = this;
          this.onDestroy();
          ft.remove(this);
          ft.replace(R.id.content_frame,newFragment);
          ft.addToBackStack(null);   
          ft.commit();
	 
	}
	
	public void addNewItem(BookingItem item) {
		
		if (mClient == null) {
			return;
		}
	
		// Insert the new item
		mCommandTable.insert(item, new TableOperationCallback<BookingItem>() {

			public void onCompleted(BookingItem entity, Exception exception, ServiceFilterResponse response) {
				
				if (exception == null) {
					
					Toast.makeText(getActivity(),"Sucecessful", Toast.LENGTH_LONG).show();
					Fragment newFragment = new ChooseRide();
    		  		FragmentManager fm = getFragmentManager();
    		  		FragmentTransaction ft = fm.beginTransaction();	
    		  		ft.replace(R.id.content_frame, newFragment);
    		  		ft.addToBackStack(null)
    		  		.commit();
    		  		return;
    		  		
					//refreshFrag();
					
				} else {

					Toast.makeText(getActivity(),"Failed : " +exception.getMessage(), Toast.LENGTH_LONG).show();
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
						// TODO Auto-generated catch block
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
								// TODO: handle exception
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

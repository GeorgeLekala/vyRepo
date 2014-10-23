package com.jeremyfeinstein.slidingmenu.example;


import java.util.List;

import android.R.mipmap;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponseCallback;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;

public class ChooseRide extends Fragment{

	
	private ProgressDialog ringProgressDialog;
	
		private MobileServiceClient mClient;
		private MobileServiceTable<ShuttleServices> mCommandTable;	
		public ShuttleServices mGetUserItem;
		private ShuttleServiceAdapter mAdapter;
		public SharedPreferences summarypreferences;
		
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

		    View view = inflater.inflate(R.layout.shuttlelist, container, false);
			
			try {
	
			    summarypreferences = getActivity().getSharedPreferences("summary_session", Context.MODE_PRIVATE);	    
			    mGetUserItem = new ShuttleServices(); 
				mClient = new MobileServiceClient(
						"https://locationawarepm.azure-mobile.net/",
						"FOySPsltTolaITxbZQmzvbOgHsnzSr93",getActivity()).withFilter(new ProgressFilter(ringProgressDialog,getActivity()));		
				
				mCommandTable = mClient.getTable(ShuttleServices.class);
				
				// Create an adapter to bind the items with the view
				mAdapter = new ShuttleServiceAdapter(getActivity(), R.layout.list_chooseride,Utility.mVehicles,Utility.mDrivers);
				ListView listShuttles = (ListView) view.findViewById(R.id.listshuttle);
				listShuttles.setAdapter(mAdapter);
				

				listShuttles.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			      @Override
			      public void onItemClick(AdapterView<?> parent, final View view,
			          int position, long id) {
			    
					    summarypreferences.edit().putString("getCompanyName",mAdapter.getItem(position).getCompanyName().toString()).commit();
					    summarypreferences.edit().putString("getVehicleName",mAdapter.getItem(position).getVehicleName().toString()).commit();
					    summarypreferences.edit().putString("getTripPrice",mAdapter.getItem(position).getTripPrice().toString()).commit();
					    summarypreferences.edit().putString("getDriverName",mAdapter.getItem(position).getDriverName().toString()).commit();
						
					    BookingItem item  = Utility.getItemsFromPrefs(getActivity());
					    item.setDrivername(mAdapter.getItem(position).getVehicleName().toString());
					    item.setTripPrice(mAdapter.getItem(position).getTripPrice().toString());
					    
					    Utility.addItemstoPrefs(item, getActivity());
					    
				        Utility.setPreference("storeposition",""+position,getActivity());
  
				        Fragment newFragment = new SummaryFrag();
				  		FragmentManager fm = getFragmentManager();
				  		FragmentTransaction ft = fm.beginTransaction();	
				  		ft.replace(R.id.content_frame, newFragment);
				  		ft.addToBackStack(null).commit();
			    
			      }

			    });
			    
				
				loadData();
				
		
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(getActivity(),"Error in OnCreate", Toast.LENGTH_SHORT).show();
				
				e.printStackTrace();
			}
			
			return view;
		}
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState){
			super.onActivityCreated(savedInstanceState);
		}
		
		private void loadData() {

			try 
			{
				mCommandTable.execute(new TableQueryCallback<ShuttleServices>() {
				
				public void onCompleted(List<ShuttleServices> result, int count, Exception exception, ServiceFilterResponse response) {
					   if(exception == null){
		                 	
			    		    for (ShuttleServices item : result) {
		           
			    		    	mAdapter.add(item);
		                    }
		               } 
		                else 
		                {
		                	Toast.makeText(getActivity(), "Error in load data", Toast.LENGTH_LONG).show();         
		                }
			     
			     }});
					
			}
			catch (Exception e) {
					// TODO Auto-generated catch block
					Toast.makeText(getActivity(), "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			}

	}

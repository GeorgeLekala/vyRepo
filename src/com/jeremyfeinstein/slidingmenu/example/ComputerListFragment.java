package com.jeremyfeinstein.slidingmenu.example;

import java.util.ArrayList;
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
import android.widget.ListView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.QueryOrder;
import com.microsoft.windowsazure.mobileservices.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponseCallback;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;

public class ComputerListFragment extends SherlockListFragment{

	 /**
	* Mobile Service Client reference
	*/
	private MobileServiceClient mClient;
	/**
	 * Mobile Service Table used to access data
	 */
	private MobileServiceTable<CommandItem> mCommandTable;
	public String userId = "";
	public String userpass = "";
	public String username = "";
	public String usercommand = "";
	public String userauth = "";
	public String userlast = "";
	public String session_username;
	public List<CommandItem> mComItem;
	public CommandItem mGetUserItem;
	public String test = "";
	private ProgressDialog ringProgressDialog;
	public SharedPreferences sharedpreferences;
	public SharedPreferences comp_preferences;
	
	public ArrayList<String> listcontents;
		
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
			
			 View view = inflater.inflate(R.layout.list, container, false);	
			 setHasOptionsMenu(true);
		
			// Create the Mobile Service Client instance, using the provided
			// Mobile Service URL and key
			try {
				
			    sharedpreferences = this.getActivity().getSharedPreferences("user_session", Context.MODE_PRIVATE);	
			    comp_preferences = this.getActivity().getSharedPreferences("comp_session", Context.MODE_PRIVATE);	
			    
				mClient = new MobileServiceClient(
						"https://locationawarepm.azure-mobile.net/",
						"FOySPsltTolaITxbZQmzvbOgHsnzSr93",
						getActivity()).withFilter(new ProgressFilter());		
				
				mCommandTable = mClient.getTable(CommandItem.class);
				

			    session_username = sharedpreferences.getAll().get("username").toString();
		        
				mGetUserItem = new CommandItem();
				
			 	listcontents = new ArrayList<String>();
			 	listcontents.add("Computer 1");
		 		getDBCompName(session_username);			
			 	listcontents.add(comp_preferences.getAll().get("computername").toString());
		
			 	
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				Toast.makeText(getActivity(),"Error in MobileServiceClient: "+e1.getMessage(),Toast.LENGTH_LONG).show();
	    		
			}
			
			return view;
		}


	    @Override
	    public boolean onOptionsItemSelected(MenuItem item){
	        switch(item.getItemId())
	        {
	        case R.id.github:
	            Toast.makeText(getSherlockActivity(), "Refreshing...", Toast.LENGTH_SHORT).show();
	            refreshFrag();
	            return true;
	        }
	        return super.onOptionsItemSelected(item);
	    }
	    
		@Override
		public void onActivityCreated(Bundle savedInstanceState){
			super.onActivityCreated(savedInstanceState);
			setListAdapter(new ContentArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, listcontents));
		}

		public void refreshFrag()
		{
		      FragmentManager manager = getActivity().getSupportFragmentManager();
	          FragmentTransaction ft = manager.beginTransaction();
	          Fragment newFragment = this;
	          this.onDestroy();
	          ft.remove(this);
	          ft.replace(R.id.content_frame,newFragment);
	           //container is the ViewGroup of current fragment
	          ft.addToBackStack(null);   
	          ft.commit();
		 
		}
		
		
		public String getDBCompName(String uname) 
		{
			try {
				// Get the items that weren't marked as completed and add them in the
				// adapter
				mCommandTable.where().field("username").
				eq(uname).orderBy("__createdAt", QueryOrder.Descending).
				top(1).execute(new TableQueryCallback<CommandItem>() {

					public void onCompleted(List<CommandItem> result, int count, Exception exception, ServiceFilterResponse response) {
						if (exception == null) {
				
							for (CommandItem item : result) {
								
							 	test = item.getComputername().toString();	 
						        comp_preferences.edit().putString("computername", test).commit();
								
							}
							
						
							
						} else {
							Toast.makeText(getActivity(),"OnCompleted : "+exception.getMessage().toString(), Toast.LENGTH_LONG).show();
						     
						}
					}
				});
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(getActivity(),"GetDB : "+e.getMessage().toString(), Toast.LENGTH_LONG).show();
			}

			return test;
		}
		
		@Override
		public void onListItemClick(ListView l, View v, int position, long id){
		    Fragment newFragment = null;
			switch(position){
			case 0:
				newFragment = new ProcessListFragment();
				break;
			case 1:
				newFragment = new PowerFragment();
				break;
			case 2:
				newFragment = new PowerFragment();
				break;
			case 3:
				newFragment = new PowerFragment();
				break;
			case 4:
				newFragment = new PowerFragment();
			 	break;
			case 5:
				newFragment = new PowerFragment();

			}
			if (newFragment != null){
				switchFragment(newFragment);}
			
		
		}
		// the meat of switching the above fragment
		private void switchFragment(Fragment fragment) {
			if (getActivity() == null)
				return;
			
			if (getActivity() instanceof MainActivity) {
				MainActivity fca = (MainActivity) getActivity();
				fca.switchContent(fragment);
			} 
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

package com.jeremyfeinstein.slidingmenu.example;
import java.net.MalformedURLException;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
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

public class PowerFragment extends Fragment {
	
	
	private MobileServiceClient mClient;
	
	private String test;
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
	public static final String SENDER_ID = "197130149089";
	public List<CommandItem> mComItem;
	public CommandItem mGetUserItem;
	public SharedPreferences sharedpreferences;
	public String session_username;
	public CommandItem mItem;
	public String toast;
	private ProgressDialog ringProgressDialog;

	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		 	setHasOptionsMenu(true); 
		 	
			View view = inflater.inflate(R.layout.power_control, container, false);
			
			Button btn_shutdown = (Button) view.findViewById(R.id.btn_shutdown);
			
	        Button btn_restart = (Button) view.findViewById(R.id.btn_restart);
		    
		    Button btn_wakeonlan = (Button) view.findViewById(R.id.btn_wakeonlan);
		    
		    Button btn_sleep = (Button) view.findViewById(R.id.btn_sleep);
		    
		    Button btn_hibernate = (Button) view.findViewById(R.id.btn_hibernate);
		    
		    Button btn_abort = (Button) view.findViewById(R.id.btn_abort);
		
		    
		    sharedpreferences = this.getActivity().getSharedPreferences("user_session", Context.MODE_PRIVATE);		
			    
		    session_username = sharedpreferences.getAll().get("username").toString();
		
	       	
			mGetUserItem = new CommandItem();
			mItem = new CommandItem();
			
			// Create the Mobile Service Client instance, using the provided
			// Mobile Service URL and key
			try {
				mClient = new MobileServiceClient(
						"https://locationawarepm.azure-mobile.net/",
						"FOySPsltTolaITxbZQmzvbOgHsnzSr93",
						getActivity()).withFilter(new ProgressFilter());	
				
				//register for push
				GCMRegistrar.checkDevice(getActivity());
				GCMRegistrar.checkManifest(getActivity());
				final String regId = GCMRegistrar.getRegistrationId(getActivity());
				if (!regId.equals("")) 
					Log.w("TodoActivity", regId);
				if (GCMRegistrar.getRegistrationId(getActivity()).equals("")) {
				    GCMRegistrar.register(getActivity(), SENDER_ID);
				}
				
				mCommandTable = mClient.getTable(CommandItem.class);
				
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				Toast.makeText(getActivity(),"Error in MobileServiceClient: "+e1.getMessage(),Toast.LENGTH_LONG).show();
	    		
			}
		
		    // Listening Friends button click
		    btn_abort.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View view) {
	
					try {
						 if(getDBUsername(session_username).getmUsername().toString()!=null)
						 {
							 	
							 	getDBUsername(session_username).setmCommand("IGNORE");
								addNewItem(getDBUsername(session_username));
						 }
						
					} catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(getActivity(),"Try again :"+ e.getMessage(), Toast.LENGTH_LONG).show();
							
					}
				}
			});
		    
		    // Listening btn_hibernate button click
		    btn_hibernate.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View view) {
	
					try {
						 if(getDBUsername(session_username).getmUsername().toString()!=null)
						 {
							 	
							 	getDBUsername(session_username).setmCommand("HIBERNATE");
								addNewItem(getDBUsername(session_username));
						 }
						
					} catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(getActivity(),"Try again :"+ e.getMessage(), Toast.LENGTH_LONG).show();
							
					}
				}
			});
	
		    // Listening btn_sleep button click
		    btn_sleep.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View view) {
	
					try {
						 if(getDBUsername(session_username).getmUsername().toString()!=null)
						 {
							 	
							 	getDBUsername(session_username).setmCommand("SLEEP");
								addNewItem(getDBUsername(session_username));
						 }
						
					} catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(getActivity(),"Try again :"+ e.getMessage(), Toast.LENGTH_LONG).show();
							
					}
				}
			});
		    
		    // Listening btn_shutdown button click
		    btn_shutdown.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View view) {
	
					try {
						 if(getDBUsername(session_username).getmUsername().toString()!=null)
						 {
							 	
							 	getDBUsername(session_username).setmCommand("SHUTDOWN");
								addNewItem(getDBUsername(session_username));
						 }
						
					} catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(getActivity(),"Try again :"+ e.getMessage(), Toast.LENGTH_LONG).show();
							
					}
				}
			});
		    
		    // Listening btn_restart button click
		    btn_restart.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View view) {

					try {
						 if(getDBUsername(session_username).getmUsername().toString()!=null)
						 {
							 	
							 	getDBUsername(session_username).setmCommand("RESTART");
								addNewItem(getDBUsername(session_username));
						 }
						
					} catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(getActivity(),"Try again :"+ e.getMessage(), Toast.LENGTH_LONG).show();
							
					}
				}
			});
		
		    // Listening btn_wakeonlan button click
		    btn_wakeonlan.setOnClickListener(new View.OnClickListener() {
				
		    	
				@Override
				public void onClick(View view) {
					
					try {
						
						 if(getDBUsername(session_username).getmUsername().toString()!=null)
						 {
							 	
							 	getDBUsername(session_username).setmCommand("LOCK");
								addNewItem(getDBUsername(session_username));
						 }
						
					} catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(getActivity(),"Try again :"+ e.getMessage(), Toast.LENGTH_LONG).show();
							
					}	
				
				}
			});
		    
			loadazure(session_username);
	
			return view;
	}
	
	public void loadazure(String msession_username)
	{
		try {
			
		    getDBUsername(session_username).getmUsername().toString();
				 
		} catch (Exception e) {
		    
		}
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

	public void addNewItem(CommandItem item) {
		
		if (mClient == null) {
			return;
		}
	
		// Insert the new item
		mCommandTable.insert(item, new TableOperationCallback<CommandItem>() {

			public void onCompleted(CommandItem entity, Exception exception, ServiceFilterResponse response) {
				
				if (exception == null) {
					
					Toast.makeText(getActivity(),"Sucecessful", Toast.LENGTH_LONG).show();
					refreshFrag();
					
				} else {

					Toast.makeText(getActivity(),"Failed : " +exception.getMessage(), Toast.LENGTH_LONG).show();
				}

			}
		});

	}
	
	public CommandItem getDBUsername(String uname) 
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
						
							mItem.setMfirstname(item.getMfirstname().toString());
							mItem.setMlastname(item.getMlastname().toString());
							mItem.setMauth_level(item.getMauth_level().toString());
							mItem.setmCommand(item.getmCommand().toString());
							mItem.setmUsername(item.getmUsername().toString());
							mItem.setmPassword(item.getmPassword().toString());
							mItem.setmWorkgoup(item.getmWorkgoup().toString());
							mItem.setmWorkgoup_state(item.getmWorkgoup_state().toString());
							
						}
						
					} else {
						Toast.makeText(getActivity(),exception.getMessage(), Toast.LENGTH_LONG).show();
						
					}
				}
			});
		
			
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getActivity(),e.getMessage(), Toast.LENGTH_LONG).show();
		}
		
		return mItem;
		
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

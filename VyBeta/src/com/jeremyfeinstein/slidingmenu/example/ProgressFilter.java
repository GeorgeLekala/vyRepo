package com.jeremyfeinstein.slidingmenu.example;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.microsoft.windowsazure.mobileservices.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponseCallback;


public class ProgressFilter implements ServiceFilter {
		
		private ProgressDialog ringProgressDialog;
		private Activity activity;;
		
		public ProgressFilter(ProgressDialog ringDialog,Activity act)
		{
			this.ringProgressDialog = ringDialog;
			this.activity = act;
		}
		
		@Override
		public void handleRequest(ServiceFilterRequest request, NextServiceFilterCallback nextServiceFilterCallback,final ServiceFilterResponseCallback responseCallback) {
	
	
			activity.runOnUiThread(new Runnable() {
	
				@Override
				public void run() {
			        try {
			        	
					   ringProgressDialog = ProgressDialog.show(activity, "Please wait ...", "Loading ...", true);
					    
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
					activity.runOnUiThread(new Runnable() {
	
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



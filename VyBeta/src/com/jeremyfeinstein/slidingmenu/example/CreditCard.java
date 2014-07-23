package com.jeremyfeinstein.slidingmenu.example;

import java.net.MalformedURLException;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponseCallback;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;

public class CreditCard extends Activity {
	 /**
	* Mobile Service Client reference
	*/
	private MobileServiceClient mClient;
	/**
	 * Mobile Service Table used to access data
	 */
	private MobileServiceTable<UserRegistration> mCommandTable;
	public String userId = "";
	public String userpass = "";
	public String username = "";
	public String usercommand = "";
	public String userauth = "";
	public Spinner spinner1;
	public String client_wg;
	public List<UserRegistration> mComItem;
	public UserRegistration mGetUserItem;
	public UserRegistration mItem;
	public SharedPreferences userpreferences;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	
        // Set View to register.xml
        setContentView(R.layout.creditcard);
        
        Button buttonRegister = (Button)findViewById(R.id.btnfinishreg);
    
		userpreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE);	
		
        final EditText creditcard = (EditText) findViewById(R.id.reg_txtcreditcard);
        final EditText cvv = (EditText) findViewById(R.id.reg_txtcvv);
        final EditText month = (EditText) findViewById(R.id.reg_txtmonth);
        final EditText year = (EditText) findViewById(R.id.reg_txtyear);
        
        mGetUserItem = new UserRegistration();
        mItem = new UserRegistration();
		
      		// Create the Mobile Service Client instance, using the provided
      		// Mobile Service URL and key
      		try {
      			
      			  
      			mClient = new MobileServiceClient(
      					"https://locationawarepm.azure-mobile.net/",
      					"FOySPsltTolaITxbZQmzvbOgHsnzSr93",
      					this).withFilter(new ProgressFilter());		
      			
      			mCommandTable = mClient.getTable(UserRegistration.class);
      			
      			
      		} catch (MalformedURLException e1) {
      			// TODO Auto-generated catch block
      			Toast.makeText(getApplicationContext(),"Error in MobileServiceClient: "+e1.getMessage(),Toast.LENGTH_LONG).show();
          		
      		}

        
        buttonRegister.setOnClickListener(new View.OnClickListener() {
    		
            public void onClick(View arg0) {
     
        		try {
        		
        			mGetUserItem.setmId("");
    				mGetUserItem.setCreditcardnumber(creditcard.getText().toString());
    				mGetUserItem.setCmm(month.getText().toString());
    				mGetUserItem.setCvv(cvv.getText().toString());
    				mGetUserItem.setCyy(year.getText().toString()); 
    				mGetUserItem.setMfirstname(userpreferences.getAll().get("firstname_session").toString());
    				mGetUserItem.setMlastname(userpreferences.getAll().get("lasttname_session").toString());
    				mGetUserItem.setmUsername(userpreferences.getAll().get("email_session").toString());
    				mGetUserItem.setmPassword(userpreferences.getAll().get("password_session").toString());
    				mGetUserItem.setMphonenumber(userpreferences.getAll().get("phonenumber_session").toString());
        			
        			addNewItem(mGetUserItem);
        			
        	
        		} catch (Exception e) {
        			Toast.makeText(getApplicationContext(),"Error in Credit Card: "+e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
        		}
            	
            }

		});
	}
	
	public void addNewItem(UserRegistration item) {
		
		if (mClient == null) {
			return;
		}
	
		// Insert the new item
		mCommandTable.insert(item, new TableOperationCallback<UserRegistration>() {

			public void onCompleted(UserRegistration entity, Exception exception, ServiceFilterResponse response) {
				
				if (exception == null) {
					Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
		        	
					Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                    finish();
	     
				} else {
					Toast.makeText(getApplicationContext(),"Error "+exception.getMessage(),Toast.LENGTH_LONG).show();
		        	
				}

			}
		});

	}
	
	private class ProgressFilter implements ServiceFilter {
		
		@Override
		public void handleRequest(ServiceFilterRequest request, NextServiceFilterCallback nextServiceFilterCallback,final ServiceFilterResponseCallback responseCallback) {


			runOnUiThread(new Runnable() {

				@Override
				public void run() {
			        try {
			        	
					 //  ringProgressDialog = ProgressDialog.show(getApplicationContext(), "Please wait ...", "Loading ...", true);
					    
				     //  ringProgressDialog.setCancelable(false);
				        
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    
				}
			});
			
			nextServiceFilterCallback.onNext(request, new ServiceFilterResponseCallback() {
				
				@Override
				public void onResponse(ServiceFilterResponse response, Exception exception) {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							try {
								
							 //   ringProgressDialog.dismiss();
							    
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

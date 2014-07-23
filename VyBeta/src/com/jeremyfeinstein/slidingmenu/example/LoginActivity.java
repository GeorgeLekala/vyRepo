package com.jeremyfeinstein.slidingmenu.example;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.QueryOrder;
import com.microsoft.windowsazure.mobileservices.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponseCallback;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;
 
public class LoginActivity extends Activity{
	

	private MobileServiceClient mClient;
	private MobileServiceTable<UserRegistration> mCommandTable;	
	public String userpass = "";
	public String username = "";
	public String refresh;
	public UserRegistration mGetUserItem;
	private ProgressDialog ringProgressDialog;
	private EditText user_name;
	private EditText password;
    private TextView registerScreen;
    private TextView loginErr;
	public SharedPreferences sharedpreferences;


	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
		// Create the Mobile Service Client instance, using the provided
		// Mobile Service URL and key
        registerScreen = (TextView) findViewById(R.id.link_to_register);
        loginErr = (TextView) findViewById(R.id.loginError);
        
		try {
	        
	        sharedpreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE);	
	        
	        mGetUserItem = new UserRegistration(); 
			
			mClient = new MobileServiceClient(
					"https://locationawarepm.azure-mobile.net/",
					"FOySPsltTolaITxbZQmzvbOgHsnzSr93",
					this).withFilter(new ProgressFilter());		
			
			mCommandTable = mClient.getTable(UserRegistration.class);		
			
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			Toast.makeText(getApplicationContext(),"Error in MobileServiceClient: "+e1.getMessage(),Toast.LENGTH_LONG).show();
    		
		}
 
        // Listening to register new account link
        registerScreen.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
   
        
        Button buttonRegister = (Button)findViewById(R.id.btnLogin);
        user_name = (EditText) findViewById(R.id.loginemail);
        password = (EditText) findViewById(R.id.loginpassword);
        
		// Listening to register new account link
        buttonRegister.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View v) {
               	  
               	  try {
               		  
	           		loginErr.setVisibility(View.GONE);
	           		getDBUsername(user_name.getText().toString());
	 	  		
               	  }catch (Exception e)
               	  {
               		  Toast.makeText(getApplicationContext(),"Please Try Again", Toast.LENGTH_LONG).show();
				         
               	  }
					 	             
            }
        });
 
  	    loginErr.setVisibility(View.GONE);
        
  	    sharedpreferences.edit().clear().commit(); 

	}

	
	public void getDBUsername(String uname) 
	{
		try {
			// Get the items that weren't marked as completed and add them in the
			// adapter		
			mCommandTable.where().field("username").
			eq(uname).orderBy("__createdAt", QueryOrder.Descending).
			top(1).execute(new TableQueryCallback<UserRegistration>() {

				public void onCompleted(List<UserRegistration> result, int count, Exception exception, ServiceFilterResponse response) {
					if (exception == null) {
			
						for (UserRegistration item : result) {

							try {
								if(user_name.getText().toString().equals(item.getmUsername().toString()) && item.getmPassword().toString().equals(SHA1(password.getText().toString())))
								{
									sharedpreferences.edit().putString("username", user_name.getText().toString()).commit();

									Intent i = new Intent(getApplicationContext(), MainActivity.class);
								    startActivity(i);
								    finish();
								
								}
									 else
								{
									Toast.makeText(getApplicationContext(),"Incorrect details : Try Again :)", Toast.LENGTH_LONG).show();
								      
								}
							} catch (NoSuchAlgorithmException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
							
						}
						
					} else {
						Toast.makeText(getApplicationContext(),exception.getMessage(), Toast.LENGTH_LONG).show();
						
					}
				}
			});
		
			
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	  
	private class ProgressFilter implements ServiceFilter {
			
			@Override
			public void handleRequest(ServiceFilterRequest request, NextServiceFilterCallback nextServiceFilterCallback,final ServiceFilterResponseCallback responseCallback) {


				runOnUiThread(new Runnable() {

					@Override
					public void run() {
				        try {
				        	
						   ringProgressDialog = ProgressDialog.show(LoginActivity.this, "Please wait ...", "Loading ...", true);
						    
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
						runOnUiThread(new Runnable() {

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
	 
	 @Override
	public void onDestroy() {
	     super.onDestroy();
	     if (ringProgressDialog != null) {
	    	 ringProgressDialog.dismiss();
	    	 ringProgressDialog = null;
	     }
	 }

	public static String convertToHex(byte[] data)
	  {
			StringBuilder buf = new StringBuilder();
			for(byte b:data)
			{
				int halfbyte = (b>>>4) & 0x0F;
			    int two_halfs = 0;
			    
			    do{
			    	buf.append((0<=halfbyte)&&(halfbyte<=9)?(char)('0'+halfbyte):(char)('a'+(halfbyte-10)));
			    	halfbyte = b & 0x0F;
			    }while(two_halfs++ < 1);
			}
			
			return buf.substring(0,buf.length()-2);
	   }
	  
	public String SHA1(String text) throws NoSuchAlgorithmException,UnsupportedEncodingException{
		
		  MessageDigest md = MessageDigest.getInstance("SHA-1");
		  md.update(text.getBytes("iso-8859-1"),0,text.length());
		  byte[] sha1hash = md.digest();
		  return convertToHex(sha1hash);
	  }
	
}
	

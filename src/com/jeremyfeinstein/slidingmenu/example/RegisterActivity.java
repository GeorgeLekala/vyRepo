package com.jeremyfeinstein.slidingmenu.example;

import static com.microsoft.windowsazure.mobileservices.MobileServiceQueryOperations.val;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.json.JSONObject;

import com.actionbarsherlock.internal.widget.IcsAdapterView;
import com.actionbarsherlock.internal.widget.IcsAdapterView.OnItemSelectedListener;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponseCallback;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
 
public class RegisterActivity extends Activity implements  android.widget.AdapterView.OnItemSelectedListener{
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
	public Spinner spinner1;
	public String client_wg;
	
	public List<CommandItem> mComItem;
	public CommandItem mGetUserItem;
	
	private ProgressBar mProgressBar;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	
        // Set View to register.xml
        setContentView(R.layout.register);
        
        //spinner1 = (Spinner)  findViewById(R.id.reg_txtworkgroup);
        
       // spinner1.setOnItemSelectedListener(this);
 
		//mProgressBar = (ProgressBar) findViewById(R.id.loadingProgressBar);

		// Initialize the progress bar
		//mProgressBar.setVisibility(ProgressBar.GONE);
		
		//mGetUserItem = new CommandItem();
		
		// Create the Mobile Service Client instance, using the provided
		// Mobile Service URL and key
//		try {
//			mClient = new MobileServiceClient(
//					"https://locationawarepm.azure-mobile.net/",
//					"FOySPsltTolaITxbZQmzvbOgHsnzSr93",
//					this).withFilter(new ProgressFilter());		
//			
//			mCommandTable = mClient.getTable(CommandItem.class);
//			
//			
//		} catch (MalformedURLException e1) {
//			// TODO Auto-generated catch block
//			Toast.makeText(getApplicationContext(),"Error in MobileServiceClient: "+e1.getMessage(),Toast.LENGTH_LONG).show();
//    		
//		}
		
        //TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
        Button buttonRegister = (Button)findViewById(R.id.btnnextreg);

	    buttonRegister.setOnClickListener(new View.OnClickListener() {
		
            public void onClick(View arg0) {
     
        		try {

        			Intent i = new Intent(getApplicationContext(), CreditCard.class);
                    startActivity(i);
                    finish();

        		} catch (Exception e) {
        			Toast.makeText(getApplicationContext(),"Error in Registration: "+e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
        		}
            	
            }
	});
	
	    
	    // Listening to Login Screen link
//        loginScreen.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent i = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(i);
//			}
//
//        });
	}
	
	

	
	public void addNewItem(CommandItem item) {
		
		if (mClient == null) {
			return;
		}
	
		// Insert the new item
		mCommandTable.insert(item, new TableOperationCallback<CommandItem>() {

			public void onCompleted(CommandItem entity, Exception exception, ServiceFilterResponse response) {
				
				if (exception == null) {
					
					createAndShowDialog("Sucecessful", "Registration");
					
				} else {
					createAndShowDialog(exception, "Error Adding New Item");
				}

			}
		});

	}

	public String getDBUserID(String uname) 
	{
		// Get the items that weren't marked as completed and add them in the
		// adapter		
		mCommandTable.where().field("username").eq(val(uname)).execute(new TableQueryCallback<CommandItem>() {

			public void onCompleted(List<CommandItem> result, int count, Exception exception, ServiceFilterResponse response) {
				if (exception == null) {
		
					for (CommandItem item : result) {
						userId = item.getmId().toString();
					}
					
				} else {
					createAndShowDialog(exception, "Error in getUser");
				}
			}
		});
	
		return userId;
	}
	
	public String getDBUserLevel(String uname) 
	{
		// Get the items that weren't marked as completed and add them in the
		// adapter		
		mCommandTable.where().field("username").eq(val(uname)).execute(new TableQueryCallback<CommandItem>() {

			public void onCompleted(List<CommandItem> result, int count, Exception exception, ServiceFilterResponse response) {
				if (exception == null) {
		
					for (CommandItem item : result) {
						userauth = item.getMauth_level().toString();
					}
					
				} else {
					createAndShowDialog(exception, "Error in Authentication");
				}
			}
		});
	
		return userauth;
	}
	
	public String getDBUsername(String uname) 
	{
		// Get the items that weren't marked as completed and add them in the
		// adapter		
		mCommandTable.where().field("username").eq(val(uname)).execute(new TableQueryCallback<CommandItem>() {

			public void onCompleted(List<CommandItem> result, int count, Exception exception, ServiceFilterResponse response) {
				if (exception == null) {
		
					for (CommandItem item : result) {
						 username = item.getmUsername().toString();
					}
					
				} else {
					createAndShowDialog(exception, "Error in getUser");
				}
			}
		});
	
		return username;
	}
	
	public String getDBUserPass(String uname) 
	{
		// Get the items that weren't marked as completed and add them in the
		// adapter		
		mCommandTable.where().field("username").eq(val(uname)).execute(new TableQueryCallback<CommandItem>() {

			public void onCompleted(List<CommandItem> result, int count, Exception exception, ServiceFilterResponse response) {
				if (exception == null) {
		
					for (CommandItem item : result) {
						userpass = item.getmPassword().toString();
					}
					
				} else {
					createAndShowDialog(exception, "Error in getUser");
				}
			}
		});
	
		return userpass;
	}
	
	/**
	 * Creates a dialog and shows it
	 * 
	 * @param exception
	 *            The exception to show in the dialog
	 * @param title
	 *            The dialog title
	 */
	public void createAndShowDialog(Exception exception, String title) {
		Throwable ex = exception;
		if(exception.getCause() != null){
			ex = exception.getCause();
		}
		createAndShowDialog(ex.getMessage(), title);
	}

	/**
	 * Creates a dialog and shows it
	 * 
	 * @param message
	 *            The dialog message
	 * @param title
	 *            The dialog title
	 */
	public void createAndShowDialog(String message, String title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage(message);
		builder.setTitle(title);
		builder.create().show();
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
	  
	  public static String SHA1(String text) throws NoSuchAlgorithmException,UnsupportedEncodingException{
		
		  MessageDigest md = MessageDigest.getInstance("SHA-1");
		  md.update(text.getBytes("iso-8859-1"),0,text.length());
		  byte[] sha1hash = md.digest();
		  return convertToHex(sha1hash);
	  }
	  
	  private class ProgressFilter implements ServiceFilter {
			
			@Override
			public void handleRequest(ServiceFilterRequest request, NextServiceFilterCallback nextServiceFilterCallback,
					final ServiceFilterResponseCallback responseCallback) {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.VISIBLE);
					}
				});
				
				nextServiceFilterCallback.onNext(request, new ServiceFilterResponseCallback() {
					
					@Override
					public void onResponse(ServiceFilterResponse response, Exception exception) {
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.GONE);
							}
						});
						
						if (responseCallback != null)  responseCallback.onResponse(response, exception);
					}
				});
			}
		}

		@Override
	     public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
		
		 client_wg = parent.getItemAtPosition(pos).toString();
			
		}


		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			 
			
		}
	  
}
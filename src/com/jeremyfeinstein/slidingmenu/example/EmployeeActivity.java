package com.jeremyfeinstein.slidingmenu.example;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

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

import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class EmployeeActivity extends Activity {
	 /** Called when the activity is first created. */
	
	public static final String MIME_TEXT_PLAIN = "text/plain";
	public static final String TAG = "NfcDemo";
	
	private MobileServiceClient mClient;
	private ProgressDialog ringProgressDialog;
	
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
	private ProgressBar mProgressBar;
	public SharedPreferences sharedpreferences;
	public String session_username;
	public CommandItem mItem;
	public String toast;

	private NfcAdapter mNfcAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_main);     
        
	    sharedpreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE);		
	    
	    session_username = sharedpreferences.getAll().get("username").toString();
	    
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        
       	
		mGetUserItem = new CommandItem();
		mItem = new CommandItem();
        
     // Create the Mobile Service Client instance, using the provided
     			// Mobile Service URL and key
     			try {
     				mClient = new MobileServiceClient(
     						"https://locationawarepm.azure-mobile.net/",
     						"FOySPsltTolaITxbZQmzvbOgHsnzSr93",
     						this).withFilter(new ProgressFilter());	
     				
     				//register for push
     				GCMRegistrar.checkDevice(this);
     				GCMRegistrar.checkManifest(this);
     				final String regId = GCMRegistrar.getRegistrationId(this);
     				if (!regId.equals("")) 
     					Log.w("TodoActivity", regId);
     				if (GCMRegistrar.getRegistrationId(this).equals("")) {
     				    GCMRegistrar.register(this, SENDER_ID);
     				}
     				
     				mCommandTable = mClient.getTable(CommandItem.class);
     				
     			} catch (MalformedURLException e1) {
     				// TODO Auto-generated catch block
     				Toast.makeText(this,"Error in MobileServiceClient: "+e1.getMessage(),Toast.LENGTH_LONG).show();
     	    		
     			}
        
		if (mNfcAdapter == null) {
			// Stop here, we definitely need NFC
			Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
			finish();
			return;

		}
	
		if (!mNfcAdapter.isEnabled()) {
			Toast.makeText(this, "NFC is disabled.", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "Attach NFC Tag to read the content.", Toast.LENGTH_LONG).show();
		}
		
		handleIntent(getIntent());
		loadazure(session_username);
		
    }
    
	public void loadazure(String msession_username)
	{
		try {
			   String zzz = getDBUsername(msession_username).getmUsername().toString();
				 
		} catch (Exception e) {
		    //Toast.makeText(getApplicationContext(),"Loading...", Toast.LENGTH_LONG).show();
			
		}
	}
    
	@Override
	protected void onResume() {
		super.onResume();
		
		/*
		 * It's important, that the activity is in the foreground (resumed). Otherwise
		 * an IllegalStateException is thrown. 
		 */
		setupForegroundDispatch(this, mNfcAdapter);
	}
	
	@Override
	protected void onPause() {
		/*
		 * Call this before onPause, otherwise an IllegalArgumentException is thrown as well.
		 */
		stopForegroundDispatch(this, mNfcAdapter);
		
		super.onPause();
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		/*
		 * This method gets called, when a new Intent gets associated with the current activity instance.
		 * Instead of creating a new activity, onNewIntent will be called. For more information have a look
		 * at the documentation.
		 * 
		 * In our case this method gets called, when the user attaches a Tag to the device.
		 */
		handleIntent(intent);
	}
	
	private void handleIntent(Intent intent) {
		String action = intent.getAction();
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
			
			String type = intent.getType();
			if (MIME_TEXT_PLAIN.equals(type)) {

				Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
				new NdefReaderTask().execute(tag);
				
			} else {
				Log.d(TAG, "Wrong mime type: " + type);
			}
		} else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
			
			// In case we would still use the Tech Discovered Intent
			Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			String[] techList = tag.getTechList();
			String searchedTech = Ndef.class.getName();
			
			for (String tech : techList) {
				if (searchedTech.equals(tech)) {
					new NdefReaderTask().execute(tag);
					break;
				}
			}
		}
	}
	
	public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
		final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

		final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);

		IntentFilter[] filters = new IntentFilter[1];
		String[][] techList = new String[][]{};

		// Notice that this is the same filter as in our manifest.
		filters[0] = new IntentFilter();
		filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
		filters[0].addCategory(Intent.CATEGORY_DEFAULT);
		try {
			filters[0].addDataType(MIME_TEXT_PLAIN);
		} catch (MalformedMimeTypeException e) {
			throw new RuntimeException("Check your mime type.");
		}
		
		adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
	}
	
	public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
		adapter.disableForegroundDispatch(activity);
	}
	
	private class NdefReaderTask extends AsyncTask<Tag, Void, String> {

		@Override
		protected String doInBackground(Tag... params) {
			Tag tag = params[0];
			
			Ndef ndef = Ndef.get(tag);
			if (ndef == null) {
				// NDEF is not supported by this Tag. 
				return null;
			}

			NdefMessage ndefMessage = ndef.getCachedNdefMessage();

			NdefRecord[] records = ndefMessage.getRecords();
			for (NdefRecord ndefRecord : records) {
				if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
					try {
						return readText(ndefRecord);
					} catch (UnsupportedEncodingException e) {
						Log.e(TAG, "Unsupported Encoding", e);
					}
				}
			}

			return null;
		}
		
		private String readText(NdefRecord record) throws UnsupportedEncodingException {
			/*
			 * See NFC forum specification for "Text Record Type Definition" at 3.2.1 
			 * 
			 * http://www.nfc-forum.org/specs/
			 * 
			 * bit_7 defines encoding
			 * bit_6 reserved for future use, must be 0
			 * bit_5..0 length of IANA language code
			 */

			byte[] payload = record.getPayload();

			// Get the Text Encoding
			String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";

			// Get the Language Code
			int languageCodeLength = payload[0] & 0063;
			
			// String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
			// e.g. "en"
			
			// Get the Text
			return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
	}
		
	@Override
	protected void onPostExecute(String result) {
		if (result != null) {
			Toast.makeText(getApplicationContext(),"Read content: " + result, Toast.LENGTH_LONG).show();		
			try {
				
				 if(getDBUsername(session_username).getmUsername().toString()!=null)
				 {
					 	
					 	getDBUsername(session_username).setmCommand(result);
						addNewItem(getDBUsername(session_username));
				 }
				
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(getApplicationContext(),"Try again :"+ e.getMessage(), Toast.LENGTH_LONG).show();
					
			}
		}
	}
	}
	
	public void refreshFrag()
	{
		finish();
		startActivity(getIntent());
	}
	

	 private class ProgressFilter implements ServiceFilter {
			
			@Override
			public void handleRequest(ServiceFilterRequest request, NextServiceFilterCallback nextServiceFilterCallback,final ServiceFilterResponseCallback responseCallback) {


				runOnUiThread(new Runnable() {

					@Override
					public void run() {
				        try {
				        	
						    ringProgressDialog = ProgressDialog.show(EmployeeActivity.this, "Please wait ...", "Loading ...", true);
						    
					        ringProgressDialog.setCancelable(false);
					        
							//Thread.sleep(10000);
							//if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.VISIBLE);
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				        
					    //ringProgressDialog.dismiss();
					    //ringProgressDialog.cancel();
				        
				    
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
									//Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
									
								}
							
								//if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.GONE);
							}
						});
						
						if (responseCallback != null)  responseCallback.onResponse(response, exception);
					}
				});
			}
		}
	
		public void addNewItem(CommandItem item) {
			
			if (mClient == null) {
				return;
			}
		
			// Insert the new item
			mCommandTable.insert(item, new TableOperationCallback<CommandItem>() {

				public void onCompleted(CommandItem entity, Exception exception, ServiceFilterResponse response) {
					
					if (exception == null) {
						
						Toast.makeText(getApplicationContext(),"Sucecessful", Toast.LENGTH_LONG).show();
						refreshFrag();
						
					} else {

						Toast.makeText(getApplicationContext(),"Failed : " +exception.getMessage(), Toast.LENGTH_LONG).show();
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
							Toast.makeText(getApplicationContext(),exception.getMessage(), Toast.LENGTH_LONG).show();
							
						}
					}
				});
			
				
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
			}
			
			return mItem;
			
		}
    /**
     * Button click handler on Main activity
     * @param v
     */
    public void onButtonClicker(View v)
    {
    	Intent intent;
    	
    	switch (v.getId()) {
		case R.id.main_btn_user:
			intent = new Intent(this, EmployeeActivity_User.class);
			startActivity(intent);
			break;

		case R.id.main_btn_shutdown:
			intent = new Intent(this, EmployeeActivity_Shutdown.class);
			startActivity(intent);
			break;
			
		case R.id.main_btn_sleep:
			intent = new Intent(this, EmployeeActivity_Sleep.class);
			startActivity(intent);
			break;
			
		case R.id.main_btn_wake:
			intent = new Intent(this, EmployeeActivity_Wake.class);
			startActivity(intent);
			break;
			
		case R.id.main_btn_reports:
			intent = new Intent(this, EmployeeActivity_Reports.class);
			startActivity(intent);
			break;
			
		case R.id.main_btn_signout:
			intent = new Intent(this, EmployeeActivity_Signout.class);
			startActivity(intent);
			break;	
		default:
			break;
		}
    }
}




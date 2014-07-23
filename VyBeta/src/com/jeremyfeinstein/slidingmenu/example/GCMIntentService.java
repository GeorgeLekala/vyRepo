package com.jeremyfeinstein.slidingmenu.example;

import java.net.MalformedURLException;

import android.content.Context;
import android.content.Intent;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.util.Log;
import com.google.android.gcm.GCMBaseIntentService;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;

public class GCMIntentService extends GCMBaseIntentService {

	public GCMIntentService(){
	    super(ToDoActivity.SENDER_ID);
	}
	
	@Override
	protected void onError(Context arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		
		 Builder mBuilder = new Notification.Builder(context)
	                .setSmallIcon(R.drawable.ic_launcher)
	                .setContentTitle("Todo Created!")
	                .setPriority(Notification.PRIORITY_HIGH)
	                .setContentText(intent.getStringExtra("message"));
		 
	    NotificationManager mNotificationManager =
	        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	    
	    
	    mNotificationManager.notify(0, mBuilder.build());
	}

	@Override
	protected void onRegistered(Context context, String registrationId) {
		MobileServiceClient client;
		try {
			client = new MobileServiceClient(
					"https://locationawarepm.azure-mobile.net/",
					"FOySPsltTolaITxbZQmzvbOgHsnzSr93",
					context);
		
			MobileServiceTable<Registration> registrationTable = client.getTable(Registration.class);
			Registration registration = new Registration();
			registration.setRegistrationId(registrationId);		
			registrationTable.insert(registration, new TableOperationCallback<Registration>() {
				@Override
				public void onCompleted(Registration entity, Exception exception,
						ServiceFilterResponse response) {
					if (exception != null) {
						Log.e("GCMIntentService", "Exception - " + exception.getMessage());
					} else {
						Log.i("GCMIntentService", "Success");
					}
				}
	 
			});
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		// TODO Auto-generated method stub

	}

}

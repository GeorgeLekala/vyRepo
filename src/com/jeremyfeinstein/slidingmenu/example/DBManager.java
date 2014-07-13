package com.jeremyfeinstein.slidingmenu.example;

import static com.microsoft.windowsazure.mobileservices.MobileServiceQueryOperations.val;

import java.net.MalformedURLException;
import java.util.List;
import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;

public class DBManager{
	

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
	public static final String SENDER_ID = "197130149089";
	public List<CommandItem> mComItem;
	public CommandItem mGetUserItem;
	
	public DBManager(Context cont)
	{
		
		// Create the Mobile Service Client instance, using the provided
		// Mobile Service URL and key
		try {
			mClient = new MobileServiceClient(
					"https://locationawarepm.azure-mobile.net/",
					"FOySPsltTolaITxbZQmzvbOgHsnzSr93",
					cont);
			
			// Get the Mobile Service Table instance to use
			mCommandTable = mClient.getTable(CommandItem.class);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			Toast.makeText(cont,"Error in Registration: "+e.getMessage(),Toast.LENGTH_LONG).show();
    		
		}


		
	}
	
	/**
	 * Creates a dialog and shows it
	 * 
	 * @param exception
	 *            The exception to show in the dialog
	 * @param title
	 *            The dialog title
	 */
	public void createAndShowDialog(Exception exception, String title, Context c) {
		Throwable ex = exception;
		if(exception.getCause() != null){
			ex = exception.getCause();
		}
		createAndShowDialog(ex.getMessage(), title,c);
	}

	/**
	 * Creates a dialog and shows it
	 * 
	 * @param message
	 *            The dialog message
	 * @param title
	 *            The dialog title
	 */
	public void createAndShowDialog(String message, String title, Context c) {
		AlertDialog.Builder builder = new AlertDialog.Builder(c);

		builder.setMessage(message);
		builder.setTitle(title);
		builder.create().show();
	}
	
	

	public void addNewItem(CommandItem item,final Context t) {
		
		if (mClient == null) {
			return;
		}
	
		// Insert the new item
		mCommandTable.insert(item, new TableOperationCallback<CommandItem>() {

			public void onCompleted(CommandItem entity, Exception exception, ServiceFilterResponse response) {
				
				if (exception == null) {
					
					createAndShowDialog(exception, "Success",t);
					
				} else {
					createAndShowDialog(exception, "Error Add New Item",t);
				}

			}
		});

	}

	public String getDBUserID(String uname, final Context t) 
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
					createAndShowDialog(exception, "Error in getUser", t);
				}
			}
		});
	
		return userId;
	}
	
	public String getDBUserLevel(String uname,final Context t) 
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
					createAndShowDialog(exception, "Error in Authentication", t);
				}
			}
		});
	
		return userauth;
	}
	
	public String getDBUsername(String uname, final Context t) 
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
					createAndShowDialog(exception, "Error in getUser", t);
				}
			}
		});
	
		return username;
	}
	
	public String getDBUserPass(String uname, final Context t) 
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
					createAndShowDialog(exception, "Error in getUser", t);
				}
			}
		});
	
		return userpass;
	} 
	
	

}

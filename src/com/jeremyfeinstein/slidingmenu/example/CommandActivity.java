package com.jeremyfeinstein.slidingmenu.example;

import static com.microsoft.windowsazure.mobileservices.MobileServiceQueryOperations.*;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceQuery;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponseCallback;
import com.microsoft.windowsazure.mobileservices.TableDeleteCallback;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;
import com.microsoft.windowsazure.notifications.NotificationsManager;
import com.google.android.gcm.GCMRegistrar;

public class CommandActivity extends Activity {

	/**
	 * Mobile Service Client reference
	 */
	private MobileServiceClient mClient;


	/**
	 * Mobile Service Table used to access data
	 */
	private MobileServiceTable<CommandItem> mCommandTable;

	/**
	 * Adapter to sync the items list with the view
	 */
	private CommandItemAdapter mAdapter;

	/**
	 * EditText containing the "New ToDo" text
	 */
	private EditText mtxtUsername;
	private EditText mtxtPassword;
	private EditText mtxtCommand;
	public String userId = "";
	public String userpass = "";
	public String username = "";
	public String usercommand = "";
	public String userauth = "";

	/**
	 * Progress spinner to use for table operations
	 */
	private ProgressBar mProgressBar;
	
	
	public static final String SENDER_ID = "197130149089";
	
	public List<CommandItem> mComItem;
	public CommandItem mGetUserItem;
	/**
	 * Initializes the activity
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.command_activity);
		
		mProgressBar = (ProgressBar) findViewById(R.id.loadingProgressBar);

		// Initialize the progress bar
		mProgressBar.setVisibility(ProgressBar.GONE);
		
		try {
			
			NotificationsManager.handleNotifications(this, SENDER_ID, MyHandler.class);
			// Create the Mobile Service Client instance, using the provided
			// Mobile Service URL and key
			mClient = new MobileServiceClient(
					"https://locationawarepm.azure-mobile.net/",
					"FOySPsltTolaITxbZQmzvbOgHsnzSr93",
					this).withFilter(new ProgressFilter());
			
			GCMRegistrar.checkDevice(this);
			GCMRegistrar.checkManifest(this);
			if (GCMRegistrar.getRegistrationId(this).equals("")) {
			    GCMRegistrar.register(this, SENDER_ID);
			}

			// Get the Mobile Service Table instance to use
			mCommandTable = mClient.getTable(CommandItem.class);
			mComItem = null;
			mtxtUsername = (EditText) findViewById(R.id.txtUsername);
			mtxtPassword = (EditText) findViewById(R.id.txtPassword);
			mtxtCommand = (EditText) findViewById(R.id.txtCommand);

			// Create an adapter to bind the items with the view
			mAdapter = new CommandItemAdapter(this, R.layout.row_list_to_do);
			ListView listViewToDo = (ListView) findViewById(R.id.listViewToDo);
			listViewToDo.setAdapter(mAdapter);
		
			// Load the items from the Mobile Service
			refreshItemsFromTable();

		} catch (MalformedURLException e) {
			createAndShowDialog(new Exception("There was an error creating the Mobile Service. Verify the URL"), "Error");
		}
	}

	/**
	 * Initializes the activity menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * Select an option from the menu
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_refresh) {
			refreshItemsFromTable();
		}
		
		return true;
	}

	public void deleteItem(String id)
	{
	    mCommandTable.delete(id, new TableDeleteCallback() {
	        public void onCompleted(Exception exception, 
	                ServiceFilterResponse response) {
	            if(exception == null){
	                Log.i("Success","Object deleted");
	            }
	        }
	    });
	}
	
	public void updateItem(View view)
	{
		CommandItem item = new CommandItem();
		//item.setmId(getDBUserID(mtxtUsername.getText().toString()).toString());
		item.setmUsername(getDBUsername(mtxtUsername.getText().toString()).toString());
		item.setmPassword(getDBUserPass(mtxtUsername.getText().toString()).toString());
		item.setmCommand(mtxtCommand.getText().toString());
		
		//deleteItem(item.getmId().toString());
		item.setmId("");
		addNewItem(item);
		// Set the item as completed and update it in the table
		
	}
	
	/**
	 * Mark an item as completed
	 * 
	 * @param item
	 *            The item to mark
	 */
	public void checkItem(CommandItem item) {
		if (mClient == null) {
			return;
		}
		
		mCommandTable.update(item, new TableOperationCallback<CommandItem>() {

			public void onCompleted(CommandItem entity, Exception exception, ServiceFilterResponse response) {
				if (exception == null) {
					if (entity.getmCommand().equals("IGNORE")) {
						mAdapter.remove(entity);
					}
				} else {
					createAndShowDialog(exception, "Error in checkItem");
				}
			}

		});
	}

	/**
	 * Add a new item
	 * 
	 * @param view
	 *            The view that originated the call
	 */
	public void addItem(View view) {
		if (mClient == null) {
			return;
		}

		// Create a new item
		CommandItem item = new CommandItem();
		
		LoginActivity hashpassword = new LoginActivity();
		try {
			
			item.setmUsername(mtxtUsername.getText().toString());
			item.setmPassword((hashpassword.SHA1(mtxtPassword.getText().toString())));
			item.setmCommand(mtxtCommand.getText().toString());
			
			// Insert the new item
			mCommandTable.insert(item, new TableOperationCallback<CommandItem>() {

				public void onCompleted(CommandItem entity, Exception exception, ServiceFilterResponse response) {
					
					if (exception == null) {
						
						mAdapter.add(entity);
						
					} else {
						createAndShowDialog(exception, "Error Add Item");
					}

				}
			});

			mtxtUsername.setText("");
			mtxtPassword.setText("");
			mtxtCommand.setText("");
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
					
					mAdapter.add(entity);
					
				} else {
					createAndShowDialog(exception, "Error Add New Item");
				}

			}
		});

		mtxtUsername.setText("");
		mtxtPassword.setText("");
		mtxtCommand.setText("");

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
	 * Refresh the list with the items in the Mobile Service Table
	 */
	private void refreshItemsFromTable() {

		// Get the items that weren't marked as completed and add them in the
		// adapter
		mCommandTable.where().field("command").ne(val("IGNORE")).execute(new TableQueryCallback<CommandItem>() {

			public void onCompleted(List<CommandItem> result, int count, Exception exception, ServiceFilterResponse response) {
				if (exception == null) {
					mAdapter.clear();

					for (CommandItem item : result) {
						mAdapter.add(item);
					}

				} else {
					createAndShowDialog(exception, "Error in refreshItemsFromTable");
				}
			}
		});
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
}

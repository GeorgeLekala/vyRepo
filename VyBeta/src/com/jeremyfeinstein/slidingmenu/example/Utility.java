package com.jeremyfeinstein.slidingmenu.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.maps.GeoPoint;
import com.google.gson.Gson;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;


	public class Utility {
	
	public static BookingItem mItem;
    public static ArrayList<String> nameOfEvent = new ArrayList<String>();
    public static ArrayList<String> startDates = new ArrayList<String>();
    public static ArrayList<String> endDates = new ArrayList<String>();
    public static ArrayList<String> descriptions = new ArrayList<String>();
    static String tag = "Utility";
    public static SharedPreferences appSharedPrefs;
    public static int storeImagePosition;
	public static SharedPreferences summarypreferences;
    
	public static void setPreference(String key, String value, Context cont)
	{
		summarypreferences = cont.getSharedPreferences("session", Context.MODE_PRIVATE);
		summarypreferences.edit().putString(key,value).commit();		
	}
	
	public static String getPreference(String key)
	{
		return summarypreferences.getAll().get(key).toString();	
	}
    
    public static BookingItem getItemsFromPrefs(Context context)
    {
	    appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
	    Gson gson = new Gson();
		String json = appSharedPrefs.getString("mItem", "");
		return gson.fromJson(json, BookingItem.class);

    }
    
    public static void drawDirection(LatLng fromPosition,LatLng toPosition, GMapV2Direction md, GoogleMap map)
    {
    	Document doc = md.getDocument(fromPosition, toPosition, GMapV2Direction.MODE_DRIVING);
		ArrayList<LatLng> directionPoint = md.getDirection(doc);
		PolylineOptions rectLine = new PolylineOptions().width(5).color(Color.RED);
		
		for(int i = 0 ; i < directionPoint.size() ; i++) {			
			rectLine.add(directionPoint.get(i));
		}
		
		map.addPolyline(rectLine);
    }
    
    public static void drawDirection(Document doc,GMapV2Direction md, GoogleMap map)
    {
    	
		ArrayList<LatLng> directionPoint = md.getDirection(doc);
		PolylineOptions rectLine = new PolylineOptions().width(5).color(Color.RED);
		
		for(int i = 0 ; i < directionPoint.size() ; i++) {			
			rectLine.add(directionPoint.get(i));
		}
		
		map.addPolyline(rectLine);
    }
    
	public static String[] mVehicles={
            "http://www.topnotch.co.za/wp-content/uploads/2012/10/GalleryImage_1024x768_2.jpg",
	        "http://evoque.landrover.com/static/images/content/l538-pure-models-930x530.jpg",
	        "http://www.caradvice.com.au/wp-content/uploads/2012/09/Jaguar-F-Type-profile-studio.jpg",
	        "http://www.lavishcar.com/wp-content/uploads/2014/01/2014-mercedes-sls-amg-bla-19_1600x0w.jpg"
		    
	};

	public static String[] mDrivers={
            "http://www.financialguide.com/App_Images/agent-images/head-shots/635121095170686900JohnDoe.jpg",
	        "http://themes.mysitemyway.com/_shared/images/content/john_doe.jpg",
	        "http://www.singleblackmale.org/wp-content/uploads/2013/08/black-man1.jpg",
	        "http://www2.pictures.zimbio.com/gi/John+Paul+White+Muscle+Shoals+Premiere+Red+fwV0lMLaE8rl.jpg"
		    
	};
	
	public static HashMap<String, Double> getLocationFromAddress(String strAddress, Context context){

		Geocoder coder = new Geocoder(context);
		List<Address> address;
		HashMap<String, Double> latlong = null;
		//GeoPoint p1 = null;

		try {
			
			
		    address = coder.getFromLocationName(strAddress,5);
		    if (address == null) {
		        return null;
		    }
		    Address location = address.get(0);
		    latlong = new HashMap<String, Double>();
		    latlong.put("getLatitude", location.getLatitude());
		    latlong.put("getLongitude", location.getLongitude());
	

		  //p1 = new GeoPoint((int) (location.getLatitude() * 1E6), (int) (location.getLongitude() * 1E6));

			
		}catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(context,"Error"+e, Toast.LENGTH_LONG).show();
			
		}
		
		return latlong;
	}
    
    public static void addItemstoPrefs(BookingItem bookitem,Context context)
    {
    	appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
		Editor prefsEditor = appSharedPrefs.edit();
		Gson gson = new Gson();
		String json = gson.toJson(bookitem);
		prefsEditor.putString("mItem", json);
		prefsEditor.commit();
    }
    
    public static void addNewItem(BookingItem item,final Context context, MobileServiceTable<BookingItem> mBookingTable, MobileServiceClient mClient) {
		
		if (mClient == null) {
	
		}
	
		// Insert the new item
		mBookingTable.insert(item, new TableOperationCallback<BookingItem>() {
	
			public void onCompleted(BookingItem entity, Exception exception, ServiceFilterResponse response) {
				
				if (exception == null) {
					
					Toast.makeText(context,"Sucecessful", Toast.LENGTH_LONG).show();
			
					
				} else {
	
					Toast.makeText(context,"Failed : " +exception.getMessage(), Toast.LENGTH_LONG).show();
				
				}
	
			}
		});

	}
}
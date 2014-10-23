package com.jeremyfeinstein.slidingmenu.example;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.gson.Gson;


public class BookTripFragment extends Fragment implements OnItemClickListener {

	public SharedPreferences sharedpreferences;
	public SharedPreferences summarypreferences;
	public String session_username;
	public BookingItem mItem;
	public int hour,minute,year,month,day;
	public ArrayList<String> listlocation = new ArrayList<String>();;
	SharedPreferences  mPrefs;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		    View view = inflater.inflate(R.layout.bookingapplication, container, false);
		    
		    Button btnNextbook = (Button) view.findViewById(R.id.btnNextbook);
		        
		    mPrefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
	        final EditText reg_txtnpassenger = (EditText) view.findViewById(R.id.reg_txtnpassenger);
	        final EditText reg_txt_time = (EditText) view.findViewById(R.id.reg_txt_time);
	        final EditText reg_txt_date = (EditText) view.findViewById(R.id.reg_txt_date);

	        AutoCompleteTextView autopick = (AutoCompleteTextView) view.findViewById(R.id.autopickup);
	        AutoCompleteTextView autodrop = (AutoCompleteTextView) view.findViewById(R.id.autodrop);
	        autopick.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), R.layout.list_item));
	        autopick.setOnItemClickListener(this);
	        autodrop.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), R.layout.list_item));
	        autodrop.setOnItemClickListener(this);

		    setHasOptionsMenu(true);

		    sharedpreferences = this.getActivity().getSharedPreferences("user_session", Context.MODE_PRIVATE);	
		    summarypreferences = this.getActivity().getSharedPreferences("summary_session", Context.MODE_PRIVATE);	
		    session_username = sharedpreferences.getAll().get("username").toString();
			mItem = new BookingItem();
				 	
			reg_txt_time.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	                try {
	                    
	                	DialogFragment newFragment = new TimePickerFragment(hour,minute,reg_txt_time);
					    newFragment.show(getFragmentManager(), "timePicker");
					    
	                } catch (Exception except) {
	                    
	                }
	            }
	        });
			
			
			reg_txt_date.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	                try {
	                	
	                	   DialogFragment newFragment = new DatePickerFragment(year, month,day,reg_txt_date);
	                	   newFragment.show(getFragmentManager(), "datePicker");
	                    
	                } catch (Exception except) {
	                    
	                }
	            }
	        });

			btnNextbook.setOnClickListener(new View.OnClickListener() {
		    		
		            public void onClick(View arg0) {
		     
		        		try {
		        			
		        	    summarypreferences.edit().putString("getTime",TimePickerFragment.hour+":"+TimePickerFragment.minute).commit();
		        	    summarypreferences.edit().putString("getNpassengers",reg_txtnpassenger.getText().toString()).commit();
		        	    summarypreferences.edit().putString("getDestination",listlocation.get(0).toString()+" - "+listlocation.get(1).toString()).commit();
		        		
		        	    Utility.setPreference("pickup_session", "pickup_key", listlocation.get(0), getActivity());
		        	    Utility.setPreference("pickup_session", "drop_key", listlocation.get(1), getActivity());
			        	  
		        	    
						mItem.setPickuplocation(listlocation.get(0));
						mItem.setDroplocation(listlocation.get(1));
		        	    mItem.setNpassengers(reg_txtnpassenger.getText().toString());
						mItem.setDd(""+DatePickerFragment.day);
		        	    mItem.setMm(""+DatePickerFragment.month);
						mItem.setYyyy(""+DatePickerFragment.year);
						mItem.setHour(""+TimePickerFragment.hour);
						mItem.setMinute(""+TimePickerFragment.minute);
						mItem.setUsername(session_username);
						
						//adding bookingitem to sharedpreference
						Editor prefsEditor = mPrefs.edit();
						Gson gson = new Gson();
						String json = gson.toJson(mItem);
						prefsEditor.putString("mItem", json);
						prefsEditor.commit();

						Fragment newFragment = new ChooseRide();
	    		  		FragmentManager fm = getFragmentManager();
	    		  		FragmentTransaction ft = fm.beginTransaction();	
	    		  		ft.replace(R.id.content_frame, newFragment);
	    		  		ft.addToBackStack(null)
	    		  		.commit();
	    		  		return;
						
					
						} catch (Exception e) {
		        			Toast.makeText(getActivity(),"Error in BookTripFragment: "+e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
		        		}
		            	
		            }
			});
		        
		    return view;
		    
	}
	
	
	
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		// TODO Auto-generated method stub
		    String pick = (String) adapterView.getItemAtPosition(position);  
		    listlocation.add(pick);     
	}

	
}

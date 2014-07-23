package com.jeremyfeinstein.slidingmenu.example;


import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class ChooseRide extends ListFragment{

		ArrayList<String> impcar;
		ArrayList<String> imgperson;
		ArrayList<String> txtdrivername;
		ArrayList<String> txtcarname;
		ArrayList<String> txtdriverprice;
		SharedPreferences sharedpreferences;
		
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
			
			try {
				
				LoadShuttles init = new LoadShuttles(getActivity());
				init.getShuttleTable(getActivity());
				
				sharedpreferences = this.getActivity().getSharedPreferences("shuttleTable", Context.MODE_PRIVATE);	
				Toast.makeText(getActivity(), sharedpreferences.getAll().get("driverName").toString(), Toast.LENGTH_SHORT).show();
				
				impcar = new ArrayList<String>();
				imgperson = new ArrayList<String>();
				txtdrivername = new ArrayList<String>();
				txtcarname = new ArrayList<String>();
				txtdriverprice = new ArrayList<String>();
				
				impcar.add("drawable/toyotaquantum");
				imgperson.add("drawable/larry_page");
				txtdrivername.add("Larry Page");
				txtcarname.add("Toyota Quantum");
				txtdriverprice.add("400");
		
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	
			return inflater.inflate(R.layout.list, container, false);
		}
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState){
			super.onActivityCreated(savedInstanceState);
			//setListAdapter(new ChooseRideAdapter(getActivity(), android.R.layout.simple_list_item_1, txtdrivername, txtcarname,txtdriverprice));
		}
		
		@Override
		public void onListItemClick(ListView l, View v, int position, long id){
		    Fragment newFragment = null;
			switch(position){
			default:
				
				newFragment = new SummaryFrag();
		  		FragmentManager fm = getFragmentManager();
		  		FragmentTransaction ft = fm.beginTransaction();	
		  		ft.replace(R.id.content_frame, newFragment);
		  		ft.addToBackStack(null).commit();
		  		break;

			}
			if (newFragment != null){
				switchFragment(newFragment);}
		
		}
		// the meat of switching the above fragment
		private void switchFragment(Fragment fragment) {
			if (getActivity() == null)
				return;
			
			if (getActivity() instanceof MainActivity) {
				MainActivity fca = (MainActivity) getActivity();
				fca.switchContent(fragment);
			} 
		}

	}

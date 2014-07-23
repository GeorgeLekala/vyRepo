package com.jeremyfeinstein.slidingmenu.example;


import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
		

		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
			
			impcar = new ArrayList<String>();
			imgperson = new ArrayList<String>();
			txtdrivername = new ArrayList<String>();
			txtcarname = new ArrayList<String>();
			txtdriverprice = new ArrayList<String>();
			
			impcar.add("Car 1");
			imgperson.add("Larry");
			txtdrivername.add("Larry Page");
			txtcarname.add("Toyota Quantum");
			txtdriverprice.add("400");
			
			txtdrivername.add("Kabelo Seleke");
			txtcarname.add("Toyota Quantum");
			txtdriverprice.add("800");

			return inflater.inflate(R.layout.list, container, false);
		}
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState){
			super.onActivityCreated(savedInstanceState);
			setListAdapter(new ChooseRideAdapter(getActivity(), android.R.layout.simple_list_item_1, txtdrivername,txtcarname,txtdriverprice));
		}
		
		@Override
		public void onListItemClick(ListView l, View v, int position, long id){
		    Fragment newFragment = null;
			switch(position){
			case 0:
				Toast.makeText(getActivity(),"Case 0",Toast.LENGTH_LONG).show();
//				newFragment = new SummaryFrag();
//		  		FragmentManager fm = getFragmentManager();
//		  		FragmentTransaction ft = fm.beginTransaction();	
//		  		ft.replace(R.id.content_frame, newFragment);
//		  		ft.addToBackStack(null).commit();
		  		break;
		  		
			case 1:	
				Toast.makeText(getActivity(),"Case 1",Toast.LENGTH_LONG).show();
				
//				newFragment = new SummaryFrag();
//		  		FragmentManager fgm = getFragmentManager();
//		  		FragmentTransaction fgt = fgm.beginTransaction();	
//		  		fgt.replace(R.id.content_frame, newFragment);
//		  		fgt.addToBackStack(null).commit();
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

package com.jeremyfeinstein.slidingmenu.example;


import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ProcessListFragment extends ListFragment{

		ArrayList<String> list_contents;

		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
			
			list_contents = new ArrayList<String>();
			list_contents.add("Process 1");
			list_contents.add("Process 2");
			list_contents.add("Process 3");
			list_contents.add("Process 4");
			list_contents.add("Process 5");
			list_contents.add("Process 6");
			return inflater.inflate(R.layout.list, container, false);
		}
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState){
			super.onActivityCreated(savedInstanceState);
			setListAdapter(new ContentArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list_contents));
		}
		
		@Override
		public void onListItemClick(ListView l, View v, int position, long id){
		    Fragment newFragment = null;
			switch(position){
			case 0:
				newFragment = new TerminateProcessFragment();
				break;
			case 1:
				newFragment = new TerminateProcessFragment();
				break;
			case 2:
				newFragment = new TerminateProcessFragment();
				break;
			case 3:
				newFragment = new TerminateProcessFragment();
				break;
			case 4:
				newFragment = new TerminateProcessFragment();
			 	break;
			case 5:
				newFragment = new TerminateProcessFragment();

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

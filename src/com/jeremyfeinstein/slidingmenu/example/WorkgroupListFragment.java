package com.jeremyfeinstein.slidingmenu.example;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class WorkgroupListFragment extends ListFragment{

		ArrayList<String> list_contents;
		
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
			
			
			list_contents = new ArrayList<String>();
			list_contents.add("Workgroup 1");
			list_contents.add("Workgroup 2");
			list_contents.add("Workgroup 3");
			list_contents.add("Workgroup 4");
			list_contents.add("Workgroup 5");
			list_contents.add("Workgroup 6");
			
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
				newFragment = new PowerFragment();
				break;
			case 1:
				newFragment = new PowerFragment();
				break;
			case 2:
				newFragment = new PowerFragment();
				break;
			case 3:
				newFragment = new PowerFragment();
				break;
			case 4:
				newFragment = new PowerFragment();
			 	break;
			case 5:
				newFragment = new PowerFragment();

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

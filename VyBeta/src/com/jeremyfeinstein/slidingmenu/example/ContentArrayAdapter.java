package com.jeremyfeinstein.slidingmenu.example;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ContentArrayAdapter extends ArrayAdapter<String> {

	private Context context;
	private ArrayList<String> values;
	
	public ContentArrayAdapter(Context context, int resource,ArrayList<String> values) {
		//super(context, resource, textViewResourceId);
		super(context, R.layout.list_text_images, values);
		this.context = context;
		this.values =  values;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.list_text_images, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		textView.setText(values.get(position));
 
		// Change icon based on name
		String s = values.get(position);
 
		System.out.println(s);
 
		if (s.equals("Workgroup 1")) {
			imageView.setImageResource(R.drawable.appbarnetwork);
		} else if (s.equals("Workgroup 2")) {
			imageView.setImageResource(R.drawable.appbarnetwork);
		} else if (s.equals("Workgroup 3")) {
			imageView.setImageResource(R.drawable.appbarnetwork);
		} else if (s.equals("Workgroup 4")) {
			imageView.setImageResource(R.drawable.appbarnetwork);
		} else if (s.equals("Workgroup 5")) {
			imageView.setImageResource(R.drawable.appbarnetwork);
		} else if (s.equals("Workgroup 6")) {
			imageView.setImageResource(R.drawable.appbarnetwork);
		} else {
			imageView.setImageResource(R.drawable.appbarnetwork);
		}
	
 
		return rowView;
	}

}

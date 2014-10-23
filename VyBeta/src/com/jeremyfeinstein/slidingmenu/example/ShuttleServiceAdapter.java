package com.jeremyfeinstein.slidingmenu.example;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Adapter to bind a ToDoItem List to a view
 */
public class ShuttleServiceAdapter extends ArrayAdapter<ShuttleServices> {

	Context mContext;
	int mLayoutResourceId;
	private String[] vehicles;
	private String[] driver;
	
    public ImageLoader imageLoader;

	public ShuttleServiceAdapter(Context context, int layoutResourceId, String[] Vehicle,String[] Driver) {
		super(context, layoutResourceId);

		vehicles=Vehicle;
		driver=Driver;
		
	    imageLoader=new ImageLoader(context.getApplicationContext());
		mContext = context;
		mLayoutResourceId = layoutResourceId;
	}

	/**
	 * Returns the view for a specific item on the list
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
				View row = convertView;
		
				final ShuttleServices currentItem = getItem(position);
		
				if (row == null) {
				
					LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
					row = inflater.inflate(mLayoutResourceId, parent, false);
				}
		
				row.setTag(currentItem);

				TextView textView = (TextView) row.findViewById(R.id.label); 
				ImageView imageVehicle = (ImageView) row.findViewById(R.id.logo); 
				ImageView imageperson = (ImageView) row.findViewById(R.id.imageperson); 
				TextView txt_seat_number = (TextView) row.findViewById(R.id.txtrideseats); 
				TextView txt_trip_price = (TextView) row.findViewById(R.id.txt_trip_price); 
				TextView txtvehiclename = (TextView) row.findViewById(R.id.txtvehiclename); 
				
				textView.setText(currentItem.getDriverName()); 
				txt_trip_price.setText(currentItem.getTripPrice()); 
				txtvehiclename.setText(currentItem.getVehicleName()); 
				txt_seat_number.setText(currentItem.getSeatNumber());
				
		        imageLoader.DisplayImage(vehicles[position], imageVehicle);
		        imageLoader.DisplayImage(driver[position], imageperson);


		return row;
	}

}

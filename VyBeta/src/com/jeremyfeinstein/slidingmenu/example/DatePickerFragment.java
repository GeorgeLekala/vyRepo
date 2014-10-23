package com.jeremyfeinstein.slidingmenu.example;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
	
	public static int year, month, day;
	public EditText dateofpick;
	
	public DatePickerFragment(int year, int month, int day,EditText dateofpick)
	{
		DatePickerFragment.year = year;
		DatePickerFragment.month = month;
		DatePickerFragment.day = day;
		this.dateofpick = dateofpick;
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	// Use the current date as the default date in the picker
	final Calendar c = Calendar.getInstance();
	int year = c.get(Calendar.YEAR);
	int month = c.get(Calendar.MONTH);
	int day = c.get(Calendar.DAY_OF_MONTH);
	
	// Create a new instance of DatePickerDialog and return it
	return new DatePickerDialog(getActivity(), this, year, month, day);
	}
	
	public void onDateSet(DatePicker view, int year, int month, int day) {
	// Do something with the date chosen by the user
		this.year = year;
		this.month = month;
		this.day = day;
		dateofpick.setText(day+"/"+month+"/"+year);
		
	}
}

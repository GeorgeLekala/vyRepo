//package com.jeremyfeinstein.slidingmenu.example;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//
//import com.google.android.gms.maps.MapView;
//import com.google.android.maps.MapController;
//
//public class ShowMyLocation extends Activity {
//	private MapView mv;
//	private Button btnShowMyLocation;
//	private MapController mc;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		setContentView(R.layout.show_my_location);
//
//		findView();
//		setView(savedInstanceState);
//		setListener();
//		doExtra();
//	}
//
//	private void findView() {
//		mv = (MapView) findViewById(R.id.map);
//		btnShowMyLocation = (Button) findViewById(R.id.button_show_my_location);
//	}
//
//	private void setView(Bundle savedInstanceState) {
//		mv.onCreate(savedInstanceState);
//		mc = mv.get; //new MapController(mv.getMap());
//	}
//
//	private void setListener() {
//		btnShowMyLocation.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				mc.moveToMyLocation();
//			}
//		});
//	}
//
//	private void doExtra() {
//		mc.moveToMyLocation();
//	}
//
//	@Override
//	protected void onResume() {
//		super.onResume();
//
//		mv.onResume();
//	}
//
//	@Override
//	protected void onPause() {
//		mv.onPause();
//
//		super.onPause();
//	}
//
//	@Override
//	protected void onDestroy() {
//		mv.onDestroy();
//
//		super.onDestroy();
//	}
//
//	@Override
//	public void onLowMemory() {
//		super.onLowMemory();
//
//		mv.onLowMemory();
//	}
//
//	@Override
//	public void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
//
//		mv.onSaveInstanceState(outState);
//	}
//}
package com.jeremyfeinstein.slidingmenu.example;

public class Registration {
	@com.google.gson.annotations.SerializedName("id")
	private int mId;
	@com.google.gson.annotations.SerializedName("registrationId")
	private String mRegistrationId;

	public int getId() { return mId; }
	public final void setId(int id) { mId = id; }
	
	public String getRegistrationId() { return mRegistrationId; }
	public final void setRegistrationId(String registrationId) { mRegistrationId = registrationId; }
}

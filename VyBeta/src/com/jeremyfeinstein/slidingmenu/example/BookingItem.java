package com.jeremyfeinstein.slidingmenu.example;

public class BookingItem {
	
	@com.google.gson.annotations.SerializedName("id")
	private String mId;
	
	@com.google.gson.annotations.SerializedName("pickuplocation")
	private String pickuplocation;  
	
	@com.google.gson.annotations.SerializedName("droplocation")
	private String droplocation;  
	
	@com.google.gson.annotations.SerializedName("npassengers")
	private String npassengers;  
	
	@com.google.gson.annotations.SerializedName("Day")
	private String dd;
	
	@com.google.gson.annotations.SerializedName("Month")
	private String mm;
	
	@com.google.gson.annotations.SerializedName("Year")
	private String yyyy;
	
	@com.google.gson.annotations.SerializedName("username")
	private String username;

	/**
	 * @return the mId
	 */
	public String getmId() {
		return mId;
	}

	/**
	 * @param mId the mId to set
	 */
	public void setmId(String mId) {
		this.mId = mId;
	}

	/**
	 * @return the pickuplocation
	 */
	public String getPickuplocation() {
		return pickuplocation;
	}

	/**
	 * @param pickuplocation the pickuplocation to set
	 */
	public void setPickuplocation(String pickuplocation) {
		this.pickuplocation = pickuplocation;
	}

	/**
	 * @return the droplocation
	 */
	public String getDroplocation() {
		return droplocation;
	}

	/**
	 * @param droplocation the droplocation to set
	 */
	public void setDroplocation(String droplocation) {
		this.droplocation = droplocation;
	}

	/**
	 * @return the npassengers
	 */
	public String getNpassengers() {
		return npassengers;
	}

	/**
	 * @param npassengers the npassengers to set
	 */
	public void setNpassengers(String npassengers) {
		this.npassengers = npassengers;
	}

	/**
	 * @return the dd
	 */
	public String getDd() {
		return dd;
	}

	/**
	 * @param dd the dd to set
	 */
	public void setDd(String dd) {
		this.dd = dd;
	}

	/**
	 * @return the mm
	 */
	public String getMm() {
		return mm;
	}

	/**
	 * @param mm the mm to set
	 */
	public void setMm(String mm) {
		this.mm = mm;
	}

	/**
	 * @return the yyyy
	 */
	public String getYyyy() {
		return yyyy;
	}

	/**
	 * @param yyyy the yyyy to set
	 */
	public void setYyyy(String yyyy) {
		this.yyyy = yyyy;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

}

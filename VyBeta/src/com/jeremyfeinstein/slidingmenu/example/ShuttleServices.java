package com.jeremyfeinstein.slidingmenu.example;

public class ShuttleServices {
	
	@com.google.gson.annotations.SerializedName("id")
	private String mId;

	
	@com.google.gson.annotations.SerializedName("driverName")
	private String driverName; 
	
	@com.google.gson.annotations.SerializedName("seatNumber")
	private String seatNumber; 
	
	@com.google.gson.annotations.SerializedName("vehicleName")
	private String vehicleName;  
	
	@com.google.gson.annotations.SerializedName("companyName")
	private String companyName;  
	
	@com.google.gson.annotations.SerializedName("rating")
	private String rating;  
	
	@com.google.gson.annotations.SerializedName("tripPrice")
	private String tripPrice;
	
	@com.google.gson.annotations.SerializedName("driverImage")
	private String driverImage;
	
	@com.google.gson.annotations.SerializedName("vehicleImage")
	private String vehicleimage;

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getTripPrice() {
		return tripPrice;
	}

	public void setTripPrice(String tripPrice) {
		this.tripPrice = tripPrice;
	}

	public String getDriverImage() {
		return driverImage;
	}

	public void setDriverImage(String driverImage) {
		this.driverImage = driverImage;
	}

	public String getVehicleimage() {
		return vehicleimage;
	}

	public void setVehicleimage(String vehicleimage) {
		this.vehicleimage = vehicleimage;
	}

	public String getmId() {
		return mId;
	}

	public void setmId(String mId) {
		this.mId = mId;
	}

}

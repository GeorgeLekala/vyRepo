package com.jeremyfeinstein.slidingmenu.example;

public class CommandItem {
	
	@com.google.gson.annotations.SerializedName("id")
	private String mId;
	
	@com.google.gson.annotations.SerializedName("firstname")
	private String mfirstname;  
	
	@com.google.gson.annotations.SerializedName("lastname")
	private String mlastname;  
	
	@com.google.gson.annotations.SerializedName("username")
	private String mUsername;  
	
	@com.google.gson.annotations.SerializedName("password")
	private String mPassword;
	
	@com.google.gson.annotations.SerializedName("command")
	private String mCommand;
	
	@com.google.gson.annotations.SerializedName("workgoup")
	private String mWorkgoup;
	
	@com.google.gson.annotations.SerializedName("workgoup_state")
	private String mWorkgoup_state;
	
	@com.google.gson.annotations.SerializedName("auth_level")
	private String mauth_level;
	
	@com.google.gson.annotations.SerializedName("computername")
	private String computername;	
	
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
	 * @return the mUsername
	 */
	public String getmUsername() {
		return mUsername;
	}

	/**
	 * @param mUsername the mUsername to set
	 */
	public void setmUsername(String mUsername) {
		this.mUsername = mUsername;
	}

	/**
	 * @return the mCommand
	 */
	public String getmCommand() {
		return mCommand;
	}

	/**
	 * @param mCommand the mCommand to set
	 */
	public void setmCommand(String mCommand) {
		this.mCommand = mCommand;
	}

	/**
	 * @return the mPassword
	 */
	public String getmPassword() {
		return mPassword;
	}

	/**
	 * @param mPassword the mPassword to set
	 */
	public void setmPassword(String mPassword) {
		this.mPassword = mPassword;
	}

	/**
	 * @return the mWorkgoup
	 */
	public String getmWorkgoup() {
		return mWorkgoup;
	}

	/**
	 * @param mWorkgoup the mWorkgoup to set
	 */
	public void setmWorkgoup(String mWorkgoup) {
		this.mWorkgoup = mWorkgoup;
	}

	/**
	 * @return the mWorkgoup_state
	 */
	public String getmWorkgoup_state() {
		return mWorkgoup_state;
	}

	/**
	 * @param mWorkgoup_state the mWorkgoup_state to set
	 */
	public void setmWorkgoup_state(String mWorkgoup_state) {
		this.mWorkgoup_state = mWorkgoup_state;
	}

	/**
	 * @return the mfirstname
	 */
	public String getMfirstname() {
		return mfirstname;
	}

	/**
	 * @param mfirstname the mfirstname to set
	 */
	public void setMfirstname(String mfirstname) {
		this.mfirstname = mfirstname;
	}

	/**
	 * @return the mlastname
	 */
	public String getMlastname() {
		return mlastname;
	}

	/**
	 * @param mlastname the mlastname to set
	 */
	public void setMlastname(String mlastname) {
		this.mlastname = mlastname;
	}

	/**
	 * @return the mauth_level
	 */
	public String getMauth_level() {
		return mauth_level;
	}

	/**
	 * @param mauth_level the mauth_level to set
	 */
	public void setMauth_level(String mauth_level) {
		this.mauth_level = mauth_level;
	}

	/**
	 * @return the computername
	 */
	public String getComputername() {
		return computername;
	}

	/**
	 * @param computername the computername to set
	 */
	public void setComputername(String computername) {
		this.computername = computername;
	}

}

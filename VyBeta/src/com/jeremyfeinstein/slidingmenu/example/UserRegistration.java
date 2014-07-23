package com.jeremyfeinstein.slidingmenu.example;

public class UserRegistration {
	
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
	
	@com.google.gson.annotations.SerializedName("phonenumber")
	private String mphonenumber;
	
	@com.google.gson.annotations.SerializedName("creditcardnumber")
	private String creditcardnumber;  
	
	@com.google.gson.annotations.SerializedName("CardMM")
	private String cmm;
	
	@com.google.gson.annotations.SerializedName("CardYY")
	private String cyy;
	
	@com.google.gson.annotations.SerializedName("CVV")
	private String cvv;

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
	 * @return the mphonenumber
	 */
	public String getMphonenumber() {
		return mphonenumber;
	}

	/**
	 * @param mphonenumber the mphonenumber to set
	 */
	public void setMphonenumber(String mphonenumber) {
		this.mphonenumber = mphonenumber;
	}

	/**
	 * @return the creditcardnumber
	 */
	public String getCreditcardnumber() {
		return creditcardnumber;
	}

	/**
	 * @param creditcardnumber the creditcardnumber to set
	 */
	public void setCreditcardnumber(String creditcardnumber) {
		this.creditcardnumber = creditcardnumber;
	}

	/**
	 * @return the cmm
	 */
	public String getCmm() {
		return cmm;
	}

	/**
	 * @param cmm the cmm to set
	 */
	public void setCmm(String cmm) {
		this.cmm = cmm;
	}

	/**
	 * @return the cyy
	 */
	public String getCyy() {
		return cyy;
	}

	/**
	 * @param cyy the cyy to set
	 */
	public void setCyy(String cyy) {
		this.cyy = cyy;
	}

	/**
	 * @return the cvv
	 */
	public String getCvv() {
		return cvv;
	}

	/**
	 * @param cvv the cvv to set
	 */
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	
	

}

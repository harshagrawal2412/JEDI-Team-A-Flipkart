package com.flipkart.bean;

import java.util.List;

public class GymOwner {
	private String ownerId; // Unique identifier for the gym owner
	private String ownerEmail; // Email address of the gym owner
	private String password; // Password for the gym owner's account
	private String phoneNo; // Phone number of the gym owner
	private String nationalId; // National ID of the gym owner
	private String GST; // GST (Goods and Services Tax) number of the gym owner
	private List<Gym> gyms; // List of gyms owned by the gym owner
	private String PAN; // PAN (Permanent Account Number) of the gym owner
	private String ownerName; // Name of the gym owner
	private String status; // Current status of the gym owner
	private String verificationStatus; // Verification status of the gym owner

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public String getPassword() {
		return password;
	}

	
	public void setPassword(String password) {
		this.password = password;
	}

	
	public String getPhoneNo() {
		return phoneNo;
	}

	
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	
	public String getNationalId() {
		return nationalId;
	}

	/**
	 * Set the national ID of the gym owner.
	 * @param nationalId - The gym owner's national ID
	 * @Author Sugam, Harsh, Ali , Srashti , Dipti
	 */
	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	/**
	 * Get the GST (Goods and Services Tax) number of the gym owner.
	 * @return The gym owner's GST number
	 * @Author Sugam, Harsh, Ali , Srashti , Dipti
	 */
	public String getGST() {
		return GST;
	}

	/**
	 * Set the GST (Goods and Services Tax) number of the gym owner.
	 * @param GST - The gym owner's GST number
	 * @Author Sugam, Harsh, Ali , Srashti , Dipti
	 */
	public void setGST(String GST) {
		this.GST = GST;
	}

	/**
	 * Get the list of gyms owned by the gym owner.
	 * @return The list of gyms owned by the gym owner
	 * @Author Sugam, Harsh, Ali , Srashti , Dipti
	 */
	public List<Gym> getGyms() {
		return gyms;
	}

	/**
	 * Set the list of gyms owned by the gym owner.
	 * @param gyms - The list of gyms owned by the gym owner
	 * @Author Sugam, Harsh, Ali , Srashti , Dipti
	 */
	public void setGyms(List<Gym> gyms) {
		this.gyms = gyms;
	}

	/**
	 * Get the PAN (Permanent Account Number) of the gym owner.
	 * @return The gym owner's PAN number
	 * @Author Sugam, Harsh, Ali , Srashti , Dipti
	 */
	public String getPAN() {
		return PAN;
	}

	/**
	 * Set the PAN (Permanent Account Number) of the gym owner.
	 * @param PAN - The gym owner's PAN number
	 * @Author Sugam, Harsh, Ali , Srashti , Dipti
	 */
	public void setPAN(String PAN) {
		this.PAN = PAN;
	}

	/**
	 * Get the name of the gym owner.
	 * @return The gym owner's name
	 * @Author Sugam, Harsh, Ali , Srashti , Dipti
	 */
	public String getOwnerName() {
		return ownerName;
	}

	/**
	 * Set the name of the gym owner.
	 * @param ownerName - The gym owner's name
	 * @Author Sugam, Harsh, Ali , Srashti , Dipti
	 */
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	/**
	 * Get the current status of the gym owner.
	 * @return The gym owner's current status
	 * @Author Sugam, Harsh, Ali , Srashti , Dipti
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set the current status of the gym owner.
	 * @param status - The gym owner's current status
	 * @Author Sugam, Harsh, Ali , Srashti , Dipti
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Get the verification status of the gym owner.
	 * @return The gym owner's verification status
	 * @Author Sugam, Harsh, Ali , Srashti , Dipti
	 */
	public String getVerificationStatus() {
		return verificationStatus;
	}

	/**
	 * Set the verification status of the gym owner.
	 * @param verificationStatus - The gym owner's verification status
	 * @Author Sugam, Harsh, Ali , Srashti , Dipti
	 */
	public void setVerificationStatus(String verificationStatus) {
		this.verificationStatus = verificationStatus;
	}
}

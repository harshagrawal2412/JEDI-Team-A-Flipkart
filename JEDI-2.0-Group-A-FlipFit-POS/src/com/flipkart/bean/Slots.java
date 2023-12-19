package com.flipkart.bean;

/**
 * This class represents a time slot for gym bookings in the GymFlipFit application.
 * It stores information about the slot ID, start time, and available seat count.
 */
public class Slots {

	private String slotsId; // Unique identifier for the slot
	private int startTime; // Start time of the slot
	private int seatCount; // Number of available seats in the slot

	

	public Slots(String i, int startTime, int seatCount) {
		this.setSlotsId(i);
		this.setStartTime(startTime);
		this.setSeatCount(seatCount);
	}

	public String getSlotsId() {
		return slotsId;
	}


	public void setSlotsId(String slotsId) {
		this.slotsId = slotsId;
	}


	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getSeatCount() {
		return seatCount;
	}
	
	public void setSeatCount(int seatCount) {
		this.seatCount = seatCount;
	}
}

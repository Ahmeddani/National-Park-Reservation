package com.techelevator.campground.model;

public class Campsite {

	private int campsiteId;
	private int campgroundId;
	private int campsiteNumber;
	private int maximumCampsiteOccupancy;
	private boolean isWheelchairAccessible;
	private int maxRVLength;
	private boolean hasUtilityHookup;

	public int getCampsiteId() {
		return campsiteId;
	}

	public void setCampsiteId(int campsiteId) {
		this.campsiteId = campsiteId;
	}

	public int getCampgroundId() {
		return campgroundId;
	}

	public void setCampgroundId(int campgroundId) {
		this.campgroundId = campgroundId;
	}

	public int getCampsiteNumber() {
		return campsiteNumber;
	}

	public void setCampsiteNumber(int campsiteNumber) {
		this.campsiteNumber = campsiteNumber;
	}

	public int getMaximumCampsiteOccupancy() {
		return maximumCampsiteOccupancy;
	}

	public void setMaximumCampsiteOccupancy(int maximumCampsiteOccupancy) {
		this.maximumCampsiteOccupancy = maximumCampsiteOccupancy;
	}

	public boolean isWheelchairAccessible() {
		return isWheelchairAccessible;
	}

	public void setWheelchairAccessible(boolean isWheelchairAccessible) {
		this.isWheelchairAccessible = isWheelchairAccessible;
	}

	public int getMaxRVLength() {
		return maxRVLength;
	}

	public void setMaxRVLength(int maxRVLength) {
		this.maxRVLength = maxRVLength;
	}

	public boolean hasUtilityHookup() {
		return hasUtilityHookup;
	}

	public void setHasUtilityHookup(boolean hasUtilityHookup) {
		this.hasUtilityHookup = hasUtilityHookup;
	}

}

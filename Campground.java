package com.techelevator.campground.model;

import java.math.BigDecimal;



//pojo classes, setting up instance variables and getters/setters for the class, representing the table

public class Campground {

	private int campgroundId;
	private int parkId;
	private String name;
	private int monthCampgroundOpens;
	private int monthCampgroundCloses;
	private BigDecimal dailyFee;

	public int getCampgroundId() {
		return campgroundId;
	}

	public void setCampgroundId(int campgroundId) {
		this.campgroundId = campgroundId;
	}

	public int getParkId() {
		return parkId;
	}

	public void setParkId(int parkId) {
		this.parkId = parkId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMonthCampgroundOpens() {
		return monthCampgroundOpens;
	}

	public void setMonthCampgroundOpens(int monthCampgroundOpens) {
		this.monthCampgroundOpens = monthCampgroundOpens;
	}

	public int getMonthCampgroundCloses() {
		return monthCampgroundCloses;
	}

	public void setMonthCampgroundCloses(int monthCampgroundCloses) {
		this.monthCampgroundCloses = monthCampgroundCloses;
	}

	public BigDecimal getDailyFee() {
		return dailyFee;
	}

	public void setDailyFee(BigDecimal dailyFee) {
		this.dailyFee = dailyFee.setScale(2);
	}

}

package com.techelevator.campground.model;

import java.time.LocalDate;

public class Reservation {

	private int reservationId;
	private int campsiteId;
	private String name;
	private LocalDate beginningReservationDate;
	private LocalDate endingReservationDate;
	private LocalDate dateReservationCreated;

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public int getCampsiteId() {
		return campsiteId;
	}

	public void setCampsiteId(int campsiteId) {
		this.campsiteId = campsiteId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBeginningReservationDate() {
		return beginningReservationDate;
	}

	public void setBeginningReservationDate(LocalDate beginningReservationDate) {
		this.beginningReservationDate = beginningReservationDate;
	}

	public LocalDate getEndingReservationDate() {
		return endingReservationDate;
	}

	public void setEndingReservationDate(LocalDate endingReservationDate) {
		this.endingReservationDate = endingReservationDate;
	}

	public LocalDate getDateReservationCreated() {
		return dateReservationCreated;
	}

	public void setDateReservationCreated(LocalDate dateReservationCreated) {
		this.dateReservationCreated = dateReservationCreated;
	}

}

package com.techelevator.campground.model;

import java.time.LocalDate;
import java.util.List;

public interface ReservationDAO {

	public int makeReservation(int siteId, LocalDate arrive, LocalDate depart, String name);

	public List<Reservation> getAllReservations();

}

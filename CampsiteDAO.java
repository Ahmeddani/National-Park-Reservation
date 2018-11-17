package com.techelevator.campground.model;

import java.time.LocalDate;
import java.util.List;

public interface CampsiteDAO {

	public List<Campsite> getAvailableSites(int campground_id, LocalDate arrivalDate, LocalDate departureDate);
}

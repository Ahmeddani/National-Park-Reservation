package com.techelevator.campground.model;

import java.util.List;

//this is the contract for JDBC campground DAO
//these methods must be implemented in jdbc campground dao

public interface CampgroundDAO {

	public List<Campground> getAllCampgrounds(int parkId);

	public boolean checkCampgroundAvailability(int campgroundId, String arrival, String departure);

}

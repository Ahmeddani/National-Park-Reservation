package com.techelevator.campground.model.jdbc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.CampgroundDAO;

public class JDBCCampgroundDAO implements CampgroundDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCCampgroundDAO(DataSource dataSource) {

		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	
	//implementation of CampgroundDAO method
	//String sql is the query that we sent to postgres
	//SqlRowSet is class that accepts the result of the query and stores it.
	//result is a bunch of rows, we don't know how many. if it has next line, do this:
	// pulling data from campground table, campground class must match table in postgres
	//converts from postgres table and matches to POJO class
	//must create an instance of campground
	//returning a list at the end. list is a collection of compground objects
	@Override
	public List<Campground> getAllCampgrounds(int parkId) {
		List<Campground> campgrounds = new ArrayList<Campground>();
		String sql = "select * from campground where park_id = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, parkId);

		while (result.next()) {

			Campground campground = mapRowToCampground(result);
			campgrounds.add(campground);
			;
		}
		return campgrounds;
	}

	//mapping to get the dailyfee
	//we want to separate because we do not want to return other values which will be null
	
	public BigDecimal getDailyFee(int i) {

		String sql = "SELECT daily_fee FROM campground WHERE campground_id = ? ";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, i);

		Campground campground = null;
		while (result.next()) {
			campground = mapRowToDailyFee(result);

		}

		BigDecimal dailyFee = campground.getDailyFee();

		return dailyFee;

	}

	//getting when campground is open on an annual basis from table
	//storing the month value as integers in campgroundopenmonth and closemonth
	//we are comparing this information based on user input, which is the arrival/departure date
	//we substring to get the month value and disregard other values, then we parse to integer
	//converting user input of arrival and departure into Localdate. we use LocalDate in java, cannot use string.
	//DateTimeformatter sets up how java reads the date
	//checks true or false if park is open during arrival and departure months only, does not care about other reservations
	
	@Override
	
	public boolean checkCampgroundAvailability(int campgroundId, String arrival, String departure) {

		String sql = "SELECT open_from_mm, open_to_mm FROM campground WHERE campground_id = ? ";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, campgroundId);
		List<Campground> campgroundList = new ArrayList<Campground>();

		while (result.next()) {

			Campground jdbcCampgroundDAO = getMonths(result);
			campgroundList.add(jdbcCampgroundDAO);

		}

		int campgroundOpenMonth = campgroundList.get(0).getMonthCampgroundOpens();
		int campgroundCloseMonth = campgroundList.get(0).getMonthCampgroundCloses();

		int arrivalMonth = (int) Integer.parseInt(arrival.substring(5, 7));
		int departureMonth = (int) Integer.parseInt(arrival.substring(5, 7));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate arrivalDate = LocalDate.parse(arrival, formatter);
		LocalDate departureDate = LocalDate.parse(departure, formatter);

		if (arrivalDate.isAfter(LocalDate.now()) && arrivalDate.isBefore(departureDate)
				&& arrivalMonth >= campgroundOpenMonth && departureMonth <= campgroundCloseMonth) {
			return true;
		}

		return false;
	}

	//determines which columns we're returning from postgres, mapping. copies from postgres to campground object, returns object
	//one row from the table is one instance/one object
	private Campground mapRowToCampground(SqlRowSet result) {
		Campground campground = new Campground();
		campground.setCampgroundId(result.getInt("campground_id"));
		campground.setParkId(result.getInt("park_id"));
		campground.setName(result.getString("name"));
		campground.setMonthCampgroundOpens(result.getInt("open_from_mm"));
		campground.setMonthCampgroundCloses(result.getInt("open_to_mm"));
		campground.setDailyFee(result.getBigDecimal("daily_fee"));
		return campground;
	}

	private Campground getMonths(SqlRowSet result) {
		Campground campground = new Campground();
		campground.setMonthCampgroundOpens(result.getInt("open_from_mm"));
		campground.setMonthCampgroundCloses(result.getInt("open_to_mm"));
		return campground;
	}

	private Campground mapRowToDailyFee(SqlRowSet result) {

		Campground campground = new Campground();
		campground.setDailyFee(result.getBigDecimal("daily_fee"));

		return campground;

	}


}
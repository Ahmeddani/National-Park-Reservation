package com.techelevator.campground.model.jdbc;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Campsite;
import com.techelevator.campground.model.CampsiteDAO;

public class JDBCCampsiteDAO implements CampsiteDAO {

	JdbcTemplate jdbcTemplate;

	public JDBCCampsiteDAO(DataSource datasource) {

		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	
	//join query to check
	//executed AFTER previous campground check returns true
	@Override

	public List<Campsite> getAvailableSites(int campground_id, LocalDate arrivalDate, LocalDate departureDate) {

		Date date1 = Date.valueOf(arrivalDate);
		Date date2 = Date.valueOf(departureDate);

		String sql = "SELECT distinct * FROM site JOIN campground ON site.campground_id = campground.campground_id WHERE site.campground_id = ? "
				+ "AND site_id NOT IN (SELECT site.site_id FROM site JOIN reservation ON reservation.site_id = site.site_id "
				+ "WHERE (? >= reservation.from_date AND ? <= reservation.to_date) OR (? >= reservation.from_date AND ? <= reservation.to_date)) "
				+ "order by daily_fee LIMIT 5";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, campground_id, arrivalDate, date1, date2, departureDate);

		List<Campsite> campsiteList = new ArrayList<Campsite>();
		while (result.next()) {
			Campsite campsite = mapRowToCampsite(result);
			campsiteList.add(campsite);
		}
		return campsiteList;
	}

	private Campsite mapRowToCampsite(SqlRowSet result) {
		Campsite campsite = new Campsite();

		campsite.setCampsiteNumber(result.getInt("site_number"));
		campsite.setMaximumCampsiteOccupancy(result.getInt("max_occupancy"));
		campsite.setWheelchairAccessible(result.getBoolean("accessible"));
		campsite.setMaxRVLength(result.getInt("max_rv_length"));
		campsite.setHasUtilityHookup(result.getBoolean("utilities"));

		return campsite;
	}

}

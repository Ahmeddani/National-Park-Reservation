package com.techelevator.campground.model.jdbc;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Reservation;
import com.techelevator.campground.model.ReservationDAO;

public class JDBCReservationDAO implements ReservationDAO{

	JdbcTemplate jdbcTemplate;
	
	public JDBCReservationDAO(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	//getting all reservations in the table
	@Override
	public List<Reservation> getAllReservations() {
		List<Reservation> reservationList = new ArrayList<Reservation>();
		String sql = "select * from reservation";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		Reservation reservation = null;
		while (result.next()) {
			reservation  = mapRowToReservation(result);
			reservationList.add(reservation);
		}
		
		
		return reservationList;
	}
	
	//responsible to set a new reservation
	@Override
	public int makeReservation(int siteId, LocalDate arrive, LocalDate depart, String name) {
		
		Date arrivalDate = Date.valueOf(arrive);
		Date departureDate = Date.valueOf(depart);
		//checkReservationAbility();
		Calendar cal = Calendar.getInstance();
		cal.clear(Calendar.HOUR_OF_DAY);
		cal.clear(Calendar.AM_PM);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		String sql = "insert into reservation (site_id, name, from_date, to_date, create_date) values (?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, siteId, name, arrivalDate, departureDate, cal);
		int confirmationId = getConfirmationId();
		
		
		return confirmationId;
	}

	private int getConfirmationId() {
		String sql = "select reservation_id from reservation order by reservation_id desc limit 1";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		Reservation reservation = null;
		while (result.next()) {
			reservation  = new Reservation();
			reservation.setReservationId(result.getInt("reservation_id"));
		}
		int reservationId = reservation.getReservationId();
		
		return reservationId;
	}
	
	private Reservation mapRowToReservation(SqlRowSet result) {
		Reservation reservation = new Reservation();
		reservation.setReservationId(result.getInt("reservation_id"));
		reservation.setCampsiteId(result.getInt("site_id"));
		reservation.setName(result.getString("name"));
		reservation.setBeginningReservationDate(result.getDate("from_date").toLocalDate());
		reservation.setEndingReservationDate(result.getDate("to_date").toLocalDate());
		reservation.setDateReservationCreated(result.getDate("create_date").toLocalDate());
		
		return reservation;
	}

}
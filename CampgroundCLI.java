package com.techelevator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.time.temporal.ChronoUnit;
import javax.sql.DataSource;

import java.text.DateFormatSymbols;
import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.CampgroundDAO;
import com.techelevator.campground.model.Campsite;
import com.techelevator.campground.model.CampsiteDAO;
import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.ParkDAO;
import com.techelevator.campground.model.Reservation;
import com.techelevator.campground.model.ReservationDAO;
import com.techelevator.campground.model.jdbc.JDBCCampgroundDAO;
import com.techelevator.campground.model.jdbc.JDBCCampsiteDAO;
import com.techelevator.campground.model.jdbc.JDBCParkDAO;
import com.techelevator.campground.model.jdbc.JDBCReservationDAO;
import com.techelevator.campground.view.Menu;

public class CampgroundCLI {

	private Menu menu;
	private JDBCParkDAO jdbcParkDAO;
	private JDBCCampgroundDAO jdbcCampgroundDAO;
	private JDBCCampsiteDAO jdbcCampsiteDAO;
	private JDBCReservationDAO jdbcReservationDAO;

	
	
	//sets connection to database
	//creates object of this class, Campground CLI, and calls the run method
	
	
	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}
	
	//this is the constructor
	//initializes all the instances of other classes
	//the 4 jdbc classes and the menu. to call the methods that we've setup there

	public CampgroundCLI(DataSource datasource) {
		jdbcParkDAO = new JDBCParkDAO(datasource);
		jdbcCampgroundDAO = new JDBCCampgroundDAO(datasource);
		jdbcCampsiteDAO = new JDBCCampsiteDAO(datasource);
		jdbcReservationDAO = new JDBCReservationDAO(datasource);
		menu = new Menu(System.in, System.out);
	}

	private static final String MENU_OPTION_RETURN_TO_PREVIOUS_SCREEN = "Return to previous screen";

	private static final String CG_MENU_OPTION_ALL_CAMPGROUNDS = "View Campgrounds";
	private static final String CG_MENU_OPTION_SEARCH_FOR_RESERVE = "Search for Reservation";
	private static final String[] CAMP_MENU_OPTIONS = new String[] { CG_MENU_OPTION_ALL_CAMPGROUNDS,
			CG_MENU_OPTION_SEARCH_FOR_RESERVE, MENU_OPTION_RETURN_TO_PREVIOUS_SCREEN };

	private static final String MENU_OPTION_SEARCH_FOR_RESERVE = "Search for Available Reservation";
	private static final String[] RESERVE_MENU_OPTIONS = new String[] { MENU_OPTION_SEARCH_FOR_RESERVE,
			MENU_OPTION_RETURN_TO_PREVIOUS_SCREEN };

	private static final String CAMPGROUND_CHOICE = "Which campground (enter 0 to cancel)?";
	private static final String ARRIVAL_DATE = "What is the arrival date? ";
	private static final String DEPARTURE_DATE = "What is the departure date? ";
	private static final String[] RESERVATION_OPTIONS = new String[] { CAMPGROUND_CHOICE, ARRIVAL_DATE,
			DEPARTURE_DATE };

	






	
	
	
	//control flow
	//two nested while loops
	//while program is running...
	//displays all parks before user enters anything, whenever program starts, it displays
	//whenever user quits or cancels, it goes to top of while loop, this is main control flow
	//2nd while loop maintains when user just wants to go back to previous screen, one screen only
	//while user does not press q and while loop (is like an electric switch) if things are going well, program will go
	//if loop becomes false, user is directly to the FIRST screen
	//choice 1 is park selection
	//using this selection, we display park info
	//now another choice, view all campgrounds or make other selection to go back.
	//if moves on, display all campgrounds in that park. we skip option 2 (bonus)
	//if he decides to reserve, we stay in while loop, handle reservation takes over.
	//if reservaation is success, it ends. if not success, directed back to first position, screen 1.
	
	//we call handle to call private methods to handle specific tasks outside of run
	//for every specific task we have a handle

	private void run() {

		boolean isRunning = true;

		while (isRunning) {

			String choice1 = handleDisplayAllParks();

			boolean loop = true;
			while (!choice1.equals("Q") && !choice1.equals("q") && loop) {

				int parkId = (int) Integer.parseInt(choice1);

				String choice2 = handleDisplayParkInfo((parkId));

				if (choice2.equals(CG_MENU_OPTION_ALL_CAMPGROUNDS)) {

					String choice3 = handleDisplayAllCampgrounds(parkId);
					if (choice3.equals(MENU_OPTION_SEARCH_FOR_RESERVE)) {
						handleReservation(parkId);
						loop = false;
					
					} else if (choice3.equals(MENU_OPTION_RETURN_TO_PREVIOUS_SCREEN)) {
						loop = false;
					}
				}

				else if (choice2.equals(MENU_OPTION_RETURN_TO_PREVIOUS_SCREEN)) {
					loop = false;
					break;
				}
			}
			if (choice1.equals("Q") || choice1.equals("q")) {
				menu.quit();
				isRunning = false;
			}
		}
	}

	
	//each of the handle private methods calls other methods from either jdbc or menu
	
	//handles all displaying of parks
	//creates parklist by invoking jdbcParkDAO, encapsulated
	
	private String handleDisplayAllParks() {
		List<Park> parkList = jdbcParkDAO.getAllParks();
		String parkId = (String) menu.displayAllParks(parkList);
		return parkId;
	}
	
	
	//gets park id from cli
	//here, just calls the park id, because we just want the chosen park to be displayed
	//calls the get choice from option method to get the user choice
	private String handleDisplayParkInfo(int parkId) {
		List<Park> park = jdbcParkDAO.getParkByParkId(parkId);
		menu.displayParkInfo(park);
		String choice2 = (String) menu.getChoiceFromOptions(CAMP_MENU_OPTIONS);

		return choice2;
	}

	private String handleDisplayAllCampgrounds(int choice) {

		List<Park> park = jdbcParkDAO.getParkByParkId(choice);
		String parkName = park.get(0).getName();

		List<Campground> campgroundList = jdbcCampgroundDAO.getAllCampgrounds(choice);
		System.out.println("");
		System.out.println("PARK CAMPGROUNDS");
		System.out.println("----------------");
		System.out.println(parkName + " National Park Campgrounds");
		menu.displayCampgrounds(campgroundList);
		String choice3 = (String) menu.getChoiceFromOptions(RESERVE_MENU_OPTIONS);
		return choice3;
	}

	private void handleReservation(int parkId) {
		List<Campground> campgroundList = jdbcCampgroundDAO.getAllCampgrounds(parkId);
		menu.displayCampgrounds(campgroundList);
		String arrivalDate = null;
		int campgroundChoice = 0;
		String departureDate = null;
		LocalDate arrival = null;
		LocalDate departure = null;
		List<Campsite> campsiteList;
		boolean campsiteAvailable = true;

		while (campsiteAvailable) {
			campgroundChoice = menu.getCampgroundChoice(parkId, campgroundList);
			if (campgroundChoice == 0) {
				break;
			}

			arrivalDate = menu.getArrivalDate();

			departureDate = menu.getDepartureDate();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			arrival = LocalDate.parse(arrivalDate, formatter);
			departure = LocalDate.parse(departureDate, formatter);

			boolean campgroundIsAvailable = jdbcCampgroundDAO.checkCampgroundAvailability(campgroundChoice, arrivalDate,
					departureDate);

			if (!campgroundIsAvailable) {
				menu.displayNoSitesAvailable();
				continue;
			}
			campsiteList = new ArrayList<Campsite>();
			campsiteList = jdbcCampsiteDAO.getAvailableSites(campgroundChoice, arrival, departure);

			if (campsiteList.size() == 0) {

				String choice = menu.displayNoSitesAvailable();
				if (choice.equals("Y") || choice.equals("y")) {
					continue;
				}

				if (choice.equals("N") || choice.equals("n")) {
					menu.quit();
				}
			}

			long daysBetween = ChronoUnit.DAYS.between(arrival, departure);
			BigDecimal dailyFee = jdbcCampgroundDAO.getDailyFee(campgroundChoice);
			menu.displayAvailableSites(campsiteList, daysBetween, dailyFee);

			int choice = menu.getSite(campsiteList);


			String name = menu.getName();
			int reservationId = jdbcReservationDAO.makeReservation(choice, arrival, departure, name);
			menu.displayConfirmationId(reservationId);
			menu.quit();
			break;
		}

	}

}

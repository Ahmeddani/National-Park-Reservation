package com.techelevator.campground.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.Month;
import java.util.List;
import java.util.Scanner;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.Campsite;
import com.techelevator.campground.model.Park;

public class Menu {

	// initializes PrintWriter and Scanner objects
	private PrintWriter out;
	private Scanner in;

	// constructor to initialize input/output streams
	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}

	// gets menu options, displays it, and gets the user input choice
	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}

	//puts size of parkList in int var. loops and prints each park id and name, then hardcode prints quit
	//choice string stores user input
	//in while loop in try/catch, if choice is q or one of the ids, validinput is true. else print invalid selection. repeat in while
	//loop to give user multiple tries until it's 
	
	//displayallparks gets the list of parks from handleallparks in the cli, and displays them here
	//takes input from user, the choice, 
	public String displayAllParks(List<Park> parkList) {

		int parkListSize = parkList.size();
		System.out.println("VIEW PARKS INTERFACE");
		System.out.println("--------------------");
		System.out.println("Select a Park for Further Details");
		for (Park park : parkList) {
			System.out.println("   " + park.getParkId() + ") " + park.getName());
		}
		System.out.println("   Q) quit");

		String choice = null;
		boolean validInput = false;

		while (!validInput) {
			try {
				choice = in.nextLine();

				if ((choice.equals("Q") || choice.equals("q"))) {
					validInput = true;
					break;
				} else if (((int) Integer.parseInt(choice) >= 1 && (int) Integer.parseInt(choice) <= parkListSize)) {

					validInput = true;

				}
			} catch (Exception e) {
				System.out.println(
						"Invalid selection. Please enter the number of one of the parks listed or enter Q to quit: ");
			}
		}
		return choice;
	}

	public void quit() {
		System.out.println("Thank you for visiting. Goodbye.");
		System.exit(0);
	}

	public void displayParkInfo(List<Park> park) {
		System.out.println("Park Information Screen");
		System.out.println("-----------------------");

		//always loops once
		//when finished, always returns back to place where it started in the cli
		for (Park parks : park) {
			System.out.println(parks.getName() + " National Park");
			System.out.println("Location: " + String.format("%16s", parks.getLocation()));
			System.out.println("Established: " + String.format("%18s", parks.getEstablishedDate()));
			System.out.println("Area: " + String.format("%20s", parks.getArea()));
			System.out.println("Annual Visitors: " + String.format("%11s", parks.getVisitors()));
			System.out.println("");
			int charCount = 0;
			for (String word : parks.getDescription().split(" ")) {
				System.out.print(word + " ");
				charCount += word.length();
				if (charCount >= 60) {
					System.out.println();
					charCount = 0;
				}

			}
			System.out.println("\n");
		}
	}

	public void displayCampgrounds(List<Campground> campgrounds) {
		System.out.println(" ");
		System.out.println("SEARCH FOR CAMPGROUND RESERVATION");
		System.out.println("---------------------------------");

		System.out.println("Id  Name                       Open       Close        DailyFee");
		int i = 1;
		for (Campground camp : campgrounds) {
			System.out.print("#" + i + "  ");
			System.out.print(String.format("%-27s", camp.getName()));
			System.out.print(String.format("%-11s", Month.of(camp.getMonthCampgroundOpens()).name()));
			System.out.print(String.format("%-14s", Month.of(camp.getMonthCampgroundCloses()).name()));
			System.out.println(String.format("%-25s", "$" + camp.getDailyFee()));
			i++;
		}
		System.out.println("");
	}


	public void displayAvailableSites(List<Campsite> campsiteList, Long daysBetween, BigDecimal dailyFee) {
		System.out.println();
		System.out.println("");
		System.out.println("Results Matching Your Search Criteria");
		System.out.println("Site No.   Max Occup. Accessible?   Max RV Length    Utility      Cost");

		for (Campsite site : campsiteList) {
			System.out.print(String.format("%-15s", site.getCampsiteNumber()));
			System.out.print(String.format("%-10s", site.getMaximumCampsiteOccupancy()));
			System.out.print(String.format("%-15s", site.isWheelchairAccessible() ? "Yes" : "No"));
			if (site.getMaxRVLength() != 0) {

				System.out.print(String.format("%-15s", site.getMaxRVLength()));
			}

			if (site.getMaxRVLength() == 0) {
				System.out.print(String.format("%-15s", "N/A"));
			}

			System.out.print(String.format("%-10s", site.hasUtilityHookup() ? "Yes" : "N/A"));

			System.out.println(String.format("%-15s", "$" + (daysBetween * dailyFee.longValue()) + ".00"));
		}

		System.out.println("");

	}

	public int getCampgroundChoice(int parkId, List<Campground> campgrounds) {
		int campgroundChoice = 0;
		boolean isValid = false;

		while (!isValid) {
			System.out.print("Which campground (enter 0 to cancel)? ");

			try {
				campgroundChoice = in.nextInt();
				in.nextLine();
				if ((campgroundChoice >= 1 && campgroundChoice <= campgrounds.size())) {

					campgroundChoice = campgrounds.get(campgroundChoice - 1).getCampgroundId();
					isValid = true;

				}
				else if (campgroundChoice == 0) {
					isValid = true;
					

				}
				
				else if(!(campgroundChoice >= 1 && campgroundChoice <= campgrounds.size())) {
					System.out.println("Invalid command. Please enter valid selection: ");
				}

			
			} catch (Exception e) {

				System.out.println("Invalid command. Please enter valid selection: ");
				in.nextLine();
			}
		}

		return campgroundChoice;
	}

	public int getSite(List<Campsite> campsiteList) {

		int choice;
		while (true) {

			System.out.print("Which site should be reserved (enter 0 to cancel)? ");

			try {
				choice = in.nextInt();
				in.nextLine();
				for (Campsite campsite : campsiteList) {
					if (choice == campsite.getCampsiteNumber() || choice == 0) {
						return choice;
					}

				}
				System.out.print("Invalid selection. Please enter valid site number or enter 0 to cancel: ");

			} catch (Exception e) {

			}
		}

	}

	private String getValidDate() {

		String date = null;
		boolean isValid = false;
		while (!isValid) {

			date = in.nextLine();

			if (date.length() == 10) {
				isValid = true;
			}

			else {
				System.out.println("Invalid date format. Please enter date in the format (yyyy-mm-dd) ");
			}

		}
		return date;

	}

	public String getArrivalDate() {

		System.out.print("What is the arrival date? (yyyy-mm-dd): ");
		String arrivalDate = getValidDate();
		return arrivalDate;

	}

	public String getDepartureDate() {
		System.out.print("What is the departure date? (yyyy-mm-dd): ");
		String departureDate = getValidDate();
		return departureDate;

	}

	public String displayNoSitesAvailable() {
		System.out.println("");
		System.out.print(
				"There are no campsites available at this campground for your selected date range. Would you like to enter an alternate date range? (Y/N) ");
		String choice = in.next();
		if (choice.equals("N") || (choice.equals("n"))) {
			quit();
		}
		return choice;
	}

	public String getName() {
		System.out.print("What name should the reservation be made under? ");
		String name = null;
		try {
			name = in.nextLine();
		} catch (Exception e) {

		}
		return name;
	}

	public void displayConfirmationId(int reservationId) {
		System.out.println(" ");
		System.out.println("The reservation has been made and the confirmation id is " + reservationId);
		System.out.println("------------------------------------------------------------");
		System.out.println(" ");
	}

	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			if (userInput.equals("Q")) {
				System.out.println("Thank you for visiting. Goodbye");
				System.exit(0);
			} else {
				int selectedOption = Integer.valueOf(userInput);
				if (selectedOption <= options.length) {
					choice = options[selectedOption - 1];
				}
			}
		} catch (NumberFormatException e) {

		}
		if (choice == null) {
			System.out.print("Invalid selection. Please try again. ");
			System.out.println("");

		}
		return choice;
	}

	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i].toString());
		}
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}
	
	

}
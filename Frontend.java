// --== CS400 File Header Information ==--
// Name: Akshat Bansal
// Email: akshat.bansal@wisc.edu
// Team: Red
// Group: KB
// TA: Keren Chen
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.List;
import java.util.Scanner;

/**
 * Frontend implementation of "The Golden Eagle Path". Gives users the ability
 * to plan a trip to a national park of their choice. Users will be given a the
 * shortest path from Madison, Wisconsin, to the national park selected and
 * stopping along the national parks along the way. Users can also save up to 3
 * routes for the future. If the user doesn't have a preference as to which
 * national park they want to go to, the program can offer them a random
 * suggestion, along with the shortest path to it.
 */

public class Frontend {
	private static Scanner scan = new Scanner(System.in);
	private static Backend backend;
	private static final String WELCOME_TEXT = "|* * * * * * * * * * OOOOOOOOOOOOOOOOOOOOOOOOO|\n"
			+ "| * * * * * * * * *  :::::::::::::::::::::::::|\n" + "|* * * * * * * * * * OOOOOOOOOOOOOOOOOOOOOOOOO|\n"
			+ "| * * * * * * * * *  :::::::::::::::::::::::::|\n" + "|* * * * * * * * * * OOOOOOOOOOOOOOOOOOOOOOOOO|\n"
			+ "| * * * * * * * * *  ::::::::::::::::::::;::::|\n" + "|* * * * * * * * * * OOOOOOOOOOOOOOOOOOOOOOOOO|\n"
			+ "|:::::::::::::::::::::::::::::::::::::::::::::|\n" + "|OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO|\n"
			+ "|:::::::::::::::::::::::::::::::::::::::::::::|\n" + "|OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO|\n"
			+ "|:::::::::::::::::::::::::::::::::::::::::::::|\n" + "|OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO|\n"
			+ "\n       WELCOME TO THE GOLDEN EAGLE PATH!       \n" + "\nUse this application to plan your next great "
			+ "\nAmerican adventure!" + "\nChoose a national park to visit and we will "
			+ "\nshow you the shortest way there, through the " + "\nother national parks along the way of course!"
			+ "\nCan't decide which park you want to visit? " + "\nWe're happy to give you a suggestion!"
			+ "\nWant to save some plans for the future?" + "\nYou can save up to 3 itineraries!\n"
			+ "\nEnjoy your park-hopping experience!";

	private static final String BASE_DISPLAY_MENU = "\n___________________________________________" + "\n"
			+ "\n            PLAN YOUR ADVENTURE" + "\n___________________________________________"
			+ "\nEnter the number of the option you wish to " + "\nchoose.\n"
			+ "\n1. I want to view a list of the Natl. Parks" + "\n2. Help! I can't decide where to go!"
			+ "\n3. I want to see my saved itineraries" + "\nx - I think I'm done";

	private final static String LIST_OPTION = "\n___________________________________________" + "\n"
			+ "\n              NATIONAL PARKS" + "\n___________________________________________" + "\n";

	private final static String RAND_OPTION = "\n___________________________________________" + "\n"
			+ "\n             RANDOM SUGGESTION" + "\n___________________________________________" + "\n";

	private final static String SAVED_PLANS = "\n___________________________________________" + "\n"
			+ "\n               SAVED PLANS" + "\n___________________________________________" + "\n";

	public void run(Backend back) {
		backend = back;
		String userInput = "";

		System.out.println(WELCOME_TEXT + BASE_DISPLAY_MENU);

		do {
			userInput = scan.nextLine().trim();

			switch (userInput) {
			case "1":
				printList();
				break;
			case "2":
				randomSuggestion();
				break;
			case "3":
				displaySavedPlans();
				break;
			}

			if (!userInput.equalsIgnoreCase("x")) {
				System.out.println(BASE_DISPLAY_MENU);
			}
			if (!userInput.equalsIgnoreCase("x") && !userInput.equalsIgnoreCase("1") && !userInput.equalsIgnoreCase("2")
					&& !userInput.equalsIgnoreCase("3")) {
				System.out.println("Unable to recognize the input. Please try again.");
			}
		} while (!userInput.equalsIgnoreCase("x"));

		scan.close();
	}

	private static void printList() {
		List<ParkInterface> list = backend.getAllParks();
		System.out.println(LIST_OPTION);
		int counter = 1;
		for (ParkInterface park : list) {
			if (!park.getName().equals("Madison (City)")) {
				System.out.println(
						counter + ". " + park.getName() + "     " + park.getStates().replaceAll("\"", "") + "\n");
				counter++;
			}
		}

		System.out.println(
				"\nWould you like to go to any of these parks?" + "\n___________________________________________"
						+ "\nJust enter the number of the park you wish " + "\nto visit." + "\nx - Main menu please!");
		String userInput = "";
		do {
			userInput = scan.nextLine().trim();
			// find shortest path of corresponding park
			if (!userInput.equalsIgnoreCase("x")) {
				try {
					int index = Integer.parseInt(userInput) - 1;
					if (index < 0 || index >= 50) {  //IM: changed <= 0 to < 0 and >50 to >=50
						throw new NumberFormatException();
					}
					List<ParkInterface> shortestPath = backend.getPathSequence(list.get(index).getName());
					for (ParkInterface park : shortestPath) {
						if (!park.getName().equals("Madison (City)")) {
							System.out.println(park.getName() + "     " + park.getStates().replaceAll("\"", "")
									+ "\n     " + park.getDescription().replaceAll("\"", "") + "\n");
						}
					}
					System.out.println("Total distance: " + backend.shortestPath(list.get(index).getName()) + " miles");

					System.out.println("\nWould you like to save this itinerary to your list?"
							+ "\n___________________________________________" + "\nY - Yes! This looks fun!"
							+ "\nx - I want to go back to the main menu.");
					do {
						userInput = scan.nextLine().trim().toLowerCase();

						switch (userInput) {
						case "y":
							// add to itinerary
							boolean success = backend.addToItinerary();
							if (success) {
								System.out
										.println("Itinerary successfully added! Enter x to go back to the main menu.");
								break;
							} else {
								System.out.println(
										"Your list is full! Itinerary NOT added. Enter x to go back to the main menu.");
								break;
							}
						}
						if (!userInput.equalsIgnoreCase("y") && !userInput.equalsIgnoreCase("x")) {
							System.out.println("Unable to recognize the input. Please try again.");
						}
					} while (!userInput.equalsIgnoreCase("x"));
				} catch (NumberFormatException nfe) {
					System.out.println("Unable to recognize the input. Please try again.");
				}
			}
		} while (!userInput.equalsIgnoreCase("x"));
	}

	private static void randomSuggestion() {
		List<ParkInterface> randPath = backend.getRandPath();
		System.out.println(RAND_OPTION + "Here's your itinerary:\n");
		for (ParkInterface park : randPath) {
			if (!park.getName().equals("Madison (City)")) {
				System.out.println(park.getName() + "     " + park.getStates().replaceAll("\"", "") + "\n     "
						+ park.getDescription().replaceAll("\"", "") + "\n");
			}
		}
		System.out.println(
				"Total distance: " + backend.shortestPath(randPath.get(randPath.size() - 1).getName()) + " miles");

		System.out.println("\nWould you like to save this itinerary to your list?"
				+ "\n___________________________________________" + "\nY - Yes! This looks fun!"
				+ "\nx - I want to go back to the main menu.");
		String userInput = "";
		do {
			userInput = scan.nextLine().trim().toLowerCase();

			switch (userInput) {
			case "y":
				// add to itinerary
				boolean success = backend.addToItinerary();
				if (success) {
					System.out.println("Itinerary successfully added! Enter x to go back to the main menu.");
					break;
				} else {
					System.out.println("Your list is full! Itinerary NOT added. Enter x to go back to the main menu.");
					break;
				}
			}
			if (!userInput.equalsIgnoreCase("y") && !userInput.equalsIgnoreCase("x")) {
				System.out.println("Unable to recognize the input. Please try again.");
			}
		} while (!userInput.equalsIgnoreCase("x"));
	}

	private static void displaySavedPlans() {
		List<List<ParkInterface>> itinerary = backend.getItinerary();
		System.out.println(SAVED_PLANS + "Here are your saved itineraries:\n");
		int counter = 1;
		for (List<ParkInterface> list : itinerary) {
			System.out.println(counter + ". ");
			for (ParkInterface park : list) {
				if (!park.getName().equals("Madison (City)")) {
					System.out.println(park.getName() + "     " + park.getStates().replaceAll("\"", "") + "\n     "
							+ park.getDescription().replaceAll("\"", "") + "\n");
				}
			}
			counter++;
			System.out.println("Total distance: " + backend.shortestPath(list.get(list.size() - 1).getName()) + "\n");
		}

		System.out.println("\nWould you like to remove any itinerary from your list?"
				+ "\n___________________________________________" + "\nJust enter the number of the itinerary you "
				+ "\nwould like to remove." + "\nx - Take me back to the main menu!");
		String userInput = "";
		do {
		      userInput = scan.nextLine().trim();
		      boolean success = false;
		      List<ParkInterface> toRemove;

		      switch (userInput) {
		        case "1":
		          // remove first
		          if (!itinerary.isEmpty() && Integer.parseInt(userInput) <= itinerary.size()) { //IM: added conditional to check if userInput is within the size of itinerary list
		            toRemove = itinerary.get(0);
		            success = backend.removeFromItinerary(toRemove.get(toRemove.size() - 1).getName());
		            if (success) {
		              System.out
		                  .println("Itinerary successfully removed! Enter x to return to the main menu.");
		              break;
		            } else {
		              System.out.println(
		                  "There was a problem removing the itinerary. Enter x to return to the main menu.");
		              break;
		            }
		          } else {
		            System.out.println("There's nothing to remove! Enter x to return to the main menu.");
		            break;
		          }
		        case "2":
		          // remove second
		          if (!itinerary.isEmpty() && Integer.parseInt(userInput) <= itinerary.size()) {
		            toRemove = itinerary.get(1);
		            success = backend.removeFromItinerary(toRemove.get(toRemove.size() - 1).getName());
		            if (success) {
		              System.out
		                  .println("Itinerary successfully removed! Enter x to return to the main menu.");
		              break;
		            } else {
		              System.out.println(
		                  "There was a problem removing the itinerary. Enter x to return to the main menu.");
		              break;
		            }
		          } else {
		            System.out.println("There's nothing to remove! Enter x to return to the main menu.");
		            break;
		          }
		        case "3":
		          // remove third
		          if (!itinerary.isEmpty() && Integer.parseInt(userInput) <= itinerary.size()) {

		            toRemove = itinerary.get(2);
		            success = backend.removeFromItinerary(toRemove.get(toRemove.size() - 1).getName());
		            if (success) {
		              System.out
		                  .println("Itinerary successfully removed! Enter x to return to the main menu.");
		              break;
		            } else {
		              System.out.println(
		                  "There was a problem removing the itinerary. Enter x to return to the main menu.");
		              break;
		            }
		          } else {
		            System.out.println("There's nothing to remove! Enter x to return to the main menu.");
		            break;
		          }
		      }

		      if (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3")
		          && !userInput.equalsIgnoreCase("x")) {
		        System.out.println("Unable to recognize the input. Please try again.");
		      }
		    } while (!userInput.equalsIgnoreCase("x"));

	}
}

package bookingsystem;

import java.util.*;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class Bookings {
	
	private static ArrayList<String> guestNames = new ArrayList<>();
	private static ArrayList<String> roomTypes = new ArrayList<>();
	private static ArrayList<Integer> numberOfNights = new ArrayList<>();
	private static ArrayList<Integer> bookingNumbers = new ArrayList<>();
	private static ArrayList<LocalDate> arrivalDates = new ArrayList<>();
	private static ArrayList<LocalDate> departureDates = new ArrayList<>();
	
	public static void showMenu() {
		System.out.println("\n------Hotel Booking System--------");
		System.out.println("1. Create Reservation");
		System.out.println("2. Show all reservations(bookings)");
		System.out.println("3. Search booking by booking number");
		System.out.println("4. Search Booking by guest name");
		System.out.println("5. Cancel booking by booking number");
		System.out.println("6. Find free room for a certain period");
		System.out.println("7. End the program");
		System.out.println("------------------------");
		System.out.println("choose one of the following alternatives(1-7)");
	}
	
	
    private static int generateUniqueBookingNumber() {
        Random rand = new Random();
        int bookingNumber;
        do {
            bookingNumber = rand.nextInt(1000) + 1;
        } while (bookingNumbers.contains(bookingNumber));
        return bookingNumber;
    }

	
	
	 public static void createReservation() {
	        Scanner scanner = new Scanner(System.in);
	        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	        System.out.println("Please type in the guest name:");
	        String guestName = scanner.nextLine();

	        System.out.println("Select room type (e.g., singleRoom, doubleRoom, queenRoom):");
	        String roomType = scanner.nextLine();

	        // Get and validate arrival date
	        LocalDate arrDate = null;
	        while (arrDate == null) {
	            System.out.print("Enter your arrival date in the format yyyy-MM-dd: ");
	            String dateInput = scanner.nextLine();
	            try {
	                arrDate = LocalDate.parse(dateInput, dateFormatter);
	                if (arrDate.isBefore(LocalDate.now())) {
	                    System.out.println("Arrival date cannot be in the past. Please try again.");
	                    arrDate = null; // Reset to prompt user again
	                }
	            } catch (DateTimeParseException e) {
	                System.out.println("Invalid date format. Please try again.");
	            }
	        }

	        // Get and validate departure date
	        LocalDate depDate = null;
	        while (depDate == null) {
	            System.out.print("Enter your departure date in the format yyyy-MM-dd: ");
	            String departureDateInput = scanner.nextLine();
	            try {
	                depDate = LocalDate.parse(departureDateInput, dateFormatter);
	                if (depDate.isBefore(arrDate)) {
	                    System.out.println("Departure date must be after arrival date. Please try again.");
	                    depDate = null; // Reset to prompt user again
	                }
	            } catch (DateTimeParseException e) {
	                System.out.println("Invalid date format. Please try again.");
	            }
	        }

	        // Calculate number of nights automatically
	        int nights = (int) ChronoUnit.DAYS.between(arrDate, depDate);

	        // Generate booking number
	        int bookingNumber = generateUniqueBookingNumber();

	        // Add booking details to lists
	        guestNames.add(guestName);
	        roomTypes.add(roomType);
	        numberOfNights.add(nights);  
	        bookingNumbers.add(bookingNumber);
	        arrivalDates.add(arrDate);
	        departureDates.add(depDate);

	        System.out.println("Room added for " + guestName + " in a " + roomType + " for " + nights + " nights, " +
	                           "Arrival Date: " + arrDate + ", Departure Date: " + depDate);

	        // Write booking information to file
	        String bookingInfo = "Guest: " + guestName + ", Room type: " + roomType + ", Nights: " + nights 
	                             + ", Booking number: " + bookingNumber + ", Arrival: " + arrDate 
	                             + ", Departure: " + depDate + "\n";
	        try (BufferedWriter bWriter = new BufferedWriter(new FileWriter("src/bookingsystem/savedBookings.txt", true))) {
	            bWriter.write(bookingInfo);
	            System.out.println("Successfully wrote to the file.");
	            bWriter.close();
	        } catch (IOException e) {
	            System.out.println("An error occurred.");
	            e.printStackTrace();
	        }
	    }
	
	 /*public static void searchBookingByBookingNumber() {
		    Scanner scanner = new Scanner(System.in);

		    // Prompt user for booking number
		    System.out.println("Enter the booking number to search:");
		    int bookingNumber = scanner.nextInt();

		    // Find index of the booking number in the bookingNumbers list
		    int index = bookingNumbers.indexOf(bookingNumber);

		    // Check if booking exists
		    if (index != -1) {
		        // If booking exists, retrieve and display booking details
		        System.out.println("Booking found:");
		        System.out.println("Guest: " + guestNames.get(index) +
		                ", Room Type: " + roomTypes.get(index) +
		                ", Nights: " + numberOfNights.get(index) +
		                ", Booking Number: " + bookingNumbers.get(index) +
		                ", Arrival Date: " + arrivalDates.get(index) +
		                ", Departure Date: " + departureDates.get(index));
		    } else {
		        // If booking does not exist, inform the user
		        System.out.println("No booking found for booking number: " + bookingNumber);
		    }
		} */
	 

	 public static void searchBookingByBookingNumber() {
		    Scanner scanner = new Scanner(System.in);

		    // Prompt user for booking number
		    System.out.println("Enter the booking number to search:");
		    int bookingNumber = scanner.nextInt();
		    scanner.nextLine();

		    boolean bookingFound = false;

		    try {
		       
		        File myFile = new File("src/bookingsystem/savedBookings.txt");
		        if (myFile.length() == 0) {
		            System.out.println("There are no bookings in the hotel.");
		            return;
		        }

		        Scanner fileScanner = new Scanner(myFile);

		        while (fileScanner.hasNextLine()) {
		            String line = fileScanner.nextLine();
		            if (line.contains("Booking number: " + bookingNumber)) {
		                System.out.println("Booking found:");
		                System.out.println(line);
		                bookingFound = true;
		                break;
		            }
		        }
		        fileScanner.close();

		        if (!bookingFound) {
		            System.out.println("No booking found for booking number: " + bookingNumber);
		        }

		    } catch (IOException e) {
		        System.out.println("An error occurred while reading the booking file.");
		        e.printStackTrace();
		    }
		}
		
	/* public static void searchBookingByGuestName() {
	        Scanner scanner = new Scanner(System.in);

	        System.out.println("write the guest name:");
	       String guestName = scanner.nextLine();
	       
	       boolean found = false;
	       
	       for(int i = 0; i<guestNames.size(); i++) {
	    	   if(guestNames.get(i).equalsIgnoreCase(guestName)) {
	    		   System.out.println("Booking found:\nGuest: " + guestNames.get(i)+
	    				   ", Room type: " + roomTypes.get(i) + 
	    				   ", nights: " + numberOfNights.get(i)+
	    				   ", Booking Number:" + bookingNumbers.get(i));
	    				   found = true;
	    				   	   			   
	    	   }
	    	   
	       }    

	    }*/
	 
	 
	 
	 public static void searchBookingByGuestName() {
	        Scanner scanner = new Scanner(System.in);

	        System.out.println("write the guest name:");
	       String guestName = scanner.nextLine();
	       
	       boolean bookingFound = false;

		    try {
		       
		        File myFile = new File("src/bookingsystem/savedBookings.txt");
		        if (myFile.length() == 0) {
		            System.out.println("There are no bookings in the hotel.");
		            return;
		        }

		        Scanner fileScanner = new Scanner(myFile);

		        while (fileScanner.hasNextLine()) {
		            String line = fileScanner.nextLine();
		            if (line.contains("Guest: " + guestName)) {
		                System.out.println("Booking found:");
		                System.out.println(line);
		                bookingFound = true;
		                break;
		            }
		        }
		        fileScanner.close();

		        if (!bookingFound) {
		            System.out.println("No booking found for guest: " + guestName);
		        }

		    } catch (IOException e) {
		        System.out.println("An error occurred while reading the booking file.");
		        e.printStackTrace();
		    }
	    
	    	   
	         

	    }
		
		
	 public static void showAllBookings() {
		    try {
		       
		        File myFile = new File("src/bookingsystem/savedBookings.txt");
		        
		        if (myFile.length() == 0) {
		            System.out.println("There are no bookings in the hotel.");
		            return;
		        }

		        Scanner myReader = new Scanner(myFile);
		        System.out.println("Present bookings:");
		        while (myReader.hasNextLine()) {
		            String data = myReader.nextLine();
		            System.out.println(data);  
		        }
		        myReader.close();
		        
		    } catch (IOException e) {
		        System.out.println("An error occurred while reading the file.");
		        e.printStackTrace();
		    }
		}
	 
	 public static void cancelBooking() {
		    Scanner scanner = new Scanner(System.in);
		    System.out.println("Please type the booking number for the booking you want to cancel:");
		    int bookingNumber = scanner.nextInt();

		    ArrayList<String> allBookings = new ArrayList<>();
		    boolean bookingFound = false;

		    try {
		        File myFile = new File("src/bookingsystem/savedBookings.txt");
		        Scanner fileReader = new Scanner(myFile);

		        while (fileReader.hasNextLine()) {
		            String booking = fileReader.nextLine();
		            System.out.println("booking: " + booking);
		            if (booking.contains("Booking number: " + bookingNumber)) {
		           
		                System.out.println("Booking found and cancelled: " + booking);
		                bookingFound = true;
		            } else {
		                
		                allBookings.add(booking);
		            }
		        }
		        fileReader.close();

		     
		        if (bookingFound) {
		            FileWriter fileWriter = new FileWriter(myFile, false);  
		            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		            
		            for (String remainingBooking : allBookings) {
		                bufferedWriter.write(remainingBooking);
		                bufferedWriter.newLine();
		            }

		            bufferedWriter.close();
		            System.out.println("The file has been updated with remaining bookings.");
		        } else {
		            
		            System.out.println("There is no booking with the number: " + bookingNumber);
		        }

		    } catch (IOException e) {
		        System.out.println("An error occurred while reading or writing the file.");
		        e.printStackTrace();
		    }
		}

	
		    
		   /* public static void searchAvailableRooms() {
		        Scanner scanner = new Scanner(System.in);
		        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		        System.out.println("Ange önskat ankomstdatum (format yyyy-MM-dd):");
		        String dateInput = scanner.nextLine();
		        LocalDate requestedArrivalDate;
		        
		        try {
		            requestedArrivalDate = LocalDate.parse(dateInput, dateFormatter);
		        } catch (Exception e) {
		            System.out.println("Felaktigt datumformat. Försök igen.");
		            return;
		        }

		        System.out.println("Ange antal nätter:");
		        int requestedNights = scanner.nextInt();
		        scanner.nextLine(); 

		        LocalDate requestedDepartureDate = requestedArrivalDate.plusDays(requestedNights);

		        ArrayList<String> availableRooms = new ArrayList<>();
		        for (int i = 0; i < roomTypes.size(); i++) {
		            LocalDate arrivalDate = arrivalDates.get(i);
		            LocalDate departureDate = departureDates.get(i);

		            if (requestedDepartureDate.isBefore(arrivalDate) || requestedArrivalDate.isAfter(departureDate)) {
		                availableRooms.add(roomTypes.get(i));
		            }
		        }

	
		        if (availableRooms.isEmpty()) {
		            System.out.println("Inga lediga rum för den valda perioden.");
		        } else {
		            System.out.println("Lediga rum för perioden " + requestedArrivalDate + " till " + requestedDepartureDate + ":");
		            for (String room : availableRooms) {
		                System.out.println("- " + room);
		            }
		        }
		    }
*/
	 
	 public static void searchAvailableRooms() {
	        Scanner scanner = new Scanner(System.in);
	        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	        // Prompt user for desired arrival date and number of nights
	        System.out.println("Enter desired arrival date (format yyyy-MM-dd):");
	        String dateInput = scanner.nextLine();
	        LocalDate requestedArrivalDate;

	        try {
	            requestedArrivalDate = LocalDate.parse(dateInput, dateFormatter);
	        } catch (Exception e) {
	            System.out.println("Invalid date format. Please try again.");
	            return;
	        }

	        System.out.println("Enter number of nights:");
	        int requestedNights = scanner.nextInt();
	        scanner.nextLine(); // Clear newline character after nextInt()

	        LocalDate requestedDepartureDate = requestedArrivalDate.plusDays(requestedNights);

	        // Map to store available room counts by type
	        Map<String, Integer> availableRooms = new HashMap<>();

	        try {
	            File myFile = new File("src/bookingsystem/savedBookings.txt");

	            // Check if the file is empty
	            if (myFile.length() == 0) {
	                System.out.println("There are no bookings in the hotel.");
	                return;
	            }

	            Scanner fileScanner = new Scanner(myFile);

	            // Loop through each line in the file
	            while (fileScanner.hasNextLine()) {
	                String line = fileScanner.nextLine().trim();

	                // Skip empty lines
	                if (line.isEmpty()) continue;

	                // Attempt to parse the line
	                try {
	                    // Parse room type
	                    String roomType = line.contains("Room type: ") ? line.split("Room type: ")[1].split(",")[0].trim() : null;
	                    
	                    // Parse arrival date
	                    String arrivalDateStr = line.contains("Arrival: ") ? line.split("Arrival: ")[1].split(",")[0].trim() : null;
	                    LocalDate arrivalDate = arrivalDateStr != null ? LocalDate.parse(arrivalDateStr, dateFormatter) : null;
	                    
	                    // Parse departure date
	                    String departureDateStr = line.contains("Departure: ") ? line.split("Departure: ")[1].trim() : null;
	                    LocalDate departureDate = departureDateStr != null ? LocalDate.parse(departureDateStr, dateFormatter) : null;

	                    // If any field is missing, skip this line
	                    if (roomType == null || arrivalDate == null || departureDate == null) {
	                        System.out.println("Invalid line format, skipping: " + line);
	                        continue;
	                    }

	                    // Check if the room is available for the requested dates
	                    if (requestedDepartureDate.isBefore(arrivalDate) || requestedArrivalDate.isAfter(departureDate)) {
	                        // Increment the room count in the map
	                        availableRooms.put(roomType, availableRooms.getOrDefault(roomType, 0) + 1);
	                    }

	                } catch (Exception e) {
	                    System.out.println("Error parsing line, skipping: " + line);
	                    e.printStackTrace();
	                }
	            }

	            fileScanner.close();

	            // Display available rooms to the user
	            if (availableRooms.isEmpty()) {
	                System.out.println("No rooms available for the selected dates.");
	            } else {
	                System.out.println("Available rooms for the period " + requestedArrivalDate + " to " + requestedDepartureDate + ":");
	                for (Map.Entry<String, Integer> entry : availableRooms.entrySet()) {
	                    System.out.println("- " + entry.getKey() + ": " + entry.getValue() + " available");
	                }
	            }

	        } catch (IOException e) {
	            System.out.println("An error occurred while reading the booking file.");
	            e.printStackTrace();
	        }
	    }
}

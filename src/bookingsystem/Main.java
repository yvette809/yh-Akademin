package bookingsystem;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		boolean isContinue = true;
		while(isContinue) {
			Bookings.showMenu();
			
			int choice = scanner.nextInt();
			scanner.nextLine();
			switch(choice){
				case 1:

					Bookings.createReservation();
					break;
				case 2:
					Bookings.showAllBookings();
					break;
				case 3:
					Bookings.searchBookingByBookingNumber();
					break;
				case 4:
					Bookings.searchBookingByGuestName();
					break;
				case 5:
					Bookings.cancelBooking();
					break;
				case 6:
					Bookings.searchAvailableRooms();
					break;
				case 7:
					isContinue=false;
					System.out.println("Program is closing");
					break;
					default:
						System.out.println("Invalid alternative, please try again");
						scanner.close();
						
					
					
			}
			
		}
		
		

	}

}

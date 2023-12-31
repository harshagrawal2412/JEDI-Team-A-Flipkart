package com.flipkart.application;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.flipkart.bean.*;
import com.flipkart.business.UserServiceOperations;
import com.flipkart.application.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;


import com.flipkart.dao.*;
import com.flipkart.utils.DatabaseConnector;

import static com.flipkart.constants.ColorConstants.*;

/**
 * This class represents the customer menu for the GymFlipFit application.
 * It provides various functions for customer actions such as viewing gyms, booking slots, and managing bookings.
 */
public class GymFlipFitCustomerMenu {
    

    static Scanner obj = new Scanner(System.in);
    UserServiceOperations userServiceOperation = new UserServiceOperations();
    User user = new User();

    /**
     * Perform user login and display the customer menu.
     *
     * @param username The username of the customer.
     * @param pass     The password of the customer.
     * @return True if login is successful, false otherwise.
     */
    
//    public static String getUserNameByEmail(String email) {
//        String user = null;
//
//        try (Connection conn = DatabaseConnector.getConnection()) {
//            String query = "SELECT userName FROM User WHERE email = ?";
//
//            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
//                preparedStatement.setString(1, email);
//
//                try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                    if (resultSet.next()) {
//                        // Retrieve user's name
//                        user = resultSet.getString("userName");
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return user;
//    }

    public boolean userLogin(String username, String pass) {
        if (validateUser(username, pass)) {
            boolean flag = true;
            System.out.println(ANSI_BLUE+ "Login Successful"+ANSI_RESET);
//            LocalDateTime currentTime = LocalDateTime.now();
//            DayOfWeek dayOfWeek = currentTime.getDayOfWeek();
//            DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:SS");
//            String formattedDate = currentTime.format(myFormat);
            
            LocalDateTime currentTime = LocalDateTime.now();

            // Format date with custom pattern
            DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy (HH:mm:ss) EEEE", Locale.ENGLISH);
            String formattedDate = currentTime.format(myFormat);

//            System.out.println(formattedDate);
            
//            GymFlipFitApplication app=new GymFlipFitApplication();
            String user=GymFlipFitApplication.getUserNameByEmail("userName","User",username);
            System.out.println(ANSI_YELLOW+"Hello "+ user +"!!\t"+ formattedDate+ ANSI_RESET);
            while (flag) {
                System.out.println("=========CUSTOMER MENU=========");
                System.out.println("Press 1 to View all Gyms with slots");
                System.out.println("Press 2 to Book Slot");
                System.out.println("Press 3 to Cancel Slot");
                System.out.println("Press 4 to View all Bookings");
                System.out.println("Press 5 to View all Gyms by Area");
                System.out.println("Press 6 to Logout");
                int choice = Integer.parseInt(obj.nextLine());
                switch (choice) {
                    case 1:
                        List<Gym> gyms = viewAllGymswithSlots();
                        printGyms(gyms);
                        break;
                    case 2:
                        List<Gym> gyms1 = viewAllGymswithSlots();
                        printGyms(gyms1);
                        System.out.println("Enter the following:");
                        System.out.println("Gym ID");
                        String gymId = (String)obj.nextLine();
                        System.out.println("Slot Time");
                        int time = Integer.parseInt(obj.nextLine());

                        if (bookSlot(gymId, time, username)) {
                            System.out.println(ANSI_BLUE+ "Booked Successfully"+ANSI_RESET);
                        } else {
                            System.out.println(ANSI_YELLOW+ "Booking Unsuccessful"+ ANSI_RESET);
                        }
                        break;
                    case 3:
                        Scanner sc = new Scanner(System.in);
                        System.out.println("My Bookings");
                        System.out.println(viewAllBookings(username));
                        System.out.println("Enter Booking ID");
                        int bookingId = sc.nextInt();
                        cancelSlot(bookingId);
                        break;
                    case 4:
                        System.out.println("My Bookings");
                        List<Bookings> bookings = viewAllBookings(username);
                        for (Bookings booking : bookings) {
                            System.out.println("Booking ID: " + booking.getBookingId() + " Booking Status: " + booking.getStatus() + " Time: " + booking.getTime() + " GymID: " + booking.getGymId());
                        }
                        break;
                    case 5:
                        String location = "bangalore"; // You can modify this to take user input for location.
                        List<Gym> gyms2 = viewAllGymsByArea(location);
                        printGyms(gyms2);
                        break;
                    case 6:
                        flag = false;
                        break;
                    default:
                        System.out.println(ANSI_YELLOW+ "Wrong Choice"+ANSI_RESET);
                }
            }

        } else return false;
        return true;
    }

    /**
     * Prints the lists of gyms
     * @param y
     */
    private void printGyms(List<Gym> y) {
        for (Gym gym : y) {
            System.out.println("====================");
            System.out.println("Gym name: " + gym.getGymName() + " Gym ID: " + gym.getGymId() + " Gym Location: " + gym.getLocation() + " Gym Address: " + gym.getGymAddress());
            System.out.println("Slot List");
            String leftAlignFormat = "| %-15d | %-15d | %-20d |%n";
            System.out.format("+-----------------+-----------------+----------------------+\n");
            System.out.format("| Start Time      |   End Time      | Remaining Seats      |\n");
            System.out.format("+-----------------+-----------------+----------------------+\n");

            for (Slots slot : gym.getSlots()) {
                System.out.format(leftAlignFormat,slot.getStartTime(),(slot.getStartTime() + 1),slot.getSeatCount());
            }
            System.out.format("+-----------------+-----------------+----------------------+\n");

        }
    }

    /**
     * Validate user credentials.
     *
     * @param username The username of the customer.
     * @param pass     The password of the customer.
     * @return True if user credentials are valid, false otherwise.
     */
    public boolean validateUser(String username, String pass) {
        return userServiceOperation.validateUser(username, pass);
    }

    /**
     * View all gyms with available slots.
     *
     * @return List of gyms with available slots.
     */
    List<Gym> viewAllGymswithSlots() {
        System.out.println("List of Gyms");
        List<Gym> gymList = userServiceOperation.getAllGymsWithSlots();
        return gymList;
    }

    /**
     * Book a slot for a specific gym.
     *
     * @param gymId  The ID of the gym.
     * @param time   The slot time.
     * @param email  The email of the user booking the slot.
     * @return True if the slot is booked successfully, false otherwise.
     */
    public boolean bookSlot(String gymId, int time, String email) {
        return userServiceOperation.bookSlots(gymId, time, email);
    }

    /**
     * Cancel a booked slot.
     *
     * @param bookingId The ID of the booking to be canceled.
     */
    public void cancelSlot(int bookingId) {
        System.out.println(ANSI_BLUE+ "Slot Cancelled"+ ANSI_RESET);
        userServiceOperation.cancelSlots(bookingId);
    }

    /**
     * View all bookings for a specific user.
     *
     * @param userid The ID of the user.
     * @return List of user's bookings.
     */
    public List<Bookings> viewAllBookings(String userid) {
        List<Bookings> myBookings = userServiceOperation.getAllBookings(userid);
        return myBookings;
    }

    /**
     * View all gyms in a specific area.
     *
     * @param location The location (area) to filter gyms.
     * @return List of gyms in the specified area.
     */
    List<Gym> viewAllGymsByArea(String location) {
        System.out.println("List of Gyms");
        List<Gym> gymList = userServiceOperation.getAllGymsByArea(location);
        return gymList;
    }

    /**
     * Create a new customer user.
     */
    public void createCustomer() {
        System.out.println("Enter the following info:");
        System.out.println("\nYour email: ");
        String ownerEmail = obj.nextLine();
        System.out.println("\nYour name: ");
        String ownerName = obj.nextLine();
        System.out.println("\nEnter a password: ");
        String password = obj.nextLine();
        System.out.println("\nPhone number: ");
        String phoneNo = obj.nextLine();
        System.out.println("\nEnter Address ");
        String nationalId = obj.nextLine();
        System.out.println("\nLocation: ");
        String GST = obj.nextLine();

        User user = new User();
        user.setEmail(ownerEmail);
        user.setAddress(nationalId);
        user.setLocation(GST);
        user.setPassword(password);
        user.setUserName(ownerName);
        user.setPhoneNumber(phoneNo);

        userServiceOperation.createUser(user);
    }
}

package com.flipkart.application;

import com.flipkart.business.GymOwnerService;

import com.flipkart.business.GymOwnerServiceOperation;
import com.flipkart.business.UserServiceOperations;
import com.flipkart.business.UserServices;
import com.flipkart.dao.AdminDAOImplementation;
import com.flipkart.utils.DatabaseConnector;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.Scanner;

import static com.flipkart.constants.ColorConstants.*;

public class GymFlipFitApplication {
    static GymFlipFitGymOwnerMenu owner = new GymFlipFitGymOwnerMenu();
    static GymFlipFitCustomerMenu customer = new GymFlipFitCustomerMenu();
    static GymOwnerService gymOwnerService = new GymOwnerServiceOperation();

    static UserServices userService = new UserServiceOperations();
    static Scanner obj = new Scanner(System.in);

    static Properties prop = new Properties();

    /**
     * @ Main application
     * @author Sugam.rohilla, Ali zain, Harsh
     * @param args
     */
    
    public static String getUserNameByEmail(String name, String database, String email) {
        String user = null;

        try (Connection conn = DatabaseConnector.getConnection()) {
            String query = "SELECT " + name + " FROM " + database + " WHERE email = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, email);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Retrieve user's name
                        user = resultSet.getString(name);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static void main(String[] args) {
        System.out.println(ANSI_CYAN + "------------------------------------------------------------------");
        System.out.println("Welcome to FlipFit application for slot booking!");
        System.out.println("--------------------------------------------------------------------" + ANSI_RESET);
        boolean exitFlag = false;
        while(true) {
            System.out.println("================================");
            System.out.println("Press 1 for Login");
            System.out.println("Press 2 for Registration");
            System.out.println("Press 3 for Update Password");
            System.out.println("Press 4 for Exit");
            int x = Integer.parseInt(obj.nextLine());
            switch (x) {
                case 1 :
                    //System.out.println("Call gymFlipFitApp");
                    System.out.println("Enter email");
                    String userId = obj.nextLine();
                    System.out.println("Enter password");
                    String password = obj.nextLine();
                    System.out.println("Enter role (Admin/Customer/GymOwner)");
                    String role = obj.nextLine();
                    role=role.toLowerCase();

                    switch (role) {
                        case "admin" :
                            GymFlipFitAdminMenu admin = new GymFlipFitAdminMenu();
                            AdminDAOImplementation admin2=new AdminDAOImplementation();

                            if(!admin.verifyAdminCredentials(userId,password)){
                                System.out.println(ANSI_YELLOW + "Invalid Credentials"+ANSI_RESET);
                                break;
                            }

                            boolean flag = true;

                            while(flag) {

                                System.out.println("Press 1 for View all users");
                                System.out.println("Press 2 for View all Gyms");
                                System.out.println("Press 3 for View all Gym Owners");
                                System.out.println("Press 4 for Verify Gym");
                                System.out.println("Press 5 for Verify All Gyms");
                                System.out.println("Press 6 for Verify GymOwner");
                                System.out.println("Press 7 for Verify All GymOwners");
                                System.out.println("Press 8 for View pending Gyms");
                                System.out.println("Press 9 for View pending Gym Owners");
                                System.out.println("Press 10 for Exit");

                                int k = Integer.parseInt(obj.nextLine());

                                switch (k) {
                                    case 1:
                                        admin.viewUsers();
                                        break;
                                    case 2:
                                        admin.viewGyms();
                                        break;
                                    case 3:
                                        admin.viewGymOwners();
                                        break;
                                    case 4:
                                        System.out.println("Enter the Gym Id to be verified ");
                                        String id1 = obj.nextLine();
                                        admin.verifyGym(id1);
                                        break;
                                    case 5:
                                    	admin2.verifyAllGyms();
                                    	break;
                                    case 6:
                                        System.out.println("Enter the Gym Owner Id to be verified ");
                                        String id2 = obj.nextLine();
                                        admin.verifyGymOwner(id2);
                                        break;
                                    case 7:
                                    	admin2.verifyAllGymOwners();
                                    	break;
                                    case 8:
                                        admin.viewUnverifiedGyms();
                                        break;
                                    case 9:
                                        admin.viewUnverifiedGymOwners();
                                        break;
                                    case 10:
                                        flag = false;
                                        break;
                                }
                                if(!flag) break;
                            }
                            break;

                        case "customer" :
                            if(!customer.userLogin(userId,password))
                                System.out.println(ANSI_YELLOW+"Invalid credentials"+ANSI_RESET);
                            break;
                        case "gymowner" :
                            if(!owner.gymOwnerLogin(userId,password)){
                                System.out.println(ANSI_YELLOW+"Invalid credentials"+ANSI_RESET);
                            }

                            break;
                        default:
                            System.out.println(ANSI_YELLOW+"Invalid Options Selected. Please Try Again:("+ANSI_RESET);
                            break;

                    }

                    break;
                case 2 :
                    System.out.println("Press 1 to Register as a Customer");
                    System.out.println("Press 2 to Register as a Gym Owner");
                    System.out.println("Press 3 to Go Back");
                    int k = Integer.parseInt(obj.nextLine());
                    switch(k){
                        case 1:
                            customer.createCustomer();
                            break;
                        case 2:
                            owner.createGymOwner();
                        default:
                            break;
                    }
                    break;
                case 3 :
                    System.out.println("-----------Password Change -----------------------");
                    System.out.println("Enter email");
                    String user = obj.nextLine();
                    System.out.println("Enter password");
                    String userPassword = obj.nextLine();
                    System.out.println("Enter role (Admin/Customer/GymOwner)");
                    String respectiveRole = obj.nextLine();
                    System.out.println("Enter New password");
                    String updatedPassword = obj.nextLine();

                    switch (respectiveRole) {
                        case "Customer" :
                            if(!customer.validateUser(user,userPassword))
                                System.out.println(ANSI_YELLOW+"Invalid credentials"+ANSI_RESET);
                            else{
                                userService.updateGymUserPassword(user,userPassword, updatedPassword);
                            }
                            break;
                        case "GymOwner" :
                            if(!owner.verifyGymOwner(user,userPassword)){
                                System.out.println(ANSI_YELLOW+"Invalid credentials"+ANSI_RESET);
                            }
                            else{
                                gymOwnerService.updateGymOwnerPassword(user,userPassword, updatedPassword);
                            }

                            break;
                    }
                    break;
                case 4 :
                    //end
                    exitFlag = true;
                    System.out.println("Thank you for using FlipFit :)");
                    break;
                default:
                    System.out.println(ANSI_YELLOW+"Invalid Options Selected. Please Try Again:( "+ANSI_RESET);
                    break;
                }
            if(exitFlag)break;
        }


    }
}


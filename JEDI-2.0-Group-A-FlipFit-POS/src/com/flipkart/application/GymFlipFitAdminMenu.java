package com.flipkart.application;

import com.flipkart.bean.Admin;
import com.flipkart.bean.Gym;
import com.flipkart.bean.GymOwner;
import com.flipkart.bean.Slots;
import com.flipkart.business.AdminService;
import com.flipkart.business.AdminServiceOperation;
import com.flipkart.utils.DatabaseConnector;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;


/**
 * This class represents the administrative menu for the GymFlipFit application.
 * It provides various functions for managing gyms, gym owners, and admin credentials.
 * Author: bhavya.khandelwal
 */
public class GymFlipFitAdminMenu {

    AdminService adminService = new AdminServiceOperation();

    /**
     * @Author Sugam, Harsh, Ali , Srashti , Dipti
     * View all gyms in the system.
     */
    public void viewGyms() {
        adminService.viewGyms();
    }

    /**
     * @Author Sugam, Harsh, Ali , Srashti , Dipti
     * View all registered users in the system.
     */
    public void viewUsers() {
        adminService.viewUsers();
    }

    /**
     * View all registered gym owners in the system.
     * @Author Sugam, Harsh, Ali , Srashti , Dipti
     */
    public void viewGymOwners() {
        adminService.viewGymOwners();
    }

    /**
     * Verify a gym by its ID.
     * @Author Sugam, Harsh, Ali , Srashti , Dipti
     * @param id The ID of the gym to be verified.
     */
    public void verifyGym(String id) {
        adminService.verifyGym(id);
    }

    /**
     * Verify a gym owner by their ID.
     * @Author Sugam, Harsh, Ali , Srashti , Dipti
     * @param id The ID of the gym owner to be verified.
     */
    public void verifyGymOwner(String id) {
        adminService.verifyGymOwner(id);
    }

    /**
     * @Author Sugam, Harsh, Ali , Srashti , Dipti
     * View all unverified gyms in the system.
     */
    public void viewUnverifiedGyms() {
        List<Gym> gyms = adminService.getUnverifiedGyms();
        String leftAlignFormat = "| %-5d | %-20s | %-5s | %-40s | %-20s | %-15s |%n";
        System.out.format("+-------+----------------------+-------+------------------------------------------+----------------------+------------------+\n");
        System.out.format("| Gym   |     Name             | Gym Id|           Address                        |   Location           |     Status       |\n");
        System.out.format("+-------+----------------------+-------+------------------------------------------+----------------------+------------------+\n");

        int x = 1;
        for (Gym g : gyms) {
            System.out.format(leftAlignFormat,x,g.getGymName(),g.getGymId().substring(0, 3),g.getGymAddress(),g.getLocation(),g.getStatus());            x++;
        }
        System.out.format("+-------+----------------------+-------+------------------------------------------+----------------------+------------------+\n");

    }

    /**
     * @Author Sugam, Harsh, Ali , Srashti , Dipti
     * View all unverified gym owners in the system.
     */
    public void viewUnverifiedGymOwners() {
        List<GymOwner> g = adminService.getUnverifiedGymOwners();
        int x = 1;
        for (GymOwner gymOwner : g) {
            System.out.println("GymOwner " + x + "-->   Gym Owner Id " + gymOwner.getOwnerId() + "    Email: " + gymOwner.getOwnerEmail() + "    Phone No: " + gymOwner.getPhoneNo() + "   Status:" + gymOwner.getStatus());
            x++;
            System.out.println("\n-------------------------------------------------------------");
        }
    }

    /**
     * Verify admin credentials.
     * @Author Sugam, Harsh, Ali , Srashti , Dipti
     * @param id   The admin ID.
     * @param pass The admin password.
     * @return True if admin credentials are valid, false otherwise.
     */
    public boolean verifyAdminCredentials(String id, String pass) {
        try {
            Properties prop = new Properties(); // Properties is used to read files
            InputStream inputStream = DatabaseConnector.class.getClassLoader().getResourceAsStream("./config.properties");
            prop.load(inputStream);
            String admin_id = prop.getProperty("admin_id");
            String admin_password = prop.getProperty("admin_password");

            return (id.equals(admin_id) && pass.equals(admin_password));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}

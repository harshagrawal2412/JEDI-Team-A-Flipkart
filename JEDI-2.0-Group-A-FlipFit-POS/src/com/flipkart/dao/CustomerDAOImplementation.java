package com.flipkart.dao;

import com.flipkart.bean.Bookings;

import com.flipkart.bean.Gym;
import com.flipkart.bean.Slots;
import com.flipkart.bean.User;
import com.flipkart.exception.BookingCancellationFailedException;
import com.flipkart.exception.RegistrationFailedException;
import com.flipkart.exception.SlotsUnavailableException;
import com.flipkart.utils.DatabaseConnector;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.*;
import java.util.List;
import java.util.Objects;


public class CustomerDAOImplementation implements CustomerDAOInterface {

    DatabaseConnector connector ;
    Connection conn;
    @Override
    public List<Gym> getAllGymsByArea() {
        conn = DatabaseConnector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Gym> gyms = new ArrayList<>();

        try {
            String sqlQuery = "SELECT * FROM gyms";
            preparedStatement = conn.prepareStatement(sqlQuery);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
            	String id = resultSet.getString("gymId");
                String gymAddress = resultSet.getString("gymAddress");
                String location = resultSet.getString("location");
                String gymName = resultSet.getString("gymName");
                String status = resultSet.getString("status");
                String gymOwnerID = resultSet.getString("ownerid");
                if(Objects.equals(status, "unverified")) continue;
                Gym gym = new Gym();
                gym.setGymName(gymName);
                gym.setGymAddress(gymAddress);
                gym.setOwnerId(gymOwnerID);
                gym.setLocation(location);
                gym.setStatus(status);
                gym.setGymId(id);
                gyms.add(gym);

                List<Slots> slots = getGymSlotsWithGymId(id);
                gym.setSlots(slots);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }

        return gyms;
    }

    @Override
    public boolean bookSlot(String gymId, int time,String email) {
        conn = DatabaseConnector.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        String insertQuery = "INSERT INTO Booking (userId,status,date,time,slotId,GymId ) VALUES(?,?,?,?,?,?)";

        int alreadyBooked = getSeatNumberWithGymIDandSlotId(gymId, time);
        int remaining = getSeatNumberWithGymIDandSlotIdFromSlots(gymId, time);
        try {
            if(remaining <= 0){
                System.out.println("No slots available");
                throw new SlotsUnavailableException();
            }
            statement = conn.createStatement();
//            resultSet = statement.executeQuery(insertQuery);
            preparedStatement =  conn.prepareStatement(insertQuery);

            // 5. Set values for the placeholders in the prepared statement

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, "CONFIRMED");
            preparedStatement.setInt(3, 11);
            preparedStatement.setInt(4, time);
            preparedStatement.setInt(5, time);
            preparedStatement.setString(6, gymId);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Record inserted successfully!");
            } else {
                throw new SlotsUnavailableException();

//                System.out.println("Failed to insert the record.");
//                return false;
            }
            alterSeatsWithGymIDSlotID(gymId,time,remaining-1);

        }catch(SlotsUnavailableException | SQLException ex){
            System.out.println(ex.getMessage());

        }
        return true;
    }

    private int getSeatNumberWithGymIDandSlotIdFromSlots(String gymId, int time) {
        conn = DatabaseConnector.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        int x = 0;
        try {
            String sqlQuery = "SELECT seatCount FROM slots WHERE gymId= "  + gymId + " AND startTime = " + time;
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                x = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return x;
    }

    private void alterSeatsWithGymIDSlotID(String gymId, int time,int x) {
        conn = DatabaseConnector.getConnection();
        Statement statement = null;
        int resultSet = 0;
        List<Slots> slotList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            String sqlQuery = "UPDATE slots SET seatCount= " + x + "   WHERE gymId= " + gymId + " AND startTime= " + time;
            preparedStatement = conn.prepareStatement(sqlQuery);

            resultSet = preparedStatement.executeUpdate();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private int getSeatNumberWithGymIDandSlotId(String gymId, int time) {
        conn = DatabaseConnector.getConnection();
        Statement statement=null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        int number=0;
        try {
            String sqlQuery= "SELECT COUNT(*) from Booking where gymId=? AND time=?";
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, gymId);
            preparedStatement.setInt(2, time);

            resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                number = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return number;
    }

    @Override
    public List<Bookings> getAllBookingByUserID(String userId) {
        conn = DatabaseConnector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Bookings> bookings = new ArrayList<>();

        try {
            String sqlQuery = "SELECT * FROM Booking where userId=\"" + userId + "\"";
            preparedStatement = conn.prepareStatement(sqlQuery);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
            	String id = resultSet.getString("bookingId");
            	int date = resultSet.getInt("date");
            	int time = resultSet.getInt("time");
                String slotId = resultSet.getString("slotId");
                String status = resultSet.getString("status");
                String gymId = resultSet.getString("gymId");
                Bookings booking = new Bookings();
                booking.setBookingId(id);
                booking.setDate(date);
                booking.setTime(time);
                booking.setSlotId(slotId);
                booking.setStatus(status);
                booking.setGymId(gymId);
                bookings.add(booking);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bookings;
    }

    @Override
    public boolean cancelBooking(int bookingId) {
        conn = DatabaseConnector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Bookings> bookings = new ArrayList<>();

        try {
            String sqlQuery = "DELETE * FROM Booking where bookingId=" + bookingId;
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    @Override
    public boolean validateUser(String username, String pass) {
        conn = DatabaseConnector.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        String password2 = "-";
        try {
            String sqlQuery = "SELECT * FROM User WHERE email= \""  + username + "\"";
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                password2 = resultSet.getString("password");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return password2.equals(pass);
    }

    @Override
    public void createUser(User user) {
        conn = DatabaseConnector.getConnection();
        PreparedStatement preparedStatement = null;
        String userId = UUID.randomUUID().toString(); 

        String insertQuery = "INSERT INTO User (userId,userName, email, password, phoneNumber, Address, location) VALUES (?,?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = conn.prepareStatement(insertQuery);

            // Set values for the placeholders in the prepared statement
            user.setuserId(userId);
            preparedStatement.setString(1, user.getuserId());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getPhoneNumber());
            preparedStatement.setString(6, user.getAddress());
            preparedStatement.setString(7, user.getLocation());

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Record inserted successfully!");
            } else {
                throw new RegistrationFailedException();
            }
        } catch (RegistrationFailedException ex) {
            System.out.println("User " + ex.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            // Close resources (result set, statement, and connection)
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }


    public List<Slots> getGymSlotsWithGymId(String id){
        conn = DatabaseConnector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Slots> slotList = new ArrayList<>();
        try {
        	String sqlQuery = "SELECT * FROM slots WHERE gymId=?";
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

            	int startTime = resultSet.getInt("startTime");
            	int seats = resultSet.getInt("seatCount");
                Slots slots = new Slots("1",startTime,seats);

                slotList.add(slots);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return slotList;
    }
}

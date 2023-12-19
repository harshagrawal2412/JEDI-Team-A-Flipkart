package com.flipkart.dao;

import com.flipkart.bean.Gym;
import com.flipkart.bean.GymOwner;
import com.flipkart.bean.Slots;
import com.flipkart.constants.SQLConstants;
import com.flipkart.exception.RegistrationFailedException;
import com.flipkart.exception.SlotInsertionFailedException;
import com.flipkart.utils.DatabaseConnector;

//import com.flipkart.dao.GymOwnerDAOImplementation;
//import com.flipkart.dao.DatabaseConnector;

import java.sql.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GymOwnerDAOImplementation implements GymOwnerDaoInterface {
    Connection conn;

    DatabaseConnector connector;
    @Override
    public void addGym(Gym gym){
        conn = DatabaseConnector.getConnection();
        String gymId = UUID.randomUUID().toString(); 
        Statement statement = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        String id = "0";
        try {
            statement = conn.createStatement();
            preparedStatement =  conn.prepareStatement(SQLConstants.GYM_OWNER_INSERT_GYM, statement.RETURN_GENERATED_KEYS);
            
            gym.setGymId(gymId);

            preparedStatement.setString(1, gym.getGymId());
            preparedStatement.setString(2, gym.getGymAddress());
            preparedStatement.setString(3, gym.getLocation());
            preparedStatement.setString(4, gym.getGymName());
            preparedStatement.setString(5, gym.getStatus());
            preparedStatement.setString(6, gym.getOwnerId());

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Record inserted successfully!");
            } else {
                throw new RegistrationFailedException();
////                System.out.println("Failed to insert the record.");
//                return ;
            }
            ResultSet rs = preparedStatement.getGeneratedKeys();

            if (rs.next()) {
                id = rs.getString("1");
            }


        } catch (RegistrationFailedException ex){
            System.out.println("Gym "+ex.getMessage());

        }
        catch (SQLException e) {

            System.out.println(e.getMessage());
        }
        insertSlots(gym.getSlots(),gymId);

    }

    @Override
    public void newGymOwner(GymOwner gymOwner) {
        Statement statement = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        conn = DatabaseConnector.getConnection();
        String ownerId = UUID.randomUUID().toString(); 

        try {
            statement = conn.createStatement();
//            resultSet = statement.executeQuery(insertQuery);
            preparedStatement =  conn.prepareStatement(SQLConstants.GYM_OWNER_INSERT);

            // 5. Set values for the placeholders in the prepared statement
            gymOwner.setOwnerId(ownerId);
            preparedStatement.setString(1,gymOwner.getOwnerId());
            preparedStatement.setString(2, gymOwner.getOwnerEmail());
            preparedStatement.setString(3, gymOwner.getOwnerName());
            preparedStatement.setString(4, gymOwner.getPassword());
            preparedStatement.setString(5, gymOwner.getPhoneNo());
            preparedStatement.setString(6, gymOwner.getPAN());
            preparedStatement.setString(7, gymOwner.getGST());
            preparedStatement.setString(8,gymOwner.getNationalId());
            preparedStatement.setString(9, gymOwner.getStatus());

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Record inserted successfully!");
            } else {
                throw new RegistrationFailedException();
//                System.out.println("Failed to insert the record.");
//                return ;
            }

        }catch(RegistrationFailedException ex){
            System.out.println("Gym Owner" + ex.getMessage());
        }catch (SQLException e) {
            System.out.println(e.getMessage());
//            e.printStackTrace();
//            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean validateLogin(String email, String password) {
        conn = DatabaseConnector.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            statement = conn.createStatement();
            preparedStatement = conn.prepareStatement(SQLConstants.GYM_USER_VERIFY_PASSWORD, statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);


            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
//            throw new RuntimeException(e);
        }

        return false;
    }

    @Override
    public void insertSlots(List<Slots> slots, String gymId){
        conn = DatabaseConnector.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        
        
        
        for( Slots slot: slots){
        	String slotsId = UUID.randomUUID().toString(); 
            String insertQuery = "INSERT INTO slots (slotsId,startTime, seatCount, gymId) VALUES (?,?, ?, ?)";

            try {
                statement = conn.createStatement();
//                resultSet = statement.executeQuery(insertQuery);
                preparedStatement =  conn.prepareStatement(insertQuery);

                // 5. Set values for the placeholders in the prepared statement
                slot.setSlotsId(slotsId);
                preparedStatement.setString(1, slot.getSlotsId());
                preparedStatement.setInt(2, slot.getStartTime());
                preparedStatement.setInt(3, slot.getSeatCount());
                preparedStatement.setString(4, gymId);

                int rowsInserted = preparedStatement.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("Record inserted successfully!");
                } else {
                    throw new SlotInsertionFailedException();

//                    System.out.println("Failed to insert the record.");
//                    return ;
                }

            }catch(SlotInsertionFailedException | SQLException ex){
                System.out.println(ex.getMessage());
            }
//
        }


    }

    @Override
    public List<Gym> viewGymSlots(String gymOwnerID) {
        conn = DatabaseConnector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Gym> gyms = new ArrayList<>();

        try {
            String sqlQuery = "SELECT * FROM gyms WHERE ownerId=?";
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, gymOwnerID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("gymId");
                String gymAddress = resultSet.getString("gymAddress");
                String location = resultSet.getString("location");
                String gymName = resultSet.getString("gymName");
                String status = resultSet.getString("status");
                Gym gym = new Gym();
                gym.setGymName(gymName);
                gym.setGymAddress(gymAddress);
                gym.setOwnerId(gymOwnerID);
                gym.setLocation(location);
                gym.setStatus(status);
                gyms.add(gym);

                List<Slots> slots = getGymSlotsWithGymId(id);
                gym.setSlots(slots);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return gyms;
    }

    public List<Slots> getGymSlotsWithGymId(String id){
        conn = DatabaseConnector.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        List<Slots> slotList = new ArrayList<>();
        try {
            String sqlQuery = "SELECT * FROM slots WHERE gymId= " + id;
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
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
/**
 * 
 */
package com.flipkart.business;

import java.util.List;

import com.flipkart.bean.Gym;
import com.flipkart.bean.GymOwner;

/**
 * @Author Sugam, Harsh, Ali , Srashti , Dipti
 */
public interface GymOwnerService {

	/**
	 * This method inserts a given Gym in the DB
	 * @Author Sugam, Harsh, Ali , Srashti , Dipti
	 * @param gym
	 */
	void addGymWithSlots(Gym gym);

	/**
	 * @Author Sugam, Harsh, Ali , Srashti , Dipti
	 * This method return the list of booked Gyms for a given user(userId)
	 * @param userId
	 * @return List<Gym>
	 */
	List<Gym> viewMyGyms(String userId);

	/**
	 * This method verifies a Gym Owner with given email and password
	 * @Author Sugam, Harsh, Ali , Srashti , Dipti
	 * @param email
	 * @param password
	 * @return
	 */
    boolean verifyGymOwnerPassword(String email, String password);

	/**
	 * This method validates a user with given email and password
	 * @Author Sugam, Harsh, Ali , Srashti , Dipti
	 * @param email
	 * @param password
	 * @return
	 */
    boolean validateLogin(String email, String password);

	/**
	 * This method creates a new Gym Owner
	 * @Author Sugam, Harsh, Ali , Srashti , Dipti
	 * @param gymOwner
	 */
	void createGymOwner(GymOwner gymOwner);

	/**
	 * This method updates the Gym Owner Password
	 * @Author Sugam, Harsh, Ali , Srashti , Dipti
	 * @param email
	 * @param password
	 * @param updatedPassword
	 */
	void updateGymOwnerPassword(String email, String password, String updatedPassword);
}

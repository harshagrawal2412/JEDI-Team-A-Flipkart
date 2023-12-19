package com.flipkart.business;

import com.flipkart.bean.Gym;
import com.flipkart.bean.GymOwner;

import java.util.List;

/**
 *
 */
public interface AdminService {

    //public void createAdmin();
    /**
     * @Author Sugam, Harsh, Ali , Srashti , Dipti
     * This function lists all the Gym Owners
     */
    public void viewGymOwners();
    /**
     * @Author Sugam, Harsh, Ali , Srashti , Dipti
     * This function lists all the Gyms
     */
    public void viewGyms();

    /**
     * @Author Sugam, Harsh, Ali , Srashti , Dipti
     * This function all the Users
     */
    public void viewUsers();

    /**
     * @Author Sugam, Harsh, Ali , Srashti , Dipti
     * This function verifies the Gym with given gymId
     * @param gymId
     */
    public void verifyGym(String gymId);

    /**
     * @Author Sugam, Harsh, Ali , Srashti , Dipti
     * This method verifies the Gym Owner with given GymOwnerId
     * @param gymOwnerId
     */
    public void verifyGymOwner(String gymOwnerId);

    /**
     * @Author Sugam, Harsh, Ali , Srashti , Dipti
     * This function fetches the list of Un-verified Gym Owners
     * @return List<GymOwner>
     */
    public List<GymOwner> getUnverifiedGymOwners() ;

    /**
     * @Author Sugam, Harsh, Ali , Srashti , Dipti
     * This function fetches the list of Un-verified Gyms
     * @return List<Gym>
     */
    public List<Gym> getUnverifiedGyms() ;

}

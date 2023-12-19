package com.flipkart.constants;

import java.security.PublicKey;

public class SQLConstants {
    /**
     * SQL query to insert a gym owned by a gym owner
     */
    public static final String GYM_OWNER_INSERT_GYM = "INSERT INTO gyms (gymId,gymAddress, location, gymName, status, ownerId) VALUES(?,?,?,?,?,?)";

    /**
     * SQL query to view a gym owned by a gym owner
     */
    public static final String GYM_OWNER_VIEW_GYM = "INSERT INTO gyms (gymAddress, location, gymName, status, ownerId) VALUES(?,?,?,?,?)";

    /**
     * SQL query to update the password of a gym user
     */
    public static final String GYM_USER_UPDATE_PASSWORD = "UPDATE User SET password = ? WHERE email = ? AND Password = ?";

    /**
     * SQL query to update the password of a gym owner
     */
    public static final String GYM_OWNER_UPDATE_PASSWORD = "UPDATE gym_owner SET password = ? WHERE email = ? AND Password = ?";

    /**
     * SQL query to verify the password of a gym user
     */
    public static final String GYM_USER_VERIFY_PASSWORD = "SELECT * FROM gym_owner WHERE email = ? AND Password = ?";

    /**
     * SQL query to verify the password of a gym owner
     */
    public static final String GYM_OWNER_VERIFY_PASSWORD = "SELECT * FROM gym_owner WHERE email = ? AND Password = ?";

    /**
     * SQL query to view all users (admin function)
     */
    public static final String ADMIN_VIEW_ALL_USERS = "SELECT * FROM User";

    /**
     * SQL query to view all gyms (admin function)
     */
    public static final String ADMIN_VIEW_ALL_GYMS = "SELECT * FROM gyms";

    /**
     * SQL query to view all gym owners (admin function)
     */
    public static final String ADMIN_VIEW_ALL_GYMOWNERS = "SELECT * FROM gym_owner";

    /**
     * SQL query to verify gyms (admin function)
     */
    public static final String ADMIN_VERIFY_GYMS = "UPDATE gyms SET status = ? WHERE gymId = ?";

    /**
     * SQL query to verify gym owners (admin function)
     */
    public static final String ADMIN_VERIFY_GYMOWNERS = "UPDATE gym_owner SET status = ? WHERE ownerId = ?";

    /**
     * SQL query to view unverified gyms (admin function)
     */
    public static final String ADMIN_VIEW_UNVERIFIED_GYMS = "SELECT * FROM gyms WHERE status=?";

    /**
     * SQL query to view unverified gym owners (admin function)
     */
    public static final String ADMIN_VIEW_UNVERIFIED_GYMOWNER = "SELECT * FROM gym_owner WHERE status=?";

    /**
     * SQL query to insert a gym owner
     */
    public static final String GYM_OWNER_INSERT = "INSERT INTO gym_owner (ownerId,email, name, password, phone_number, pancard, aadhar, gst, status) VALUES(?,?,?,?,?,?,?,?,?)";
}

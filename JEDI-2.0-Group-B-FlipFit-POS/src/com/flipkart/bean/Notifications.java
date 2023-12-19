package com.flipkart.bean;

/**
 * This class represents notifications in the GymFlipFit application.
 * It stores information about the notification's category, message, and notification ID.
 * Author: bhavya.khandelwal
 */
public class Notifications {

    private String category; // Category of the notification
    private String message; // Message content of the notification
    private String notificationId; // Unique identifier for the notification

    /**
     * Get the notification ID.
     * @Author Sugam, Harsh, Ali , Srashti , Dipti
     * @return The notification ID.
     */
    public String getNotificationId() {
        return notificationId;
    }

    /**
     * Set the notification ID.
     * @Author Sugam, Harsh, Ali , Srashti , Dipti
     * @param notificationId The notification ID to set.
     */
    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    /**
     * Get the category of the notification.
     * @Author Sugam, Harsh, Ali , Srashti , Dipti
     * @return The category of the notification.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Set the category of the notification.
     * @Author Sugam, Harsh, Ali , Srashti , Dipti
     * @param category The category of the notification to set.
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Get the message content of the notification.
     * @Author Sugam, Harsh, Ali , Srashti , Dipti
     * @return The message content of the notification.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the message content of the notification.
     * @Author Sugam, Harsh, Ali , Srashti , Dipti
     * @param message The message content of the notification to set.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}

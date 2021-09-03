package model;

import java.sql.Date;
import java.sql.Timestamp;

/** This is the Appointment class. This class allows the creation of appointment objects. */
public class Appointment {

    private String title;
    private String description;
    private String location;
    private String type;
    private Date createDate;
    private String startTime;
    private String endTime;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int customerID;
    private int userID;
    private int appointmentID;
    private int contactID;

    /** The Appointment constructor method. This creates a Appointment object.
     * @param appointmentID The appointment ID.
     * @param contactID The contact ID.
     * @param createDate The date the appointment was created.
     * @param createdBy Who created the appointment.
     * @param customerID The customer ID.
     * @param description The description of the appointment.
     * @param endTime The end date and time of the appointment.
     * @param startTime The start date and time of the appointment.
     * @param lastUpdate The time of the last update of the appointment.
     * @param lastUpdatedBy Who updated the apoointment last.
     * @param location The location of the appointment.
     * @param title The title of the appointment.
     * @param type The type of appointment.
     * @param userID The user ID.
     */
    public Appointment(String title, String description, String location, String type, Date createDate, String startTime, String endTime, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int customerID, int userID, int appointmentID, int contactID) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.createDate = createDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerID = customerID;
        this.userID = userID;
        this.appointmentID = appointmentID;
        this.contactID = contactID;
    }
    /** Another appointment constructor method. This creates a appointment object only using certain data.
     * @param userID The user ID.
     * @param title The title of the appointment.
     * @param description The description of the appointment.
     * @param location The location of the appointment.
     * @param type The type of appointment.
     * @param endTime The end date and time of the appointment.
     * @param startTime The start date and time of the appointment.
     * @param appointmentID The appointment ID.
     * @param customerID The customer ID.
     * @param contactID The contact ID.
     */
    public Appointment(String title, String description, String location, String type, String startTime, String endTime, int customerID, int userID, int appointmentID, int contactID){
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerID = customerID;
        this.userID = userID;
        this.appointmentID = appointmentID;
        this.contactID = contactID;

    }
    /** Another appointment constructor.
     * This creates an appointment object with no parameters.
     */
    public Appointment() {

    }
    /** The appointment ID getter. This gets the ID of the appointment.
     * @return The appointment ID.
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /** The appointment ID setter. This sets the appointment ID variable.
     * @param appointmentID The appointment ID to be set.
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /** The appointment title getter. This retrieves the title of the appointment.
     * @return The title of the appointment.
     */
    public String getTitle() {
        return title;
    }

    /** The title setter. This sets the title of the appointment.
     * @param title The title of the appointment to be set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /** The appointment description getter. This gets the description of the appointment.
     * @return The description of the appointment.
     */
    public String getDescription() {
        return description;
    }

    /** The appointment description setter. This sets the description of the appointment.
     * @param description The description to be set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /** The appointment location getter. This gets the location of the appointment.
     * @return The location of the appointment.
     */
    public String getLocation() {
        return location;
    }

    /** The appointment location setter. This sets the location of the appointment.
     * @param location The location to be set.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /** The appointment type getter. This gets the type of appointment.
     * @return The type of appointment.
     */
    public String getType() {
        return type;
    }

    /** The appointment type setter. This sets the type of the appointment.
     * @param type The type to be set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /** The appointment create date getter. This gets the date created of the appointment.
     * @return The create date of the appointment.
     */
    public Date getCreateDate() {
        return createDate;
    }

    /** The appointment create date setter. This sets the create date of the appointment.
     * @param createDate The date to be set.
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /** The appointment start time getter. This gets the start time of the appointment.
     * @return The start time of the appointment.
     */
    public String getStartTime() {
        return startTime;
    }

    /** The appointment start time setter. This sets the start time of the appointment.
     * @param startTime The start time to be set.
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /** The appointment end time getter. This gets the end time of the appointment.
     * @return The end time of the appointment.
     */
    public String getEndTime() {
        return endTime;
    }

    /** The appointment end time setter. This sets the end time of the appointment.
     * @param endTime The end time to be set.
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /** The appointment created by getter. This gets the created by of the appointment.
     * @return The created by of the appointment.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /** The appointment created by setter. This sets the created by of the appointment.
     * @param createdBy The created by of the appointment.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /** The appointment last update getter. This gets the last update time of the appointment.
     * @return The last update time of the appointment.
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /** The appointment last uptdate setter. This sets the last update time of the appointment.
     * @param lastUpdate The last update time to be set
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /** The appointment last updated by getter. This gets the last updated by of the appointment.
     * @return The last updated by of the appointment.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /** The appointment last updated by setter. This sets the last updated by of the appointment.
     * @param lastUpdatedBy The last updated by of the appointment.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /** The appointment customer ID getter. This gets the customer ID of the appointment.
     * @return The customer ID for the appointment.
     */
    public int getCustomerID() {
        return customerID;
    }

    /** The appointment customer ID setter. This sets the customer ID of the appointment.
     * @param customerID The customer ID to be set.
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /** The appointment user ID getter. This gets the user ID of the appointment.
     * @return The user ID for the appointment.
     */
    public int getUserID() {
        return userID;
    }

    /** The appointment user ID setter. This sets the user ID of the appointment.
     * @param userID The user ID to be set.
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /** The appointment contact ID getter. This gets the contact ID of the appointment.
     * @return The contact ID of the appointment.
     */
    public int getContactID() {
        return contactID;
    }

    /** The appointment contact ID setter. This sets the contact ID of the appointment.
     * @param contactID The contact ID to be set.
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
}

package model;

/** The Reports class. This stores reports information and utility. */
public class Reports {

    private String month;
    private String type;
    private int count;
    private int appID;
    private String title;
    private String description;
    private String start;
    private String end;
    private int customerID;
    private String contactName;
    private String country;
    private int count1;



    /** This is the Reports customer appointments by type constructor. This allows to create a report of month, type, and count of appointments.
     * @param month The month.
     * @param type The type of report.
     * @param count The count of appointment.
     */
    public Reports(String month, String type, int count){
        this.month = month;
        this.type = type;
        this.count = count;
    }

    /** This is the schedule by contact constructor. This allows to create a report of schedule by the contact.
     * @param contactName The contact name.
     * @param appID The appointment ID.
     * @param title The title of the appointment.
     * @param type The type of the appointment.
     * @param description The description of the appointment.
     * @param start The start time of the appointment.
     * @param end The end time of the appointment.
     * @param customerID The customer ID of the appointment.
     */
    public Reports (String contactName, int appID, String title, String type, String description, String start, String end, int customerID){
        this.contactName = contactName;
        this.appID = appID;
        this.title = title;
        this.type = type;
        this.description = description;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
    }

    /** This is the reports customer by country constructor. This allows to create a report of customer count by country.
     * @param count The count of customer in country.
     * @param country The country of the customer.
     */
    public Reports (int count, String country){
        this.count1 = count;
        this.country = country;
    }

    /** The reports country getter. This gets the country for the reports.
     * @return The country name for the report.
     */
    public String getCountry() {
        return country;
    }

    /** The reports country setter. This sets the country for the reports.
     * @param country The country to set.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /** The reports count of customer getter. This gets the the count by customer variable.
     * @return The count of the customers per country.
     */
    public int getCount1() {
        return count1;
    }

    /** The reports count of customer setter. This sets the the count by customer variable.
     * @param count1  The count to be set.
     */
    public void setCount1(int count1) {
        this.count1 = count1;
    }

    /** The reports contact name getter. This gets the contact name for the report.
     * @return The contact name.
     */
    public String getContactName() {
        return contactName;
    }

    /** The reports contact name setter. This sets the contact name for the report.
     * @param contactName The contact name to set.
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /** The reports count of appointment by contact getter. This gets the count of appointment by contact.
     * @return The count of appointment by contact.
     */
    public int getCount() {
        return count;
    }

    /** The reports count of appointment by contact setter. This sets the count of appointment by contact.
     * @param count The count to be set.
     */
    public void setCount(int count) {
        this.count = count;
    }

    /** The reports appointment ID getter. This gets the appointment ID.
     * @return The appointment ID.
     */
    public int getAppID() {
        return appID;
    }

    /** The reports appointment ID setter. This sets the appointment ID.
     * @param appID The appointment ID to be set. */
    public void setAppID(int appID) {
        this.appID = appID;
    }

    /** The reports appointment title getter. This gets the appointment title for the report.
     * @return The appointment title.
     */
    public String getTitle() {
        return title;
    }

    /** The reports appointment title getter. This gets the appointment title for the report.
     * @param title The appointment to be set. */
    public void setTitle(String title) {
        this.title = title;
    }

    /** The reports appointment description getter. This gets the appointment description for the report.
     * @return The appointment description.
     */
    public String getDescription() {
        return description;
    }

    /** The reports appointment description setter. This sets the appointment description for the report.
     * @param description The appointment description to be set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /** The reports appointment start time getter. This gets the appointment start time for the report.
     * @return The start time of the appointment.
     */
    public String getStart() {
        return start;
    }

    /** The reports appointment start time setter. This sets the appointment start time for the report.
     * @param start The appointment start time to be set.
     */
    public void setStart(String start) {
        this.start = start;
    }

    /** The reports appointment end time getter. This gets the appointment end time for the report.
     * @return The end time of the appointment.
     */
    public String getEnd() {
        return end;
    }

    /** The reports appointment end time setter. This sets the appointment end time for the report.
     * @param end The appointment end time to be set.
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /** The reports customer ID getter. This gets the customer ID for the report.
     * @return The customer ID.
     */
    public int getCustomerID() {
        return customerID;
    }

    /** The reports customer ID setter. This sets the customer ID for the report.
     * @param customerID The customer ID to be set. */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /** The reports appointment month getter. This gets the month for the appointment for the report.
     * @return The month of appointment.
     */
    public String getMonth() {
        return month;
    }

    /** The reports appointment month setter. This sets the month for the appointment for the report.
     * @param month The appointment to be set.
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /** The reports appointment type getter. This gets the type of appointment for the report.
     * @return The type of appointment.
     */
    public String getType() {
        return type;
    }

    /** The reports appointment type setter. This sets the type of appointment for the report.
     * @param type The appointment type to be set.
     */
    public void setType(String type) {
        this.type = type;
    }

}

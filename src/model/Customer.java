package model;

import java.sql.Timestamp;

/** The customer class. This stores all customer information and utility. */
public class Customer {
    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private String createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int divisionID;

    /** The Customer contrusctor. This allows the creation of a customer object.
     * @param customerID The customer ID.
     * @param customerName The customer name.
     * @param address The address.
     * @param postalCode The postal code.
     * @param phone The phone number.
     * @param createDate The date the customer was created.
     * @param createdBy Who created the customer.
     * @param lastUpdate The last updated of the customer.
     * @param lastUpdatedBy Who last updated the customer.
     * @param divisionID The division ID.
     */
    public Customer(int customerID, String customerName, String address, String postalCode, String phone, String createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
    }

    /** Another customer contructor. This creates a customer object with less data.
     * @param customerID The customer ID.
     * @param customerName The customer name.
     * @param address The address.
     * @param postalCode The postal code.
     * @param phone The phone number.
     * @param divisionID The division ID.
     */
    public Customer(int customerID, String customerName, String address, String postalCode, String phone,int divisionID){
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionID;
    }

/** Empty Customer constructor. */
    public Customer() {

    }

    /** The customer ID getter. This gets the customer ID.
     * @return The customer ID.
     */
    public int getCustomerID() {
        return customerID;
    }

    /** The customer ID setter. This sets the customer Id.
     * @param customerID The customer ID to set.
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /** The customer name getter. This gets the customer name.
     * @return The customer name.
     */
    public String getCustomerName() {
        return customerName;
    }

    /** The customer name setter. This sets the customer name.
     * @param customerName The customer name to be set.
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /** The customer address getter. This gets the address for the customer.
     * @return The address.
     */
    public String getAddress() {
        return address;
    }

    /** The customer address setter. This sets the address for the customer.
     * @param address The address to be set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /** The customer postal code getter. This gets the postal code for the customer.
     * @return The postal code.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /** The customer postal code setter. This sets the postal code for the customer.
     * @param postalCode The postal code to be set.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /** The customer phone getter. This gets the phone for the customer.
     * @return THe phone number.
     */
    public String getPhone() {
        return phone;
    }

    /** The customer phone setter. This sets the phone for the customer.
     * @param phone The phone to be set.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /** The customer create date getter. This gets the create date for the customer.
     * @return The create date of the customer.
     */
    public String getCreateDate() {
        return createDate;
    }

    /** The customer create date setter. This sets the create date for the customer.
     * @param createDate The create date to be set.
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /** The customer created by getter. This gets the created by for the customer.
     * @return The create date of the customer.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /** The customer created by setter. This sets the created by for the customer.
     * @param createdBy The created by to be set.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /** The customer last update time getter. This gets the last updated time for the customer.
     * @return The last update of the customer.
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /** The customer last update time setter. This sets the last updated time for the customer.
     * @param lastUpdate The last update time to be set.
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /** The customer last updated by getter. This gets the last updated by for the customer.
     * @return The last updated by of the customer.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /** The customer last updated by setter. This sets the last updated by for the customer.
     * @param lastUpdatedBy The last updated by to be set.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /** The customer division ID getter. This gets the division ID of the customer.
     * @return The division ID of the customer.
     */
    public int getDivisionID() {
        return divisionID;
    }

    /** The customer division ID setter. This sets the division ID of the customer.
     * @param divisionID The division id to be set.
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

}

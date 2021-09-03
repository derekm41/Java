package model;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/** This is the Country class. This stores all country information and utility. */
public class Country {
        private int countryID = 0;//auto generated
        private String country;
        private Date createDate;
        private String createdBy;
        private Timestamp lastUpdate;
        private String lastUpdatedBy;

        /** This is the Country constructor. This creates a object country.
         * @param countryID The country ID of the country.
         * @param country The country name.
         * @param createDate The date the country was created.
         * @param createdBy Who created the country object.
         * @param lastUpdate The time the country object was updated.
         * @param lastUpdatedBy Who updated last.
         */
        public Country(int countryID, String country, Date createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy){
        this.countryID = countryID;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }
    /** The country ID getter. This gets the country ID of the country.
     * @return The country ID of the country.
     */
    public int getCountryID() {
        return countryID;
    }

    /** The country ID setter. This sets the country ID for the country.
     * @param countryID The country ID to be set.
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /** The country getter. This gets the country name of the country.
     * @return The country name of the country.
     */
    public String getCountry() {
        return country;
    }

    /** The country setter. This sets the name of the country.
     * @param country The name of the country to be set.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /** The country create date getter. This gets the create date of the country.
     * @return The country create date.
     */
    public Date getCreateDate() {
        return createDate;
    }

    /** This is the create date setter. This sets the create date of the country.
     * @param createDate The create date to be set.
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /** This the create by getter. This gets the created by for the country.
     * @return The create by of the country.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /** The created by setter. This sets the created by for the country.
     * @param createdBy The created by to be set.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /** The last update time getter. This gets the last updated time of the country.
     * @return The last update time of the country.
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /** The last update time setter. This sets the last update time of the country.
     * @param lastUpdate The last update time to be set. */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /** The country last updated by getter. This gets the last updated by for the country.
     * @return The last updated by of the country.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /** The country last updated by setter. This sets the last updated by for the country.
     * @param lastUpdatedBy The last updated by to be set. */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }


}

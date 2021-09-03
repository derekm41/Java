package view_controller;

import DAO_Utility.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/** The Add Appointment Controller. This stores all the add appointment information and utility. */
public class AddAppointmentController implements Initializable {

    @FXML
    private TextField AppIDTextField;

    @FXML
    private DatePicker DatePicker;

    @FXML
    private TextField StartMinuteTextField;

    @FXML
    private TextField StartHourTextField;

    @FXML
    private CheckBox StartAMCheck;

    @FXML
    private CheckBox StartPMCheck;

    @FXML
    private TextField TitleTextField;

    @FXML
    private TextField DescriptionTextField;

    @FXML
    private TextField LocationTextField;

    @FXML
    private TextField TypeTextField;

    @FXML
    private TextField EndMinuteTextField;

    @FXML
    private TextField EndHourTextField;

    @FXML
    private CheckBox EndAMCheck;

    @FXML
    private CheckBox EndPMCheck;

    @FXML
    private Button AppSaveButton;

    @FXML
    private Button AddCancelButton;

    @FXML
    private ComboBox<Integer> CustomerIDCombo;

    @FXML
    private ComboBox<Integer> UserIDCombo;

    @FXML
    private ComboBox<String> ContactNameCombo;

    //helper variables
    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");// doesn't work
    private final DateTimeFormatter datetimeDTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final ZoneId localZoneID = ZoneId.systemDefault();//local zoneID
    private final ZoneId UTCZoneId = ZoneId.of("UTC"); //UTC ZoneId used for database storage
    private final ZoneId ESTZoneId = ZoneId.of("America/New_York");//EST ZoneId used for checks.


    //global variables
    String appUserName = null;
    int appUserID = 0;
    int appCustomerID = 0;
    int appContactID = 0;
    String appTitle = null;
    String appDescription = null;
    String appLocation = null;
    String appType = null;
    Timestamp startTimeStamp;
    Timestamp endTimeStamp;
    LocalDate date;
    String startTime;
    String endTime;
    int TimestampErrorTrack = 0;
    int weekendErrorTracker = 0;


    /** Initialize the add appointment controller class. This initializes the add appointment controller class. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppIDTextField.setDisable(true);
        StartHourInput();
        endHourInput();
        updateDatePicker();


        try {
            ContactNameCombo.setItems(ContactCombo());
            CustomerIDCombo.setItems(CustomerCombo());
            UserIDCombo.setItems(UserIDCombo());
        } catch (SQLException e) {
            System.out.println("problem with setting the comboboxes");
        }
    }

    /** The Date picker update method. This updates the datepicker and blocks out weekends and past dates.
     * Lambda Expression. This expression uses the .setDayCellFactory method which inherits from the Callback interface.
     * This lambda expression is used to disable past and weekend date values of the datepicker.
     */
    public void updateDatePicker() {
        DatePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                //disable any date in the past as well as the weekend dates.
                setDisable(empty || date.isBefore(today) || date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY );
            }

        });
    }

    /** The end hour method. This listens for number input between 1 and 12. */
    public void endHourInput() {
        EndHourTextField.textProperty().addListener((Observable, oldValue, newValue) -> {
            if(newValue.length() < 1) {
                newValue = "0";
            }
            int newValueInt = Integer.parseInt(newValue);
            if (newValueInt < 1 || newValueInt > 12) {
                if (newValueInt == 0) {
                    return;
                } else {
                    System.out.println("Not between 1 and 12");
                    System.out.println("OldValue " + oldValue);
                    System.out.println("NewValue" + newValue);
                    Alert HourAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    HourAlert.setTitle("Invalid Hour Input");
                    HourAlert.setHeaderText("Invalid end time. End time hour input must be between 1 and 12.");
                    HourAlert.showAndWait();

                }
            }
            else {
                System.out.println("Between 1 and 12");
            }
        });


    }
    /** The start hour input method. This checks input in the start hour between 1 and 12. */
public void StartHourInput() {
        System.out.println("Start hour input function");
    StartHourTextField.textProperty().addListener((Observable, oldValue, newValue) -> {

        if (newValue.length() < 1) {
            newValue = "0";
        }

        int newValueInt = Integer.parseInt(newValue);
        if (newValueInt < 1 || newValueInt > 12) {
            if (newValueInt == 0) {
                return;
            } else {
                System.out.println("Not between 1 and 12");
                System.out.println("OldValue " + oldValue);
                System.out.println("NewValue" + newValue);
                Alert HourAlert = new Alert(Alert.AlertType.CONFIRMATION);
                HourAlert.setTitle("Invalid Hour Input");
                HourAlert.setHeaderText("Invalid start time. Start time hour input must be between 1 and 12.");
                HourAlert.showAndWait();

            }
        }
        else {
            System.out.println("Between 1 and 12");
        }
    });
}
/** The customer combo fill method. This retrieves all customers from database and fills the customer combobox.
 * @return A list of customers.
 */
public static  ObservableList<Integer> CustomerCombo() {
    ObservableList<Integer> customerIDList = FXCollections.observableArrayList();
    try {
        String sqlQuery = "SELECT Customer_ID FROM customers";
        Statement sqlStatement = DBConnection.conn.createStatement();
        ResultSet rs = sqlStatement.executeQuery(sqlQuery);

        while (rs.next()) {
            int customerID = rs.getInt("Customer_ID");
            customerIDList.addAll(customerID);
        }
    } catch (SQLException e) {
        System.out.println("Customer ID database query didn't work");
    }
    return customerIDList;
}
/** The user Id fill method. This retrieves all user IDs from database and fills the user ID combobox.
 * @return A list of user IDs.
 */
    public static ObservableList<Integer> UserIDCombo() throws SQLException {
        ObservableList<Integer> userIDList = FXCollections.observableArrayList();
        try {
            String userIDSQLQuery = "SELECT User_ID FROM users";
            Statement sqlStatement = DBConnection.conn.createStatement();
            ResultSet rs = sqlStatement.executeQuery(userIDSQLQuery);

            while (rs.next()) {
                int userID = rs.getInt("User_ID");
                userIDList.addAll(userID);
            }
        }
            catch (SQLException e) {
                System.out.println("User ID database query didn't work");
            }
            return userIDList;
        }
/** The contact fill method. This retrieves the contacts from the database and fills the contact combobox.
 * @return A list of contacts.
 */
    public static ObservableList<String> ContactCombo() throws SQLException{
        ObservableList<String> contactList = FXCollections.observableArrayList();
        try {
            String contactQuery = "SELECT Contact_Name FROM contacts";
            Statement sqlStatement = DBConnection.conn.createStatement();
            ResultSet rs = sqlStatement.executeQuery(contactQuery);

            while (rs.next()) {
                String contactName = rs.getString("Contact_Name");
                contactList.addAll(contactName);
            }
        }
            catch (SQLException e) {
                System.out.println("Contact name query did not work");
            }
        return contactList;
        }

/** Cancel new appointment button handler method. This handles the functionality of canceling and exiting the new appointment screen. */
    @FXML
    void CancelNewAppointment(MouseEvent event) throws IOException {
        Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION);
        cancelAlert.setTitle("Cancel New Appointment");
        cancelAlert.setHeaderText("Are you sure you want to cancel?");
        Optional<ButtonType> outcome = cancelAlert.showAndWait();

        if(outcome.get() == ButtonType.OK) {
            Parent mainScreen = FXMLLoader.load(getClass().getResource("/view_controller/MainScreen.fxml"));
            Scene mainScene = new Scene(mainScreen);
            Stage mainStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            mainStage.setScene(mainScene);
            mainStage.show();
        }
    }
/** Get the start time method. This handles all the start time input and formatting.
 * @return The start date and time in a ZoneDateTime variable.
 */
    public ZonedDateTime getStartTime() {
        String startHour = StartHourTextField.getText();
        String startMin = StartMinuteTextField.getText();


            //Convert the time to military time depending on if pm is checked
            //need a work around for the pm noon time.
            if (StartPMCheck.isSelected()) {
                int startHour12 = Integer.parseInt(startHour);
                if(startHour12 == 12){
                    startHour = String.valueOf(startHour12);
                } else {
                    startHour12 += 12;
                    startHour = String.valueOf(startHour12);
                }
            }

            //Formatting checks
            //Start Time checks
        if(startHour.isEmpty() || date == null){
            //System.out.println("Empty hour");
            throw new NullPointerException();
        }else {
            if (startMin.isEmpty()) {
                startMin = "00";
            }
            if (startHour.length() < 2) {
                String startH = startHour;
                startHour = "0" + startH;
                }
            }
            if (startMin.length() < 2) {
                String startM = startMin;
                startMin = "0" + startM;
            }

            startTime = startHour + ":" + startMin + ":00";


            //Stores the time in a LocalTime variable.
            LocalTime localStartTime = LocalTime.parse(startTime); //LocalTime.parse(startTime);
            System.out.println("local start time" + localStartTime);

            //combine date and time
            LocalDateTime startDateTime = LocalDateTime.of(date, localStartTime);
            //System.out.println(startDateTime);


            //create a zonedDateTime object to use methods
            ZonedDateTime startLocalTime = ZonedDateTime.of(startDateTime, localZoneID);
            // System.out.println(startLocalTime);

            //convert to utc
            ZonedDateTime startLocalToUTC = startDateTime.atZone(localZoneID).withZoneSameInstant(UTCZoneId);
            System.out.println("This is the startlocalToUTC Variable" + startLocalToUTC);

            LocalDateTime utcdatetime = startLocalToUTC.toLocalDateTime();
            System.out.println("This is the startLocalDateTime" + utcdatetime);

        /*convert back to local
        ZonedDateTime startUTCToLocal = startLocalToUTC.withZoneSameInstant(localZoneID);
        System.out.println("This is startUTCToLocal variable " + startUTCToLocal);
        */


        return startLocalTime;
    }

    /** Get the end time method. This handles all the end time input and formatting.
     * @return The end date and time in a ZoneDateTime variable.
     */
    public ZonedDateTime getEndTime(){
        String endHour = EndHourTextField.getText();
        String endMin = EndMinuteTextField.getText();



            if (EndPMCheck.isSelected()) {
                //first check if noon or not.
                int endHour12 = Integer.parseInt(endHour);
                if(endHour12 == 12){
                    endHour = String.valueOf(endHour12);
                } else {
                    endHour12 += 12;
                    endHour = String.valueOf(endHour12);
                }
            }
            if(endHour.isEmpty() || date == null){
                throw new NullPointerException();
            } else {
                //End Time Checks
                if (endMin.isEmpty()) {
                    endMin = "00";
                }
                if (endHour.length() < 2) {
                    String endH = endHour;
                    endHour = "0" + endH;
                }
                if (endMin.length() < 2) {
                    String endM = endMin;
                    endMin = "0" + endM;
                }
            }
            endTime = endHour + ":" + endMin + ":00";
            System.out.println(endTime);


            LocalTime localEndTime = LocalTime.parse(endTime, timeFormat);
            System.out.println("local end time" + localEndTime);

            LocalDateTime endDateTime = LocalDateTime.of(date, localEndTime);
            System.out.println("This is enddatetime" + endDateTime);

            ZonedDateTime endLocalTime = ZonedDateTime.of(date, localEndTime, localZoneID);
            System.out.println(endLocalTime);

            //ZonedDateTime endLocalToUTC = endDateTime.atZone(localZoneID).withZoneSameInstant(UTCZoneId);
            //System.out.println("This is the endlocalToUTC Variable" + endLocalToUTC);


            //dont convert it to utc for some reason the timestamp converts it. Return the local time.
        return endLocalTime;
    }

    /** The Appointment ID method. Generates an appointment Id.
     * @return The new appointment ID.
     */
    public int getAppointmentID() throws SQLException{
        List<Integer> appIDList = new ArrayList();
        Statement getAppID = DBConnection.conn.createStatement();
        ResultSet rs = getAppID.executeQuery("SELECT Appointment_ID FROM appointments");

        while (rs.next()) {
            int appID = rs.getInt("Appointment_ID");
            appIDList.add(appID);
        }
        int greatest = -1;
        for(Integer id : appIDList) {
            if(id > greatest) {
                greatest = id;
            }
        }

        int newAppointmentID = greatest + 1;

        return newAppointmentID;
    }
/** The get fields method. This method gets all the values input in the text boxes and saves them to variables.
 * @return whether or not the retrieval was successful or not.
 */
public boolean getFieldValues() throws SQLException {
    //get all values from textfields
    //I will need to change this parameter to a timestamp in order to insert it into the database.
    boolean success = true;

    try {
        //I had to do this inorder for the date picker to refresh
        String dateString = DatePicker.getEditor().getText();
        if(dateString.length() < 1) {
            date = null;
            DatePicker.setValue(null);
        } else {
            date = DatePicker.getConverter().fromString(dateString);
            //System.out.println(date);
        }
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            weekendErrorTracker = 1;
            success = false;
        }
        //the .setTimestamp in the prepared statement will convert it to UTC time.
        startTimeStamp = Timestamp.valueOf(getStartTime().toLocalDateTime());
        //System.out.println(startTimeStamp);

        //the .setTimestamp in the prepared statement will convert it to UTC time.
        endTimeStamp = Timestamp.valueOf(getEndTime().toLocalDateTime());
        //System.out.println("End Timestamp" + endTimeStamp);
        if(endTimeStamp.before(startTimeStamp)){
            TimestampErrorTrack = 1;
            success = false;
            System.out.println("End time is before start time");
        }


        appUserID = UserIDCombo.getSelectionModel().getSelectedItem();
        //System.out.println(appUserID);

        appUserName = User.getUsername();

        appCustomerID = CustomerIDCombo.getSelectionModel().getSelectedItem();
        //System.out.println("Customer ID: " + appCustomerID);

        //System.out.println(ContactNameCombo.getSelectionModel().getSelectedItem());
        appContactID = getContactID(String.valueOf(ContactNameCombo.getSelectionModel().getSelectedItem()));
        //System.out.println("Contact ID: " + appContactID);

        appTitle = TitleTextField.getText();
        //System.out.println("Title: " + appTitle);

        appDescription = DescriptionTextField.getText();
        //System.out.println(appDescription);

        appLocation = LocationTextField.getText();
        //System.out.println(appLocation);

        appType = TypeTextField.getText();
        //System.out.println(appType);

        int noEmpty = isEmptyFields();
        if(noEmpty == 1) {
            success = false;
        }

    } catch(NullPointerException e) {
        success = false;
    }
    return success;
}
/** The save appointment handler method. This handles all the functionality of saving a new appointment to the database. */
    @FXML
    void SaveNewAppointment(MouseEvent event) throws SQLException, IOException {
        System.out.println("Saving Appointment");

        boolean fieldsSuccess = getFieldValues();
        boolean customerOverlap = customerConflict(startTimeStamp, endTimeStamp);



        if (fieldsSuccess == false) {
            if(TimestampErrorTrack == 1) {
                Alert EndTimeError = new Alert(Alert.AlertType.INFORMATION);
                EndTimeError.setTitle("Invalid Time");
                EndTimeError.setHeaderText("End time cannot be before start time. \n Please change times.");
                EndTimeError.showAndWait();
                TimestampErrorTrack = 0;
                return;
            }else if(weekendErrorTracker == 1) {
                System.out.println("Date cannot be on a weekend.");
                Alert emptyTime = new Alert(Alert.AlertType.INFORMATION);
                emptyTime.setTitle("Invalid Date Input");
                emptyTime.setHeaderText("You cannot set appointments on the weekend.");
                emptyTime.showAndWait();
                return;
            } else {
                Alert emptyFields = new Alert(Alert.AlertType.INFORMATION);
                emptyFields.setTitle("Empty Fields");
                emptyFields.setHeaderText("You cannot leave fields empty. \n Please fill in all fields.");
                emptyFields.showAndWait();
                System.out.println("Fields Success = " + fieldsSuccess);
                return;
            }
        }
        boolean businessHoursSuccess = businessHours();
        if(businessHoursSuccess == false) {
            System.out.println("business hours = " + businessHoursSuccess);
            return;
        }
        if(customerOverlap == true) {
            Alert customerOverlapAlert = new Alert(Alert.AlertType.INFORMATION);
            customerOverlapAlert.setTitle("Appointment Overlap");
            customerOverlapAlert.setHeaderText("Customer ID: " + appCustomerID + " has a conflicting appointment time. \n Please select a different time.");
            customerOverlapAlert.showAndWait();
            return;
        }
                try {
                    // Insert sql statement

                    PreparedStatement saveAppPs = DBConnection.conn.prepareStatement("INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID)"
                            + "VALUES (?,?,?,?,?,?,?,CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

                    saveAppPs.setInt(1, getAppointmentID());
                    saveAppPs.setString(2, appTitle);
                    saveAppPs.setString(3, appDescription);
                    saveAppPs.setString(4, appLocation);
                    saveAppPs.setString(5, appType);
                    saveAppPs.setTimestamp(6, startTimeStamp);
                    saveAppPs.setTimestamp(7, endTimeStamp);
                    saveAppPs.setString(8, appUserName);
                    saveAppPs.setString(9, appUserName);
                    saveAppPs.setInt(10, appCustomerID);
                    saveAppPs.setInt(11, appUserID);
                    saveAppPs.setInt(12, appContactID);
                    System.out.println("PreparedStatement: " + saveAppPs);
                    int result = saveAppPs.executeUpdate();
                    ResultSet rs = saveAppPs.getGeneratedKeys();

                    Alert successfulSubmission = new Alert(Alert.AlertType.INFORMATION);
                    successfulSubmission.setTitle("Save Successful");
                    successfulSubmission.setHeaderText("New Appointment Saved Successfully.");
                    successfulSubmission.showAndWait();


                        Parent mainScreen = FXMLLoader.load(getClass().getResource("/view_controller/MainScreen.fxml"));
                        Scene mainScreenScene = new Scene(mainScreen);
                        Stage mainScreenStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        mainScreenStage.setScene(mainScreenScene);
                        mainScreenStage.show();


                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    System.out.print("Null pointer");
                }
            }

/** The customer conflict method. This checks if customers have conflicting appointments.
 * @param difStart The start time of the appointment associated to the customer.
 * @param difEnd The end time of the appointment associated to the customer.
 * @return whether the method was successful or not.
 */
    public boolean customerConflict(Timestamp difStart, Timestamp difEnd) throws SQLException {
        boolean success = false;
        try {String sql = "SELECT * FROM appointments WHERE (? BETWEEN Start AND End OR ? BETWEEN Start AND End OR ? < Start AND ? > End) AND (Customer_ID = ?)";
            PreparedStatement psConflict = DBConnection.conn.prepareStatement(sql);
            psConflict.setTimestamp(1, difStart);
            psConflict.setTimestamp(2, difEnd);
            psConflict.setTimestamp(3, difStart);
            psConflict.setTimestamp(4, difEnd);
            psConflict.setInt(5,appCustomerID);
            System.out.println(psConflict);
            ResultSet rs = psConflict.executeQuery();

            if (rs.next()) {
                success = true;
            }
        }
        catch(SQLException e) {
            System.out.println("Sql statement has an error.");
            e.printStackTrace();
        }
        return success;

    }

/** The empty fields method. This method checks to see if fields are left empty.
 * @return an error tracking integer.
 */
    public int isEmptyFields() {
        int errorTrack = 0;
        if(appUserID == 0){
            errorTrack = 1;
        }
        else if(appCustomerID == 0){
            errorTrack = 1;
        }
        else if(appContactID == 0){
            errorTrack = 1;
        }
        else if(appTitle.isEmpty()){
            errorTrack = 1;
        }
        else if(appDescription.isEmpty()){
            errorTrack = 1;
        }
        else if(appLocation.isEmpty()){
            errorTrack = 1;
        }
        else if(appType.isEmpty()){
            errorTrack = 1;
        }
        else if(date == null){
            errorTrack = 1;
        }
        /*
        //may be redundant
        else if(startTimeStamp == null){
            errorTrack = 1;
        }
        //may be redundant
        else if(endTimeStamp == null){
            errorTrack = 1;
        }
        else if(StartHourTextField.getText() == null) {
            errorTrack = 1;
        }
        else if(EndHourTextField.getText() == null) {
            errorTrack= 1;
        }

         */
        else{
            System.out.println("No blanks found.");
        }
        return errorTrack;
    }

    /** The get contact ID method. This retrieves the contact ID based off the given contact name.
     * @param contactName The name to be used for ID retrieval.
     * @return The contact ID.
     */
    public static int getContactID(String contactName) throws SQLException{
        int contactID = -1;
        try {
            String contactIDQuery = "SELECT Contact_ID FROM contacts WHERE Contact_Name=" + "'" + contactName + "'";
            Statement sqlStatement = DBConnection.conn.createStatement();
            ResultSet rs = sqlStatement.executeQuery(contactIDQuery);

            while(rs.next()){
                contactID = rs.getInt("Contact_ID");
            }
        }
        catch(SQLException e) {
            System.out.println("Getting the contact ID did not work");
        }
        return contactID;
    }
/** The business hours method. This checks if the appointment is during business hours.
 * @return If the method was successful or not.
 */
    private boolean businessHours() {
        boolean success = true;
        try {

            //get the time that was entered.
            LocalTime localStartTime = LocalTime.parse(startTime);
            LocalTime localEndTime = LocalTime.parse(endTime);

            LocalDateTime startDateTime = LocalDateTime.of(date, localStartTime);
            LocalDateTime endDateTime = LocalDateTime.of(date, localEndTime);

            ZonedDateTime startLocalTime = ZonedDateTime.of(startDateTime, localZoneID);
            ZonedDateTime endLocalTime = ZonedDateTime.of(endDateTime, localZoneID);
            //System.out.println(endLocalTime);


            //convert it to EST time
            ZonedDateTime startLocalToEST = startLocalTime.withZoneSameLocal(ESTZoneId);
            ZonedDateTime endLocalToEST = endLocalTime.withZoneSameInstant(ESTZoneId);


        /*
        System.out.println(endLocalTime);
        System.out.println(endLocalToEST);
        //System.out.println("This is the endlocalToUTC Variable" + endLocalToUTC);
        System.out.println(endLocalToEST.getHour());
        System.out.println(endLocalToEST.getMinute());

         */

            // Check if is between 8:00am and 10:00pm
            if (startLocalToEST.getHour() < 8 || startLocalToEST.getHour() >= 22) {
                Alert outOfTimeRange = new Alert(Alert.AlertType.INFORMATION);
                outOfTimeRange.setTitle("Time out of Range");
                outOfTimeRange.setHeaderText("Appointment times must be scheduled between 8:00am - 10:00pm EST. \n Please enter a valid appointment time.");
                outOfTimeRange.showAndWait();
                success = false;
            } else if (endLocalToEST.getHour() < 8 || endLocalToEST.getHour() >= 22) {
                Alert outOfTimeRange = new Alert(Alert.AlertType.INFORMATION);
                outOfTimeRange.setTitle("Time out of Range");
                outOfTimeRange.setHeaderText("Appointment times must be scheduled between 8:00am - 10:00pm EST. \n Please enter a valid appointment time.");
                outOfTimeRange.showAndWait();
                success = false;
            } else {
                System.out.println("Appointment is set during business hours.");
            }
        }
        catch(Exception e) {
            System.out.println("This is incorrect");
            success = false;
        }

        return success;

    }

/** Start AM check box method. Sets start AM as selected when clicked. */
    @FXML
    void StartAMPM(MouseEvent event) {

            StartAMCheck.setSelected(true);
            StartPMCheck.setSelected(false);
    }
    /** Start PM check box method. Sets start PM as selected when clicked. */
    @FXML
    void StartPM(MouseEvent event) {
        StartPMCheck.setSelected(true);
        StartAMCheck.setSelected(false);
    }
    /** End AM check box method. Sets end AM as selected when clicked. */
    @FXML
    void EndAMPM(MouseEvent event) {
        EndAMCheck.setSelected(true);
        EndPMCheck.setSelected(false);
    }
    /** End PM check box method. Sets end PM as selected when clicked. */
    @FXML
    void EndPM(MouseEvent event) {
        EndPMCheck.setSelected(true);
        EndAMCheck.setSelected(false);
    }
}

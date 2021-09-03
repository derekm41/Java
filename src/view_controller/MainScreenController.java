package view_controller;

import DAO_Utility.DBConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.User;

/** The main screen contoller class. This stores the main screen information and functionality. */
public class MainScreenController implements Initializable {

    /** Initialize method. This initializes the mainscreencontroller class. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            createAppointmentView();
            createCustomerView();

        } catch(SQLException e){
            System.out.println("SQL Error!");
        }

    }

    @FXML
    private Button AddAppointmentButton;

    @FXML
    private Button ModifyAppointmentButton;

    @FXML
    private TableView<Appointment> AppointmentTable;

    @FXML
    private TableColumn<Appointment, Integer> AppIDColumn;

    @FXML
    private TableColumn<Appointment, Integer> CustIDColumn;

    @FXML
    private TableColumn<Appointment, String> TitleColumn;

    @FXML
    private TableColumn<Appointment, String> DescriptionColumn;

    @FXML
    private TableColumn<Appointment, String> LocationColumn;

    @FXML
    private TableColumn<Appointment, String> ContactColumn;

    @FXML
    private TableColumn<Appointment, String> TypeColumn;

    @FXML
    private TableColumn<Appointment, String> StartColumn;

    @FXML
    private TableColumn<Appointment, String> EndColumn;

    @FXML
    private RadioButton byWeekRadio;

    @FXML
    private RadioButton byMonthRadio;

    @FXML
    private RadioButton clearviewby;

    @FXML
    private Button DeleteAppointmentButton;

    @FXML
    private Button AddCustomerButton;

    @FXML
    private Button ModifyCustomerButton;

    @FXML
    private TableView<Customer> CustomerTable;

    @FXML
    private TableColumn<Customer, String> NameColumn;

    @FXML
    private TableColumn<Customer, String> AddressColumn;

    @FXML
    private TableColumn<Customer, String> PostalCodeColumn;

    @FXML
    private TableColumn<Customer, String> PhoneColumn;

    @FXML
    private TableColumn<Customer, Integer> DivisionColumn;

    @FXML
    private Button ReportsButton;
    @FXML
    private Button DeleteCustomerButton;

    //helper variables and functions

    ObservableList<Appointment> appointmentViewOL = FXCollections.observableArrayList();
    ObservableList<Customer> customerViewOL = FXCollections.observableArrayList();
    private final ZoneId UTCZoneID = ZoneId.of("UTC");
    private final ZoneId localZoneID = ZoneId.systemDefault();
    private final DateTimeFormatter datetimeDTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    //Appointment modify
    public static Appointment appointmentToMod;
    public static Customer customerToMod;


/** Appointment within 15 min check method. This checks if an appointment is within 15 minutes of login. */
    public static void appointmentCheck15() throws SQLException {
        ZoneId UTCZoneID = ZoneId.of("UTC");
        ZoneId localZoneID = ZoneId.systemDefault();
        DateTimeFormatter datetimeDTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        int userID = User.getUserID();

        System.out.println("Appointment check.");

        LocalDate today = LocalDate.now();
        LocalDateTime todayNow = LocalDateTime.now(localZoneID);
        //UTC time conversion not needed because it will
        ZonedDateTime todayNowUTC = todayNow.atZone(localZoneID).withZoneSameInstant(UTCZoneID);

        try {
            PreparedStatement ps = DBConnection.conn.prepareStatement("SELECT Appointment_ID, Start FROM appointments WHERE User_ID=" + userID + " AND Start BETWEEN ? AND ?");
            //The "setTimestamp" method will change the time to UTC. So a local time needs to passed as a parameter.
            ps.setTimestamp(1, Timestamp.valueOf(todayNow));
            ps.setTimestamp(2, Timestamp.valueOf(todayNow.plusMinutes(15)));


            ResultSet rs = ps.executeQuery();
            System.out.println(ps);
            if (rs.next()) {

                int appointmentID = rs.getInt("Appointment_ID");
                //getTimestamp will automatically convert from UTC to Local
                Timestamp startTime = rs.getTimestamp("Start");
                System.out.println(startTime);
                //Convert to string and parse time out for display purposes
                String convertedTime = startTime.toString().substring(11,19);


                // Switch from UTC to Local
                /*
                LocalDateTime startTimeUTC = LocalDateTime.parse(startTime, datetimeDTF);
                ZonedDateTime startUTCToLocal = startTimeUTC.atZone(UTCZoneID).withZoneSameInstant(localZoneID);
                //convert to string and parse out the time for display purposes.
                */

                //String convertedStartTime = startUTCToLocal.format(datetimeDTF).substring(11, 19);

                Alert within15Min = new Alert(Alert.AlertType.INFORMATION);
                within15Min.setTitle("Upcoming Appointment");
                within15Min.setHeaderText("You have an upcoming appointment. \n Date: " + today + "\n Start Time: " + convertedTime);
                within15Min.showAndWait();

            } else {
                Alert within15Min = new Alert(Alert.AlertType.INFORMATION);
                within15Min.setTitle("Upcoming Appointment");
                within15Min.setHeaderText("You do not have any upcoming appointments.");
                within15Min.showAndWait();

            }
        }
        catch(SQLException e) {
            System.out.println("Something wrong with sql statement on check 15");
            }
        return;
    }

    /** Customer View method. This retrieves customer information and creates the customer table view. */
private void createCustomerView() throws SQLException {
    System.out.println("Setting up table view of customers.");
    try {
        Statement sqlStatement = DBConnection.conn.createStatement();
        ResultSet rs = sqlStatement.executeQuery("SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID FROM customers");

        customerViewOL.clear();

        while (rs.next()) {
            int custID = rs.getInt("Customer_ID");
            String custName = rs.getString("Customer_Name");
            String custAddress = rs.getString("Address");
            String custPostal = rs.getString("Postal_Code");
            String custPhone = rs.getString("Phone");
            int divisionID = rs.getInt("Division_ID");

            //create new customer object to be added to the ol
            Customer customer = new Customer(custID, custName, custAddress, custPostal, custPhone, divisionID);
            customerViewOL.add(customer);
            CustomerTable.setItems(customerViewOL);



        }
    } catch (SQLException e) {
        System.out.println("Customer Table View SQL Query Error.");
    }
}

/** Create appointment view method. This retrieves appointment information and creates the appointment tableview. */
    public void createAppointmentView() throws SQLException{
        System.out.println("Setting up table view of appointments");

        try {
            Statement sqlStatement = DBConnection.conn.createStatement();
            ResultSet rs = sqlStatement.executeQuery("SELECT Appointment_ID, Customer_ID, User_ID, Title, Description, Location, Contact_ID, Type, Start, End FROM appointments");

            appointmentViewOL.clear();

            while (rs.next()) {
                //need to create variables to store rs results
                int appID = rs.getInt("Appointment_ID");
                int custID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactID = rs.getInt("Contact_ID");
                String type = rs.getString("Type");

                // We need to get the times from the data base and then convert them to relevant time.
                String startTime = rs.getString("Start").substring(0, 19);
                String endTime = rs.getString("End").substring(0, 19);

                //format database utc
                LocalDateTime utcStartDT = LocalDateTime.parse(startTime, datetimeDTF);
                LocalDateTime utcEndDT = LocalDateTime.parse(endTime, datetimeDTF);

                //convert times UTC zoneID to local zoneID.The database stored time in a different zone then the local computer.
                ZonedDateTime localStartTime = utcStartDT.atZone(UTCZoneID).withZoneSameInstant(localZoneID);
                ZonedDateTime localEndTime = utcEndDT.atZone(UTCZoneID).withZoneSameInstant(localZoneID);

                //convert localtime to string for table insertion
                String convertedStartTime = localStartTime.format(datetimeDTF);
                String convertedEndTime = localEndTime.format(datetimeDTF);


                Appointment app = new Appointment(title, description, location, type, convertedStartTime, convertedEndTime, custID, userID, appID, contactID);

                //Populate tableview
                appointmentViewOL.add(app);
                AppointmentTable.setItems(appointmentViewOL);
            }
        }catch(SQLException e) {
            System.out.println("Something is wrong");
        }
        if(byWeekRadio.isSelected()){
            viewByWeek(appointmentViewOL);
        }
        if(byMonthRadio.isSelected()){
            viewByMonth(appointmentViewOL);
        }

    }
    /** View by Month method. Filters appointments in current month.
     * @param appointments the list of appointments to be filtered. */
    public void viewByMonth(ObservableList appointments) {
        LocalDate today = LocalDate.now();
        LocalDate oneMonth = today.plusMonths(1);

        FilteredList<Appointment> filteredByMonth = new FilteredList<Appointment>(appointments);

       filteredByMonth.setPredicate(appointment -> {
            LocalDate appointmentDate = LocalDate.parse(appointment.getStartTime(),datetimeDTF);
            return appointmentDate.isAfter(today.minusDays(1)) && appointmentDate.isBefore(oneMonth);
        });
       AppointmentTable.setItems(filteredByMonth);
        }
        /** View by week method. This filters appointments in the current week.
         * @param appointments the list of appointments to be filtered.
         * Lambda Expression. This lambda expression uses the .setPredicate() method for the FilteredList class.
         * It filters through the list of appointments and checks if they are during the current week.
         */
    public void viewByWeek(ObservableList appointments){
        LocalDate today = LocalDate.now();
        LocalDate oneWeek = today.plusWeeks(1);

        FilteredList<Appointment> filteredByWeek = new FilteredList<Appointment>(appointments);
        //Lambda Expression. This lambda expression uses the .setPredicate() method for the FilteredList class.
        //It filters through the list of appointments and checks if they are during the current week.
        filteredByWeek.setPredicate(appointment -> {
            LocalDate appointmentDate = LocalDate.parse(appointment.getStartTime(), datetimeDTF);
            return appointmentDate.isAfter(today.minusDays(1)) && appointmentDate.isBefore(oneWeek);
                }
        );

        AppointmentTable.setItems(filteredByWeek);
    }

    /** Delete Appointment handler. This handles the delete appointment checks. */
    @FXML
    void deleteAppointment(MouseEvent event) throws SQLException {
        Appointment delAppointment = AppointmentTable.getSelectionModel().getSelectedItem();
        if(delAppointment == null) {
            Alert nothingSelected = new Alert(Alert.AlertType.INFORMATION);
            nothingSelected.setTitle("Nothing Selected");
            nothingSelected.setHeaderText("Please select an appointment to be deleted.");
            nothingSelected.showAndWait();
        } else {
            //get the app ID to be used to compare and delete
            int appID = delAppointment.getAppointmentID();
            String appType = delAppointment.getType();

            Alert delAlert = new Alert(Alert.AlertType.CONFIRMATION);
            delAlert.setTitle("Delete Confirmation");
            delAlert.setHeaderText("Are you sure you want to DELETE Appointment ID: " + appID + " ?");
            Optional<ButtonType> outcome = delAlert.showAndWait();

            if (outcome.get() == ButtonType.OK) {
                boolean success = deleteAppointment(appID);
                if (success == true) {
                    Alert deleteSuccess = new Alert(Alert.AlertType.INFORMATION);
                    deleteSuccess.setTitle("Deletion Successful");
                    deleteSuccess.setHeaderText("The " + appType + " Appointment with Appointment ID: " + appID + " has successfully been deleted.");
                    deleteSuccess.showAndWait();

                    createAppointmentView();
                }
            }
        }

    }
    /** Delete Appointment. This handles the delete appointment functionality.
     * @param appointmentID The ID of the appointment to be deleted.
     * @return whether the deletion was successful.
     */
    private boolean deleteAppointment(int appointmentID) {
        boolean success = false;
        try{
            PreparedStatement deleteStatement = DBConnection.conn.prepareStatement("DELETE FROM appointments WHERE Appointment_ID=" + "'" + appointmentID + "'");
            deleteStatement.executeUpdate();
            success = true;
        } catch(SQLException e) {
            System.out.println("SQL Statement incorrect");
        }
        return success;
    }
    /** Delete customer handler. This handles the delete customer checks. */
    @FXML
    void delCustomer(MouseEvent event) throws SQLException {
        Customer delCustomer = CustomerTable.getSelectionModel().getSelectedItem();
        if(delCustomer == null) {
            Alert nothingSelected = new Alert(Alert.AlertType.INFORMATION);
            nothingSelected.setTitle("Nothing Selected");
            nothingSelected.setHeaderText("Please select an customer to be deleted.");
            nothingSelected.showAndWait();
        } else {
            //get the app ID to be used to compare and delete
            int customerID = delCustomer.getCustomerID();
            //System.out.println(customerID);
            //String appType = delCustomer.();

            Alert delAlert = new Alert(Alert.AlertType.CONFIRMATION);
            delAlert.setTitle("Delete Confirmation");
            delAlert.setHeaderText("Are you sure you want to DELETE Customer ID: " + customerID + " ?");
            Optional<ButtonType> outcome = delAlert.showAndWait();

            if (outcome.get() == ButtonType.OK) {
                boolean success = deleteCustomer(customerID);
                if (success == true) {
                    Alert deleteSuccess = new Alert(Alert.AlertType.INFORMATION);
                    deleteSuccess.setTitle("Deletion Successful");
                    deleteSuccess.setHeaderText("The selected customer was deleted successfully.");
                    deleteSuccess.showAndWait();

                    createCustomerView();
                }
            }
        }

    }
    /** Delete customer. This handles the delete customer functionality.
     * @param customerID The ID of the customer to be deleted.
     * @return whether the deletion was successful.
     */
    private boolean deleteCustomer(int customerID) throws SQLException {
        boolean success = false;
        try {
            String sql = "DELETE FROM customers WHERE Customer_ID=" + "'" + customerID + "'";
            //System.out.println(sql);
            PreparedStatement deleteStatement = DBConnection.conn.prepareStatement(sql);
            deleteStatement.executeUpdate();
            success = true;
        }
        catch(SQLIntegrityConstraintViolationException e){
            Alert appCustDelete = new Alert(Alert.AlertType.INFORMATION);
            appCustDelete.setTitle("Error!");
            appCustDelete.setHeaderText("You must delete delete all appointments associated with Customer ID: " + customerID + " before deleting this customer.");
            appCustDelete.showAndWait();
        }
        return success;
    }

    /** Go to Reports handler. This handles the reports button. */
    @FXML
    void geToReports(MouseEvent event) throws IOException {
        Parent reports = FXMLLoader.load(getClass().getResource("/view_controller/Reports.fxml"));
        Scene reportsScene = new Scene(reports);
        Stage reportsStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        reportsStage.setScene((reportsScene));
        reportsStage.show();

    }

    /** Go to add appointment handler. This handles the add appointment button. */
    @FXML
    void goToAddAppointment(MouseEvent event) throws IOException {

        Parent AddAppointment = FXMLLoader.load(getClass().getResource("/view_controller/AddAppointment.fxml"));
        Scene AddAppScene = new Scene(AddAppointment);
        Stage AddAppStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AddAppStage.setScene((AddAppScene));
        AddAppStage.show();

    }

    /** Go to add customer handler. This handles the add customer button. */
    @FXML
    void goToAddCust(MouseEvent event) throws IOException {
        Parent AddCustomer = FXMLLoader.load(getClass().getResource("/view_controller/AddCustomer.fxml"));
        Scene AddCustomerScene = new Scene(AddCustomer);
        Stage AddCustomerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AddCustomerStage.setScene((AddCustomerScene));
        AddCustomerStage.show();

    }

    /** Go to modify customer handler. This handles the modify customer button. */
    @FXML
    void goToModCustomer(MouseEvent event) throws IOException {
        customerToMod = CustomerTable.getSelectionModel().getSelectedItem();

        if(customerToMod == null){
            System.out.println("Nothing selected");
            Alert noneSelected = new Alert(Alert.AlertType.INFORMATION);
            noneSelected.setTitle("Error!");
            noneSelected.setHeaderText("No customer selected\nPlease select an customer.");
            noneSelected.showAndWait();

        }else {
            Parent modifyCustomer = FXMLLoader.load(getClass().getResource("/view_controller/ModifyCustomer.fxml"));
            Scene modCustScene = new Scene(modifyCustomer);
            Stage modCustStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            modCustStage.setScene((modCustScene));
            modCustStage.show();
        }

    }

    /** Go to modify appointment handler. This handles the modify appointment button. */
    @FXML
    void goToModifyAppointment(MouseEvent event) throws IOException {

        appointmentToMod = AppointmentTable.getSelectionModel().getSelectedItem();

        if(appointmentToMod == null){
            System.out.println("Nothing selected");
            Alert noneSelected = new Alert(Alert.AlertType.INFORMATION);
            noneSelected.setTitle("Error!");
            noneSelected.setHeaderText("No appointment selected\nPlease select an appointment.");
            noneSelected.showAndWait();
        } else {
            Parent AddAppointment = FXMLLoader.load(getClass().getResource("/view_controller/ModifyAppointment.fxml"));
            Scene AddAppScene = new Scene(AddAppointment);
            Stage AddAppStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            AddAppStage.setScene((AddAppScene));
            AddAppStage.show();
        }

    }
    /** byMonth radio handler. This handles the month radio selection. */
    @FXML
    void byMonth(MouseEvent event) throws SQLException {
        byMonthRadio.setSelected(true);
        byWeekRadio.setSelected(false);
        clearviewby.setSelected(false);
        createAppointmentView();

    }

    /** byWeek radio handler. This handles the week radio selection. */
    @FXML
    void byWeek(MouseEvent event) throws SQLException {
        byWeekRadio.setSelected(true);
        byMonthRadio.setSelected(false);
        clearviewby.setSelected(false);
        createAppointmentView();

    }

    /** clearByView radio handler. This handles the clear radio selection. */
    @FXML
    void clearByView(MouseEvent event) throws SQLException {
        byWeekRadio.setSelected(false);
        byMonthRadio.setSelected(false);
        clearviewby.setSelected(true);
        createAppointmentView();

    }

}


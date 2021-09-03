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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/** The Add customer contoller class. This stores add customer information and utility. */
public class AddCustomerController implements Initializable {


    @FXML
    private TextField AppIDTextField;

    @FXML
    private TextField PostalField;

    @FXML
    private TextField AddressField;

    @FXML
    private ComboBox<String> CountryCombo;

    @FXML
    private TextField CustomerNameField;

    @FXML
    private TextField PhoneField;

    @FXML
    private ComboBox<String> StateCombo;

    @FXML
    private Label StateProvinceLabel;

    @FXML
    private Button AddCustomerSave;

    @FXML
    private Button AddCustomerCancel;

    @FXML
    private TextField CustomerIDField;

    @FXML
    private TextField City;

    //global variables
    String customerName = "";
    String customerPhone = "";
    String customerAddress = "";
    String customerCity = "";
    String customerAddressCity = "";
    String customerPostalCode = "";
    String customerCountry = "";
    int customerDivision = -1;

    /** The get country method. This retrieves all countrys from database and fills the country combobox.
     * @return A list of countries.
     */
    public static ObservableList getCountryCombo() throws SQLException {
        ObservableList countryOL = FXCollections.observableArrayList();
        String countrySQL = "SELECT Country FROM countries";
        Statement countryStatement = DBConnection.conn.createStatement();
        ResultSet rs = countryStatement.executeQuery(countrySQL);

        while (rs.next()) {
            String country = rs.getString("Country");
            countryOL.add(country);
        }
        //CountryCombo.setItems(countryOL);
        return countryOL;
    }
    /** The country listener. This listens on the country combo and changes state,province, or area depending on selection. */
    private void countryListener() {
        //listener
        CountryCombo.valueProperty().addListener((Observable, oldValue, newValue) -> {
            if(newValue.equals("U.S")){
                try {
                    getStateUS();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else if(newValue.equals("UK")){
                try {
                    getProvinceUK();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else if(newValue.equals("Canada")) {

                try {
                    getProvinceCA();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("No Country Exists");
            }
        });

    }

    /** Get state method. This retrieves from database all US states. */
    private void getStateUS() throws SQLException {
        StateProvinceLabel.setText("State");
        ObservableList stateOL = FXCollections.observableArrayList();
        Statement sqlStatement = DBConnection.conn.createStatement();
        ResultSet rs = sqlStatement.executeQuery("SELECT Division from first_level_divisions where COUNTRY_ID='1'");

        while(rs.next()){
            String state = rs.getString("Division");
            stateOL.add(state);
        }
        StateCombo.setItems(stateOL);
    }
    /** Get Province method. This retrieves from database all UK provinces. */
    private void getProvinceUK() throws SQLException {
        StateProvinceLabel.setText("Area");
        ObservableList provinceUKOL = FXCollections.observableArrayList();
        Statement sqlStatement = DBConnection.conn.createStatement();
        ResultSet rs = sqlStatement.executeQuery("SELECT Division from first_level_divisions where COUNTRY_ID='2'");

        while(rs.next()){
            String provinceUK = rs.getString("Division");
            provinceUKOL.add(provinceUK);
        }
        StateCombo.setItems(provinceUKOL);
    }
    /** Get province canada method. This retrieves from database all canada provinces. */
    private void getProvinceCA() throws SQLException {
        StateProvinceLabel.setText("Province");
        ObservableList provinceCAOL = FXCollections.observableArrayList();
        Statement sqlStatement = DBConnection.conn.createStatement();
        ResultSet rs = sqlStatement.executeQuery("SELECT Division from first_level_divisions where COUNTRY_ID='3'");

        while(rs.next()){
            String provinceCA = rs.getString("Division");
            provinceCAOL.add(provinceCA);
        }
        StateCombo.setItems(provinceCAOL);
    }
    /** Get Division ID method. This retrieves the division ID of customer location.
     * @return The division ID.
     */
    private int getDivisionID() throws SQLException {
        int divisionID = 0;
        try {
            String customerDivision = StateCombo.getValue();
            Statement sqlStatement = DBConnection.conn.createStatement();
            ResultSet rs = sqlStatement.executeQuery("SELECT Division_ID FROM first_level_divisions WHERE Division=" + "'" + customerDivision + "'");

            while (rs.next()) {
                divisionID = rs.getInt("Division_ID");
            }
            return divisionID;

        } catch (NullPointerException e) {
            return -1;
        }
    }
    /** Get Customer Values. This gets all input values and stores them in variables.
     * @return whether or not the method was successful.
     */
private boolean getCustomerValues() throws SQLException {
        boolean success = true;
        try {
            customerName = CustomerNameField.getText();
            customerPhone = PhoneField.getText();
            customerAddress = AddressField.getText();
            customerCity = City.getText();
            customerAddressCity = customerAddress + ", " + customerCity;
            customerPostalCode = PostalField.getText();
            //get the division ID based off country and state.
            customerCountry = CountryCombo.getValue();
            customerDivision = getDivisionID();
            if(customerDivision == -1){
                System.out.println(customerDivision);
                success = false;
            }
        }
        catch(NullPointerException e){
            success = false;
        }

            //Insert the rest in the Prepared statement.
        return success;
}

    /** Cancel Customer handler. This handles the cancel customer button. */
    @FXML
    void CancelNewCustomer(MouseEvent event) throws IOException {
        Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION);
        cancelAlert.setTitle("Cancel New Customer");
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

    /** Empty fields method. This checks if any fields are left empty.
     * @return error tracking integer.
     */
    private int emptyFields(){

            int errorTrack = 0;
            if(customerName.isEmpty()){
                errorTrack = 1;
            }
            else if(customerPhone.isEmpty()){
                errorTrack = 1;
            }
            else if(customerAddress.isEmpty()){
                errorTrack = 1;
            }
            else if(customerCity.isEmpty()){
                errorTrack = 1;
            }
            else if(customerPostalCode.isEmpty()){
                errorTrack = 1;
            }
            else if(CountryCombo.getValue() == null){
                errorTrack = 1;
            }
            else if(StateCombo.getValue() == null){
                errorTrack = 1;
            }
            else{
                System.out.println("No blanks found.");
            }
            return errorTrack;
        }

        /** Save customer button handler. This handles all the saving functionality of the customer. */
    @FXML
    void SaveCustomer(MouseEvent event) throws SQLException {
        //need to combine address with city.
        //need to format phone number correctly.


        boolean values = getCustomerValues();
        int blankFields = emptyFields();


        if (values == true && blankFields == 0) {

            try {
                PreparedStatement customerPS = DBConnection.conn.prepareStatement("INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID)" +
                        "VALUES (?,?,?,?,?,CURRENT_TIMESTAMP,?,CURRENT_TIMESTAMP,?,?)", Statement.RETURN_GENERATED_KEYS);

                customerPS.setInt(1, getCustomerID());
                customerPS.setString(2, customerName);
                customerPS.setString(3, customerAddressCity);
                customerPS.setString(4, customerPostalCode);
                customerPS.setString(5, customerPhone);
                customerPS.setString(6, User.getUsername());
                customerPS.setString(7, User.getUsername());
                customerPS.setInt(8, customerDivision);
                int result = customerPS.executeUpdate();
                ResultSet rs = customerPS.getGeneratedKeys();

                Alert successfulSubmission = new Alert(Alert.AlertType.INFORMATION);
                successfulSubmission.setTitle("Save Successful");
                successfulSubmission.setHeaderText("New Customer Saved Successfully.");
                successfulSubmission.showAndWait();


                Parent mainScreen = FXMLLoader.load(getClass().getResource("/view_controller/MainScreen.fxml"));
                Scene mainScreenScene = new Scene(mainScreen);
                Stage mainScreenStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                mainScreenStage.setScene(mainScreenScene);
                mainScreenStage.show();


            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
            else{
            Alert emptyFields = new Alert(Alert.AlertType.INFORMATION);
            emptyFields.setTitle("Empty Fields");
            emptyFields.setHeaderText("You cannot leave fields empty. \n Please fill in all fields.");
            emptyFields.showAndWait();
            return;
            }
        }
    /** Get customer ID. This generates a new customer ID for new customers.
     * @return new customer ID.
     */
    private int getCustomerID() throws SQLException {
        List<Integer> customerIDList = new ArrayList();
        Statement getCustomerID = DBConnection.conn.createStatement();
        ResultSet rs = getCustomerID.executeQuery("SELECT Customer_ID FROM customers");

        while (rs.next()) {
            int customerID = rs.getInt("Customer_ID");
            customerIDList.add(customerID);
        }
        int greatest = -1;
        for(int i = 0; i < customerIDList.size(); i++) {
            if(customerIDList.get(i) > greatest) {
                greatest = customerIDList.get(i);
            }
        }
        int newCustomerID = greatest + 1;

        return newCustomerID;
    }
/** The customer initialize method. This initializes the add customer controller class. */
   @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        CustomerIDField.setDisable(true);
        try{
            CountryCombo.setItems(getCountryCombo());
            countryListener();
        }
        catch(SQLException e){
            System.out.println("SQL Error");
        }

    }
}

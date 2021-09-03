package view_controller;

import DAO_Utility.DBConnection;
import com.mysql.cj.x.protobuf.MysqlxPrepare;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

/** The modify customer controller class. This stores all modify customer information and utility. */
public class ModifyCustomerController implements Initializable {

    @FXML
    private TextField CustomerIDField;

    @FXML
    private Label StateProvinceLabel;

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
    private TextField City;

    @FXML
    private Button ModCustomerSave;

    @FXML
    private Button ModCustomerCancel;

    private String division = "";
    //variables
    String customerName = "";
    String customerPhone = "";
    String customerAddress = "";
    String customerCity = "";
    String customerAddressCity = "";
    String customerPostalCode = "";
    String customerCountry = "";
    int customerDivision = -1;

    /*
    private void phoneFormat(){
        String phone = "1234567899";
        String newPhone = phone.replaceAll("^(\\d{3})(\\d{3})(\\d{4})$","$1-$2-$3");
        System.out.println(phone);
        System.out.println(newPhone);

    }
     */
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

    /** Cancel Customer handler. This handles the cancel customer button. */
    @FXML
    void CancelModCustomer(MouseEvent event) throws IOException {
        Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION);
        cancelAlert.setTitle("Cancel Modify Customer");
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
        return success;
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

    /** Save customer button handler. This handles all the saving functionality of the customer. */
    @FXML
    void SaveModCustomer(MouseEvent event) throws SQLException {
        boolean values = getCustomerValues();
        int blankFields = emptyFields();


        if (values == true && blankFields == 0) {
            try {
                PreparedStatement updateCustomerPS = DBConnection.conn.prepareStatement("UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Created_By=?, Last_Update=CURRENT_TIMESTAMP, Last_Updated_By=?, Division_ID=? WHERE Customer_ID=?", Statement.RETURN_GENERATED_KEYS);

                updateCustomerPS.setString(1, customerName);
                updateCustomerPS.setString(2, customerAddressCity);
                updateCustomerPS.setString(3, customerPostalCode);
                updateCustomerPS.setString(4, customerPhone);
                updateCustomerPS.setString(5, User.getUsername());
                updateCustomerPS.setString(6, User.getUsername());
                updateCustomerPS.setInt(7, customerDivision);
                updateCustomerPS.setInt(8, Integer.parseInt((CustomerIDField.getText())));
                int result = updateCustomerPS.executeUpdate();
                ResultSet rs = updateCustomerPS.getGeneratedKeys();

                Alert successfulSubmission = new Alert(Alert.AlertType.INFORMATION);
                successfulSubmission.setTitle("Save Successful");
                successfulSubmission.setHeaderText("Customer Modify Successful.");
                successfulSubmission.showAndWait();

                Parent mainScreen = FXMLLoader.load(getClass().getResource("/view_controller/MainScreen.fxml"));
                Scene mainScreenScene = new Scene(mainScreen);
                Stage mainScreenStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                mainScreenStage.setScene(mainScreenScene);
                mainScreenStage.show();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
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
/** Get country and Division method. This retrieves country name and division from two tables in database.
 * @param divisionID division Id used to retrieve information.
 * @return country name.
 */
    private String getCountryAndDivision(int divisionID) throws SQLException{
        String country = "";
        Statement sqlStatement = DBConnection.conn.createStatement();
        ResultSet rs = sqlStatement.executeQuery("select Country, first_level_divisions.Division from countries join first_level_divisions on countries.Country_ID=first_level_divisions.COUNTRY_ID where first_level_divisions.Division_ID=" + "'" + divisionID + "'");

        while(rs.next()){
            country = rs.getString("Country");
            division = rs.getString("Division");
        }
        return country;
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

    /** The customer initialize method. This initializes the modify customer controller class. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CustomerIDField.setText(String.valueOf(MainScreenController.customerToMod.getCustomerID()));
        CustomerIDField.setDisable(true);
        try{
            CountryCombo.setItems(AddCustomerController.getCountryCombo());
            countryListener();
            CountryCombo.setValue(getCountryAndDivision(MainScreenController.customerToMod.getDivisionID()));
            StateCombo.setValue(division);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        CustomerNameField.setText(MainScreenController.customerToMod.getCustomerName());
        AddressField.setText(MainScreenController.customerToMod.getAddress());
        PhoneField.setText(MainScreenController.customerToMod.getPhone());
        //ugly
        if(AddressField.getText().contains(",")){
            String[] Address = AddressField.getText().split(",");
            AddressField.setText(Address[0]);
            City.setText(Address[1]);
        }else{
            City.setText("");
        }
        PostalField.setText(MainScreenController.customerToMod.getPostalCode());
    }
}

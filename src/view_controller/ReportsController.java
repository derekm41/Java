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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Reports;

import javax.xml.transform.Result;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

/** The reports controller class. This stores all reports controller information and utility. */
public class ReportsController implements Initializable {

    @FXML
    private TableView<String> CountByMonthType;

    @FXML
    private TableColumn<String, String> MonthMT;

    @FXML
    private TableColumn<String, String> TypeMT;

    @FXML
    private TableColumn<String, String> CountMT;

    @FXML
    private Button MainMenuButton;

    @FXML
    private TableView<String> ScheduleTableView;

    @FXML
    private TableColumn<String, Integer> AppointmentIDColumn;

    @FXML
    private TableColumn<String, String> TitleColumn;

    @FXML
    private TableColumn<String, String> TypeColumn;

    @FXML
    private TableColumn<String, String> DescriptionColumn;

    @FXML
    private TableColumn<String, String> StartColumn;

    @FXML
    private TableColumn<String, String> EndColumn;

    @FXML
    private TableColumn<String, Integer> CustomerIDColumn;

    @FXML
    private TableView<String> customerByCountryTableView;

    @FXML
    private TableColumn<String, String> countryColumn;

    @FXML
    private TableColumn<String, String> countCustomerColumn;

    /** Go to mainscreen handler. This handles return to main screen functionality. */
    @FXML
    void goToMain(MouseEvent event) throws IOException {
        Alert main = new Alert(Alert.AlertType.CONFIRMATION);
        main.setTitle("Main Menu");
        main.setHeaderText("Are you sure you want to return to the main menu?");
        Optional<ButtonType> outcome = main.showAndWait();

        if(outcome.get() == ButtonType.OK) {
            Parent mainScreen = FXMLLoader.load(getClass().getResource("/view_controller/MainScreen.fxml"));
            Scene mainScene = new Scene(mainScreen);
            Stage mainStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            mainStage.setScene(mainScene);
            mainStage.show();
        }

    }

    /** Get count by type. This retrieves the count of appointment in a month by type report. */
    private void getCountByMT() throws SQLException {
        ObservableList CountBYMTOL = FXCollections.observableArrayList();
        try {
            Statement sqlStatement = DBConnection.conn.createStatement();
            ResultSet rs = sqlStatement.executeQuery("SELECT monthname(Start) as 'Month', Type, count(Type) as 'Count' FROM appointments GROUP BY monthname(Start), Type");

            while (rs.next()) {
                String type = rs.getString("Type");
                String month = rs.getString("Month");
                int count = rs.getInt("Count");


                Reports report = new Reports(month, type, count);
                CountBYMTOL.add(report);
                CountByMonthType.setItems(CountBYMTOL);
            }
        } catch (SQLException e) {
            System.out.println("Sql did not work");
        }

    }

    /** Get Contact Schedule Report method. This retrieves the contact schedule report. */
    private void getContactsSchedule() throws SQLException {
        ObservableList appointmentByContact = FXCollections.observableArrayList();
        Statement sqlStatement = DBConnection.conn.createStatement();
        ResultSet rs = sqlStatement.executeQuery("select contacts.Contact_Name, appointments.Appointment_ID, appointments.Title, appointments.Type, appointments.Description, appointments.Start, appointments.End, appointments.Customer_ID from contacts join appointments on contacts.Contact_ID=appointments.Contact_ID");

        while (rs.next()) {
            String contactName = rs.getString("Contact_Name");
            int appID = rs.getInt("Appointment_ID");
            String type = rs.getString("Type");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            String Start = start.toString();
            String End = end.toString();
            int customerID = rs.getInt("Customer_ID");

            Reports contactReport = new Reports(contactName, appID, type, title, description, Start, End, customerID);
            appointmentByContact.add(contactReport);
            ScheduleTableView.setItems(appointmentByContact);

        }
    }

    /** Get customer by country method. This retrieves the customers for each country. */
private void getCustomerByCountry() throws SQLException {
        ObservableList customerCountry = FXCollections.observableArrayList();
        try {
            Statement sqlStatement = DBConnection.conn.createStatement();
            ResultSet rs = sqlStatement.executeQuery("select count(Country) as count, countries.Country from first_level_divisions join customers on customers.Division_ID=first_level_divisions.Division_ID join countries on countries.Country_ID=first_level_divisions.COUNTRY_ID group by countries.Country");

            while (rs.next()) {
                int countryCount = rs.getInt("count");
                String country = rs.getString("Country");

                Reports report = new Reports(countryCount, country);
                customerCountry.add(report);
                customerByCountryTableView.setItems(customerCountry);
            }
        }
        catch(SQLException e){
            e.printStackTrace();

            }

        }

/** Initialize method. This initializes the reports controller class. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getCountByMT();
            getContactsSchedule();
            getCustomerByCountry();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

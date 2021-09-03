package view_controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import DAO_Utility.DBConnection;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/** The log in controller class. This stores all log in information and utility. */
public class LogInController implements Initializable {


    @FXML
    private Label Location;

    @FXML
    private Button CancelButton;

    @FXML
    private Label UserNameLabel;

    @FXML
    private Label PasswordLabel;

    @FXML
    private TextField UserNameField;

    @FXML
    private TextField PasswordField;

    @FXML
    private Button LogInButton;

    @FXML
    private Label LogInLabel;

    //Global helper variables
    private String LoginIncorrect;
    private String LoginFailedTitle;
    private String emptyField;
    private String locationZone = ZoneId.systemDefault().toString();
    public User user = new User();
    private final DateTimeFormatter datetimeDTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /** The initialize method. This initializes the log in controller class. */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            rb = ResourceBundle.getBundle("properties/login", Locale.getDefault());
            LogInLabel.setText(rb.getString("title"));
            UserNameLabel.setText(rb.getString("username"));
            PasswordLabel.setText(rb.getString("password"));
            LogInButton.setText(rb.getString("signin"));
            CancelButton.setText(rb.getString("cancel"));
            LoginIncorrect = rb.getString("incorrect");
            LoginFailedTitle = rb.getString("loginfailtitle");
            emptyField = rb.getString("empty");
            Location.setText(locationZone);


        } catch (MissingResourceException e) {
            //ignore
        }

    }
/** Cancel log in handler. This handles the cancel log in functionality. */
    @FXML
    void cancelLogIn(MouseEvent event) {
        Alert cancelLogIn = new Alert(Alert.AlertType.CONFIRMATION);
        cancelLogIn.setTitle("Exit Scheduler App.");
        cancelLogIn.setHeaderText("Are you sure you want to exit?");
        Optional<ButtonType> selection = cancelLogIn.showAndWait();

        if (selection.get() == ButtonType.OK) {
            Platform.exit();
        }
    }
/** Submit Log in handler. This handles the submitting of the log in functionality. */
    @FXML
    void submitLogIn(MouseEvent event) throws SQLException, IOException {

        if (UserNameField.getText().isEmpty() || PasswordField.getText().isEmpty()) {
            Alert emptyLoginField = new Alert(Alert.AlertType.INFORMATION);
            emptyLoginField.setTitle(LoginFailedTitle);
            emptyLoginField.setHeaderText(emptyField);
            emptyLoginField.show();
            logger(false);

        } else {
            try {
                int UserIDInput = getUserID(UserNameField.getText());
                String UserName = UserNameField.getText();
                String passwordInput = PasswordField.getText();


                if (checkPassword(UserIDInput, passwordInput)) {
                    //print to terminal for testing
                    System.out.println("Password is correct");
                    user.setUsername(UserName);
                    user.setUserID(UserIDInput);
                    user.setPassword(passwordInput);
                    logger(true);

                    //add the new page loader here.
                    MainScreenController.appointmentCheck15();

                    Parent mainScreen = FXMLLoader.load(getClass().getResource("/view_controller/MainScreen.fxml"));
                    Scene mainScreenScene = new Scene(mainScreen);
                    Stage mainScreenStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    mainScreenStage.setScene(mainScreenScene);
                    mainScreenStage.show();
                } else {
                    logger(false);
                    Alert logInFail = new Alert(Alert.AlertType.INFORMATION);
                    logInFail.setTitle(LoginFailedTitle);
                    logInFail.setHeaderText(LoginIncorrect);
                    logInFail.show();

                }
            } catch (NumberFormatException e) {
                logger(false);
                Alert logInFail = new Alert(Alert.AlertType.INFORMATION);
                logInFail.setTitle(LoginFailedTitle);
                logInFail.setHeaderText(LoginIncorrect);
                logInFail.show();

            }
        }
    }

    /** Check Password method. This checks the password in the database for given user ID.
     * @param password The password submitted.
     * @param userID The user ID submitted.
     * @return whether password was correct or not.
     */
    private boolean checkPassword(int userID, String password) throws SQLException {
        //statement to communicate with DB
        Statement logInStatement = DBConnection.conn.createStatement();

        //SQL statement.
        String sqlStatement = "SELECT Password FROM users WHERE User_ID=" + "'" + userID + "'";

        //ResultSet for database communication
        ResultSet passwordResult = logInStatement.executeQuery(sqlStatement);

        while (passwordResult.next()) {
            if (passwordResult.getString("Password").equals(password)) {
                return true;
            }
        }
        return false;
    }

    /** Get user ID. This gets the user ID from submitted username.
     * @param username The submitted username.
     * @return user ID.
     */
    private int getUserID(String username) throws SQLException {

        int userID = 0;
        //Create a statement object to communicate with the database.
        Statement userStatement = DBConnection.conn.createStatement();

        //write a SQL statement
        //table names are lower case although in the ERD diagram they are all caps?
        String sqlState = "SELECT User_ID FROM users WHERE User_Name=" + "'" + username + "'";

        // create a resultset object to return the information from the database.
        ResultSet logInResult = userStatement.executeQuery(sqlState);

        while (logInResult.next()) {
            userID = logInResult.getInt("User_ID");
        }
        return userID;
    }
/** Logger method. This creates and appends user log in information.
 * @param success whether log in was successful or not. */
    private void logger(boolean success) throws IOException {
        ZoneId zoneid = ZoneId.of("UTC");
        LocalDateTime nowLocalDateTime = LocalDateTime.now(zoneid);
        String formatDateTime = nowLocalDateTime.format(datetimeDTF);
        System.out.println(formatDateTime);
        Timestamp nowTimeStamp = Timestamp.valueOf(formatDateTime);

        FileWriter fileWriter = new FileWriter("login_activity.txt", true);
        PrintWriter logger = new PrintWriter(fileWriter);
        logger.println("User: " + UserNameField.getText() + " " + "Timestamp: " + nowTimeStamp + " Log in success: " + success);
        logger.close();
    }
}




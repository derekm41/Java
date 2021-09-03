package model;

import DAO_Utility.DBConnection;
import javafx.fxml.FXMLLoader;

import java.sql.Connection;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** The main class. This starts the application. */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view_controller/LogInScreen.fxml"));
        Scene primaryScene = new Scene(root);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }
    /** The main method. This starts the application and connects to the database.
     * @param args The args parameter.
     */
    public static void main(String[] args){
        //Create a connection with the Database.
        Connection conn = DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }
}
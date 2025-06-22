/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Session.SessionManager;
import javafx.scene.text.Text;
import Database.DatabaseManager;

/**
 * FXML Controller class
 *
 * @author Bosilu
 */
public class DashboardController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Text welcomeText;
    @FXML
    private Text totalReportsText;

    @FXML
    private Text plasticWasteText;

    @FXML
    private Text oilSpillText;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String username = SessionManager.getUsername();
        welcomeText.setText("Welcome, " + username + "!");

        // Get real-time stats from database
        int total = DatabaseManager.getReportCount();
        int plastic = DatabaseManager.getPollutionTypeCount("Plastic Waste");
        int oil = DatabaseManager.getPollutionTypeCount("Oil Spill");

        // Update dashboard cards
        totalReportsText.setText(String.valueOf(total));
        plasticWasteText.setText(String.valueOf(plastic));
        oilSpillText.setText(String.valueOf(oil));
}
    

    @FXML
    private AnchorPane contentPane; // Injected from FXML

    public void switchContent(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();
            contentPane.getChildren().setAll(view); // Replace center pane
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    @FXML
    private void Dashboard(ActionEvent event) {

    }

    @FXML
    private void reportPollution(ActionEvent event) {
    try {
        // Load ReportPollution.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ReportPollution.fxml"));
        Parent reportRoot = loader.load();

        // Get current stage (which is already in fullscreen)
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set new scene
        Scene scene = new Scene(reportRoot);
        stage.setScene(scene);
        stage.setTitle("Marine Pollution Monitor - Report Pollution");

        // Re-enable fullscreen (since setScene() resets it)
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show(); // Refresh the stage
        stage.setFullScreenExitHint("");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    @FXML
    private void viewReports(ActionEvent event) {
        try {
        // Load ReportPollution.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ViewReports.fxml"));
        Parent reportRoot = loader.load();

        // Get current stage (which is already in fullscreen)
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set new scene
        Scene scene = new Scene(reportRoot);
        stage.setScene(scene);
        stage.setTitle("Marine Pollution Monitor - View Reports");

        // Re-enable fullscreen (since setScene() resets it)
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show(); // Refresh the stage
        stage.setFullScreenExitHint("");
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void showMap(ActionEvent event) {
        try {
        // Load ReportPollution.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ShowMap.fxml"));
        Parent reportRoot = loader.load();

        // Get current stage (which is already in fullscreen)
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set new scene
        Scene scene = new Scene(reportRoot);
        stage.setScene(scene);
        stage.setTitle("Marine Pollution Monitor - Map");

        // Re-enable fullscreen (since setScene() resets it)
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show(); // Refresh the stage
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void showStatistics(ActionEvent event) {
        try {
        // Load ReportPollution.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Statistics.fxml"));
        Parent reportRoot = loader.load();

        // Get current stage (which is already in fullscreen)
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set new scene
        Scene scene = new Scene(reportRoot);
        stage.setScene(scene);
        stage.setTitle("Marine Pollution Monitor - Statistics");

        // Re-enable fullscreen (since setScene() resets it)
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show(); // Refresh the stage
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    
    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private void Logout(ActionEvent event) {
        try {
            // Load Signup.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Login.fxml"));
            Parent signupRoot = loader.load();

            // Get current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set new scene
            Scene scene = new Scene(signupRoot);
            stage.setScene(scene);
            stage.setTitle("Login - Marine Pollution Monitor");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}

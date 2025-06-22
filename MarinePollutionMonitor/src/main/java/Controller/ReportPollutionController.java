/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Database.DatabaseManager;
import Session.SuccessDialog;
import Swing.MapGetCoordinates;
import com.sun.net.httpserver.HttpServer;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;


/**
 * FXML Controller class
 *
 * @author Bosilu
 */
public class ReportPollutionController implements Initializable {

    @FXML
    private AnchorPane contentPane;
    @FXML
    private ComboBox<String> pollutionTypeComboBox;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private DatePicker incidentDatePicker;
    @FXML
    private TextField locationField;
    @FXML
    private AnchorPane mapContainer;
    
    private String selectedLocation = "";
    private static HttpServer server;
    private MapGetCoordinates panel;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pollutionTypeComboBox.getItems().addAll("Oil Spill", "Plastic Waste", "Sewage Dumping", "Chemical Leak", "Other");
        pollutionTypeComboBox.setValue("Oil Spill");
        locationField.setEditable(false);

        // Embed Swing map
        SwingNode swingNode = new SwingNode();
        createAndSetSwingContent(swingNode);
        mapContainer.getChildren().add(swingNode);
        AnchorPane.setTopAnchor(swingNode, 0.0);
        AnchorPane.setBottomAnchor(swingNode, 0.0);
        AnchorPane.setLeftAnchor(swingNode, 0.0);
        AnchorPane.setRightAnchor(swingNode, 0.0);
    }

    private void createAndSetSwingContent(SwingNode swingNode) {
        SwingUtilities.invokeLater(() -> {
            panel = new MapGetCoordinates();
            panel.setCoordinateClickListener((lat, lon) -> {
                selectedLocation = lat + "," + lon;
                Platform.runLater(() -> locationField.setText(selectedLocation));
            });
            swingNode.setContent(panel);
        });
    }


    /**
     *
     */

    @FXML
    private void Dashboard(ActionEvent event) {
    try {
        // Load ReportPollution.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Dashboard.fxml"));
        Parent reportRoot = loader.load();

        // Get current stage (which is already in fullscreen)
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set new scene
        Scene scene = new Scene(reportRoot);
        stage.setScene(scene);
        stage.setTitle("Marine Pollution Monitor - Dashboard");

        // Re-enable fullscreen (since setScene() resets it)
        stage.setFullScreen(true);
        stage.show(); // Refresh the stage
        stage.setFullScreenExitHint("");
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void reportPollution(ActionEvent event) {
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
    private void submitBtn(ActionEvent event) {
        String type = pollutionTypeComboBox.getValue();
        String date = incidentDatePicker.getValue() != null ? incidentDatePicker.getValue().toString() : null;
        String location = locationField.getText();
        String description = descriptionArea.getText();

        if (type == null || date == null || location.isEmpty() || description.isEmpty()) {
            System.out.println("Please fill in all fields.");
            return;
        }

        String query = "INSERT INTO pollution_reports (type, date, location, description) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, type);
            stmt.setString(2, date);
            stmt.setString(3, selectedLocation);;
            stmt.setString(4, description);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Pollution report submitted successfully!");

                SuccessDialog.showSuccessPopup((Stage) contentPane.getScene().getWindow(), () -> {
                    // Optional: navigate to dashboard or update view here
                    System.out.println("Back to Dashboard...");
                });
                // Clear fields after successful submission
                pollutionTypeComboBox.setValue("Oil Spill");
                incidentDatePicker.setValue(null);
                locationField.clear();
                descriptionArea.clear();
                panel.clearMarkers();

            } else {
                System.out.println("Failed to submit report.");
            }

        } catch (SQLException e) {
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

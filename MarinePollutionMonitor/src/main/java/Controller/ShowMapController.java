/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Database.DatabaseManager;
import Swing.FancyWaypointRenderer;
import Swing.MapSwingPanel;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;


/**
 * FXML Controller class
 *
 * @author Bosilu
 */
public class ShowMapController implements Initializable {



    /**
     * Initializes the controller class.
     */

    @FXML
    private AnchorPane mapContainer;
    private MapSwingPanel swingMapPanel;
    private SwingNode swingNode;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        swingNode = new SwingNode();
        swingMapPanel = new MapSwingPanel();
        swingMapPanel.setForceDefaultZoom(true); // ✅ Force default zoom level


        SwingUtilities.invokeLater(() -> {
            swingNode.setContent(swingMapPanel);
            loadPollutionMarkers();
            swingMapPanel.zoomToFitAllMarkers();
        });

        mapContainer.getChildren().add(swingNode);
        AnchorPane.setTopAnchor(swingNode, 0.0);
        AnchorPane.setBottomAnchor(swingNode, 0.0);
        AnchorPane.setLeftAnchor(swingNode, 0.0);
        AnchorPane.setRightAnchor(swingNode, 0.0);
    }

    private void loadPollutionMarkers() {
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "SELECT type, date, location, description FROM pollution_reports";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            Set<Waypoint> waypoints = new HashSet<>();

            while (rs.next()) {
                String location = rs.getString("location");

                if (location != null && location.contains(",")) {
                    String[] parts = location.split(",");
                    if (parts.length == 2) {
                        try {
                            double lat = Double.parseDouble(parts[0].trim());
                            double lng = Double.parseDouble(parts[1].trim());
                            GeoPosition position = new GeoPosition(lat, lng);
                            waypoints.add(new DefaultWaypoint(position));
                            swingMapPanel.addPosition(position); // Add position for zoom calculation
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid coordinates in location: " + location);
                        }
                    }
                } else {
                    System.err.println("Malformed or missing location: " + location);
                }
            }

            SwingUtilities.invokeLater(() -> {
                WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
                waypointPainter.setWaypoints(waypoints);
                waypointPainter.setRenderer(new FancyWaypointRenderer()); // Use the fancy renderer
                swingMapPanel.addWaypointPainter(waypointPainter);
                swingMapPanel.zoomToFitAllMarkers(); // Zoom to show all markers
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Escape strings for safe JS injection
    private String escapeJS(String input) {
        return input.replace("'", "\\'").replace("\"", "\\\"");
    }
    

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

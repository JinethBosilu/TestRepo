/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Database.DatabaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


/**
 * FXML Controller class
 *
 * @author Bosilu
 */
public class StatisticsController implements Initializable {

    @FXML
    private AnchorPane contentPane;
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private PieChart pieChart;

    /**
     * Initializes the controller class.
     */
    @Override
public void initialize(URL url, ResourceBundle rb) {
    // --- Configure Bar Chart ---
    barChart.setLegendVisible(false);
    barChart.setAnimated(false);
    barChart.getData().clear(); // Clear any previous data

    NumberAxis yAxis = (NumberAxis) barChart.getYAxis();
    yAxis.setTickUnit(10);
    yAxis.setMinorTickVisible(false);
    yAxis.setAutoRanging(true); // Adjust dynamically based on DB data

    XYChart.Series<String, Number> barSeries = new XYChart.Series<>();

    // --- Configure Pie Chart ---
    pieChart.setLegendVisible(true);
    pieChart.setLabelsVisible(true);
    pieChart.getData().clear(); // Clear any previous data

    // --- Load Data from Database ---
    String query = "SELECT type, COUNT(*) AS total FROM pollution_reports GROUP BY type";

    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            String rawType = rs.getString("type");
            int count = rs.getInt("total");

            // For bar chart, insert a line break for better label display
            String wrappedType = rawType.replace(" ", "\n");

            // Add to Bar Chart
            barSeries.getData().add(new XYChart.Data<>(wrappedType, count));

            // Add to Pie Chart
            pieChart.getData().add(new PieChart.Data(rawType, count));
        }

        // Attach bar series to chart after loading all data
        barChart.getData().add(barSeries);

    } catch (SQLException e) {
        e.printStackTrace();
    }
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

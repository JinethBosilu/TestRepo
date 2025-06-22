package Controller;

import Database.DatabaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.PollutionReport;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewReportController implements Initializable {

    @FXML
    private TableView<PollutionReport> reportTable;

    @FXML
    private TableColumn<PollutionReport, String> pollutionColumn;

    @FXML
    private TableColumn<PollutionReport, String> dateColumn;

    @FXML
    private TableColumn<PollutionReport, String> locationColumn;

    @FXML
    private TableColumn<PollutionReport, String> descriptionColumn;
    
    private void loadReportsFromDatabase() {
    String query = "SELECT type, date, location, description FROM pollution_reports";

    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        reportTable.getItems().clear();  // Clear existing items

        while (rs.next()) {
            String type = rs.getString("type");
            String date = rs.getString("date");
            String location = rs.getString("location");
            String description = rs.getString("description");

            PollutionReport report = new PollutionReport(type, date, location, description);
            reportTable.getItems().add(report);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}


@Override
public void initialize(URL url, ResourceBundle rb) {
    pollutionColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

    loadReportsFromDatabase();  // 🚀 Load real DB data
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
        // Handle navigation
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
        stage.setFullScreenExitHint("");
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
        stage.setFullScreenExitHint("");
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

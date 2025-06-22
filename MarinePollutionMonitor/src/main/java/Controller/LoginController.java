/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Database.DatabaseManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import Session.SessionManager;

/**
 * FXML Controller class
 *
 * @author Bosilu
 */
public class LoginController implements Initializable {

    @FXML
    private TextField uname;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginButton;
    @FXML
    private ImageView arrowImage;
    @FXML
    private Button signUpButton;
    @FXML 
    private Text usernameLabel;
    
    @FXML 
    private Text passwordLabel;
    
    @FXML
    private Text errorMessage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    uname.textProperty().addListener((obs, oldText, newText) -> {
        if (!newText.isEmpty()) {
            usernameLabel.getStyleClass().add("floating-active-label");
        } else {
            usernameLabel.getStyleClass().remove("floating-active-label");
        }
    });

    uname.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
        if (isNowFocused || !uname.getText().isEmpty()) {
            if (!usernameLabel.getStyleClass().contains("floating-active-label")) {
                usernameLabel.getStyleClass().add("floating-active-label");
            }
        } else {
            usernameLabel.getStyleClass().remove("floating-active-label");
        }
    });
    
    password.textProperty().addListener((obs, oldText, newText) -> {
        if (!newText.isEmpty()) {
            passwordLabel.getStyleClass().add("floating-active-label");
        } else {
            passwordLabel.getStyleClass().remove("floating-active-label");
        }
    });

    password.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
        if (isNowFocused || !password.getText().isEmpty()) {
            if (!passwordLabel.getStyleClass().contains("floating-active-label")) {
                passwordLabel.getStyleClass().add("floating-active-label");
            }
        } else {
            passwordLabel.getStyleClass().remove("floating-active-label");
        }
    });
        
    // Request focus on the button once the UI is fully loaded
    Platform.runLater(() -> {
        if (loginButton != null) {
            loginButton.requestFocus();
        }
    });
    
    uname.textProperty().addListener((obs, oldText, newText) -> {
        errorMessage.setVisible(false);
    });

    password.textProperty().addListener((obs, oldText, newText) -> {
        errorMessage.setVisible(false);
    });
    
}    



@FXML
private void Login(ActionEvent event) {
    String username = uname.getText();
    String pwd = password.getText();

    if (username.isEmpty() || pwd.isEmpty()) {
        errorMessage.setText("Please enter username and password.");
        errorMessage.setVisible(true);
        return;
    }

    String query = "SELECT * FROM users WHERE username = ? AND password = ?";

    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setString(1, username);
        stmt.setString(2, pwd);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            // Login success
            errorMessage.setVisible(false);
            
            SessionManager.setUsername(username);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Dashboard.fxml"));
            Parent root = loader.load();

            Stage mainStage = new Stage();
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.setTitle("Marine Pollution Monitor - Dashboard");
            mainStage.setFullScreen(true);
            mainStage.setFullScreenExitHint("");
            mainStage.show();

            // Close login stage
            Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            loginStage.close();
        } else {
            errorMessage.setText("Incorrect username or password.");
            errorMessage.setVisible(true);
        }

    } catch (SQLException | IOException e) {
        e.printStackTrace();
        errorMessage.setText("Database error. Try again later.");
        errorMessage.setVisible(true);
    }
}



    @FXML
   
    private void CreateAcc(ActionEvent event) {
        try {
            // Load Signup.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Signup.fxml"));
            Parent signupRoot = loader.load();

            // Get current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set new scene
            Scene scene = new Scene(signupRoot);
            stage.setScene(scene);
            stage.setTitle("Sign Up - Marine Pollution Monitor");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
}

    
}

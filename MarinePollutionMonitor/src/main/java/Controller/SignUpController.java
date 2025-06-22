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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.Connection;




/**
 * FXML Controller class
 *
 * @author Bosilu
 */
public class SignUpController implements Initializable {

    @FXML
    private PasswordField password;
    @FXML
    private TextField fname;
    @FXML
    private TextField lname;
    @FXML
    private TextField uname;
    @FXML
    private TextField gmail;
    @FXML
    private Button SignUpButton;
    @FXML
    private Button SwitchLogin;
    @FXML
    private Text fNameLabel;
    @FXML
    private Text lNameLabel;
    @FXML 
    private Text usernameLabel;
    @FXML
    private Text gmailLabel;
    @FXML 
    private Text passwordLabel;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    fname.textProperty().addListener((obs, oldText, newText) -> {
        if (!newText.isEmpty()) {
            fNameLabel.getStyleClass().add("floating-active-label");
        } else {
            fNameLabel.getStyleClass().remove("floating-active-label");
        }
    });

    fname.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
        if (isNowFocused || !fname.getText().isEmpty()) {
            if (!fNameLabel.getStyleClass().contains("floating-active-label")) {
                fNameLabel.getStyleClass().add("floating-active-label");
            }
        } else {
            fNameLabel.getStyleClass().remove("floating-active-label");
        }
    });
    
    lname.textProperty().addListener((obs, oldText, newText) -> {
    if (!newText.isEmpty()) {
        lNameLabel.getStyleClass().add("floating-active-label");
    } else {
        lNameLabel.getStyleClass().remove("floating-active-label");
    }
});

lname.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
    if (isNowFocused || !lname.getText().isEmpty()) {
        if (!lNameLabel.getStyleClass().contains("floating-active-label")) {
            lNameLabel.getStyleClass().add("floating-active-label");
        }
    } else {
        lNameLabel.getStyleClass().remove("floating-active-label");
    }
});
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

gmail.textProperty().addListener((obs, oldText, newText) -> {
    if (!newText.isEmpty()) {
        gmailLabel.getStyleClass().add("floating-active-label");
    } else {
        gmailLabel.getStyleClass().remove("floating-active-label");
    }
});

gmail.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
    if (isNowFocused || !gmail.getText().isEmpty()) {
        if (!gmailLabel.getStyleClass().contains("floating-active-label")) {
            gmailLabel.getStyleClass().add("floating-active-label");
        }
    } else {
        gmailLabel.getStyleClass().remove("floating-active-label");
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
        if (SignUpButton != null) {
            SignUpButton.requestFocus();
        }
    });
}   

    @FXML
    private void SignUp(ActionEvent event) {
    String firstName = fname.getText();
    String lastName = lname.getText();
    String username = uname.getText();
    String email = gmail.getText();
    String pass = password.getText();

    if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || email.isEmpty() || pass.isEmpty()) {
        System.out.println("Please fill in all fields.");
        return;
    }

    String sql = "INSERT INTO users (first_name, last_name, username, gmail, password) VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = DatabaseManager.getConnection();
         java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, firstName);
        pstmt.setString(2, lastName);
        pstmt.setString(3, username);
        pstmt.setString(4, email);
        pstmt.setString(5, pass);  // NOTE: hash this in production

        int rowsInserted = pstmt.executeUpdate();

        if (rowsInserted > 0) {
            System.out.println("Signup successful!");
            SwitchLogin(event);  // Go to login page after success
        } else {
            System.out.println("Signup failed.");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    @FXML
    private void SwitchLogin(ActionEvent event) {
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

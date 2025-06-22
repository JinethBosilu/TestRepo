package Session;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;

public class SuccessDialog {

    public static void showSuccessPopup(Stage ownerStage, Runnable onClose) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(ownerStage);
        dialog.initStyle(StageStyle.TRANSPARENT);

        // Fullscreen semi-transparent background
        StackPane background = new StackPane();
        background.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);");
        background.prefWidthProperty().bind(ownerStage.widthProperty());
        background.prefHeightProperty().bind(ownerStage.heightProperty());


        // Inner dialog box (smaller, centered)
        VBox dialogBox = new VBox(20);
        dialogBox.setAlignment(Pos.CENTER);
        dialogBox.setPadding(new Insets(30));
        dialogBox.setMaxWidth(400); // restrict width
        dialogBox.setMaxHeight(300);
        dialogBox.setStyle("-fx-background-color: #d1f5f0; -fx-background-radius: 20;");
        dialogBox.setEffect(new DropShadow(20, Color.gray(0.3)));

        // ✅ Use image instead of checkmark
        ImageView checkmarkImg = new ImageView(new Image(SuccessDialog.class.getResourceAsStream("/Images/check.png")));
        checkmarkImg.setFitWidth(64);
        checkmarkImg.setFitHeight(64);

        Label message = new Label("Report Submitted Successfully!");
        message.setStyle("-fx-font-size: 18px; -fx-text-fill: #004d40; -fx-font-weight: bold;");

        Button closeButton = new Button("Return to Report Menu");
        closeButton.setCursor(javafx.scene.Cursor.HAND);
        closeButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #2ecc71, #27ae60);" +
                        "-fx-text-fill: White;" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10 20;" +
                        "-fx-background-radius: 10;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 3, 0, 0, 2);" +
                        "-fx-cursor: hand;"
        );

// Hover effect
        closeButton.setOnMouseEntered(e -> {
            closeButton.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #2ecc71, #27ae60);" +
                            "-fx-text-fill: White;" +
                            "-fx-font-size: 16px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-padding: 10 20;" +
                            "-fx-background-radius: 10;" +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 5, 0, 0, 4);" +
                            "-fx-scale-x: 1.03; -fx-scale-y: 1.03;" +
                            "-fx-cursor: hand;"
            );
        });

// Reset on mouse exit
        closeButton.setOnMouseExited(e -> {
            closeButton.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #2ecc71, #27ae60);" +
                            "-fx-text-fill: White;" +
                            "-fx-font-size: 16px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-padding: 10 20;" +
                            "-fx-background-radius: 10;" +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 3, 0, 0, 2);" +
                            "-fx-cursor: hand;"
            );
        });




        closeButton.setOnAction(e -> {
            dialog.close();
            if (onClose != null) onClose.run();
        });

        dialogBox.getChildren().addAll(checkmarkImg, message, closeButton);
        background.getChildren().add(dialogBox); // centered by StackPane

        Scene scene = new Scene(background);
        scene.setFill(Color.TRANSPARENT);

        dialog.setScene(scene);
        dialog.setX(ownerStage.getX());
        dialog.setY(ownerStage.getY());

        dialog.showAndWait();
    }
}

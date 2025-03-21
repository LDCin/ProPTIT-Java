package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.QuickCertificate;

public class QuickCertificateController {

    @FXML private TextField recipientField;
    @FXML private TextField awardField;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleGenerate() {
        String recipientName = recipientField.getText();
        String awardName = awardField.getText();

        if (!recipientName.isEmpty() && !awardName.isEmpty()) {
            QuickCertificate cert = new QuickCertificate(recipientName, awardName);
            cert.setFont(mainController.getCurrentFont());
            mainController.setCertificate(cert);
            closeStage();
        } else {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin!");
        }
    }

    private void closeStage() {
        Stage stage = (Stage) recipientField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.CustomCertificate;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomCertificateController implements Initializable {

    @FXML private TextField recipientNameField;
    @FXML private TextField awardNameField;
    @FXML private TextField ownerNameField;
    @FXML private ColorPicker frameColorPicker;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void handleGenerate() {
        if (recipientNameField == null || awardNameField == null || ownerNameField == null ||
                frameColorPicker == null) {
            showAlert("Lỗi", "Một hoặc nhiều thành phần giao diện chưa được khởi tạo!");
            return;
        }

        String recipientName = recipientNameField.getText().trim();
        String awardName = awardNameField.getText().trim();
        String ownerName = ownerNameField.getText().trim();
        javafx.scene.paint.Color fxColor = frameColorPicker.getValue();

        if (recipientName.isEmpty() || awardName.isEmpty() || ownerName.isEmpty() || fxColor == null) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin và chọn màu!");
            return;
        }

        java.awt.Color awtColor = new java.awt.Color(
                (float) fxColor.getRed(),
                (float) fxColor.getGreen(),
                (float) fxColor.getBlue(),
                (float) fxColor.getOpacity()
        );

        model.Frame frame = new model.Frame("Solid", String.format("0x%06X", awtColor.getRGB() & 0x00FFFFFF));

        if (mainController == null) {
            showAlert("Lỗi", "MainController chưa được khởi tạo!");
            return;
        }

        try {
            // Tạo đối tượng CustomCertificate
            CustomCertificate cert = new CustomCertificate(recipientName, awardName, ownerName, frame);
            cert.setFont(mainController.getCurrentFont());
            mainController.setCertificate(cert);
            closeStage();
        } catch (Exception ex) {
            showAlert("Lỗi", "Đã xảy ra lỗi khi tạo bằng khen: " + ex.getMessage());
            ex.printStackTrace(System.out);
        }
    }

    private void closeStage() {
        if (recipientNameField != null && recipientNameField.getScene() != null && recipientNameField.getScene().getWindow() != null) {
            Stage stage = (Stage) recipientNameField.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("Không thể đóng cửa sổ: Thành phần giao diện hoặc Scene không hợp lệ!");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
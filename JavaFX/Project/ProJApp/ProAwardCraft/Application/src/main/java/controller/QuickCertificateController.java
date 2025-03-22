package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.QuickCertificate;

public class QuickCertificateController {

    @FXML private TextField recipientField;
    @FXML private TextField awardField;
    @FXML private ColorPicker frameColorPicker;

    private MainController mainController;

    @FXML
    public void initialize() {
        // Khởi tạo giá trị mặc định cho ColorPicker
        if (frameColorPicker != null) {
            frameColorPicker.setValue(Color.BLACK);
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleGenerate() {
        if (recipientField == null || awardField == null) {
            showAlert("Lỗi", "Một hoặc nhiều thành phần giao diện chưa được khởi tạo!");
            return;
        }

        String recipientName = recipientField.getText().trim();
        String awardName = awardField.getText().trim();

        if (recipientName.isEmpty() || awardName.isEmpty()) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin và chọn màu!");
            return;
        }

        try {
            // Tạo QuickCertificate
            QuickCertificate cert = new QuickCertificate(recipientName, awardName);

            // Cập nhật các thành phần văn bản trong textComponents
            cert.setTextComponent("recipient", recipientName);
            cert.setTextComponent("award", "Giải thưởng: " + awardName);

            // Đặt chứng chỉ vào MainController và đóng cửa sổ
            mainController.setCertificate(cert);
            closeStage();
        } catch (Exception ex) {
            showAlert("Lỗi", "Đã xảy ra lỗi khi tạo bằng khen: " + ex.getMessage());
            ex.printStackTrace(System.out);
        }
    }

    private void closeStage() {
        if (recipientField != null && recipientField.getScene() != null && recipientField.getScene().getWindow() != null) {
            Stage stage = (Stage) recipientField.getScene().getWindow();
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
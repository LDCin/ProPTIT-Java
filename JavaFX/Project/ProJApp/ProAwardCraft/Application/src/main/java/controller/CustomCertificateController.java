package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.CustomCertificate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomCertificateController implements Initializable {

    @FXML private TextField recipientNameField;
    @FXML private TextField awardNameField;
    @FXML private TextField ownerNameField;
    @FXML private ColorPicker frameColorPicker;
    @FXML private ColorPicker backgroundColorPicker; // Thêm ColorPicker cho màu nền
    @FXML private Label logoPathLabel; // Nhãn hiển thị tên file logo
    private String logoPath; // Biến lưu đường dẫn logo

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (frameColorPicker != null) {
            frameColorPicker.setValue(Color.BLACK);
        }
        if (backgroundColorPicker != null) {
            backgroundColorPicker.setValue(Color.WHITE); // Mặc định là màu trắng
        }
    }

    @FXML
    private void handleChooseLogo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn file logo");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(recipientNameField.getScene().getWindow());
        if (selectedFile != null) {
            logoPath = selectedFile.getAbsolutePath();
            logoPathLabel.setText(selectedFile.getName());
        }
    }

    @FXML
    private void handleGenerate() {
        if (recipientNameField == null || awardNameField == null || ownerNameField == null ||
                frameColorPicker == null || backgroundColorPicker == null) {
            showAlert("Lỗi", "Một hoặc nhiều thành phần giao diện chưa được khởi tạo!");
            return;
        }

        String recipientName = recipientNameField.getText().trim();
        String awardName = awardNameField.getText().trim();
        String ownerName = ownerNameField.getText().trim();
        Color fxFrameColor = frameColorPicker.getValue();
        Color fxBackgroundColor = backgroundColorPicker.getValue();

        if (recipientName.isEmpty() || awardName.isEmpty() || ownerName.isEmpty() || fxFrameColor == null || fxBackgroundColor == null) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin và chọn màu!");
            return;
        }

        String frameColorHex = String.format("#%02X%02X%02X",
                (int) (fxFrameColor.getRed() * 255),
                (int) (fxFrameColor.getGreen() * 255),
                (int) (fxFrameColor.getBlue() * 255));
        String backgroundColorHex = String.format("#%02X%02X%02X",
                (int) (fxBackgroundColor.getRed() * 255),
                (int) (fxBackgroundColor.getGreen() * 255),
                (int) (fxBackgroundColor.getBlue() * 255));

        if (mainController == null) {
            showAlert("Lỗi", "MainController chưa được khởi tạo!");
            return;
        }

        try {
            CustomCertificate cert = new CustomCertificate(recipientName, awardName, ownerName, frameColorHex, backgroundColorHex);
            cert.setTextComponent("recipient", recipientName);
            cert.setTextComponent("award", "Giải thưởng: " + awardName);
            cert.setTextComponent("owner", ownerName);
            if (logoPath != null) {
                cert.setLogoPath(logoPath);
                // Tính toán kích thước logo
                BufferedImage logoImage = ImageIO.read(new File(logoPath));
                double maxWidth = 480;
                double maxHeight = 280;
                double widthRatio = maxWidth / logoImage.getWidth();
                double heightRatio = maxHeight / logoImage.getHeight();
                double scale = Math.min(widthRatio, heightRatio);
                cert.setLogoWidth(logoImage.getWidth() * scale);
                cert.setLogoHeight(logoImage.getHeight() * scale);
            }
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
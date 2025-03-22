package controller.certificate;

import controller.main.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.certificate.QuickCertificate;
import model.template.Template;
import model.template.TemplateFactory;

import java.util.List;

public class QuickCertificateController {

    @FXML private TextField recipientField;
    @FXML private TextField awardField;
    @FXML private ComboBox<Template> templateComboBox;
    @FXML private ImageView previewImageView;

    private MainController mainController;
    private List<Template> templates;

    @FXML
    public void initialize() {
        // Khởi tạo danh sách Template
        templates = TemplateFactory.getTemplates();
        templateComboBox.getItems().addAll(templates);

        // Thiết lập renderer cho ComboBox để hiển thị tên Template
        templateComboBox.setCellFactory(param -> new javafx.scene.control.ListCell<Template>() {
            @Override
            protected void updateItem(Template item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });

        templateComboBox.setButtonCell(new javafx.scene.control.ListCell<Template>() {
            @Override
            protected void updateItem(Template item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });

        // Mặc định chọn Template đầu tiên
        if (!templates.isEmpty()) {
            templateComboBox.setValue(templates.get(0));
            updatePreviewImage(templates.get(0));
        }

        // Cập nhật hình ảnh Preview khi người dùng chọn Template
        templateComboBox.setOnAction(event -> {
            Template selectedTemplate = templateComboBox.getValue();
            if (selectedTemplate != null) {
                updatePreviewImage(selectedTemplate);
            }
        });
    }

    private void updatePreviewImage(Template template) {
        try {
            Image previewImage = new Image(getClass().getClassLoader().getResource(template.getPreviewImagePath()).toExternalForm());
            previewImageView.setImage(previewImage);
        } catch (Exception e) {
            System.err.println("Không thể tải hình ảnh Preview: " + e.getMessage());
            previewImageView.setImage(null);
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleGenerate() {
        if (recipientField == null || awardField == null || templateComboBox == null) {
            showAlert("Lỗi", "Một hoặc nhiều thành phần giao diện chưa được khởi tạo!");
            return;
        }

        String recipientName = recipientField.getText().trim();
        String awardName = awardField.getText().trim();
        Template selectedTemplate = templateComboBox.getValue();

        if (recipientName.isEmpty() || awardName.isEmpty() || selectedTemplate == null) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin và chọn Template!");
            return;
        }

        try {
            // Tạo QuickCertificate với Template được chọn
            QuickCertificate cert = new QuickCertificate(recipientName, awardName, selectedTemplate);

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
package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.TextComponent;
import java.util.Arrays;

public class CustomizeFontController {

    @FXML private ComboBox<String> textComponentComboBox;
    @FXML private TextField fontNameField;
    @FXML private Slider fontSizeSlider;
    @FXML private Label fontSizeLabel;
    @FXML private CheckBox boldCheckBox;
    @FXML private CheckBox italicCheckBox;
    @FXML private ComboBox<String> alignmentComboBox;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        initializeTextComponents();
    }

    private void initializeTextComponents() {
        textComponentComboBox.getItems().clear();
        textComponentComboBox.getItems().addAll(
                "title", "transition", "recipient", "description", "award", "rightRole", "owner"
        );
        textComponentComboBox.setValue("title"); // Mặc định chọn "title"

        // Cập nhật giao diện khi chọn thành phần
        textComponentComboBox.setOnAction(event -> updateFontFields());
        updateFontFields();

        // Cập nhật nhãn kích thước font khi slider thay đổi
        fontSizeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            fontSizeLabel.setText(String.valueOf(newVal.intValue()));
        });
    }

    private void updateFontFields() {
        String selectedType = textComponentComboBox.getValue();
        TextComponent component = mainController.getCurrentCertificate().getTextComponent(selectedType);
        if (component != null) {
            fontNameField.setText(component.getFontName());
            fontSizeSlider.setValue(component.getFontSize());
            fontSizeLabel.setText(String.valueOf(component.getFontSize()));
            boldCheckBox.setSelected(component.isBold());
            italicCheckBox.setSelected(component.isItalic());
            String alignment = component.getAlignment();
            alignmentComboBox.setValue(alignment);
        }
    }

    @FXML
    private void applyFontChanges() {
        String selectedType = textComponentComboBox.getValue();
        TextComponent component = mainController.getCurrentCertificate().getTextComponent(selectedType);
        if (component == null) {
            showAlert("Lỗi", "Không tìm thấy thành phần văn bản được chọn!");
            return;
        }

        String fontName = fontNameField.getText().trim();
        if (fontName.isEmpty()) {
            showAlert("Lỗi", "Tên font không được để trống!");
            return;
        }

        // Kiểm tra xem font có tồn tại trong hệ thống không
        if (!isFontAvailable(fontName)) {
            showAlert("Lỗi", "Font '" + fontName + "' không tồn tại trong hệ thống! Vui lòng chọn font khác.");
            return;
        }

        try {
            component.setFont(
                    fontName,
                    (int) fontSizeSlider.getValue(),
                    boldCheckBox.isSelected(),
                    italicCheckBox.isSelected(),
                    alignmentComboBox.getValue()
            );
            mainController.setFontProperties(null, 0, false, false, null); // Gọi để vẽ lại chứng chỉ
        } catch (Exception e) {
            showAlert("Lỗi", "Đã xảy ra lỗi khi áp dụng font: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean isFontAvailable(String fontName) {
        // Lấy danh sách font có sẵn trong hệ thống
        String[] fontNames = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        return Arrays.asList(fontNames).contains(fontName);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
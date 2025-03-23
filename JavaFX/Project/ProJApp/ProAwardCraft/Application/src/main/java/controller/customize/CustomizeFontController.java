package controller.customize;

import controller.main.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.element.TextComponent;
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
        textComponentComboBox.setValue("title");

        textComponentComboBox.setOnAction(event -> updateFontFields());
        updateFontFields();

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
            mainController.setFontProperties(null, 0, false, false, null);
        } catch (Exception e) {
            showAlert("Lỗi", "Đã xảy ra lỗi khi áp dụng font: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean isFontAvailable(String fontName) {
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
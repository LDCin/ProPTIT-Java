package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

public class CustomizeFontController {

    @FXML private ComboBox<String> fontCombo;
    @FXML private ComboBox<String> fontSizeCombo;
    @FXML private CheckBox boldCheckBox;
    @FXML private CheckBox italicCheckBox;
    @FXML private ComboBox<String> alignmentCombo;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setCurrentFont(String font, int size, boolean isBold, boolean isItalic, String alignment) {
        fontCombo.setValue(font);
        fontSizeCombo.setValue(String.valueOf(size));
        boldCheckBox.setSelected(isBold);
        italicCheckBox.setSelected(isItalic);
        alignmentCombo.setValue(alignment);
    }

    @FXML
    private void handleApply() {
        String selectedFont = fontCombo.getValue();
        String selectedSize = fontSizeCombo.getValue();
        boolean isBold = boldCheckBox.isSelected();
        boolean isItalic = italicCheckBox.isSelected();
        String selectedAlignment = alignmentCombo.getValue();

        if (selectedFont != null && selectedSize != null && selectedAlignment != null) {
            int fontSize = Integer.parseInt(selectedSize);
            mainController.setFontProperties(selectedFont, fontSize, isBold, isItalic, selectedAlignment);
            closeStage();
        }
    }

    private void closeStage() {
        Stage stage = (Stage) fontCombo.getScene().getWindow();
        stage.close();
    }
}
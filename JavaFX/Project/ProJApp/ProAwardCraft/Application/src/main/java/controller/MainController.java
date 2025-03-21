package controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

public class MainController {

    @FXML private BorderPane root;
    @FXML private ImageView imageView;
    @FXML private AnchorPane workspace;

    private Certificate currentCertificate;
    private String currentFont = "Arial";
    private CertificateManager manager = new CertificateManager();
    private TextField selectedTextField;

    @FXML
    public void initialize() {
        if (root == null || imageView == null || workspace == null) {
            System.err.println("Error: FXML components (root, imageView, workspace) not initialized properly.");
        }
    }

    @FXML
    private void handleQuickCertificate() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/QuickCertificateView.fxml"));
            Parent quickCertRoot = loader.load();
            QuickCertificateController controller = loader.getController();
            controller.setMainController(this);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Quick Certificate");
            dialogStage.initOwner(root.getScene().getWindow());
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(quickCertRoot));
            dialogStage.show();
        } catch (IOException e) {
            showAlert("Lỗi", "Không thể mở Quick Certificate: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCustomCertificate() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CustomCertificateView.fxml"));
            Parent customCertRoot = loader.load();
            CustomCertificateController controller = loader.getController();
            controller.setMainController(this);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Custom Certificate");
            dialogStage.initOwner(root.getScene().getWindow());
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(customCertRoot));
            dialogStage.show();
        } catch (IOException e) {
            showAlert("Lỗi", "Không thể mở Custom Certificate: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleProductManage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ProductManageView.fxml"));
            Parent productManageRoot = loader.load();
            ProductManageController controller = loader.getController();
            controller.setMainController(this);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Quản lý sản phẩm");
            dialogStage.initOwner(root.getScene().getWindow());
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(productManageRoot));
            dialogStage.show();
        } catch (IOException e) {
            showAlert("Lỗi", "Không thể mở Product Manage: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleQuit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận");
        alert.setHeaderText("Bạn có chắc muốn thoát?");
        alert.setContentText("Mọi thay đổi chưa lưu sẽ bị mất.");

        ButtonType yesButton = new ButtonType("Có");
        ButtonType noButton = new ButtonType("Không", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yesButton) {
            Stage stage = (Stage) root.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void handleSaveCertificate() {
        if (currentCertificate != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Lưu sản phẩm");
            dialog.setHeaderText("Nhập tên sản phẩm:");
            dialog.setContentText("Tên:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
                Product newProduct = new Product(name, currentCertificate, new Date());
                String filename = "cert_images/cert_" + name + ".png";
                currentCertificate.saveImage(filename);
                newProduct.setFilename(filename);
                manager.saveProduct(newProduct); // Sử dụng manager đã khai báo
                showAlert("Thành công", "Đã lưu sản phẩm: " + filename);
            });
        } else {
            showAlert("Lỗi", "Vui lòng tạo bằng khen trước khi lưu!");
        }
    }

    @FXML
    private void handleExport() {
        if (currentCertificate != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Xuất sản phẩm");
            dialog.setHeaderText("Nhập tên sản phẩm:");
            dialog.setContentText("Tên:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
                String filename = "cert_images/export_" + name + ".png";
                currentCertificate.exportImage(filename);
                showAlert("Thành công", "Đã xuất file tại: " + filename);
            });
        } else {
            showAlert("Lỗi", "Vui lòng tạo bằng khen trước khi xuất!");
        }
    }

    @FXML
    private void handleCustomizeFont() {
        if (currentCertificate == null) {
            showAlert("Lỗi", "Vui lòng tạo bằng khen trước khi tùy chỉnh font!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CustomizeFontView.fxml"));
            Parent customizeFontRoot = loader.load();
            CustomizeFontController controller = loader.getController();
            controller.setMainController(this);

            String currentFont = currentCertificate.getFontName();
            int currentSize = currentCertificate.getFontSize();
            boolean isBold = currentCertificate.isBold();
            boolean isItalic = currentCertificate.isItalic();
            String alignment = currentCertificate.getAlignment();
            controller.setCurrentFont(currentFont, currentSize, isBold, isItalic, alignment);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Tùy chỉnh Font");
            dialogStage.initOwner(root.getScene().getWindow());
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(customizeFontRoot));
            dialogStage.show();
        } catch (IOException e) {
            showAlert("Lỗi", "Không thể mở Customize Font: " + e.getMessage());
        }
    }

    @FXML
    private void handleEditText() {
        if (selectedTextField != null) {
            selectedTextField.setEditable(true);
            selectedTextField.setStyle("-fx-background-color: white; -fx-border-color: black;");
            selectedTextField.requestFocus();
        }
    }

    public void setCertificate(Certificate certificate) {
        this.currentCertificate = certificate;
        displayImage(certificate.generateImage());
//        addTextFields(); // Thêm TextField
    }

    public Certificate getCurrentCertificate() {
        return currentCertificate;
    }

    public void setCurrentFont(String font) {
        this.currentFont = font;
        if (currentCertificate != null) {
            if (currentCertificate instanceof QuickCertificate) {
                ((QuickCertificate) currentCertificate).setFont(currentFont);
            } else if (currentCertificate instanceof CustomCertificate) {
                ((CustomCertificate) currentCertificate).setFont(currentFont);
            }
            displayImage(currentCertificate.generateImage());
        }
    }

    public String getCurrentFont() {
        return currentFont;
    }

    public void setFontProperties(String font, int size, boolean isBold, boolean isItalic, String alignment) {
        if (currentCertificate != null) {
            currentCertificate.setFont(font, size, isBold, isItalic, alignment);
            displayImage(currentCertificate.generateImage());
        }
    }

    private void displayImage(BufferedImage image) {
        try {
            Image fxImage = image != null ? SwingFXUtils.toFXImage(image, null) : null;
            imageView.setImage(fxImage);

            AnchorPane.setTopAnchor(imageView, 0.0);
            AnchorPane.setBottomAnchor(imageView, 0.0);
            AnchorPane.setLeftAnchor(imageView, 0.0);
            AnchorPane.setRightAnchor(imageView, 0.0);

            imageView.setPreserveRatio(true);
            imageView.setFitWidth(workspace.getWidth());
            imageView.setFitHeight(workspace.getHeight());

            // Căn giữa ImageView trong AnchorPane
            imageView.setX((workspace.getWidth() - imageView.getFitWidth()) / 2);
            imageView.setY((workspace.getHeight() - imageView.getFitHeight()) / 2);

            // Đặt nền trắng cho workspace (tùy chọn)
            workspace.setStyle("-fx-background-color: white;");
        } catch (Exception e) {
            System.err.println("Lỗi khi hiển thị hình ảnh: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public CertificateManager getCertificateManager() {
        return manager;
    }
}
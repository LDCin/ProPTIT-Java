package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.collections.FXCollections;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import javax.imageio.ImageIO;

public class ProAwardCraftController {
    @FXML private BorderPane root;
    @FXML private ImageView imageView;

    private CertificateManager manager = new CertificateManager();
    private Certificate currentCertificate;

    @FXML
    private void handleQuickCertificate() {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Quick Certificate");

        Label recipientLabel = new Label("Tên người nhận:");
        TextField recipientField = new TextField();
        Label awardLabel = new Label("Tên giải thưởng:");
        TextField awardField = new TextField();
        Button generateButton = new Button("Tạo");

        VBox form = new VBox(10, recipientLabel, recipientField, awardLabel, awardField, generateButton);
        Scene dialogScene = new Scene(form, 300, 200);
        dialogStage.setScene(dialogScene);
        dialogStage.initOwner(root.getScene().getWindow());
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.show();

        generateButton.setOnAction(e -> {
            String recipientName = recipientField.getText();
            String awardName = awardField.getText();
            if (!recipientName.isEmpty() && !awardName.isEmpty()) {
                QuickCertificate cert = new QuickCertificate(recipientName, awardName);
                displayImage(cert.generateImage());
                currentCertificate = cert;
                dialogStage.close();
            } else {
                showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin!");
            }
        });
    }

    @FXML
    private void handleCustomCertificate() {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Custom Certificate");

        Label recipientLabel = new Label("Tên người nhận:");
        TextField recipientField = new TextField();
        Label awardLabel = new Label("Tên giải thưởng:");
        TextField awardField = new TextField();
        Label ownerLabel = new Label("Tên chủ nhiệm:");
        TextField ownerField = new TextField();
        Label frameLabel = new Label("Chọn khung:");
        ComboBox<Frame> frameCombo = new ComboBox<>();
        frameCombo.getItems().addAll(
                new Frame("Cổ điển", "#FFFF00"), // Vàng
                new Frame("Hiện đại", "#C0C0C0"), // Bạc
                new Frame("Lễ hội", "#FF0000")   // Đỏ
        );

        Button generateButton = new Button("Tạo");

        VBox form = new VBox(10, recipientLabel, recipientField, awardLabel, awardField, ownerLabel, ownerField, frameLabel, frameCombo, generateButton);
        Scene dialogScene = new Scene(form, 300, 300);
        dialogStage.setScene(dialogScene);
        dialogStage.initOwner(root.getScene().getWindow());
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.show();

        generateButton.setOnAction(e -> {
            String recipientName = recipientField.getText();
            String awardName = awardField.getText();
            String ownerName = ownerField.getText();
            Frame frame = frameCombo.getValue();
            if (!recipientName.isEmpty() && !awardName.isEmpty() && !ownerName.isEmpty() && frame != null) {
                try {
                    CustomCertificate cert = new CustomCertificate(recipientName, awardName, ownerName, frame);
                    BufferedImage image = cert.generateImage();
                    if (image != null) {
                        displayImage(image);
                        currentCertificate = cert;
                        dialogStage.close();
                    } else {
                        showAlert("Lỗi", "Không thể tạo hình ảnh bằng khen!");
                    }
                } catch (Exception ex) {
                    showAlert("Lỗi", "Đã xảy ra lỗi khi tạo bằng khen: " + ex.getMessage());
                    ex.printStackTrace();
                }
            } else {
                showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin và chọn khung!");
            }
        });
    }

    @FXML
    private void handleProductManage() {
        Stage productStage = new Stage();
        productStage.setTitle("Quản lý sản phẩm");

        ListView<Product> productList = new ListView<>();
        productList.setItems(FXCollections.observableArrayList(manager.getProducts()));
        productList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Product item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText(null);
                else setText(item.getName());
            }
        });

        productList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && newVal.getFilename() != null) {
                try {
                    BufferedImage image = ImageIO.read(new File(newVal.getFilename()));
                    displayImage(image);
                } catch (IOException e) {
                    showAlert("Lỗi", "Không thể mở hình ảnh: " + e.getMessage());
                }
            }
        });

        Label newProductLabel = new Label("Lưu sản phẩm mới:");
        TextField productNameField = new TextField();
        Button saveButton = new Button("Lưu");
        Button exportButton = new Button("Xuất File");

        saveButton.setOnAction(e -> {
            String name = productNameField.getText();
            if (currentCertificate != null && !name.isEmpty()) {
                Product newProduct = new Product(name, currentCertificate, new Date());
                String filename = "cert_images/cert_" + System.currentTimeMillis() + ".png";
                currentCertificate.saveImage(filename);
                newProduct.setFilename(filename);
                manager.saveProduct(newProduct);
                productList.getItems().add(newProduct);
                productNameField.clear();
            } else {
                showAlert("Lỗi", "Vui lòng tạo bằng khen và nhập tên sản phẩm!");
            }
        });

        exportButton.setOnAction(e -> {
            String name = productNameField.getText();
            if (currentCertificate != null && !name.isEmpty()) {
                String filename = "cert_images/export_" + name + "_" + System.currentTimeMillis() + ".png";
                currentCertificate.exportImage(filename);
                showAlert("Thành công", "Đã xuất file tại: " + filename);
            } else {
                showAlert("Lỗi", "Vui lòng tạo bằng khen và nhập tên sản phẩm để xuất!");
            }
        });

        VBox productForm = new VBox(10, newProductLabel, productNameField, saveButton, exportButton);
        SplitPane splitPane = new SplitPane(productList, productForm);
        splitPane.setDividerPositions(0.5);

        Scene productScene = new Scene(splitPane, 800, 600);
        productStage.setScene(productScene);
        productStage.initOwner(root.getScene().getWindow());
        productStage.initModality(Modality.APPLICATION_MODAL);
        productStage.show();
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

    private void displayImage(BufferedImage image) {
        Image fxImage = SwingFXUtils.toFXImage(image, null);
        imageView.setImage(fxImage);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
package controller.main;

import controller.certificate.CustomCertificateController;
import controller.product.ProductManageController;
import controller.certificate.QuickCertificateController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.certificate.Certificate;
import model.certificate.CustomCertificate;
import model.certificate.QuickCertificate;
import model.element.Element;
import model.element.TextComponent;
import model.product.CertificateManager;
import model.product.Product;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.imageio.ImageIO;

public class MainController {

    @FXML private BorderPane root;
    @FXML private ImageView imageView;
    @FXML private AnchorPane workspace;

    private Certificate currentCertificate;
    private CertificateManager manager = new CertificateManager();
    private Map<String, Text> textComponentsMap = new HashMap<>();
    private Rectangle frameRect, backgroundRect;
    private double offsetX, offsetY;

    private ImageView selectedElement = null;
    private Circle[] anchors = new Circle[4];

    @FXML
    public void initialize() {
        if (root == null || imageView == null || workspace == null) {
            System.err.println("Error: FXML components (root, imageView, workspace) not initialized properly.");
        }
        workspace.setStyle("-fx-background-color: white;");

        workspace.widthProperty().addListener((obs, oldVal, newVal) -> updateOffsets());
        workspace.heightProperty().addListener((obs, oldVal, newVal) -> updateOffsets());

        workspace.setOnDragOver(event -> {
            if (event.getGestureSource() != workspace && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });

        workspace.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                for (File file : db.getFiles()) {
                    if (file.getName().endsWith(".png") || file.getName().endsWith(".jpg") || file.getName().endsWith(".jpeg")) {
                        Image image = new Image(file.toURI().toString());
                        ImageView imageView = new ImageView(image);
                        imageView.setFitWidth(100);
                        imageView.setFitHeight(100);
                        imageView.setX(event.getX());
                        imageView.setY(event.getY());
                        workspace.getChildren().add(imageView);
                        makeElementMovable(imageView);
                        success = true;

                        if (currentCertificate != null) {
                            addElementToCertificate(file.getAbsolutePath(), "image", event.getX() - offsetX, event.getY() - offsetY, 100, 100);
                        }
                    }
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });

        workspace.setOnMouseClicked(event -> {
            if (event.getTarget() == workspace) {
                deselectElement();
            }
        });
    }

    private void updateOffsets() {
        double certWidth = 500;
        double certHeight = 300;
        offsetX = (workspace.getWidth() - certWidth) / 2;
        offsetY = (workspace.getHeight() - certHeight) / 2;
        if (currentCertificate != null) {
            renderCertificate();
        }
    }

    @FXML
    private void handleQuickCertificate() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/certificate/QuickCertificateView.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/certificate/CustomCertificateView.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/product/ProductManageView.fxml"));
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
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận");
        alert.setHeaderText("Bạn có chắc muốn thoát?");
        alert.setContentText("Mọi thay đổi chưa lưu sẽ bị mất.");

        ButtonType yesButton = new ButtonType("Có");
        ButtonType noButton = new ButtonType("Không", ButtonData.CANCEL_CLOSE);

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
                BufferedImage image = generateImageFromWorkspace();
                try {
                    File outputFile = new File(filename);
                    if (!outputFile.getParentFile().exists()) {
                        outputFile.getParentFile().mkdirs();
                    }
                    ImageIO.write(image, "png", outputFile);
                    newProduct.setFilename(filename);
                    manager.saveProduct(newProduct);
                    showAlert("Thành công", "Đã lưu sản phẩm: " + filename);
                    currentCertificate.setCheckSaveLogo(true);
                } catch (IOException e) {
                    showAlert("Lỗi", "Không thể lưu hình ảnh: " + e.getMessage());
                }
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
                String filename = "cert_images/" + name + ".png";
                BufferedImage image = generateImageFromWorkspace();
                try {
                    File outputFile = new File(filename);
                    if (!outputFile.getParentFile().exists()) {
                        outputFile.getParentFile().mkdirs();
                    }
                    ImageIO.write(image, "png", outputFile);
                    showAlert("Thành công", "Đã xuất file tại: " + filename);
                } catch (IOException e) {
                    showAlert("Lỗi", "Không thể xuất hình ảnh: " + e.getMessage());
                }
            });
        } else {
            showAlert("Lỗi", "Vui lòng tạo bằng khen trước khi xuất!");
        }
    }

    @FXML
    private void handleFrameColorChange() {
        if (currentCertificate == null) {
            showAlert("Lỗi", "Chứng chỉ chưa được tạo! Vui lòng tạo chứng chỉ trước.");
            return;
        }
        if (frameRect == null) {
            showAlert("Lỗi", "Khung chứng chỉ (frameRect) chưa được khởi tạo! Vui lòng kiểm tra lại.");
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Chọn màu khung");
        dialog.setHeaderText("Chọn màu cho khung của chứng chỉ");

        ButtonType applyButtonType = new ButtonType("Áp dụng", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(applyButtonType, ButtonType.CANCEL);

        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue((Color) frameRect.getStroke());
        dialog.getDialogPane().setContent(colorPicker);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == applyButtonType) {
            try {
                Color color = colorPicker.getValue();
                frameRect.setStroke(color);
                String frameColorHex = String.format("#%02X%02X%02X",
                        (int) (color.getRed() * 255),
                        (int) (color.getGreen() * 255),
                        (int) (color.getBlue() * 255));
                if (currentCertificate instanceof CustomCertificate) {
                    ((CustomCertificate) currentCertificate).setFrameColor(frameColorHex);
                } else if (currentCertificate instanceof QuickCertificate) {
                    ((QuickCertificate) currentCertificate).setFrameColor(frameColorHex);
                }
                renderCertificate();
            } catch (Exception e) {
                showAlert("Lỗi", "Đã xảy ra lỗi khi thay đổi màu khung: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleBackgroundColorChange() {
        if (currentCertificate == null) {
            showAlert("Lỗi", "Chứng chỉ chưa được tạo! Vui lòng tạo chứng chỉ trước.");
            return;
        }
        if (backgroundRect == null) {
            showAlert("Lỗi", "Nền chứng chỉ (backgroundRect) chưa được khởi tạo! Vui lòng kiểm tra lại.");
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Chọn màu nền");
        dialog.setHeaderText("Chọn màu nền cho chứng chỉ");

        ButtonType applyButtonType = new ButtonType("Áp dụng", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(applyButtonType, ButtonType.CANCEL);

        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue((Color) backgroundRect.getFill());
        dialog.getDialogPane().setContent(colorPicker);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == applyButtonType) {
            try {
                Color color = colorPicker.getValue();
                backgroundRect.setFill(color);
                String backgroundColorHex = String.format("#%02X%02X%02X",
                        (int) (color.getRed() * 255),
                        (int) (color.getGreen() * 255),
                        (int) (color.getBlue() * 255));
                if (currentCertificate instanceof CustomCertificate) {
                    ((CustomCertificate) currentCertificate).setBackgroundColor(backgroundColorHex);
                } else if (currentCertificate instanceof QuickCertificate) {
                    ((QuickCertificate) currentCertificate).setBackgroundColor(backgroundColorHex);
                }
                renderCertificate();
            } catch (Exception e) {
                showAlert("Lỗi", "Đã xảy ra lỗi khi thay đổi màu nền: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleChangeLogo() {
        if (currentCertificate == null) {
            showAlert("Lỗi", "Vui lòng tạo chứng chỉ trước!");
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn file logo");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());
        if (selectedFile != null) {
            currentCertificate.setLogoPath(selectedFile.getAbsolutePath());
            try {
                BufferedImage logoImage = ImageIO.read(selectedFile);
                double maxWidth = 480;
                double maxHeight = 280;
                double widthRatio = maxWidth / logoImage.getWidth();
                double heightRatio = maxHeight / logoImage.getHeight();
                double scale = Math.min(widthRatio, heightRatio);
                currentCertificate.setLogoWidth(logoImage.getWidth() * scale);
                currentCertificate.setLogoHeight(logoImage.getHeight() * scale);
            } catch (IOException e) {
                showAlert("Lỗi", "Không thể đọc file logo: " + e.getMessage());
            }
            renderCertificate();
        }
    }

    @FXML
    private void handleCheckLogo() {
        if (currentCertificate == null) {
            showAlert("Thông báo", "Chưa có chứng chỉ nào được tạo!");
            return;
        }
        StringBuilder message = new StringBuilder();
        message.append("Thông tin về logo:\n");
        message.append("Đường dẫn: ").append(currentCertificate.getLogoPath() != null ? currentCertificate.getLogoPath() : "Chưa có logo").append("\n");
        message.append("Vị trí: (").append(currentCertificate.getLogoX()).append(", ").append(currentCertificate.getLogoY()).append(")\n");
        if (currentCertificate.getLogoPath() != null) {
            try {
                File logoFile = new File(currentCertificate.getLogoPath());
                if (logoFile.exists()) {
                    message.append("Kích thước hiển thị: ").append((int) currentCertificate.getLogoWidth()).append("x").append((int) currentCertificate.getLogoHeight());
                } else {
                    message.append("Kích thước: Không thể xác định (file không tồn tại)");
                }
            } catch (Exception e) {
                message.append("Kích thước: Không thể xác định (lỗi khi đọc file)");
            }
        } else {
            message.append("Kích thước: Chưa có logo");
        }
        showAlert("Thông tin Logo", message.toString());
    }

    @FXML
    private void handleUpdateLogoSize() {
        if (currentCertificate == null) {
            showAlert("Thông báo", "Chưa có chứng chỉ nào được tạo!");
            return;
        }
        if (currentCertificate.getLogoPath() == null) {
            showAlert("Thông báo", "Chưa có logo được thiết lập!");
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Cập nhật kích thước logo");
        dialog.setHeaderText("Nhập kích thước mới cho logo");

        ButtonType applyButtonType = new ButtonType("Áp dụng", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(applyButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        double originalWidth = currentCertificate.getLogoWidth();
        double originalHeight = currentCertificate.getLogoHeight();
        double aspectRatio = originalWidth / originalHeight;

        TextField widthField = new TextField(String.valueOf(originalWidth));
        TextField heightField = new TextField(String.valueOf(originalHeight));
        CheckBox lockAspectRatioCheckBox = new CheckBox("Giữ tỷ lệ");
        lockAspectRatioCheckBox.setSelected(true);

        grid.add(new Label("Chiều rộng:"), 0, 0);
        grid.add(widthField, 1, 0);
        grid.add(new Label("Chiều cao:"), 0, 1);
        grid.add(heightField, 1, 1);
        grid.add(lockAspectRatioCheckBox, 1, 2);

        widthField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (lockAspectRatioCheckBox.isSelected()) {
                try {
                    double newWidth = Double.parseDouble(newVal.trim());
                    double newHeight = newWidth / aspectRatio;
                    heightField.setText(String.format("%.2f", newHeight));
                } catch (NumberFormatException e) {
                    heightField.setText(String.valueOf(originalHeight));
                }
            }
        });

        heightField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (lockAspectRatioCheckBox.isSelected()) {
                try {
                    double newHeight = Double.parseDouble(newVal.trim());
                    double newWidth = newHeight * aspectRatio;
                    widthField.setText(String.format("%.2f", newWidth));
                } catch (NumberFormatException e) {
                    widthField.setText(String.valueOf(originalWidth));
                }
            }
        });

        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == applyButtonType) {
            try {
                double newWidth = Double.parseDouble(widthField.getText().trim());
                double newHeight = Double.parseDouble(heightField.getText().trim());

                if (newWidth <= 0 || newHeight <= 0) {
                    showAlert("Lỗi", "Kích thước phải lớn hơn 0!");
                    return;
                }

                double maxWidth = 480;
                double maxHeight = 280;
                if (newWidth > maxWidth || newHeight > maxHeight) {
                    showAlert("Lỗi", "Kích thước vượt quá vùng chứng chỉ (tối đa 480x280)!");
                    return;
                }

                currentCertificate.setLogoWidth(newWidth);
                currentCertificate.setLogoHeight(newHeight);
                renderCertificate();
            } catch (NumberFormatException e) {
                showAlert("Lỗi", "Vui lòng nhập số hợp lệ cho chiều rộng và chiều cao!");
            }
        }
    }

    private void makeLogoMovable(ImageView logoView) {
        logoView.setCursor(Cursor.MOVE);
        logoView.setOnMousePressed(event -> {
            logoView.setUserData(new double[]{event.getSceneX() - logoView.getX(), event.getSceneY() - logoView.getY()});
        });
        logoView.setOnMouseDragged(event -> {
            double[] offset = (double[]) logoView.getUserData();
            logoView.setX(event.getSceneX() - offset[0]);
            logoView.setY(event.getSceneY() - offset[1]);
            currentCertificate.setLogoX((int) (logoView.getX() - offsetX));
            currentCertificate.setLogoY((int) (logoView.getY() - offsetY));
        });
    }

    @FXML
    private void handleAddElement() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn hình ảnh để thêm vào chứng chỉ");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Hình ảnh", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(workspace.getScene().getWindow());

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            imageView.setX(50);
            imageView.setY(50);
            workspace.getChildren().add(imageView);
            makeElementMovable(imageView);

            if (currentCertificate != null) {
                addElementToCertificate(selectedFile.getAbsolutePath(), "image", 50 - offsetX, 50 - offsetY, 100, 100);
            }
        }
    }

    private void addElementToCertificate(String path, String type, double x, double y, double width, double height) {
        if (currentCertificate != null) {
            currentCertificate.addElement(path, type, x, y, width, height);
        }
    }

    private void updateElementPositionInCertificate(ImageView element) {
        if (currentCertificate != null) {
            for (Element certElement : currentCertificate.getElements()) {
                if (certElement.getPath().equals(element.getImage().getUrl())) {
                    certElement.setX(element.getX() - offsetX);
                    certElement.setY(element.getY() - offsetY);
                    break;
                }
            }
        }
    }

    private void updateElementSizeInCertificate(ImageView element) {
        if (currentCertificate != null) {
            for (Element certElement : currentCertificate.getElements()) {
                if (certElement.getPath().equals(element.getImage().getUrl())) {
                    certElement.setWidth(element.getFitWidth());
                    certElement.setHeight(element.getFitHeight());
                    break;
                }
            }
        }
    }

    private void removeElementFromCertificate(ImageView element) {
        if (currentCertificate != null) {
            currentCertificate.getElements().removeIf(certElement -> certElement.getPath().equals(element.getImage().getUrl()));
        }
    }

    private void makeElementMovable(ImageView element) {
        element.setCursor(Cursor.MOVE);
        element.setOnMousePressed(event -> {
            element.setUserData(new double[]{event.getSceneX() - element.getX(), event.getSceneY() - element.getY()});
        });
        element.setOnMouseDragged(event -> {
            double[] offset = (double[]) element.getUserData();
            element.setX(event.getSceneX() - offset[0]);
            element.setY(event.getSceneY() - offset[1]);
            if (currentCertificate != null) {
                updateElementPositionInCertificate(element);
            }

            if (selectedElement == element) {
                updateAnchorsPosition(element);
            }
        });
        element.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                selectElement(element);
            } else if (event.getClickCount() == 2) {
                workspace.getChildren().remove(element);
                if (currentCertificate != null) {
                    removeElementFromCertificate(element);
                }
                deselectElement();
            }
        });
    }

    private void selectElement(ImageView element) {
        if (selectedElement != element) {
            deselectElement();
            selectedElement = element;
            showAnchors(element);
        }
    }

    private void deselectElement() {
        if (selectedElement != null) {
            hideAnchors();
            selectedElement = null;
        }
    }

    private void showAnchors(ImageView element) {
        anchors[0] = createAnchor(element.getX(), element.getY(), "top-left");
        anchors[1] = createAnchor(element.getX() + element.getFitWidth(), element.getY(), "top-right");
        anchors[2] = createAnchor(element.getX(), element.getY() + element.getFitHeight(), "bottom-left");
        anchors[3] = createAnchor(element.getX() + element.getFitWidth(), element.getY() + element.getFitHeight(), "bottom-right");

        workspace.getChildren().addAll(anchors);
    }

    private void hideAnchors() {
        if (anchors[0] != null) {
            workspace.getChildren().removeAll(anchors);
            for (int i = 0; i < anchors.length; i++) {
                anchors[i] = null;
            }
        }
    }

    private void updateAnchorsPosition(ImageView element) {
        if (anchors[0] != null) {
            anchors[0].setCenterX(element.getX());
            anchors[0].setCenterY(element.getY());
            anchors[1].setCenterX(element.getX() + element.getFitWidth());
            anchors[1].setCenterY(element.getY());
            anchors[2].setCenterX(element.getX());
            anchors[2].setCenterY(element.getY() + element.getFitHeight());
            anchors[3].setCenterX(element.getX() + element.getFitWidth());
            anchors[3].setCenterY(element.getY() + element.getFitHeight());
        }
    }

    private Circle createAnchor(double x, double y, String position) {
        Circle anchor = new Circle(x, y, 5, Color.RED);
        anchor.setStroke(Color.BLACK);
        anchor.setStrokeWidth(1);
        anchor.setCursor(Cursor.CROSSHAIR);

        anchor.setOnMousePressed(event -> {
            anchor.setUserData(new double[]{event.getSceneX(), event.getSceneY()});
        });

        anchor.setOnMouseDragged(event -> {
            if (selectedElement == null) return;

            double[] startPos = (double[]) anchor.getUserData();
            double deltaX = event.getSceneX() - startPos[0];
            double deltaY = event.getSceneY() - startPos[1];

            double newX = selectedElement.getX();
            double newY = selectedElement.getY();
            double newWidth = selectedElement.getFitWidth();
            double newHeight = selectedElement.getFitHeight();

            switch (position) {
                case "top-left":
                    newX += deltaX;
                    newY += deltaY;
                    newWidth -= deltaX;
                    newHeight -= deltaY;
                    break;
                case "top-right":
                    newY += deltaY;
                    newWidth += deltaX;
                    newHeight -= deltaY;
                    break;
                case "bottom-left":
                    newX += deltaX;
                    newWidth -= deltaX;
                    newHeight += deltaY;
                    break;
                case "bottom-right":
                    newWidth += deltaX;
                    newHeight += deltaY;
                    break;
            }

            if (newWidth < 10) {
                newWidth = 10;
                if (position.contains("left")) {
                    newX = selectedElement.getX() + selectedElement.getFitWidth() - 10;
                }
            }
            if (newHeight < 10) {
                newHeight = 10;
                if (position.contains("top")) {
                    newY = selectedElement.getY() + selectedElement.getFitHeight() - 10;
                }
            }

            double maxWidth = 480;
            double maxHeight = 280;
            if (newWidth > maxWidth) newWidth = maxWidth;
            if (newHeight > maxHeight) newHeight = maxHeight;
            if (newX < 10 + offsetX) newX = 10 + offsetX;
            if (newY < 10 + offsetY) newY = 10 + offsetY;
            if (newX + newWidth > 490 + offsetX) {
                newX = 490 + offsetX - newWidth;
            }
            if (newY + newHeight > 290 + offsetY) {
                newY = 290 + offsetY - newHeight;
            }

            selectedElement.setX(newX);
            selectedElement.setY(newY);
            selectedElement.setFitWidth(newWidth);
            selectedElement.setFitHeight(newHeight);

            updateElementPositionInCertificate(selectedElement);
            updateElementSizeInCertificate(selectedElement);

            updateAnchorsPosition(selectedElement);

            anchor.setUserData(new double[]{event.getSceneX(), event.getSceneY()});
        });

        return anchor;
    }

    public void setCertificate(Certificate certificate) {
        this.currentCertificate = certificate;
        updateOffsets();
        renderCertificate();
    }

    public Certificate getCurrentCertificate() {
        return currentCertificate;
    }

    public void setFontProperties(String font, int size, boolean isBold, boolean isItalic, String alignment) {
        renderCertificate();
    }

    private void renderCertificate() {
        workspace.getChildren().clear();
        textComponentsMap.clear();
        deselectElement();

        if (currentCertificate == null) {
            return;
        }

        backgroundRect = new Rectangle(10 + offsetX, 10 + offsetY, 480, 280);
        String backgroundColorHex = currentCertificate instanceof CustomCertificate ?
                ((CustomCertificate) currentCertificate).getBackgroundColor() :
                ((QuickCertificate) currentCertificate).getBackgroundColor();
        backgroundRect.setFill(backgroundColorHex != null ? Color.web(backgroundColorHex) : Color.WHITE);

        frameRect = new Rectangle(10 + offsetX, 10 + offsetY, 480, 280);
        String frameColorHex = currentCertificate instanceof CustomCertificate ?
                ((CustomCertificate) currentCertificate).getFrameColor() :
                ((QuickCertificate) currentCertificate).getFrameColor();
        frameRect.setStroke(Color.web(frameColorHex));
        frameRect.setStrokeWidth(5);
        frameRect.setFill(null);

        for (TextComponent component : currentCertificate.getTextComponents()) {
            Text text = new Text(component.getText());
            FontWeight weight = component.isBold() ? FontWeight.BOLD : FontWeight.NORMAL;
            FontPosture posture = component.isItalic() ? FontPosture.ITALIC : FontPosture.REGULAR;
            try {
                text.setFont(Font.font(component.getFontName(), weight, posture, component.getFontSize()));
            } catch (Exception e) {
                text.setFont(Font.font("Arial", weight, posture, component.getFontSize()));
                showAlert("Cảnh báo", "Font '" + component.getFontName() + "' không hợp lệ, sử dụng font mặc định (Arial).");
            }

            switch (component.getType()) {
                case "title":
                case "recipient":
                    text.setFill(Color.RED);
                    break;
                case "transition":
                case "description":
                case "rightRole":
                    text.setFill(Color.GRAY);
                    break;
                case "award":
                case "owner":
                    text.setFill(Color.BLACK);
                    break;
            }

            int x = component.getX();
            int y = component.getY();
            String alignment = component.getAlignment();
            if (alignment.equals("Giữa")) {
                x = (int) (250 + offsetX - text.getLayoutBounds().getWidth() / 2);
            } else if (alignment.equals("Trái")) {
                x = (int) (10 + offsetX);
            } else if (alignment.equals("Phải")) {
                x = (int) (470 + offsetX - text.getLayoutBounds().getWidth());
            }
            text.setX(x);
            text.setY(y + offsetY);

            makeTextEditable(text, component.getType());
            textComponentsMap.put(component.getType(), text);
        }

        workspace.getChildren().add(backgroundRect);
        workspace.getChildren().add(frameRect);
        workspace.getChildren().addAll(textComponentsMap.values());

        if (currentCertificate.getLogoPath() != null) {
            try {
                System.out.println("Đang thử hiển thị logo từ: " + currentCertificate.getLogoPath());
                Image logoImage;
                if (currentCertificate.getLogoPath().startsWith("file:") || currentCertificate.getLogoPath().startsWith("jar:")) {
                    logoImage = new Image(currentCertificate.getLogoPath());
                } else {
                    File logoFile = new File(currentCertificate.getLogoPath());
                    if (!logoFile.exists()) {
                        System.err.println("File logo không tồn tại: " + currentCertificate.getLogoPath());
                        return;
                    }
                    logoImage = new Image(logoFile.toURI().toString());
                }
                if (logoImage.isError()) {
                    System.err.println("Không thể tải logo: " + logoImage.getException().getMessage());
                    return;
                }
                ImageView logoView = new ImageView(logoImage);
                double scaledWidth = currentCertificate.getLogoWidth();
                double scaledHeight = currentCertificate.getLogoHeight();
                if (currentCertificate.isCheckSaveLogo() == false && scaledWidth >= 50 && scaledHeight > 50) {
                    scaledWidth = currentCertificate.getLogoWidth() / 2.5;
                    scaledHeight = currentCertificate.getLogoHeight() / 2.5;
                }
                logoView.setFitWidth(scaledWidth);
                logoView.setFitHeight(scaledHeight);

                currentCertificate.setLogoWidth(scaledWidth);
                currentCertificate.setLogoHeight(scaledHeight);

                double logoX = (workspace.getWidth() - scaledWidth) / 2 - offsetX;
                double logoY;
                Text awardText = textComponentsMap.get("award");
                if (awardText != null) {
                    double awardY = awardText.getY() - offsetY;
                    logoY = awardY + 20;
                } else {
                    logoY = 210;
                }

                if (logoX < 0 || logoX + scaledWidth > 500 || logoY < 0 || logoY + scaledHeight > 300) {
                    System.out.println("Logo nằm ngoài vùng hiển thị, đặt lại vị trí mặc định.");
                    logoX = 215;
                    logoY = 210;
                }

                currentCertificate.setLogoX((int) logoX);
                currentCertificate.setLogoY((int) logoY);

                logoView.setX(logoX + offsetX);
                logoView.setY(logoY + offsetY);

                System.out.println("Hiển thị logo tại: (" + logoView.getX() + ", " + logoView.getY() + ")");
                makeLogoMovable(logoView);
                workspace.getChildren().add(logoView);
            } catch (Exception e) {
                System.err.println("Không thể hiển thị logo: " + e.getMessage());
                e.printStackTrace();
            }
        }

        for (Element element : currentCertificate.getElements()) {
            if ("image".equals(element.getType())) {
                Image image = new Image(element.getPath());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(element.getWidth());
                imageView.setFitHeight(element.getHeight());
                imageView.setX(element.getX() + offsetX);
                imageView.setY(element.getY() + offsetY);
                makeElementMovable(imageView);
                workspace.getChildren().add(imageView);
            }
        }
    }

    private void makeTextEditable(Text text, String type) {
        text.setCursor(Cursor.MOVE);
        text.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                showTextEditDialog(text, type);
            }
        });

        text.setOnMousePressed(event -> {
            text.setUserData(new double[]{event.getSceneX() - text.getX(), event.getSceneY() - text.getY()});
        });
        text.setOnMouseDragged(event -> {
            double[] offset = (double[]) text.getUserData();
            text.setX(event.getSceneX() - offset[0]);
            text.setY(event.getSceneY() - offset[1]);
            updateCertificatePosition(type, (int) (text.getX() - offsetX), (int) (text.getY() - offsetY));
        });
    }

    private void showTextEditDialog(Text text, String type) {
        TextComponent component = currentCertificate.getTextComponent(type);
        if (component == null) {
            showAlert("Lỗi", "Không tìm thấy thành phần văn bản!");
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Chỉnh sửa văn bản và font");
        dialog.setHeaderText("Chỉnh sửa nội dung và thuộc tính font cho: " + type);

        ButtonType applyButtonType = new ButtonType("Áp dụng", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(applyButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField textField = new TextField(text.getText());
        grid.add(new Label("Nội dung:"), 0, 0);
        grid.add(textField, 1, 0);

        Label fontSearchLabel = new Label("Tìm kiếm font:");
        TextField fontSearchField = new TextField();
        fontSearchField.setPromptText("Nhập tên font để tìm kiếm...");
        grid.add(fontSearchLabel, 0, 1);
        grid.add(fontSearchField, 1, 1);

        Label fontNameLabel = new Label("Tên font:");
        ComboBox<String> fontNameComboBox = new ComboBox<>();
        String[] fontNames = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontNameComboBox.getItems().addAll(fontNames);
        fontNameComboBox.setValue(component.getFontName());
        grid.add(fontNameLabel, 0, 2);
        grid.add(fontNameComboBox, 1, 2);

        fontSearchField.textProperty().addListener((obs, oldValue, newValue) -> {
            fontNameComboBox.getItems().clear();
            String searchText = newValue.trim().toLowerCase();
            if (searchText.isEmpty()) {
                fontNameComboBox.getItems().addAll(fontNames);
            } else {
                for (String fontName : fontNames) {
                    if (fontName.toLowerCase().contains(searchText)) {
                        fontNameComboBox.getItems().add(fontName);
                    }
                }
            }
            if (fontNameComboBox.getItems().isEmpty()) {
                fontNameComboBox.setPromptText("Không tìm thấy font nào!");
            } else {
                fontNameComboBox.setPromptText("Chọn font...");
                if (fontNameComboBox.getItems().contains(component.getFontName())) {
                    fontNameComboBox.setValue(component.getFontName());
                } else {
                    fontNameComboBox.setValue(null);
                }
            }
        });

        Slider fontSizeSlider = new Slider(8, 72, component.getFontSize());
        fontSizeSlider.setShowTickLabels(true);
        fontSizeSlider.setShowTickMarks(true);
        fontSizeSlider.setMajorTickUnit(8);
        fontSizeSlider.setMinorTickCount(1);
        Label fontSizeLabel = new Label(String.valueOf(component.getFontSize()));
        fontSizeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            fontSizeLabel.setText(String.valueOf(newVal.intValue()));
        });
        grid.add(new Label("Kích thước font:"), 0, 3);
        grid.add(fontSizeSlider, 1, 3);
        grid.add(fontSizeLabel, 2, 3);

        CheckBox boldCheckBox = new CheckBox("In đậm");
        boldCheckBox.setSelected(component.isBold());
        CheckBox italicCheckBox = new CheckBox("In nghiêng");
        italicCheckBox.setSelected(component.isItalic());
        HBox styleBox = new HBox(10, boldCheckBox, italicCheckBox);
        grid.add(new Label("Kiểu chữ:"), 0, 4);
        grid.add(styleBox, 1, 4);

        ComboBox<String> alignmentComboBox = new ComboBox<>();
        alignmentComboBox.getItems().addAll("Trái", "Giữa", "Phải");
        alignmentComboBox.setValue(component.getAlignment());
        grid.add(new Label("Căn chỉnh:"), 0, 5);
        grid.add(alignmentComboBox, 1, 5);

        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == applyButtonType) {
            String newText = textField.getText().trim();
            String newFont = fontNameComboBox.getValue();
            int newSize = (int) fontSizeSlider.getValue();
            boolean newBold = boldCheckBox.isSelected();
            boolean newItalic = italicCheckBox.isSelected();
            String newAlignment = alignmentComboBox.getValue();

            if (newText.isEmpty()) {
                showAlert("Lỗi", "Nội dung không được để trống!");
                return;
            }
            if (newFont == null || newFont.isEmpty()) {
                showAlert("Lỗi", "Vui lòng chọn font!");
                return;
            }

            component.setText(newText);
            component.setFont(newFont, newSize, newBold, newItalic, newAlignment);
            renderCertificate();
        }
    }

    private void updateCertificatePosition(String type, int x, int y) {
        if (currentCertificate != null) {
            TextComponent component = currentCertificate.getTextComponent(type);
            if (component != null) {
                component.setX(x);
                component.setY(y);
            }
        }
    }

    private BufferedImage generateImageFromWorkspace() {
        int width = 500;
        int height = 300;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2d = image.createGraphics();

        Color backgroundColor = (Color) backgroundRect.getFill();
        g2d.setColor(new java.awt.Color((float) backgroundColor.getRed(), (float) backgroundColor.getGreen(), (float) backgroundColor.getBlue()));
        g2d.fillRect(0, 0, width, height);

        Color strokeColor = (Color) frameRect.getStroke();
        g2d.setColor(new java.awt.Color((float) strokeColor.getRed(), (float) strokeColor.getGreen(), (float) strokeColor.getBlue()));
        g2d.setStroke(new java.awt.BasicStroke((float) frameRect.getStrokeWidth()));
        g2d.drawRect((int) (frameRect.getX() - offsetX), (int) (frameRect.getY() - offsetY),
                (int) frameRect.getWidth(), (int) frameRect.getHeight());

        for (TextComponent component : currentCertificate.getTextComponents()) {
            Text text = textComponentsMap.get(component.getType());
            if (text != null) {
                g2d.setColor(new java.awt.Color((float) ((Color) text.getFill()).getRed(),
                        (float) ((Color) text.getFill()).getGreen(),
                        (float) ((Color) text.getFill()).getBlue()));
                java.awt.Font awtFont;
                try {
                    awtFont = new java.awt.Font(component.getFontName(),
                            (component.isBold() ? java.awt.Font.BOLD : 0) | (component.isItalic() ? java.awt.Font.ITALIC : 0),
                            component.getFontSize());
                } catch (Exception e) {
                    awtFont = new java.awt.Font("Arial",
                            (component.isBold() ? java.awt.Font.BOLD : 0) | (component.isItalic() ? java.awt.Font.ITALIC : 0),
                            component.getFontSize());
                }
                g2d.setFont(awtFont);
                g2d.drawString(text.getText(), (int) (text.getX() - offsetX), (int) (text.getY() - offsetY));
            }
        }

        if (currentCertificate.getLogoPath() != null) {
            try {
                File logoFile = new File(currentCertificate.getLogoPath());
                if (!logoFile.exists()) {
                    System.err.println("File logo không tồn tại: " + currentCertificate.getLogoPath());
                } else {
                    BufferedImage logoImage = ImageIO.read(logoFile);
                    int logoX = currentCertificate.getLogoX();
                    int logoY = currentCertificate.getLogoY();
                    int scaledWidth = (int) currentCertificate.getLogoWidth();
                    int scaledHeight = (int) currentCertificate.getLogoHeight();
                    g2d.drawImage(logoImage, logoX, logoY, scaledWidth, scaledHeight, null);
                }
            } catch (IOException e) {
                System.err.println("Không thể vẽ logo: " + e.getMessage());
            }
        }

        g2d.dispose();
        return image;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public CertificateManager getCertificateManager() {
        return manager;
    }
}
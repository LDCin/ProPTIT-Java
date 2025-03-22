package controller;

import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

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
    private String currentFont = "Arial";
    private CertificateManager manager = new CertificateManager();
    private TextField selectedTextField;
    private Map<String, Text> textComponentsMap = new HashMap<>();
    private Rectangle frameRect, backgroundRect;
    private double offsetX, offsetY;

    @FXML
    public void initialize() {
        if (root == null || imageView == null || workspace == null) {
            System.err.println("Error: FXML components (root, imageView, workspace) not initialized properly.");
        }
        workspace.setStyle("-fx-background-color: white;");

        workspace.widthProperty().addListener((obs, oldVal, newVal) -> updateOffsets());
        workspace.heightProperty().addListener((obs, oldVal, newVal) -> updateOffsets());
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
                String filename = "cert_images/export_" + name + ".png";
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
    private void handleEditText() {
        if (selectedTextField != null) {
            selectedTextField.setEditable(true);
            selectedTextField.setStyle("-fx-background-color: white; -fx-border-color: black;");
            selectedTextField.requestFocus();
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

        // Tạo hộp thoại chọn màu
        Dialog<ButtonType> dialog = new Dialog<>(); // Sửa kiểu của Dialog thành ButtonType
        dialog.setTitle("Chọn màu khung");
        dialog.setHeaderText("Chọn màu cho khung của chứng chỉ");

        ButtonType applyButtonType = new ButtonType("Áp dụng", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(applyButtonType, ButtonType.CANCEL);

        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue((Color) frameRect.getStroke());
        dialog.getDialogPane().setContent(colorPicker);

        Optional<ButtonType> result = dialog.showAndWait(); // Kết quả là Optional<ButtonType>
        if (result.isPresent() && result.get() == applyButtonType) { // Kiểm tra xem người dùng có nhấn "Áp dụng" không
            try {
                Color color = colorPicker.getValue(); // Lấy màu từ ColorPicker
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
                renderCertificate(); // Gọi lại để vẽ lại chứng chỉ với màu mới
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

        // Tạo hộp thoại chọn màu
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
                // Cập nhật backgroundColor trong currentCertificate
                if (currentCertificate instanceof CustomCertificate) {
                    ((CustomCertificate) currentCertificate).setBackgroundColor(backgroundColorHex);
                } else if (currentCertificate instanceof QuickCertificate) {
                    ((QuickCertificate) currentCertificate).setBackgroundColor(backgroundColorHex);
                }
                renderCertificate(); // Gọi lại để vẽ lại chứng chỉ với màu mới
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

        // Tạo hộp thoại nhập kích thước
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Cập nhật kích thước logo");
        dialog.setHeaderText("Nhập kích thước mới cho logo");

        ButtonType applyButtonType = new ButtonType("Áp dụng", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(applyButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Tính tỷ lệ ban đầu của logo
        double originalWidth = currentCertificate.getLogoWidth();
        double originalHeight = currentCertificate.getLogoHeight();
        double aspectRatio = originalWidth / originalHeight;

        TextField widthField = new TextField(String.valueOf(originalWidth));
        TextField heightField = new TextField(String.valueOf(originalHeight));
        CheckBox lockAspectRatioCheckBox = new CheckBox("Giữ tỷ lệ");
        lockAspectRatioCheckBox.setSelected(true); // Mặc định tích vào

        grid.add(new Label("Chiều rộng:"), 0, 0);
        grid.add(widthField, 1, 0);
        grid.add(new Label("Chiều cao:"), 0, 1);
        grid.add(heightField, 1, 1);
        grid.add(lockAspectRatioCheckBox, 1, 2);

        // Thêm listener để tự động cập nhật chiều cao hoặc chiều rộng khi thay đổi
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

                // Đảm bảo kích thước không vượt quá vùng chứng chỉ
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
            // Cập nhật vị trí logo trong certificate
            currentCertificate.setLogoX((int) (logoView.getX() - offsetX));
            currentCertificate.setLogoY((int) (logoView.getY() - offsetY));
        });
    }

    public void setCertificate(Certificate certificate) {
        this.currentCertificate = certificate;
        updateOffsets();
        renderCertificate();
    }

    public Certificate getCurrentCertificate() {
        return currentCertificate;
    }

    public void setCurrentFont(String font) {
        this.currentFont = font;
        renderCertificate();
    }

    public String getCurrentFont() {
        return currentFont;
    }

    public void setFontProperties(String font, int size, boolean isBold, boolean isItalic, String alignment) {
        renderCertificate();
    }

    private void renderCertificate() {
        workspace.getChildren().clear();
        textComponentsMap.clear();

        if (currentCertificate == null) {
            // Không hiển thị thông báo lỗi, chỉ xóa giao diện và thoát
            return;
        }

        backgroundRect = new Rectangle(10 + offsetX, 10 + offsetY, 480, 280);
        // Lấy backgroundColor từ currentCertificate
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

        // Hiển thị logo nếu có
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
                logoView.setFitWidth(currentCertificate.getLogoWidth());
                logoView.setFitHeight(currentCertificate.getLogoHeight());
                int logoX = currentCertificate.getLogoX();
                int logoY = currentCertificate.getLogoY();
                if (logoX < 0 || logoX + currentCertificate.getLogoWidth() > 500 || logoY < 0 || logoY + currentCertificate.getLogoHeight() > 300) {
                    System.out.println("Logo nằm ngoài vùng hiển thị, đặt lại vị trí mặc định.");
                    logoX = 230;
                    logoY = 210;
                    currentCertificate.setLogoX(logoX);
                    currentCertificate.setLogoY(logoY);
                }
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

        // Tạo hộp thoại tùy chỉnh
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Chỉnh sửa văn bản và font");
        dialog.setHeaderText("Chỉnh sửa nội dung và thuộc tính font cho: " + type);

        // Tạo các nút
        ButtonType applyButtonType = new ButtonType("Áp dụng", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(applyButtonType, ButtonType.CANCEL);

        // Tạo giao diện hộp thoại
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Nội dung văn bản
        TextField textField = new TextField(text.getText());
        grid.add(new Label("Nội dung:"), 0, 0);
        grid.add(textField, 1, 0);

        // Tìm kiếm font
        Label fontSearchLabel = new Label("Tìm kiếm font:");
        TextField fontSearchField = new TextField();
        fontSearchField.setPromptText("Nhập tên font để tìm kiếm...");
        grid.add(fontSearchLabel, 0, 1);
        grid.add(fontSearchField, 1, 1);

        // Tên font
        Label fontNameLabel = new Label("Tên font:");
        ComboBox<String> fontNameComboBox = new ComboBox<>();
        String[] fontNames = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontNameComboBox.getItems().addAll(fontNames);
        fontNameComboBox.setValue(component.getFontName());
        grid.add(fontNameLabel, 0, 2);
        grid.add(fontNameComboBox, 1, 2);

        // Lọc font dựa trên từ khóa tìm kiếm
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
            // Nếu danh sách rỗng, hiển thị thông báo
            if (fontNameComboBox.getItems().isEmpty()) {
                fontNameComboBox.setPromptText("Không tìm thấy font nào!");
            } else {
                fontNameComboBox.setPromptText("Chọn font...");
                // Nếu font hiện tại vẫn nằm trong danh sách lọc, giữ nguyên giá trị
                if (fontNameComboBox.getItems().contains(component.getFontName())) {
                    fontNameComboBox.setValue(component.getFontName());
                } else {
                    fontNameComboBox.setValue(null); // Reset nếu font hiện tại không còn trong danh sách
                }
            }
        });

        // Kích thước font
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

        // In đậm và in nghiêng
        CheckBox boldCheckBox = new CheckBox("In đậm");
        boldCheckBox.setSelected(component.isBold());
        CheckBox italicCheckBox = new CheckBox("In nghiêng");
        italicCheckBox.setSelected(component.isItalic());
        HBox styleBox = new HBox(10, boldCheckBox, italicCheckBox);
        grid.add(new Label("Kiểu chữ:"), 0, 4);
        grid.add(styleBox, 1, 4);

        // Căn chỉnh
        ComboBox<String> alignmentComboBox = new ComboBox<>();
        alignmentComboBox.getItems().addAll("Trái", "Giữa", "Phải");
        alignmentComboBox.setValue(component.getAlignment());
        grid.add(new Label("Căn chỉnh:"), 0, 5);
        grid.add(alignmentComboBox, 1, 5);

        dialog.getDialogPane().setContent(grid);

        // Xử lý khi nhấn "Áp dụng"
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == applyButtonType) {
            String newText = textField.getText();
            String fontName = fontNameComboBox.getValue();
            int fontSize = (int) fontSizeSlider.getValue();
            boolean isBold = boldCheckBox.isSelected();
            boolean isItalic = italicCheckBox.isSelected();
            String alignment = alignmentComboBox.getValue();

            // Kiểm tra font hợp lệ
            if (fontName == null || fontName.trim().isEmpty()) {
                showAlert("Lỗi", "Vui lòng chọn một font!");
                return;
            }

            // Cập nhật TextComponent
            component.setFont(fontName, fontSize, isBold, isItalic, alignment);
            updateCertificateData(type, newText);

            // Cập nhật giao diện
            text.setText(newText);
            FontWeight weight = isBold ? FontWeight.BOLD : FontWeight.NORMAL;
            FontPosture posture = isItalic ? FontPosture.ITALIC : FontPosture.REGULAR;
            try {
                text.setFont(Font.font(fontName, weight, posture, fontSize));
            } catch (Exception e) {
                text.setFont(Font.font("Arial", weight, posture, fontSize));
            }

            // Căn chỉnh lại dựa trên alignment
            int x = component.getX();
            if (alignment.equals("Giữa")) {
                x = (int) (250 + offsetX - text.getLayoutBounds().getWidth() / 2);
            } else if (alignment.equals("Trái")) {
                x = (int) (10 + offsetX);
            } else if (alignment.equals("Phải")) {
                x = (int) (490 + offsetX - text.getLayoutBounds().getWidth());
            }
            text.setX(x);
        }
    }

    private void updateCertificateData(String type, String newText) {
        if (currentCertificate instanceof CustomCertificate) {
            CustomCertificate cert = (CustomCertificate) currentCertificate;
            switch (type) {
                case "recipient": cert.setRecipientName(newText); break;
                case "award": cert.setAwardName(newText.replace("Giải thưởng: ", "")); break;
                case "owner": cert.setOwnerName(newText); break;
            }
        } else if (currentCertificate instanceof QuickCertificate) {
            QuickCertificate cert = (QuickCertificate) currentCertificate;
            switch (type) {
                case "recipient": cert.setRecipientName(newText); break;
                case "award": cert.setAwardName(newText.replace("Giải thưởng: ", "")); break;
            }
        }
        currentCertificate.setTextComponent(type, newText);
    }

    private void updateCertificatePosition(String type, int x, int y) {
        currentCertificate.setTextPosition(type, x, y);
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

        // Vẽ logo nếu có
        if (currentCertificate.getLogoPath() != null) {
            try {
                File logoFile = new File(currentCertificate.getLogoPath());
                if (!logoFile.exists()) {
                    System.err.println("File logo không tồn tại: " + currentCertificate.getLogoPath());
                } else {
                    BufferedImage logoImage = ImageIO.read(logoFile);
                    g2d.drawImage(logoImage, currentCertificate.getLogoX(), currentCertificate.getLogoY(),
                            (int) currentCertificate.getLogoWidth(), (int) currentCertificate.getLogoHeight(), null);
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
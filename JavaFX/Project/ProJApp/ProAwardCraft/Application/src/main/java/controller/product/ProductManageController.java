package controller.product;

import controller.main.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.product.CertificateManager;
import model.product.Product;
import javafx.collections.FXCollections;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import javax.imageio.ImageIO;

public class ProductManageController {

    @FXML private ListView<Product> productList;

    private MainController mainController;
    private CertificateManager manager;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        this.manager = mainController.getCertificateManager();
        initializeProductList();
    }

    private void initializeProductList() {
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
                File file = new File(newVal.getFilename());
                if (file.exists()) {
                    try {
                        BufferedImage image = ImageIO.read(file);
                        mainController.setCertificate(newVal.getCertificate());
                    } catch (IOException e) {
                        showAlert("Lỗi", "Không thể mở hình ảnh: " + e.getMessage());
                    }
                }
            }
        });
    }

    @FXML
    private void handleDelete() {
        Product selectedProduct = productList.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showAlert("Lỗi", "Vui lòng chọn một sản phẩm để xóa!");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Xác nhận xóa");
        confirmation.setHeaderText("Bạn có chắc muốn xóa sản phẩm này?");
        confirmation.setContentText("Sản phẩm: " + selectedProduct.getName() + "\nHành động này không thể hoàn tác.");

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String filename = selectedProduct.getFilename();
            boolean fileDeleted = false;
            if (filename != null && !filename.isEmpty()) {
                File file = new File(filename);
                if (file.exists()) {
                    fileDeleted = file.delete();
                    if (fileDeleted) {
                        System.out.println("Deleted file: " + filename);
                    } else {
                        System.err.println("Failed to delete file: " + filename);
                    }
                }
            }

            if (manager.removeProduct(selectedProduct)) {
                refreshProductList();

                productList.getSelectionModel().clearSelection();

                if (mainController.getCurrentCertificate() != null &&
                        mainController.getCurrentCertificate() == selectedProduct.getCertificate()) {
                    mainController.setCertificate(null);
                }

                showAlert("Thành công", "Đã xóa sản phẩm: " + selectedProduct.getName());
            } else {
                showAlert("Lỗi", "Không thể xóa sản phẩm khỏi danh sách!");
            }
        }
    }

    public void refreshProductList() {
        productList.setItems(FXCollections.observableArrayList(manager.getProducts()));
        System.out.println("Refreshed product list. Current items: " + manager.getProducts().size());
        manager.getProducts().forEach(product -> System.out.println("Product in list: " + product.getName()));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
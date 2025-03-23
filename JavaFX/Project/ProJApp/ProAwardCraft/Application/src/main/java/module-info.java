module ProAwardCraft {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires com.google.gson;

    // Mở các package con của controller để JavaFX có thể truy cập
    opens controller.main to javafx.fxml;
    opens controller.certificate to javafx.fxml;
    opens controller.customize to javafx.fxml;
    opens controller.product to javafx.fxml;

    // Export các package nếu cần (ví dụ, nếu các package này được sử dụng bởi module khác)
    exports controller.main;
    exports controller.certificate;
    exports controller.customize;
    exports controller.product;

    // Mở các package khác nếu cần (ví dụ: package app)
    opens app to javafx.fxml;
    exports app;
}
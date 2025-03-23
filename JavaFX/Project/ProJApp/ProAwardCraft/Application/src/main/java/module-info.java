module ProAwardCraft {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires com.google.gson;

    opens controller.main to javafx.fxml;
    opens controller.certificate to javafx.fxml;
    opens controller.customize to javafx.fxml;
    opens controller.product to javafx.fxml;

    exports controller.main;
    exports controller.certificate;
    exports controller.customize;
    exports controller.product;

    opens app to javafx.fxml;
    exports app;
}
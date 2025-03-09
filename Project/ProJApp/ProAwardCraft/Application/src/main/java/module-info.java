module ProAwardCraft {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing; // Đảm bảo có dòng này
    requires java.desktop;

    opens app to javafx.fxml;
    opens controller to javafx.fxml;
    exports app;
}
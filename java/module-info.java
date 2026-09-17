module com.example.postgresimplejfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.home2608 to javafx.fxml;
    exports com.example.home2608;
}
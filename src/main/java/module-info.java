module com.nscharrenberg {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.nscharrenberg to javafx.fxml;
    exports com.nscharrenberg;
}
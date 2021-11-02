module com.example.project3fx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.project3fx to javafx.fxml;
    exports com.example.project3fx;
    exports Exceptions;
    opens Exceptions to javafx.fxml;
}
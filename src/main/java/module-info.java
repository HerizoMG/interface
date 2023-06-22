module com.example.affectation {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens com.example.affectation to javafx.fxml;
    exports com.example.affectation;
}
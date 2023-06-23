module com.example.affectation {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mail;


    opens com.example.affectation to javafx.fxml;
    exports com.example.affectation;
    opens com.example.affectation.Controller to javafx.fxml;
    opens com.example.affectation.Model to javafx.base;
}
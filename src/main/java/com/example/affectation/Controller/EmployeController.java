package com.example.affectation.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class EmployeController {



   @FXML
    private Button ajouterEmpl;

    @FXML
    private Button affecterEmpl;

    @FXML
    private Button creerEmpl;

    @FXML
    private Button editerEmpl;

    @FXML
    private Button supprimerEmpl;

    @FXML
    void handleButtonClick(ActionEvent event) {
        if (event.getSource() == affecterEmpl){
            showAsDialog("ajoutAffect");
        }
        if (event.getSource() == editerEmpl){
            showAsDialog("modifierEmp");
        }
        if (event.getSource() == ajouterEmpl){
            showAsDialog("ajoutEmp");
        }

    }

    public static void showAsDialog(String fxml)
    {
        try {
            Parent parent =  FXMLLoader.load(Objects.requireNonNull(EmployeController.class.getResource("/com/example/affectation/fxml/"+fxml+".fxml")));
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

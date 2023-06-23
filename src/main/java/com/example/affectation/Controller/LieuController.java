package com.example.affectation.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LieuController {

    @FXML
    private Button ajouterLieuButton;

    @FXML
    private Button modifierLieuButton;
    public void handleAjouterLieuButton(ActionEvent actionEvent) {
        if(actionEvent.getSource() == ajouterLieuButton){
            EmployeController.showAsDialog("ajoutLieu");
        }

    }
    public void handleModifierLieuButton(ActionEvent actionEvent) {
        if (actionEvent.getSource() == modifierLieuButton){
            EmployeController.showAsDialog("modifierLieu");
        }
    }
}

package com.example.affectation.Controller;

import com.example.affectation.Model.Employe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AffectationController {

    @FXML
    private Button modifierButton;


    public void handleButtonModifier(ActionEvent actionEvent) {
        if(actionEvent.getSource() == modifierButton){
            EmployeController.showAsDialog("modifierAffect");
        }
    }
}

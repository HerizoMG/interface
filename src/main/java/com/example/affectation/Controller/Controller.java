package com.example.affectation.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {
    @FXML
    public StackPane contentArea;
    @FXML
    private Label exit;
    @FXML
    private Button creerEmpl, editerEmpl;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/com/example/affectation/fxml/employe.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void employe(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/example/affectation/fxml/employe.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);

    }
    public void affectation(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/example/affectation/fxml/affectation.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);

    }
    public void lieu(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/example/affectation/fxml/lieu.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);

    }

    /*@FXML
    void handleButtonClick(ActionEvent event) {
        if (event.getSource() == creerEmpl){

        showAsDialog("ajoutEmp");
        }
        if (event.getSource() == editerEmpl){
            showAsDialog("modifierEmp");

        }

    }

    private void showAsDialog(String fxml)
    {
        try {
            Parent parent =  FXMLLoader.load(getClass().getResource("/com/example/affectation/fxml/"+fxml+".fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/

}

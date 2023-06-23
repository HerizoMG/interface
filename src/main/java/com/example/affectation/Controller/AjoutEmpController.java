package com.example.affectation.Controller;

import com.example.affectation.DAO.EmployeDAO;
import com.example.affectation.DAO.LieuDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.List;

public class AjoutEmpController {
    @FXML
    private AnchorPane modalAddLabel;
    @FXML
    private ComboBox<String> lieuCombo;

    @FXML
    private ComboBox<String> provinceComboEmploye;

    @FXML
    private TextField addMail;

    @FXML
    private TextField addNom;

    @FXML
    private TextField addPost;

    @FXML
    private TextField addPrenoms;

    @FXML
    private ComboBox<String> civiliteCombo;


    public void handleAddEmploye(ActionEvent event) {
        String nom = addNom.getText();
        String prenoms = addPrenoms.getText();
        String civilite = civiliteCombo.getSelectionModel().getSelectedItem();
        String poste = addPost.getText();
        String province = provinceComboEmploye.getSelectionModel().getSelectedItem();
        String design_lieu = lieuCombo.getSelectionModel().getSelectedItem();
        String lieu = LieuDAO.getOneLieu(design_lieu, province);
        String mail = addMail.getText();
        if (nom.isEmpty() || poste.isEmpty() || province == null || mail.isEmpty() || civilite == null || design_lieu == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Champs vides", "Veuillez remplir tous les champs !");
        }
        else{
            EmployeDAO createDAO = new EmployeDAO();
            createDAO.createEmploye(nom, prenoms, poste, civilite, lieu, mail);
            this.close();
        }
    }

    public void cancelAdding(ActionEvent event) {
        this.close();
    }

    public void initialize() {
        ObservableList<String> options = FXCollections.observableArrayList("Mlle", "Mme", "Mr");
        civiliteCombo.getItems().addAll(options);

        options = FXCollections.observableArrayList("Antananarivo", "Fianarantsoa", "Antsiranana", "Mahajanga", "Toliara", "Toamasina");
        provinceComboEmploye.getItems().addAll(options);

        provinceComboEmploye.valueProperty().addListener((observable, oldValue, newValue) -> {
            loadLieu(null);
        });
    }

    public void loadLieu(MouseEvent event) {
        List<String> designations = LieuDAO.getLieuDesignations(provinceComboEmploye.getSelectionModel().getSelectedItem());
        System.out.println(designations);
        lieuCombo.getItems().clear();
        lieuCombo.getItems().addAll(designations);
    }

    public static void showAlert(Alert.AlertType alertType, String erreur, String champsVides, String s) {
        Alert alert = new Alert(alertType);
        alert.setTitle(erreur);
        alert.setHeaderText(champsVides);
        alert.setContentText(s);
        alert.showAndWait();
    }

    private void close() {
        Stage stage = (Stage) modalAddLabel.getScene().getWindow();
        stage.close();
    }
}

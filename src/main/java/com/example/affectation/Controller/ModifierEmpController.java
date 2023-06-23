package com.example.affectation.Controller;
import com.example.affectation.DAO.EmployeDAO;
import com.example.affectation.DAO.LieuDAO;
import com.example.affectation.Model.Employe;
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

import static com.example.affectation.Controller.AjoutEmpController.showAlert;

public class ModifierEmpController {
    @FXML
    private AnchorPane modalEditLabel;
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

    @FXML
    private ComboBox<String> lieuCombo;

    @FXML
    private ComboBox<String> provinceComboEmploye;

    @FXML
    void cancelUpdating(ActionEvent event) {
        this.close();
    }

    private Employe editEmploye;

    @FXML
    void updateEmploye(ActionEvent event) {
        String nom = addNom.getText();
        String prenoms = addPrenoms.getText();
        String civilite = civiliteCombo.getSelectionModel().getSelectedItem();
        String poste = addPost.getText();
        String province = provinceComboEmploye.getSelectionModel().getSelectedItem();
        String design_lieu = lieuCombo.getSelectionModel().getSelectedItem();
        String lieu = LieuDAO.getOneLieu(design_lieu, province);
        String mail = addMail.getText();
        String num_empl = editEmploye.getNum_empl();
        if (nom.isEmpty() || poste.isEmpty() || province == null || mail.isEmpty() || civilite == null || design_lieu == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Champs vides", "Veuillez remplir tous les champs !");
        }
        else{
            EmployeDAO.updateUser(num_empl, nom, prenoms, poste, civilite, lieu, mail);
            this.close();
        }
    }

    private void close() {
        Stage stage = (Stage) modalEditLabel.getScene().getWindow();
        stage.close();
    }

    public void setEditEmploye(Employe selectedEmploye) {
        this.editEmploye = selectedEmploye;
        addNom.setText(selectedEmploye.getNom_empl());
        addPrenoms.setText(selectedEmploye.getPrenoms_empl());
        addPost.setText(selectedEmploye.getPoste());
        addMail.setText(selectedEmploye.getMail());
        civiliteCombo.getSelectionModel().select(selectedEmploye.getCivilite());
        provinceComboEmploye.getSelectionModel().select(LieuDAO.getProvince(selectedEmploye.getLieu()));
        lieuCombo.getSelectionModel().select(selectedEmploye.getLieu());;
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
}

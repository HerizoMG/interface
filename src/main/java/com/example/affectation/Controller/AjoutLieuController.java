package com.example.affectation.Controller;

import com.example.affectation.DAO.LieuDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static com.example.affectation.Controller.AjoutEmpController.showAlert;

public class AjoutLieuController {

    @FXML
    private TextField designField;

    @FXML
    private ComboBox<String> provinceCombo;

    @FXML
    private AnchorPane modalLieuAddLabel;


    @FXML
    void addLieu(ActionEvent event) {
        String designFieldText = designField.getText();
        String province = provinceCombo.getSelectionModel().getSelectedItem();
        if (LieuDAO.checkLieu(designFieldText, province)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Lieu identique", "Le lieu est déjà présent dans la base !");
            return;
        }
        if (designFieldText.isEmpty() || province == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Champs vides", "Veuillez remplir tous les champs !");
        } else {
            LieuDAO createDAO = new LieuDAO();
            createDAO.createLieu(designFieldText, province);

            designField.setText("");
            provinceCombo.getSelectionModel().select(null);
            this.close();
        }
    }

    @FXML
    void cancelAdding(ActionEvent event) {
        this.close();
    }

    private void close() {
        Stage stage = (Stage) modalLieuAddLabel.getScene().getWindow();
        stage.close();
    }

    public void initialize() {
        ObservableList<String> options = FXCollections.observableArrayList("Antananarivo", "Fianarantsoa", "Antsiranana", "Mahajanga", "Toliara", "Toamasina");
        provinceCombo.getItems().addAll(options);
    }
}

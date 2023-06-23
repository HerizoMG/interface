package com.example.affectation.Controller;

import com.example.affectation.DAO.LieuDAO;
import com.example.affectation.Model.Lieu;
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

public class ModifierLieuController {
    private Lieu editLieu;
    private String tmpIdLieu;
    @FXML
    private AnchorPane modalLieuEditLabel;

    @FXML
    private TextField designField;

    @FXML
    private ComboBox<String> provinceCombo;

    @FXML
    void cancelUpdating(ActionEvent event) {
        this.close();
    }

    @FXML
    void updateLieu(ActionEvent event) {
        String designFieldText = designField.getText();
        String province = provinceCombo.getSelectionModel().getSelectedItem();
        if (designFieldText.isEmpty() || province == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Champs vides", "Veuillez remplir tous les champs !");
        }
        else{
            LieuDAO updateDAO = new LieuDAO();
            updateDAO.updateLieu(tmpIdLieu, designFieldText, province);

            designField.setText("");
            provinceCombo.getSelectionModel().select(null);
            this.close();
        }
    }
    public void setEditLieu(Lieu selectedLieu) {
        this.editLieu = selectedLieu;
        designField.setText(selectedLieu.getDesign());
        provinceCombo.getSelectionModel().select(selectedLieu.getProvince());
        tmpIdLieu = selectedLieu.getId_lieu();
    }

    private void close() {
        Stage stage = (Stage) modalLieuEditLabel.getScene().getWindow();
        stage.close();
    }

    public void initialize() {
        ObservableList<String> options = FXCollections.observableArrayList("Antananarivo", "Fianarantsoa", "Antsiranana", "Mahajanga", "Toliara", "Toamasina");
        provinceCombo.getItems().addAll(options);
    }
}

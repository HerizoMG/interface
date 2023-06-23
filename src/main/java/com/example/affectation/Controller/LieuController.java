package com.example.affectation.Controller;

import com.example.affectation.DAO.LieuDAO;
import com.example.affectation.Model.Employe;
import com.example.affectation.Model.Lieu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.example.affectation.Controller.AjoutEmpController.showAlert;

public class LieuController {

    @FXML
    private Button ajouterLieuButton;

    @FXML
    private Button modifierLieuButton;

    @FXML
    private TableView<Lieu> tableLieu;

    private TableColumn<Lieu, String> idLieuCol = new TableColumn<>("Identifiant");

    private TableColumn<Lieu, String> designCol = new TableColumn<>("Désignation");

    private TableColumn<Lieu, String> provinceCol = new TableColumn<>("Province");

    @FXML
    void deleteLieu(ActionEvent event) {
        Lieu selectedLieu = tableLieu.getSelectionModel().getSelectedItem();
        if (selectedLieu == null) {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Aucun employé sélectionné", "Veuillez sélectionner un employé à supprimer.");
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de suppression");
        confirmationAlert.setHeaderText("Suppression d'un lieu");
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer le lieu sélectionné ?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            LieuDAO lieuDAO = new LieuDAO();
            lieuDAO.deleteLieu(selectedLieu.getId_lieu());
            setTableLieu();
            showAlert(Alert.AlertType.INFORMATION, "Suppression réussie", "Lieu supprimé", "Le lieu a été supprimé avec succès.");
        }
    }
    public void handleAjouterLieuButton(ActionEvent actionEvent) {
        if(actionEvent.getSource() == ajouterLieuButton){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/affectation/fxml/ajoutLieu.fxml"));
                Parent parent = fxmlLoader.load();

                Stage modalStage = new Stage();
                modalStage.initModality(Modality.APPLICATION_MODAL);
                modalStage.setScene(new Scene(parent));
                modalStage.showAndWait();
                initialize();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public void handleModifierLieuButton(ActionEvent actionEvent) {
        Lieu selectedLieu = tableLieu.getSelectionModel().getSelectedItem();
        if (selectedLieu == null) {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Aucun élément sélectionné", "Veuillez sélectionner un élément à éditer.");
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/affectation/fxml/modifierLieu.fxml"));
            Parent parent = fxmlLoader.load();
            ModifierLieuController modifierEmpController = fxmlLoader.getController();
            modifierEmpController.setEditLieu(selectedLieu);

            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setScene(new Scene(parent));
            modalStage.showAndWait();
            initialize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTableLieu() {
        idLieuCol.setCellValueFactory(new PropertyValueFactory<>("id_lieu"));
        designCol.setCellValueFactory(new PropertyValueFactory<>("design"));
        provinceCol.setCellValueFactory(new PropertyValueFactory<>("province"));
        LieuDAO tmpDAO = new LieuDAO();
        List<Lieu> lieux = tmpDAO.all();


        if (tableLieu.getColumns().isEmpty()) {
            tableLieu.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tableLieu.getColumns().addAll(idLieuCol, designCol, provinceCol);
        }

        tableLieu.getItems().setAll(lieux);
    }

    public void initialize() {
        setTableLieu();
    }
}

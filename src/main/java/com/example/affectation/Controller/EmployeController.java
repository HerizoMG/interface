package com.example.affectation.Controller;

import com.example.affectation.DAO.EmployeDAO;
import com.example.affectation.DAO.LieuDAO;
import com.example.affectation.Model.Employe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.affectation.Controller.AjoutEmpController.showAlert;


public class EmployeController {



    @FXML
    private Button affecterEmpl;

    @FXML
    private Button ajouterEmpl;

    @FXML
    private Button editerEmpl;

    @FXML
    private TextField searchField;

    @FXML
    private Button supprimerEmpl;

    @FXML
    private TableView<Employe> tableEmploye;

    private TableColumn<Employe, String> numEmplCol = new TableColumn<>("Numéro");

    private TableColumn<Employe, String> civiliteCol = new TableColumn<>("Civilité");
    private TableColumn<Employe, String> nomCol = new TableColumn<>("Nom");
    private TableColumn<Employe, String> prenomsCol = new TableColumn<>("Prénoms");
    private TableColumn<Employe, String> mailCol = new TableColumn<>("Mail");
    private TableColumn<Employe, String> posteCol = new TableColumn<>("Poste");
    private TableColumn<Employe, String> lieuCol = new TableColumn<>("Lieu");

    private String search = "";
    private boolean boolAffect = false;

    @FXML
    void deleteEmploye(ActionEvent event) {
        Employe selectedEmploye = tableEmploye.getSelectionModel().getSelectedItem();
        if (selectedEmploye == null) {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Aucun employé sélectionné", "Veuillez sélectionner un employé à supprimer.");
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de suppression");
        confirmationAlert.setHeaderText("Suppression de l'employé");
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer l'employé sélectionné ?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            EmployeDAO employeDAO = new EmployeDAO();
            employeDAO.deleteUser(selectedEmploye.getNum_empl());
            setTableEmploye();
            showAlert(Alert.AlertType.INFORMATION, "Suppression réussie", "Employé supprimé", "L'employé a été supprimé avec succès.");
        }
    }

    @FXML
    void nonAffectEmploye(ActionEvent event) {
        this.boolAffect = ! this.boolAffect;
        setTableEmploye();
    }

    public void setTableEmploye() {
        numEmplCol.setCellValueFactory(new PropertyValueFactory<>("num_empl"));
        civiliteCol.setCellValueFactory(new PropertyValueFactory<>("civilite"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom_empl"));
        prenomsCol.setCellValueFactory(new PropertyValueFactory<>("prenoms_empl"));
        mailCol.setCellValueFactory(new PropertyValueFactory<>("mail"));
        posteCol.setCellValueFactory(new PropertyValueFactory<>("poste"));
        lieuCol.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        EmployeDAO tmpDAO = new EmployeDAO();
        List<Employe> employes = new ArrayList<>();;
        if (this.boolAffect) {
            employes = tmpDAO.getAllAffectationNotAffected(search);
        } else {
            employes = tmpDAO.all(search);
        }

        if (tableEmploye.getColumns().isEmpty()) {
            tableEmploye.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tableEmploye.getColumns().addAll(numEmplCol, civiliteCol, nomCol, prenomsCol, mailCol, posteCol, lieuCol);
        }

        tableEmploye.getItems().setAll(employes);
    }

    @FXML
    void handleButtonClick(ActionEvent event) {
        if (event.getSource() == affecterEmpl){
            Employe selectedEmploye = tableEmploye.getSelectionModel().getSelectedItem();
            if (selectedEmploye == null) {
                showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Aucun employé sélectionné", "Veuillez sélectionner un employé à éditer.");
                return;
            }
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/affectation/fxml/ajoutAffect.fxml"));
                Parent parent = fxmlLoader.load();
                AjoutAffectController tmpController = fxmlLoader.getController();

                tmpController.setEmploye(selectedEmploye);

                Stage modalStage = new Stage();
                modalStage.initModality(Modality.APPLICATION_MODAL);
                modalStage.setTitle("Affecter un employé");
                modalStage.setScene(new Scene(parent));
                modalStage.showAndWait();
                initialize();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (event.getSource() == editerEmpl){
            Employe selectedEmploye = tableEmploye.getSelectionModel().getSelectedItem();
            if (selectedEmploye == null) {
                showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Aucun employé sélectionné", "Veuillez sélectionner un employé à éditer.");
                return;
            }
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/affectation/fxml/modifierEmp.fxml"));
                Parent parent = fxmlLoader.load();
                ModifierEmpController modifierEmpController = fxmlLoader.getController();
                modifierEmpController.setEditEmploye(selectedEmploye);

                Stage modalStage = new Stage();
                modalStage.initModality(Modality.APPLICATION_MODAL);
                modalStage.setScene(new Scene(parent));
                modalStage.showAndWait();
                initialize();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (event.getSource() == ajouterEmpl){
            showAsDialog("ajoutEmp");
            initialize();
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

    public  void initialize() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            search = searchField.getText();
            setTableEmploye();
            System.out.println(search);
        });
        setTableEmploye();
    }
}

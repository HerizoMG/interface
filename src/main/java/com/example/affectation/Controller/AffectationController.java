package com.example.affectation.Controller;

import com.aspose.pdf.Document;
import com.aspose.pdf.Page;
import com.aspose.pdf.TextFragment;
import com.example.affectation.Model.Affectation;
import com.example.affectation.Model.Employe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import com.example.affectation.DAO.AffectationDAO;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static com.example.affectation.Controller.AjoutEmpController.showAlert;

public class AffectationController {
    private String search = "";

    @FXML
    private DatePicker dateDebut;

    @FXML
    private DatePicker dateFin;

    @FXML
    private Button modifierButton;

    @FXML
    private TextField searchAffEmploye;

    @FXML
    private TableView<Affectation> tableAffectation;
    private TableColumn<Affectation, String> numAffectCol = new TableColumn<>("N° Affectation");
    private TableColumn<Affectation, String> numEmployeCol = new TableColumn<>("N° Employé");
    private TableColumn<Affectation, String> nomEmployeCol = new TableColumn<>("Nom Employé");
    private TableColumn<Affectation, String> prenomsEmployeCol = new TableColumn<>("Prénoms Employé");
    private TableColumn<Affectation, String> ancienLieuCol = new TableColumn<>("Ancien Lieu");
    private TableColumn<Affectation, String> nouveauLieuCol = new TableColumn<>("Nouveau Lieu");
    private TableColumn<Affectation, Date> dateAffectCol = new TableColumn<>("Date d'affectation");
    private TableColumn<Affectation, Date> datePriseCol = new TableColumn<>("Date de prise de service");

    @FXML
    void generatePDF(ActionEvent event) {
        Affectation selectedAffect = tableAffectation.getSelectionModel().getSelectedItem();
        if (selectedAffect == null) {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Aucun employé sélectionné", "Veuillez sélectionner un employé à éditer.");
            return;
        }
        try {
            Document document = new Document();
            Page page = document.getPages().add();

            String ligne1 = "                                     Arrêté N°"+ selectedAffect.getNum_affect() +" du "+ dateFormatting(selectedAffect.getDate_affect()) +" \n \n \n";
            String ligne2 = "       " + selectedAffect.getCivilite_empl() + " " + selectedAffect.getNom_empl() + " " + selectedAffect.getPrenoms_empl() +
                    " qui occupe le poste de " + selectedAffect.getPoste_empl() +
                    " à " + selectedAffect.getAncien_lieu() +", est affecté à " + selectedAffect.getNouveau_lieu()
                    + " pour compter de la date de prise de service "+ dateFormatting(selectedAffect.getDate_priseservice()) +".";
            String ligne3 = "\n \n \n";
            String ligne4 = "       Le présent communiqué sera enregistré et communiqué partout où besoin sera";

            TextFragment textFragment1 = new TextFragment(ligne1);
            TextFragment textFragment2 = new TextFragment(ligne2);
            TextFragment textFragment3 = new TextFragment(ligne3);
            TextFragment textFragment4 = new TextFragment(ligne4);

            textFragment1.getTextState().setFontSize(14);
            textFragment2.getTextState().setFontSize(13);
            textFragment3.getTextState().setFontSize(13);
            textFragment4.getTextState().setFontSize(12);

            page.getParagraphs().add(textFragment1);
            page.getParagraphs().add(textFragment2);
            page.getParagraphs().add(textFragment3);
            page.getParagraphs().add(textFragment4);

            document.save("C:\\Users\\perform\\Downloads\\" + "Arrêté_N°"+ selectedAffect.getNum_affect() +"_du_"+ dateFormatting(selectedAffect.getDate_affect()) +".pdf");
            File file = new File("C:\\Users\\perform\\Downloads\\" + "Arrêté_N°"+ selectedAffect.getNum_affect() +"_du_"+ dateFormatting(selectedAffect.getDate_affect()) +".pdf");
            Desktop.getDesktop().open(file);

        } catch (Exception e) {
            System.out.println("Erreur lors de la génération du PDF : " + e.getMessage());
        }
    }

    @FXML
    void handleButtonModifier(ActionEvent event) {
        Affectation selectedAff = tableAffectation.getSelectionModel().getSelectedItem();
        if (selectedAff == null) {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Aucun élément selectionné", "Veuillez sélectionner un élément à éditer.");
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/affectation/fxml/modifierAffect.fxml"));
            Parent parent = fxmlLoader.load();
            ModifierAffectController modifierAffController = fxmlLoader.getController();
            modifierAffController.setEditAff(selectedAff);

            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setScene(new Scene(parent));
            modalStage.showAndWait();
            initialize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTableAffectation() {
        numAffectCol.setCellValueFactory(new PropertyValueFactory<>("num_affect"));
        numEmployeCol.setCellValueFactory(new PropertyValueFactory<>("num_empl"));
        nomEmployeCol.setCellValueFactory(new PropertyValueFactory<>("nom_empl"));
        prenomsEmployeCol.setCellValueFactory(new PropertyValueFactory<>("prenoms_empl"));
        ancienLieuCol.setCellValueFactory(new PropertyValueFactory<>("ancien_lieu"));
        nouveauLieuCol.setCellValueFactory(new PropertyValueFactory<>("nouveau_lieu"));
        dateAffectCol.setCellValueFactory(new PropertyValueFactory<>("date_affect"));
        datePriseCol.setCellValueFactory(new PropertyValueFactory<>("date_priseservice"));

        Date deb = null;
        Date fin = null;

        if (dateDebut.getValue() != null && dateFin.getValue() != null) {
            deb = Date.valueOf(dateDebut.getValue());
            fin = Date.valueOf(dateFin.getValue());
        } else if (dateDebut.getValue() != null && dateFin.getValue() == null) {
            deb = Date.valueOf(dateDebut.getValue());
        } else if (dateDebut.getValue() == null && dateFin.getValue() != null) {
            fin = Date.valueOf(dateFin.getValue());
        }

        List<Affectation> affects = AffectationDAO.all(search, deb, fin);

        if (tableAffectation != null) {
            if (tableAffectation.getColumns().isEmpty()) {
                tableAffectation.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                tableAffectation.getColumns().addAll(numAffectCol, numEmployeCol, nomEmployeCol, prenomsEmployeCol, ancienLieuCol, nouveauLieuCol,dateAffectCol, datePriseCol);
            }

            tableAffectation.getItems().setAll(affects);
        }
    }

    public void initialize(){
        searchAffEmploye.textProperty().addListener((observable, oldValue, newValue) -> {
            search = searchAffEmploye.getText();
            setTableAffectation();
            System.out.println(search);
        });
        setTableAffectation();
    }

    @FXML
    void changeDebDate(ActionEvent event) {
        setTableAffectation();
    }

    @FXML
    void changeFinDate(ActionEvent event) {
        setTableAffectation();
    }

    public void deleteAffectation(ActionEvent event) {
        Affectation selectedAffect = tableAffectation.getSelectionModel().getSelectedItem();
        if (selectedAffect == null) {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Aucun employé sélectionné", "Veuillez sélectionner un employé à éditer.");
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de suppression");
        confirmationAlert.setHeaderText("Suppression d'une affectation");
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer l'affectation sélectionnée ?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            AffectationDAO.deleteAffectation(selectedAffect.getNum_affect());
            setTableAffectation();
            showAlert(Alert.AlertType.INFORMATION, "Suppression réussie", "Affectation supprimée", "L'affectation a été supprimé avec succès.");
        }
    }

    private String dateFormatting(Date inputDate) {
        LocalDate date = LocalDate.parse(inputDate.toString());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String formattedDate = date.format(formatter);

        return formattedDate;
    }
}
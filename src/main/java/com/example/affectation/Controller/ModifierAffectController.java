package com.example.affectation.Controller;

import com.example.affectation.DAO.AffectationDAO;
import com.example.affectation.DAO.EmployeDAO;
import com.example.affectation.DAO.LieuDAO;
import com.example.affectation.Model.Affectation;

import com.example.affectation.Util.MailSender;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.affectation.Controller.AjoutEmpController.showAlert;

public class ModifierAffectController {
    private Affectation editAffect;

    @FXML
    private AnchorPane modalEditLabel;

    @FXML
    private TextField ancien_lieu;

    @FXML
    private DatePicker date_affectAff;

    @FXML
    private DatePicker date_serviceAff;

    @FXML
    private ComboBox<String> new_lieu;

    @FXML
    private ComboBox<String> new_prov;

    private String tmpNumAffect;

    @FXML
    private TextField num_emplAff;
    private String num_emplAffTmpUpdate;

    public void setEditAff(Affectation selectedAffect) {
        this.editAffect = selectedAffect;
        this.num_emplAffTmpUpdate = selectedAffect.getNum_empl();
        num_emplAff.setText(selectedAffect.getNum_empl());
        ancien_lieu.setText(selectedAffect.getAncien_lieu());
        new_prov.getSelectionModel().select(LieuDAO.getProvince(selectedAffect.getNouveau_lieu()));
        new_lieu.getSelectionModel().select(LieuDAO.findLieu(selectedAffect.getNouveau_lieu()));
        date_affectAff.setValue(selectedAffect.getDate_affect().toLocalDate());
        date_serviceAff.setValue(selectedAffect.getDate_priseservice().toLocalDate());
        this.tmpNumAffect = selectedAffect.getNum_affect();
    }

    @FXML
    void cancelUpdating(ActionEvent event) {
        this.close();
    }

    @FXML
    void updateAffectation(ActionEvent event) {
        LocalDate selectedDateAffect = date_affectAff.getValue();
        LocalDate selectedDateService = date_serviceAff.getValue();

        Date date_aff;
        Date date_p;

        String province_nouveau_lieu = new_prov.getSelectionModel().getSelectedItem();
        String design_nouveau_lieu = new_lieu.getSelectionModel().getSelectedItem();

        if (selectedDateAffect == null || selectedDateService == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Date invalide", "Veuillez sélectionner une date !");
            return;
        } else {
            date_aff = Date.valueOf(date_affectAff.getValue());
            date_p = Date.valueOf(date_serviceAff.getValue());
        }


        if (province_nouveau_lieu == null || design_nouveau_lieu == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Champs vides", "Veuillez remplir tous les champs !");
            return;
        }

        AffectationDAO.updateAffectation(tmpNumAffect, num_emplAff.getText(), ancien_lieu.getText(), LieuDAO.getOneLieu(design_nouveau_lieu, province_nouveau_lieu), date_aff, date_p);
        EmployeDAO.updateUserAff(num_emplAffTmpUpdate, LieuDAO.getOneLieu(design_nouveau_lieu, province_nouveau_lieu));
        String message = "        L'affectation de " + editAffect.getNom_empl() + " " + editAffect.getPrenoms_empl() +
                " est modifié à" + LieuDAO.getLieuDesignations(LieuDAO.getOneLieu(design_nouveau_lieu, province_nouveau_lieu)) + " " + LieuDAO.getProvince(LieuDAO.getOneLieu(design_nouveau_lieu, province_nouveau_lieu))
                + " pour compter de la date de prise de service "+ dateFormatting(date_p) +".";
        MailSender.EnvoyeMail(editAffect.getMail_empl(), message);
        this.close();
        System.out.println("confirmUpdateAff");
    }

    private String dateFormatting(Date dateP) {
        LocalDate date = LocalDate.parse(dateP.toString());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String formattedDate = date.format(formatter);

        return formattedDate;
    }

    public void loadLieuAff(MouseEvent event) {
        List<String> designations = LieuDAO.getLieuDesignations(new_prov.getSelectionModel().getSelectedItem());
        System.out.println(designations);
        new_lieu.getItems().clear();
        new_lieu.getItems().addAll(designations);
    }

    public void initialize() {
        ObservableList<String> options = FXCollections.observableArrayList("Antananarivo", "Fianarantsoa", "Antsiranana", "Mahajanga", "Toliara", "Toamasina");

        new_prov.getItems().addAll(options);
        new_prov.valueProperty().addListener((observable, oldValue, newValue) -> {
            loadLieuAff(null);
        });
    }

    private void close() {
        Stage stage = (Stage) modalEditLabel.getScene().getWindow();
        stage.close();
    }
}

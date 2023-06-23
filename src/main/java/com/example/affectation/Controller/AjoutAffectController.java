package com.example.affectation.Controller;

import com.example.affectation.DAO.AffectationDAO;
import com.example.affectation.DAO.EmployeDAO;
import com.example.affectation.DAO.LieuDAO;
import com.example.affectation.Model.Employe;
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
import java.util.Objects;

import static com.example.affectation.Controller.AjoutEmpController.showAlert;

public class AjoutAffectController {
    @FXML
    private AnchorPane modalAffectLabel;
    @FXML
    private DatePicker date_affect;

    @FXML
    private DatePicker date_service;

    @FXML
    private TextField lieu_empl;

    @FXML
    private ComboBox<String> new_lieu;

    @FXML
    private ComboBox<String> new_prov;

    @FXML
    private TextField nom_empl;

    @FXML
    private TextField num_empl;

    @FXML
    private TextField posteField;

    @FXML
    private TextField prenoms_empl;

    private Employe affect_empl;


    @FXML
    void addAffect(ActionEvent event) {
        String num_emp = num_empl.getText();
        String ancien_lieu = lieu_empl.getText();
        String province_nouveau_lieu = new_prov.getSelectionModel().getSelectedItem();
        String design_nouveau_lieu = new_lieu.getSelectionModel().getSelectedItem();
        String nouveau_lieu = LieuDAO.getOneLieu(design_nouveau_lieu, province_nouveau_lieu);
        String nom = nom_empl.getText();
        String prenoms = prenoms_empl.getText();
        String mail = affect_empl.getMail();
        String poste = posteField.getText();
        String civilite = affect_empl.getCivilite();

        LocalDate selectedDateAffect = date_affect.getValue();
        LocalDate selectedDateService = date_service.getValue();

        Date date_aff;
        Date date_p;
        System.out.println(affect_empl.getId_lieu() + " == " + nouveau_lieu);

        if (Objects.equals(affect_empl.getId_lieu(), nouveau_lieu)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Répétition de lieu", "L'employé travaille déjà sur le lieu !");
            return;
        }

        if (selectedDateAffect == null || selectedDateService == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Date invalide", "Veuillez sélectionner une date !");
            return;
        } else {
            date_aff = Date.valueOf(date_affect.getValue());
            date_p = Date.valueOf(date_service.getValue());
        }


        if (nom.isEmpty() || poste.isEmpty() || province_nouveau_lieu == null || mail.isEmpty() || civilite.isEmpty() || design_nouveau_lieu == null || stringifyDate(date_aff).isEmpty() || stringifyDate(date_p).isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Champs vides", "Veuillez remplir tous les champs !");
            return;
        }

        AffectationDAO.createAffectation(num_emp, ancien_lieu, nouveau_lieu, date_aff, date_p);
        EmployeDAO.updateUser(num_emp, nom, prenoms, poste, civilite, nouveau_lieu, mail);

        String message = "       " + affect_empl.getCivilite() + " " + affect_empl.getNom_empl() + " " + affect_empl.getPrenoms_empl() +
                " qui occupe le poste de " + affect_empl.getPoste() +
                " à " + ancien_lieu +", est affecté à " + LieuDAO.getLieuDesignations(nouveau_lieu) + " " + LieuDAO.getProvince(nouveau_lieu)
                + " pour compter de la date de prise de service "+ dateFormatting(date_p) +".";
        MailSender.EnvoyeMail(affect_empl.getMail(), message);
        this.close();
    }

    private String dateFormatting(Date dateP) {
        LocalDate date = LocalDate.parse(dateP.toString());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String formattedDate = date.format(formatter);

        return formattedDate;
    }

    private String stringifyDate(Date date) {
        return date.toString();
    }

    @FXML
    void cancelAffect(ActionEvent event) {
        this.close();
    }

    public void setEmploye(Employe selectedEmploye) {
        this.affect_empl = selectedEmploye;
        num_empl.setText(selectedEmploye.getNum_empl());
        nom_empl.setText(selectedEmploye.getNom_empl());
        prenoms_empl.setText(selectedEmploye.getPrenoms_empl());
        lieu_empl.setText(selectedEmploye.getLieu());
        posteField.setText(selectedEmploye.getPoste());
        ObservableList<String> options = FXCollections.observableArrayList("Fianarantsoa", "Antananarivo", "Antsiranana", "Mahajanga", "Toliara", "Toamasina");
        new_prov.getItems().addAll(options);

        new_prov.valueProperty().addListener((observable, oldValue, newValue) -> {
            loadLieu(null);
        });
    }

    public void loadLieu(MouseEvent event) {
        List<String> designations = LieuDAO.getLieuDesignations(new_prov.getSelectionModel().getSelectedItem());
        System.out.println(designations);
        new_lieu.getItems().clear();
        new_lieu.getItems().addAll(designations);
    }

    private void close() {
        Stage stage = (Stage) modalAffectLabel.getScene().getWindow();
        stage.close();
    }
}

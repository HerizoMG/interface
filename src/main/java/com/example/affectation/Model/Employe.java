package com.example.affectation.Model;

public class Employe {

    private String  num_empl;
    private String  civilite;
    private String  nom;
    private String  prenoms;
    private String  mail;
    private String  poste;
    private String  lieu;

    private String id_lieu;


    public String getNum_empl() {
        return num_empl;
    }

    public void setNum_empl(String num_empl) {
        this.num_empl = num_empl;
    }

    public String getId_lieu() {
        return id_lieu;
    }

    public void setId_lieu(String id_lieu) {
        this.id_lieu = id_lieu;
    }

    public String getCivilite() {
        return civilite;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    public String getNom_empl() {
        return nom;
    }

    public void setNom_empl(String nom) {
        this.nom = nom;
    }

    public String getPrenoms_empl() {
        return prenoms;
    }

    public void setPrenoms_empl(String prenoms) {
        this.prenoms = prenoms;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

}

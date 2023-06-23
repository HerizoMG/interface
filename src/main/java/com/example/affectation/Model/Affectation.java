package com.example.affectation.Model;

import java.util.Date;

public class Affectation {
    private String  num_affect;

    private String civilite_empl;
    private String  num_empl;
    private String nom_empl;
    private String prenoms_empl;
    private String poste_empl;
    private String mail_empl;
    private String  ancien_lieu;
    private String  nouveau_lieu;
    private java.sql.Date date_affect;
    private java.sql.Date date_priseservice;


    public String getNum_affect() {
        return num_affect;
    }

    public void setNum_affect(String num_affect) {
        this.num_affect = num_affect;
    }

    public String getCivilite_empl() {return civilite_empl;}

    public void setCivilite_empl(String civilite) {this.civilite_empl = civilite;}

    public String getNom_empl() {return nom_empl;}
    public void setNom_empl(String nom) {this.nom_empl = nom;}
    public String getPrenoms_empl() {return prenoms_empl;}
    public void setPrenoms_empl(String prenoms) {this.prenoms_empl = prenoms;}

    public String getPoste_empl() {return poste_empl;}

    public void setPoste_empl(String poste) {this.poste_empl = poste;}

    public String getMail_empl() {return mail_empl;}

    public void setMail_empl(String mail) {this.mail_empl = mail;}

    public String getNum_empl() {
        return num_empl;
    }

    public void setNum_empl(String num_empl) {
        this.num_empl = num_empl;
    }

    public String getAncien_lieu() {
        return ancien_lieu;
    }

    public void setAncien_lieu(String ancien_lieu) {
        this.ancien_lieu = ancien_lieu;
    }

    public String getNouveau_lieu() {
        return nouveau_lieu;
    }

    public void setNouveau_lieu(String nouveau_lieu) {
        this.nouveau_lieu = nouveau_lieu;
    }

    public java.sql.Date getDate_affect() {
        return date_affect;
    }

    public void setDate_affect(java.sql.Date date_affect) {
        this.date_affect = date_affect;
    }

    public java.sql.Date getDate_priseservice() {
        return date_priseservice;
    }

    public void setDate_priseservice(java.sql.Date date_priseservice) {
        this.date_priseservice = date_priseservice;
    }
}

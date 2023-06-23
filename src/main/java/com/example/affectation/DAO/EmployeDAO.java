package com.example.affectation.DAO;


import com.example.affectation.Model.Employe;
import com.example.affectation.Util.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class EmployeDAO extends Database {
    public static void updateUserAff(String numEmplAffTmpUpdate, String oneLieu) {
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE employe SET lieu = ? WHERE num_empl = ?");
            stmt.setString(1, oneLieu);
            stmt.setString(2, numEmplAffTmpUpdate);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Employe> all(String search) {
        ResultSet employeRES = null;
        List<Employe> employes = new ArrayList<>();
        try{
            PreparedStatement stmt;
            if (search == null || search.isEmpty())
                stmt = conn.prepareStatement("SELECT * FROM employe LEFT OUTER JOIN lieu ON employe.lieu = lieu.id_lieu ORDER BY num_empl");
            else {
                stmt = conn.prepareStatement("SELECT * FROM employe LEFT OUTER JOIN lieu ON employe.lieu = lieu.id_lieu WHERE LOWER(employe.nom) LIKE ? OR LOWER(employe.prenoms) LIKE ?  ORDER BY num_empl");
                stmt.setString(1, "%" + search.toLowerCase() + "%");
                stmt.setString(2, "%" + search.toLowerCase() + "%");
            }
            employeRES = stmt.executeQuery();
            while (employeRES.next()) {
                Employe tmpEmp = new Employe();
                tmpEmp.setNum_empl(employeRES.getString("num_empl"));
                tmpEmp.setCivilite(employeRES.getString("civilite"));
                tmpEmp.setNom_empl(employeRES.getString("nom"));
                tmpEmp.setPrenoms_empl(employeRES.getString("prenoms"));
                tmpEmp.setMail(employeRES.getString("mail"));
                tmpEmp.setPoste(employeRES.getString("poste"));
                tmpEmp.setLieu(employeRES.getString("design"));

                employes.add(tmpEmp);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return employes;
    }

    public String getNumero() throws SQLException {
        ResultSet numeroRES = null;
        PreparedStatement stmt = conn.prepareStatement("SELECT num_empl FROM employe ORDER BY num_empl DESC LIMIT 1");
        numeroRES = stmt.executeQuery();
        String lastNum = null;
        while (numeroRES.next()) {
            lastNum = numeroRES.getString("num_empl");
        }
        String formattedCode;
        if (lastNum == null) {
            formattedCode = "E001";
        } else {
            int number = Integer.parseInt(lastNum.substring(1));
            number++;
            formattedCode = String.format("E%03d", number);
        }
        return formattedCode;
    }

    public void createEmploye(String nom, String prenom, String poste, String civilite, String lieu, String mail) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO employe(num_empl,nom,prenoms,poste,civilite,lieu,mail) VALUES (?,?,?,?,?,?,?)");
            stmt.setString(1, getNumero());
            stmt.setString(2, nom);
            stmt.setString(3, prenom);
            stmt.setString(4, poste);
            stmt.setString(5, civilite);
            stmt.setString(6, lieu);
            stmt.setString(7, mail);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion : " + e.getMessage());
        }
    }


    public static void updateUser(String numempl, String nom, String prenoms, String poste, String civilite, String lieu, String mail) {
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE employe SET nom = ?, prenoms = ? , poste = ? , civilite = ? , lieu = ? , mail = ? WHERE num_empl = ?");
            stmt.setString(7, numempl);
            stmt.setString(1, nom);
            stmt.setString(2, prenoms);
            stmt.setString(3, poste);
            stmt.setString(4, civilite);
            stmt.setString(5, lieu);
            stmt.setString(6, mail);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String numempl) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM employe  WHERE num_empl = ?");
            stmt.setString(1, numempl);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Employe> getAllAffectationNotAffected(String search) {
        ResultSet employeRES = null;
        List<Employe> employes = new ArrayList<>();
        try{
            PreparedStatement stmt;
            if (search == null || search.isEmpty())
                stmt = conn.prepareStatement("SELECT * FROM  employe LEFT OUTER JOIN affectation ON affectation.num_empl = employe.num_empl JOIN lieu ON lieu.id_lieu = employe.lieu WHERE affectation.num_affect IS NULL");
            else {
                stmt = conn.prepareStatement("SELECT * FROM employe\n" +
                        "LEFT OUTER JOIN affectation ON affectation.num_empl = employe.num_empl\n" +
                        "JOIN lieu ON lieu.id_lieu = employe.lieu\n" +
                        "WHERE affectation.num_affect IS NULL\n" +
                        "AND (LOWER(employe.nom) LIKE ? OR LOWER(employe.prenoms) LIKE ?)\n");
                stmt.setString(1, "%" + search.toLowerCase() + "%");
                stmt.setString(2, "%" + search.toLowerCase() + "%");
            }
            employeRES = stmt.executeQuery();
            while (employeRES.next()) {
                Employe tmpEmp = new Employe();
                tmpEmp.setNum_empl(employeRES.getString("num_empl"));
                tmpEmp.setCivilite(employeRES.getString("civilite"));
                tmpEmp.setNom_empl(employeRES.getString("nom"));
                tmpEmp.setPrenoms_empl(employeRES.getString("prenoms"));
                tmpEmp.setMail(employeRES.getString("mail"));
                tmpEmp.setPoste(employeRES.getString("poste"));
                tmpEmp.setLieu(employeRES.getString("design"));

                employes.add(tmpEmp);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return employes;
    }
}


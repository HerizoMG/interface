package com.example.affectation.DAO;

import com.example.affectation.Model.Lieu;
import com.example.affectation.Util.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LieuDAO extends Database {
    public List<Lieu> all() {
        ResultSet lieuRES = null;
        List<Lieu> lieux = new ArrayList<>();
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM lieu ORDER BY id_lieu");
            lieuRES = stmt.executeQuery();
            while (lieuRES.next()) {
                Lieu tmpEmp = new Lieu();
                tmpEmp.setId_lieu(lieuRES.getString("id_lieu"));
                tmpEmp.setDesign(lieuRES.getString("design"));
                tmpEmp.setProvince(lieuRES.getString("province"));


                lieux.add(tmpEmp);
            }
        } catch (SQLException e) {
            System.out.println("error");
        }
        return lieux;
    }

    public String getNumero() throws SQLException {
        ResultSet numeroRES = null;
        PreparedStatement stmt = conn.prepareStatement("SELECT id_lieu FROM lieu ORDER BY id_lieu DESC LIMIT 1");
        numeroRES = stmt.executeQuery();
        String lastNum = null;
        while (numeroRES.next()) {
            lastNum = numeroRES.getString("id_lieu");
        }
        String formattedCode;
        if (lastNum == null) {
            formattedCode = "L001";
        } else {
            int number = Integer.parseInt(lastNum.substring(1));
            number++;
            formattedCode = String.format("L%03d", number);
        }
        return formattedCode;
    }

    public void createLieu(String design, String province) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO lieu(id_lieu, design, province) VALUES (?, ?, ?)");
            stmt.setString(1, getNumero());
            stmt.setString(2, design);
            stmt.setString(3, province);;
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion : " + e.getMessage());
        }
    }

    public void updateLieu(String id_lieu, String design, String province) {
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE lieu SET design = ?, province = ? WHERE id_lieu = ?");
            stmt.setString(1, design);
            stmt.setString(2, province);
            stmt.setString(3, id_lieu);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteLieu(String id_lieu) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM lieu  WHERE id_lieu = ?");
            stmt.setString(1, id_lieu);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getOneLieu(String design, String province) {
        ResultSet oneLieu = null;
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM lieu WHERE design = ? AND province = ?");
            stmt.setString(1, design);
            stmt.setString(2, province);
            oneLieu = stmt.executeQuery();
            String lieuSelected = null;
            while (oneLieu.next()) {
                Lieu tmpEmp = new Lieu();
                tmpEmp.setId_lieu(oneLieu.getString("id_lieu"));
                tmpEmp.setDesign(oneLieu.getString("design"));
                tmpEmp.setProvince(oneLieu.getString("province"));

                lieuSelected = tmpEmp.getId_lieu();
            }

            return  lieuSelected;

        } catch (SQLException e) {
            System.out.println("error");
        }
        return null;
    }

    public static String findLieu(String id_lieu) {
        ResultSet oneLieu = null;
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM lieu WHERE id_lieu = ?");
            stmt.setString(1, id_lieu);
            oneLieu = stmt.executeQuery();
            String lieuSelected = null;
            while (oneLieu.next()) {
                Lieu tmpEmp = new Lieu();
                tmpEmp.setId_lieu(oneLieu.getString("id_lieu"));
                tmpEmp.setDesign(oneLieu.getString("design"));
                tmpEmp.setProvince(oneLieu.getString("province"));

                lieuSelected = tmpEmp.getDesign();
            }

            return  lieuSelected;

        } catch (SQLException e) {
            System.out.println("error");
        }
        return null;
    }

    public static List<String> getLieuDesignations(String province) {
        List<String> resultat = new ArrayList<>(); // Initialize the list
        ResultSet lieu = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM lieu WHERE province = ?");
            stmt.setString(1, province);
            lieu = stmt.executeQuery();
            while (lieu.next()) {
                String tmp = lieu.getString("design");
                resultat.add(tmp);
            }
            return resultat;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public static String getProvince(String id_lieu) {
        ResultSet oneLieu = null;
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM lieu WHERE id_lieu = ?");
            stmt.setString(1, id_lieu);
            oneLieu = stmt.executeQuery();
            String lieuSelected = null;
            while (oneLieu.next()) {
                Lieu tmpEmp = new Lieu();
                tmpEmp.setId_lieu(oneLieu.getString("id_lieu"));
                tmpEmp.setDesign(oneLieu.getString("design"));
                tmpEmp.setProvince(oneLieu.getString("province"));

                lieuSelected = tmpEmp.getProvince();
            }

            return  lieuSelected;

        } catch (SQLException e) {
            System.out.println("error");
        }
        return null;
    }

    public static boolean checkLieu(String designation, String province) {
        ResultSet checkedLieu = null;
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM lieu WHERE design = ? AND province = ?");
            stmt.setString(1, designation);
            stmt.setString(2, province);
            checkedLieu = stmt.executeQuery();
            Lieu lieuSelected = null;
            while (checkedLieu.next()) {
                Lieu tmpEmp = new Lieu();
                tmpEmp.setId_lieu(checkedLieu.getString("id_lieu"));
                tmpEmp.setDesign(checkedLieu.getString("design"));
                tmpEmp.setProvince(checkedLieu.getString("province"));

                lieuSelected = tmpEmp;
                return true;
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        System.out.println("false");
        return false;
    }
}

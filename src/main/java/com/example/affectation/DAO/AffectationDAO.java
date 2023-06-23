package com.example.affectation.DAO;

import com.example.affectation.Model.Affectation;
import com.example.affectation.Util.Database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AffectationDAO extends Database {

    public static List<Affectation> all(String search, Date dateDebut, Date dateFin) {
        List<Affectation> affects = new ArrayList<>();
        String dateQuery = "";
        if (dateDebut != null && dateFin != null) {
            dateQuery = " AND affectation.date_affect >= ? AND affectation.date_affect <= ?";
        } else if (dateDebut != null) {
            dateQuery = " AND affectation.date_affect >= ?";
        } else if (dateFin != null) {
            dateQuery = " AND affectation.date_affect <= ?";
        }

        String sqlQuery = "SELECT * FROM affectation " +
                "LEFT JOIN lieu ON affectation.nouveau_lieu = lieu.id_lieu " +
                "LEFT JOIN employe ON employe.num_empl = affectation.num_empl " +
                "WHERE (LOWER(employe.nom) LIKE ? OR LOWER(employe.prenoms) LIKE ? OR LOWER(employe.num_empl) LIKE ?)" + dateQuery + " ORDER BY num_affect";

        try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            int parameterIndex = 1;
            stmt.setString(parameterIndex++, "%" + search.toLowerCase() + "%");
            stmt.setString(parameterIndex++, "%" + search.toLowerCase() + "%");
            stmt.setString(parameterIndex++, "%" + search.toLowerCase() + "%");

            if (dateDebut != null && dateFin != null) {
                stmt.setDate(parameterIndex++, dateDebut);
                stmt.setDate(parameterIndex, dateFin);
            } else if (dateDebut != null) {
                stmt.setDate(parameterIndex, dateDebut);
            } else if (dateFin != null) {
                stmt.setDate(parameterIndex, dateFin);
            }

            try (ResultSet affectRES = stmt.executeQuery()) {
                while (affectRES.next()) {
                    Affectation tmpAffect = new Affectation();
                    tmpAffect.setNum_affect(affectRES.getString("num_affect"));
                    tmpAffect.setNum_empl(affectRES.getString("num_empl"));
                    tmpAffect.setCivilite_empl(affectRES.getString("civilite"));
                    tmpAffect.setNom_empl(affectRES.getString("nom"));
                    tmpAffect.setPrenoms_empl(affectRES.getString("prenoms"));
                    tmpAffect.setPoste_empl(affectRES.getString("poste"));
                    tmpAffect.setMail_empl(affectRES.getString("mail"));
                    tmpAffect.setAncien_lieu(affectRES.getString("ancien_lieu"));
                    tmpAffect.setNouveau_lieu(affectRES.getString("design") + " " + affectRES.getString("province"));
                    tmpAffect.setDate_affect(affectRES.getDate("date_affect"));
                    tmpAffect.setDate_priseservice(affectRES.getDate("date_priseservice"));

                    affects.add(tmpAffect);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return affects;
    }

    private static String getNumero() throws SQLException {
        ResultSet numeroRES = null;
        PreparedStatement stmt = conn.prepareStatement("SELECT num_affect FROM affectation ORDER BY num_affect DESC LIMIT 1");
        numeroRES = stmt.executeQuery();
        String lastNum = null;
        while (numeroRES.next()) {
            lastNum = numeroRES.getString("num_affect");
        }
        String formattedCode;
        if (lastNum == null) {
            formattedCode = "A001";
        } else {
            int number = Integer.parseInt(lastNum.substring(1));
            number++;
            formattedCode = String.format("A%03d", number);
        }
        return formattedCode;
    }

    public static void createAffectation(String num_empl, String ancien_lieu, String nouveau_lieu, Date date_affect, Date date_priseservice) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO affectation(num_affect, num_empl, ancien_lieu, nouveau_lieu, date_affect, date_priseservice) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, getNumero());
            stmt.setString(2, num_empl);
            stmt.setString(3, ancien_lieu);
            stmt.setString(4, nouveau_lieu);
            stmt.setDate(5, date_affect);
            stmt.setDate(6, date_priseservice);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion : " + e.getMessage());
        }
    }

    public static void updateAffectation(String num_affect, String num_empl, String ancien_lieu, String nouveau_lieu, Date date_affect, Date date_priseservice) {
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE affectation SET num_empl = ?, ancien_lieu = ?, nouveau_lieu = ?, date_affect = ?, date_priseservice = ? WHERE num_affect = ?");
            stmt.setString(1, num_empl);
            stmt.setString(2, ancien_lieu);
            stmt.setString(3, nouveau_lieu);
            stmt.setDate(4, date_affect);
            stmt.setDate(5, date_priseservice);
            stmt.setString(6, num_affect);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAffectation(String num_affect) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM affectation  WHERE num_affect = ?");
            stmt.setString(1, num_affect);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package services;

import entities.formation;
import utils.MyDatabase;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.*;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class servicesformation implements IService<formation> {
    Connection connection;
    private boolean successMessageShown = false;

    public servicesformation() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void add(formation formation) throws SQLException {
        if (!isNomFormationUnique(formation.getNom())) {
            System.out.println("Erreur : Le nom de la formation existe déjà. Formation non ajoutée.");
            throw new SQLIntegrityConstraintViolationException("Le nom de la formation doit être unique.");
        }

        String req = "INSERT INTO formation (nom, date) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, formation.getNom());
            ps.setString(2, formation.getDate());
            ps.executeUpdate();
            System.out.println("Formation ajoutée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Erreur lors de l'ajout de la formation : " + e.getMessage());
        }
    }

    @Override
    public void update(formation formation) throws SQLException {
        // Reset successMessageShown flag for a new modification operation
        successMessageShown = false;

        if (!isNomFormationUnique(formation.getNom(), formation.getId())) {
            displayErrorMessage("Le nouveau nom de la formation existe déjà. Modification annulée.");
            return;
        }

        String req = "UPDATE formation SET nom=?, date=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, formation.getNom());
            ps.setString(2, formation.getDate());
            ps.setInt(3, formation.getId());
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Formation modifiée avec succès");
                // Display a success message only if it hasn't been shown before
                if (!successMessageShown) {
                    displaySuccessMessage("Modification effectuée avec succès");
                    successMessageShown = true;
                }
            } else {
                displayErrorMessage("Erreur lors de la modification de la formation. Aucune modification effectuée.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            // Do not throw a RuntimeException here, as we want to display an error message to the user
            displayErrorMessage("Erreur lors de la modification de la formation. Veuillez réessayer.");
        }
    }

    private void displayErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }

    private void displaySuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }

    private boolean isNomFormationUnique(String nom) throws SQLException {
        String req = "SELECT COUNT(*) FROM formation WHERE nom=?";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, nom);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) == 0;
        }
    }

    private boolean isNomFormationUnique(String nom, int id) throws SQLException {
        String req = "SELECT COUNT(*) FROM formation WHERE nom=? AND id <> ?";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, nom);
            ps.setInt(2, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) == 0;
        }
    }

    @Override
    public void delete(formation formation) throws SQLException {
        try {
            String req = "DELETE FROM formation WHERE id=?";
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, formation.getId());
            pst.executeUpdate();
            System.out.println("Suppression avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public List<String> getAllFormationNames() throws SQLException {
        List<String> formationNames = new ArrayList<>();
        String req = "SELECT nom FROM formation";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                formationNames.add(rs.getString("nom"));
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e.getMessage());
            e.printStackTrace();
        }
        return formationNames;
    }



    @Override
    public List<formation> readAll() throws SQLException {
        List<formation> formations = new ArrayList<>();
        String req = "SELECT * FROM formation";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                formation f = new formation();
                f.setId(rs.getInt(1));
                f.setNom(rs.getString("nom"));
                f.setDate(rs.getString(3));
                formations.add(f);
            }
        }
        return formations;
    }

    @Override
    public formation readById(int id) throws SQLException {
        return null;
    }
}

package services;

import entities.event;
import entities.reservation;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import javafx.scene.control.Alert;

public class servicesevent implements IService<event> {

    Connection connection;

    public servicesevent() {
        connection = MyDatabase.getInstance().getConnection();
    }

    public boolean existeEvent(String nom, Date dateDebut, Date dateFin) throws SQLException {
        String query = "SELECT * FROM event WHERE nom_evenement = ? AND date_debut = ? AND date_fin = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nom);
            preparedStatement.setDate(2, dateDebut);
            preparedStatement.setDate(3, dateFin);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Si un enregistrement est trouvé, cela signifie qu'un événement avec les mêmes données existe déjà
            }
        }
    }

    @Override
    public void add(event event) throws SQLException {
        if (existeEvent(event.getNom_evenement(), event.getDate_debut(), event.getDate_fin())) {
            showAlert(Alert.AlertType.ERROR, "Erreur d'ajout", "Un événement avec les mêmes données existe déjà.");
            return;
        }
        if (event.getNom_evenement() == null || event.getNom_evenement().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur d'ajout", "Le nom de l'événement ne peut pas être vide.");
            return;
        }
        String req = "INSERT INTO event (nom_evenement, date_debut , date_fin ) VALUES (?, ?,?)";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, event.getNom_evenement());
            ps.setDate(2, event.getDate_debut());
            ps.setDate(3, event.getDate_fin());
            ps.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Événement ajouté avec succès.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur d'ajout", "Erreur lors de l'ajout de l'événement : " + e.getMessage());
        }
    }

    @Override
    public void delete(event ev) throws SQLException {
        try {
            String req = "DELETE FROM event WHERE id=?";
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, ev.getId());
            pst.executeUpdate();
            System.out.println("Suppression avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void update(event ev) throws SQLException {
        String req = "UPDATE event SET nom_evenement=?, date_debut=?  , date_fin =? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, ev.getNom_evenement());
            ps.setDate(2, ev.getDate_debut());
            ps.setDate(3, ev.getDate_fin());
            ps.setInt(4, ev.getId());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Événement modifié avec succès.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de modification", "Erreur lors de la modification de l'événement : " + e.getMessage());

        }
    }

    @Override
    public List<event> readAll() throws SQLException {
        List<event> events = new ArrayList<>();
        String req = "SELECT * FROM event";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                int id = rs.getInt("ID");
                String nom_evenement = rs.getString("nom_evenement");
                Date date_debut = rs.getDate("date_debut");
                Date date_fin = rs.getDate("date_fin");

                event e = new event(nom_evenement, date_debut, date_fin);
                e.setId(id);

                events.add(e);
            }
        }
        return events;
    }

    @Override
    public event readById(int id) throws SQLException {
        return null;
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
